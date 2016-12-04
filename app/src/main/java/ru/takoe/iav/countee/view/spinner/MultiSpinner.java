package ru.takoe.iav.countee.view.spinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.Spinner;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;

public class MultiSpinner extends Spinner implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnCancelListener {

    private StringItemList items;

    private boolean[] selected;

    private String defaultText = getContext().getString(R.string.filter_prompt);

    private MultiSpinnerListener listener;

    public MultiSpinner(Context context) {
        super(context);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public MultiSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        selected[which] = isChecked;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        listener.onItemsSelected(selected);
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(items.toStringArray(), selected, this);
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(this);
        builder.show();
        return true;
    }

    public void setItems(StringItemList items, MultiSpinnerListener listener) {
        this.items = items;
        this.listener = listener;

        setEachItemSelection(false);
    }

    private void setEachItemSelection(boolean areSelected) {
        selected = new boolean[items.size()];
        for (int i = 0; i < selected.length; i++) {
            selected[i] = areSelected;
        }
    }

    private String buildSpinnerText() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean someUnselected = false;
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                stringBuilder.append(items.getItem(i).getContent());
                stringBuilder.append(", ");
            } else {
                someUnselected = true;
            }
        }
        String spinnerText;
        final int maxLength = 10;
        if (someUnselected) {
            spinnerText = stringBuilder.toString();
            if (spinnerText.length() > 2)
                spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
            if (spinnerText.length() > maxLength) {
                return defaultText;
            }
            return spinnerText;
        } else {
            return defaultText;
        }
    }

    public interface MultiSpinnerListener {
        void onItemsSelected(boolean[] selected);
    }
}