package com.hack.easyhomeloan.activities.detail.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.hack.easyhomeloan.R;

import java.text.DecimalFormat;
import java.util.List;

public class AmortizationListAdapter extends BaseAdapter {

    private DecimalFormat decimalFormatTwoDigit = new DecimalFormat("##,##,###");
    private List<String> rowMonth;
    private List<String> rowEMI;
    private List<String> rowPrincipal;
    private List<String> rowOutStanding;
    private List<String> rowIntrest;
    private Context mContext;

    public AmortizationListAdapter(Context mContext, List<String> rowMonth, List<String> rowEMI,
                                   List<String> rowPrincipal, List<String> rowOutStanding, List<String> rowIntrest) {

        this.mContext = mContext;
        this.rowEMI = rowEMI;
        this.rowMonth = rowMonth;
        this.rowOutStanding = rowOutStanding;
        this.rowPrincipal = rowPrincipal;
        this.rowIntrest = rowIntrest;
    }

    @Override
    public int getCount() {
        return rowMonth.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (null == view) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.adapter_amort_view, parent, false);
            holder = new ViewHolder();
            holder.tvMonth = view.findViewById(R.id.TextViewMonth);
            holder.tvEmi = view.findViewById(R.id.TextViewEMI);
            holder.tvPrincipal = view.findViewById(R.id.TextViewPrincipal);
            holder.tvOutstanding = view.findViewById(R.id.TextViewOutStanding);
            holder.tvIntrest = view.findViewById(R.id.TextViewIntrest);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (position % 2 == 0) {
            view.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorBackground, null));
        } else {
            view.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.colorWhite, null));
        }
        int stepUpPosition = -1;
        if (stepUpPosition == position) {
            view.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.tvEmi.setTextColor(Color.WHITE);
            holder.tvMonth.setTextColor(Color.WHITE);
            holder.tvPrincipal.setTextColor(Color.WHITE);
            holder.tvOutstanding.setTextColor(Color.WHITE);
            holder.tvIntrest.setTextColor(Color.WHITE);
        } else {
            holder.tvEmi.setTextColor(Color.BLACK);
            holder.tvMonth.setTextColor(Color.BLACK);
            holder.tvPrincipal.setTextColor(Color.BLACK);
            holder.tvOutstanding.setTextColor(Color.BLACK);
            holder.tvIntrest.setTextColor(Color.BLACK);
        }

        holder.tvEmi.setText(decimalFormatTwoDigit.format(Double.parseDouble(rowEMI.get(position))));
        holder.tvMonth.setText(rowMonth.get(position));
        holder.tvPrincipal.setText(decimalFormatTwoDigit.format(Double.parseDouble(rowPrincipal.get(position))));
        holder.tvOutstanding.setText(decimalFormatTwoDigit.format(Double.parseDouble(rowOutStanding.get(position))));
        holder.tvIntrest.setText(decimalFormatTwoDigit.format(Double.parseDouble(rowIntrest.get(position))));

        return view;
    }

    static class ViewHolder {
        TextView tvMonth, tvEmi, tvPrincipal, tvOutstanding, tvIntrest;
    }
}
