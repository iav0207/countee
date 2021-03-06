package ru.takoe.iav.countee.fragment;

import javax.inject.Inject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.application.CounteeApp;
import ru.takoe.iav.countee.fragment.content.settings.SettingsFragmentContent;
import ru.takoe.iav.countee.fragment.content.settings.SettingsRecyclerViewAdapter;
import ru.takoe.iav.countee.fragment.listener.CancelButtonListener;
import ru.takoe.iav.countee.fragment.listener.ExportButtonListener;
import ru.takoe.iav.countee.fragment.listener.ImportButtonListener;
import ru.takoe.iav.countee.fragment.listener.SettingsFragmentButtonListener;
import ru.takoe.iav.countee.view.ViewProvider;
import ru.takoe.iav.countee.view.dialog.OpenFileDialog;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SettingsFragment extends Fragment
        implements View.OnClickListener, OpenFileDialog.OpenDialogListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int VAL_COLUMN_COUNT = 1;
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;

    @Inject ViewProvider viewProvider;
    @Inject ExportButtonListener exportButtonListener;
    @Inject ImportButtonListener importButtonListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SettingsFragment() {
        // required empty public constructor
    }

    @SuppressWarnings("unused")
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, VAL_COLUMN_COUNT);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CounteeApp.getInstance()
                .getSettingsFragmentComponent(this)
                .injectInto(this);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new SettingsRecyclerViewAdapter(SettingsFragmentContent.ITEMS, mListener));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewProvider.getImportDataButton().setOnClickListener(this);
        viewProvider.getExportDataButton().setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (viewProvider.getExportDataButton().getId() == view.getId()) {
            buildPasswordDialog(exportButtonListener);
        } else if (viewProvider.getImportDataButton().getId() == view.getId()) {
            buildOpenFileDialog();
        }
    }

    @Override
    public void onSelectedFile(String fileName) {
        buildPasswordDialog(importButtonListener.withSourceFile(fileName));
    }

    private void buildPasswordDialog(SettingsFragmentButtonListener positiveButtonListener) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.enter_password_title)
                .setView(positiveButtonListener.newEditText())
                .setPositiveButton(android.R.string.ok, positiveButtonListener)
                .setNegativeButton(android.R.string.cancel, new CancelButtonListener(getContext(), viewProvider))
                .show();
    }

    private void buildOpenFileDialog() {
        new OpenFileDialog(getContext())
                .setOpenDialogListener(this)
                .show();
    }

    public interface OnFragmentInteractionListener {
        void onListFragmentInteraction(SettingsFragmentContent.Item item);
    }

}
