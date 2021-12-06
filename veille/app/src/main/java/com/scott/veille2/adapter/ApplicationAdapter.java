package com.scott.veille2.adapter;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scott.veille2.R;
import com.scott.veille2.model.OfferApplication;

import java.util.List;

public class ApplicationAdapter extends BaseAdapter {

    private Context context;
    private final List<OfferApplication> applicationList;
    private static LayoutInflater inflater = null;

    public ApplicationAdapter(Context context, List<OfferApplication> applicationList) {
        this.context = context;
        this.applicationList = applicationList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return applicationList.size();
    }

    @Override
    public Object getItem(int position) {
        return applicationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        OfferApplication offerApplication = (OfferApplication) getItem(position);

        if (view == null)
            view = inflater.inflate(R.layout.app_row, null);

        TextView name = view.findViewById(R.id.offerName);
        TextView studentName = view.findViewById(R.id.studentName);
        TextView status = view.findViewById(R.id.status);

        name.setText(offerApplication.getOffer().getTitle());
        studentName.setText(offerApplication.getStudentName());
        status.setText(offerApplication.getStatus());

        return view;
    }
}
