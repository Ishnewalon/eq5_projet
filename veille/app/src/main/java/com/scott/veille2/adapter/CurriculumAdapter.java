package com.scott.veille2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scott.veille2.R;
import com.scott.veille2.model.Curriculum;
import com.scott.veille2.model.StudentCurriculumDTO;

import java.util.List;

public class CurriculumAdapter extends BaseAdapter {

    private Context context;
    private final List<Curriculum> curriculumList;
    private static LayoutInflater inflater = null;

    public CurriculumAdapter(Context context, List<Curriculum> curriculumList) {
        this.context = context;
        this.curriculumList = curriculumList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return curriculumList.size();
    }

    @Override
    public Object getItem(int position) {
        return curriculumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null)
            view = inflater.inflate(R.layout.curriculum_row, null);

        TextView name = view.findViewById(R.id.name);
        TextView validity = view.findViewById(R.id.validity);

        if (curriculumList.get(position).getValid() != null) {
            validity.setText(curriculumList.get(position).getValid() ? "Valide" : "Invalide");
        }else {
            validity.setText("Invalide");
        }

        name.setText(curriculumList.get(position).getName());

        return view;
    }
}
