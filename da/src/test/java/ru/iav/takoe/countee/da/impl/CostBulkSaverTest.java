package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.base.Joiner;
import org.joda.time.DateTime;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.Cache;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.iav.takoe.countee.da.impl.CostBulkSaverTestData.bulkOf;
import static ru.iav.takoe.countee.da.impl.CostBulkSaverTestData.emptyBulk;
import static ru.iav.takoe.countee.test.CounteeTestUtils.createFile;
import static ru.iav.takoe.countee.test.CounteeTestUtils.getRandomCost;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

@ParametersAreNonnullByDefault
public class CostBulkSaverTest {

    @Mock
    private CostFileNamesFactory fileNamesFactory;

    @Mock
    private JsonConverter jsonConverter;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private LocalWriter writer;

    @Mock
    private Cache costsCache;

    private File fileOne, fileTwo;

    @InjectMocks private CostBulkSaver costBulkSaver;

    @BeforeClass
    public void init() throws Exception {
        initMocks(this);
        fileOne = createFile("bulk1");
        fileTwo = createFile("bulk2");
    }

    @BeforeMethod
    public void reset() throws Exception {
        Mockito.reset(fileNamesFactory, jsonConverter, writer, costsCache);
        Files.deleteIfExists(fileOne.toPath());
        Files.deleteIfExists(fileTwo.toPath());

        when(fileNamesFactory.getFileForDate(anyDate()))
                .thenReturn(fileOne, fileTwo);
        when(jsonConverter.serialize(any(Serializable.class)))
                .thenAnswer(new Answer<String>() {
                    @Override
                    public String answer(InvocationOnMock invocation) throws Throwable {
                        CostsData data = invocation.getArgument(0);
                        return Joiner.on("\n").join(data.getDescriptor().keySet());
                    }
                });
    }

    @Test
    public void shouldInvalidateCostCacheIfThereAreSomeCostsInMultimap() {
        costBulkSaver.save(bulkOf(getRandomCost()));
        verify(costsCache).invalidate();
    }

    @Test
    public void shouldNotInvalidateCostCacheIfThereWereNoCostsToSave() {
        costBulkSaver.save(emptyBulk());
        verify(costsCache, never()).invalidate();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeIfArgumentIsNull() {
        costBulkSaver.save(null);
    }

    @Test
    public void shouldClearPreviouslyPresentFilesWithClashingNamesIfAny() throws Exception {
        String prevContent = getRandomString();
        writer.clearWrite(prevContent, fileOne);

        costBulkSaver.save(bulkOf(getRandomCost()));

        Iterable<String> lines = Files.readAllLines(fileOne.toPath(), Charset.defaultCharset());
        String contentAfterSaving = Joiner.on("").join(lines);
        assertFalse(contentAfterSaving.contains(prevContent));
    }

    @Test(dataProvider = "bulkSave", dataProviderClass = CostBulkSaverTestData.class)
    public void shouldWriteSerializedCostsToFiles(DateCostMultimap bulk, Object ignore)
            throws Exception
    {
        costBulkSaver.save(bulk);

        Iterator<File> expectedFiles = Arrays.asList(fileOne, fileTwo).iterator();
        for (DateTime dateTime : bulk.keySet()) {
            Collection<Cost> costs = bulk.get(dateTime);
            checkFileContainsUuids(expectedFiles.next(), costs);
        }
    }

    private void checkFileContainsUuids(File file, Iterable<Cost> costs) throws Exception {
        List<String> lines = Files.readAllLines(
                file.toPath(), Charset.defaultCharset());
        String fileContent = Joiner.on("\n").join(lines);
        for (Cost cost : costs) {
            assertTrue(fileContent.contains(uuid(cost)));
        }
    }

    private static String uuid(Cost cost) {
        return cost.getUuid().toString();
    }

    private static DateTime anyDate() {
        return any(DateTime.class);
    }

}
