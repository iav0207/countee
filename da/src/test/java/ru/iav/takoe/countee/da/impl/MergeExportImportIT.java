package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Joiner;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.crypt.impl.SimpleGostCryptFacade;
import ru.iav.takoe.countee.da.Cache;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.FileFactory;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import static org.apache.commons.lang3.StringUtils.LF;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.iav.takoe.countee.test.CounteeTestUtils.createFile;
import static ru.iav.takoe.countee.test.CounteeTestUtils.getSetOfRandomCosts;
import static ru.iav.takoe.countee.utils.DateUtils.day;

@ParametersAreNonnullByDefault
public class MergeExportImportIT {

    private static final String password = "i-test-it";

    @Mock(answer = Answers.CALLS_REAL_METHODS) private LocalWriter writer;
    @Mock(answer = Answers.CALLS_REAL_METHODS) private LocalReader reader;
    @Mock(answer = Answers.CALLS_REAL_METHODS) private SimpleGostCryptFacade cryptFacade;

    @Spy
    private CostFileNamesFactory fileNamesFactory = new CostFileNamesFactory(new FileFactory());

    @Mock private CostReader costReader;
    @Mock private Cache costsCache;
    @Mock private JsonParser jsonParser;
    @Mock private JsonConverter jsonConverter;

    @InjectMocks private CostBulkSaver costBulkSaver;
    @InjectMocks private DataExporterImpl dataExporter;
    @InjectMocks private MergeFileDataImporter dataImporter;

    private final int costsCount = 4_000;
    private Collection<Cost> costs = getSetOfRandomCosts(costsCount);
    private Map<String, Cost> costMap = toMap(costs);

    private File exportFile;

    private boolean disableEncryption = false;  // for debugging

    @BeforeClass
    public void init() throws Exception {

        initMocks(this);

        // need to instantiate manually to inject real costBulkSaver
        dataImporter = new MergeFileDataImporter(reader, jsonParser, costReader, costBulkSaver, cryptFacade);

        when(jsonConverter.serialize(any(Serializable.class)))
                .thenAnswer(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        CostsData data = invocation.getArgument(0);
                        logInfo("Mock JSON converter: descriptor size = " + data.getDescriptor().size());
                        return Joiner.on(LF).join(data.getDescriptor().keySet());
                    }
                });
        when(jsonParser.deserialize(anyString(), eq(CostsData.class)))
                .thenAnswer(new Answer<CostsData>() {
                    @Override
                    public CostsData answer(InvocationOnMock invocation) throws Throwable {
                        List<String> uuids = Arrays.asList(
                                ((String) invocation.getArgument(0)).split(LF));
                        Map<String, Cost> descriptor = new LinkedHashMap<>();
                        for (String uuid : uuids) {
                            if (costMap.containsKey(uuid)) {
                                descriptor.put(uuid, costMap.get(uuid));
                            }
                        }
                        CostsData result = new CostsData();
                        result.setDescriptor(descriptor);
                        return result;
                    }
                });

        if (disableEncryption) {
            disableEncryption();
        }

        exportFile = createFile("imp-exp-it");
    }

    @BeforeMethod
    public void reset() throws Exception {
        Files.deleteIfExists(exportFile.toPath());
    }

    @DataProvider(name = "clearExistingCostFilesOrNot")
    public static Object[][] clearExistingCostFilesOrNot() {
        return new Object[][] {{true, null}, {false, null}};
    }

    private void prepare(boolean clearExistingCostFiles) throws Exception {
        if (clearExistingCostFiles) {
            for (File eachCostFile : fileNamesFactory.getAllCostFiles()) {
                Files.deleteIfExists(eachCostFile.toPath());
            }
        }

        DateCostMultimap multimap = new DateCostMultimap();
        for (Cost each : costs) {
            multimap.put(day(each.getTimestamp()), each);
        }
        costBulkSaver.save(multimap);

        File backupDir = new File("io/costs-backup");
        if (backupDir.exists()) {
            for (File f : backupDir.listFiles()) {
                Files.deleteIfExists(f.toPath());
            }
        } else {
            Files.createDirectory(backupDir.toPath());
        }

        for (File eachCostFile : fileNamesFactory.getAllCostFiles()) {
            File dest = new File(backupDir.toString() + "/" + eachCostFile.getName());
            Files.copy(eachCostFile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Test(dataProvider = "clearExistingCostFilesOrNot", invocationCount = 2)
    public void shouldBeSymmetricExportImport(boolean clearExistingCostFiles, Object ignore) throws Exception {

        prepare(clearExistingCostFiles);

        dataExporter.exportAllData(exportFile, password);
        for (File eachCostFile : fileNamesFactory.getAllCostFiles()) {
            Files.deleteIfExists(eachCostFile.toPath());
        }
        dataImporter.importData(exportFile, password);

        // checking that every cost was imported
        Set<String> importedCostsUuids = readAllImportedCostsUuids();
        assertEquals(importedCostsUuids, costMap.keySet());

        File exportCopy = new File(exportFile.toString() + ".copy");
        Files.copy(exportFile.toPath(), exportCopy.toPath(),
                StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);

        dataExporter.exportAllData(exportFile, password);

        // checking that exporting second time gives exactly the same output
        assertEquals(reader.read(exportFile), reader.read(exportCopy));
    }

    private Set<String> readAllImportedCostsUuids() {
        Set<String> importedCostsUuids = new HashSet<>();
        int n = 0;
        for (File costFileWithUuids : fileNamesFactory.getAllCostFiles()) {
            String fileContent = reader.read(costFileWithUuids);
            List<String> linesFromFile = Arrays.asList(fileContent.split(LF));
            n += linesFromFile.size();
            importedCostsUuids.addAll(linesFromFile);
        }
        assertEquals(importedCostsUuids.size(), n);
        assertEquals(n, costsCount);
        return importedCostsUuids;
    }

    private static Map<String, Cost> toMap(Iterable<Cost> costs) {
        Map<String, Cost> map = new HashMap<>();
        for (Cost each : costs) {
            map.put(each.getUuid().toString(), each);
        }
        return map;
    }

    private void disableEncryption() {
        when(cryptFacade.encrypt(anyString(), anyString()))
                .thenAnswer(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArgument(0);
                    }
                });
        when(cryptFacade.decrypt(anyString(), anyString()))
                .thenAnswer(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        return invocation.getArgument(0);
                    }
                });
    }

}
