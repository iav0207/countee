package ru.takoe.iav.countee.fragment.content.stats.adapter;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.StatsFragment;
import ru.takoe.iav.countee.fragment.content.common.StringItem;
import ru.takoe.iav.countee.fragment.content.common.StringItemList;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

public class ChartsRecyclerViewAdapter extends RecyclerView.Adapter<ChartsRecyclerViewAdapter.ViewHolder>
        implements SpinnerAdapter{


    private final StringItemList mValues;

    private final StatsFragment.OnFragmentInteractionListener mListener;

    private LayoutInflater inflater;

    public ChartsRecyclerViewAdapter(StringItemList items,
                                     StatsFragment.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater(parent).inflate(R.layout.charts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.getItem(position);
        holder.mIdView.setText(mValues.getItem(position).id);
        holder.mContentView.setText(mValues.getItem(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getStandardView(position, parent, true);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public Object getItem(int i) {
        return mValues.getItem(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getStandardView(position, parent, false);
    }

    private View getStandardView(int position, ViewGroup parent, boolean dropdown) {
        View row = inflater(parent).inflate(R.layout.charts_item, parent, false);
        TextView title = (TextView) row.findViewById(R.id.charts_item_name);
        title.setText(mValues.getItem(position).getContent());
        if (!dropdown) {
            title.setAlpha(0.5f);
            title.setText(R.string.chart_prompt);
        }
        return row;
    }

    private LayoutInflater inflater(ViewGroup parent) {
        if (isNull(inflater)) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return inflater;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public StringItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

}
