package ru.takoe.iav.countee.view.dialog;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static ru.takoe.iav.countee.android.util.DisplayUtil.getDefaultDisplay;
import static ru.takoe.iav.countee.android.util.DisplayUtil.getLinearLayoutMinHeight;
import static ru.takoe.iav.countee.android.util.DisplayUtil.getScreenSize;
import static ru.takoe.iav.countee.view.dialog.FileSelectionHolder.selectedIndex;

/**
 * Taken with gratitude from
 * <a href="https://github.com/Scogun/Android-OpenFileDialog">Android-OpenFileDialog : Scogun@github.com"</a>.
 */
public class OpenFileDialog extends AlertDialog.Builder {

    private String currentPath = Environment.getExternalStorageDirectory().getPath();
    private final List<File> files = new ArrayList<>();
    private TextView title;
    private ListView listView;
    private FilenameFilter filenameFilter;
    private OpenDialogListener listener;
    private String accessDeniedMessage;
    private boolean foldersOnly = false;

    public interface OpenDialogListener {
        void onSelectedFile(String fileName);
    }

    public OpenFileDialog(Context context) {
        super(context);
        title = createTitle(context);
        changeTitle();
        LinearLayout linearLayout = createMainLayout(context);
        linearLayout.addView(createBackItem(context));
        listView = createListView(context);
        linearLayout.addView(listView);
        setCustomTitle(title)
                .setView(linearLayout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener == null) {
                            return;
                        }
                        if (selectedIndex > -1) {
                            listener.onSelectedFile(listView.getItemAtPosition(selectedIndex).toString());
                        }
                        if (foldersOnly) {
                            listener.onSelectedFile(currentPath);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, null);
    }

    @Override
    public AlertDialog show() {
        files.addAll(getFiles(currentPath));
        listView.setAdapter(new FileAdapter(getContext(), files));
        return super.show();
    }

    public OpenFileDialog setFilter(final String filter) {
        filenameFilter = new FilenameFilter() {

            @Override
            public boolean accept(File file, String fileName) {
                File tempFile = new File(path(file.getPath(), fileName));
                return !tempFile.isFile() || tempFile.getName().matches(filter);
            }
        };
        return this;
    }

    public OpenFileDialog setOnlyFoldersFilter() {
        filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String fileName) {
                File tempFile = new File(path(file.getPath(), fileName));
                return tempFile.isDirectory();
            }
        };
        foldersOnly = true;
        return this;
    }

    public OpenFileDialog setOpenDialogListener(OpenDialogListener listener) {
        this.listener = listener;
        return this;
    }

    public OpenFileDialog setAccessDeniedMessage(String message) {
        this.accessDeniedMessage = message;
        return this;
    }

    private LinearLayout createMainLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setMinimumHeight(getLinearLayoutMinHeight(context));
        return linearLayout;
    }

    private int getItemHeight(Context context) {
        TypedValue value = new TypedValue();
        DisplayMetrics metrics = new DisplayMetrics();
        context.getTheme().resolveAttribute(android.R.attr.listPreferredItemHeightSmall, value, true);
        getDefaultDisplay(context).getMetrics(metrics);
        return (int) TypedValue.complexToDimension(value.data, metrics);
    }

    private TextView createTextView(Context context, int style) {
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, style);
        int itemHeight = getItemHeight(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
        textView.setMinHeight(itemHeight);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(15, 0, 0, 0);
        return textView;
    }

    private TextView createTitle(Context context) {
        return createTextView(context, android.R.style.TextAppearance_DeviceDefault_DialogWindowTitle);
    }

    private TextView createBackItem(Context context) {
        TextView textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_Small);
        Drawable drawable = getContext().getResources().getDrawable(android.R.drawable.ic_menu_directions);
        drawable.setBounds(0, 0, 60, 60);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                File file = new File(currentPath);
                File parentDirectory = file.getParentFile();
                if (parentDirectory != null) {
                    currentPath = parentDirectory.getPath();
                    rebuildFiles(((FileAdapter) listView.getAdapter()));
                }
            }
        });
        return textView;
    }

    private int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width() + 80;
    }

    private void changeTitle() {
        final String displayedTitleText;
        String titleText = currentPath;
        int screenWidth = getScreenSize(getContext()).x;
        int maxWidth = (int) (screenWidth * 0.99);
        if (getTextWidth(titleText, title.getPaint()) > maxWidth) {
            while (getTextWidth("..." + titleText, title.getPaint()) > maxWidth) {
                int start = titleText.indexOf(File.pathSeparator, 2);
                titleText = titleText.substring(start > 0 ? start : 2);
            }
            displayedTitleText = "..." + titleText;
        } else {
            displayedTitleText = titleText;
        }
        title.setText(displayedTitleText);
    }

    private List<File> getFiles(String directoryPath) {
        File directory = new File(directoryPath);
        File[] list = Optional.fromNullable(directory.listFiles(filenameFilter)).or(new File[]{});
        List<File> fileList = Arrays.asList(list);
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file, File file2) {
                if (file.isDirectory() && file2.isFile()) {
                    return -1;
                } else if (file.isFile() && file2.isDirectory()) {
                    return 1;
                }
                return file.getPath().compareTo(file2.getPath());
            }
        });
        return fileList;
    }

    private void rebuildFiles(ArrayAdapter<File> adapter) {
        try {
            List<File> fileList = getFiles(currentPath);
            files.clear();
            selectedIndex = -1;
            files.addAll(fileList);
            adapter.notifyDataSetChanged();
            changeTitle();
        } catch (NullPointerException e) {
            String message = getContext().getResources().getString(android.R.string.unknownName);
            if (!isBlank(accessDeniedMessage)) {
                message = accessDeniedMessage;
            }
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private ListView createListView(Context context) {
        ListView view = new ListView(context);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                final ArrayAdapter<File> adapter = (FileAdapter) adapterView.getAdapter();
                File file = adapter.getItem(index);
                if (file.isDirectory()) {
                    currentPath = file.getPath();
                    rebuildFiles(adapter);
                } else {
                    selectedIndex = index == selectedIndex ? -1 : index;
                    adapter.notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    private static String path(String... fileNames) {
        return Joiner.on(File.pathSeparator).join(fileNames);
    }

}
