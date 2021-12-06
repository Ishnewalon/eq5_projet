package com.scott.veille2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.scott.veille2.R;
import com.scott.veille2.model.User;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private final List<User> userList;
    private static LayoutInflater inflater = null;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        User user = (User) getItem(position);

        if (view == null)
            view = inflater.inflate(R.layout.user_row, null);

        TextView fullName = view.findViewById(R.id.nomComplet);
        TextView numTel = view.findViewById(R.id.numTel);
        TextView email = view.findViewById(R.id.courriel);

        fullName.setText(user.getLastName() + ", " + user.getFirstName());
        numTel.setText(user.getPhone());
        email.setText(user.getEmail());

        return view;
    }
}
