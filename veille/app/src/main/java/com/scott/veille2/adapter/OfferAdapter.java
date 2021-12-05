package com.scott.veille2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scott.veille2.R;
import com.scott.veille2.model.Offer;

import java.util.List;

public class OfferAdapter extends BaseAdapter {

    private Context context;
    private final List<Offer> offers;
    private static LayoutInflater inflater = null;

    public OfferAdapter(Context context, List<Offer> offers) {
        this.context = context;
        this.offers = offers;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return offers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return offers.get(position).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.offer_row, null);

        TextView header = view.findViewById(R.id.header);
        TextView salary = view.findViewById(R.id.salaire);
        TextView desc = view.findViewById(R.id.desc);

        header.setText(offers.get(position).getTitle());
        salary.setText("$" + offers.get(position).getSalary());
        desc.setText(offers.get(position).getDescription());

        return view;
    }
}
