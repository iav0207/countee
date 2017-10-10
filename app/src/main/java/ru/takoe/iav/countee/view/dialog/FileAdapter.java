package ru.takoe.iav.countee.view.dialog;

import java.io.File;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import static android.R.color.holo_blue_dark;
import static android.R.color.transparent;
import static ru.takoe.iav.countee.view.dialog.FileSelectionHolder.selectedIndex;

@ParametersAreNonnullByDefault
class FileAdapter extends ArrayAdapter<File> {

    private Drawable folderIcon;
    private Drawable fileIcon;

    FileAdapter(Context context, List<File> files) {
        super(context, android.R.layout.simple_list_item_1, files);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        if (view == null) {
            return null;
        }

        File file = getItem(position);
        view.setText(file.getName());
        if (file.isDirectory()) {
            setDrawable(view, folderIcon);
        } else {
            setDrawable(view, fileIcon);
            int backgroundColor = selectedIndex == position ? holo_blue_dark : transparent;
            view.setBackgroundColor(getContext().getResources().getColor(backgroundColor));
        }
        return view;
    }

    private void setDrawable(TextView view, @Nullable Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, 60, 60);
        }
        view.setCompoundDrawables(drawable, null, null, null);
    }

    public FileAdapter setFolderIcon(Drawable drawable) {
        this.folderIcon = drawable;
        return this;
    }

    public FileAdapter setFileIcon(Drawable drawable) {
        this.fileIcon = drawable;
        return this;
    }
}
