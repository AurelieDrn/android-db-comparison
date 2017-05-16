package com.nantes.polytech.netapsys.activity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nantes.polytech.netapsys.R;

import java.util.List;

/**
 * Created by Aurelie on 15/11/2016.
 */

class MyDatabase {
    String name;
    boolean selected = false;

    public MyDatabase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}


public class CheckBoxAdapter extends ArrayAdapter<MyDatabase> {

    private List<MyDatabase> databaseList;
    private Activity context;

    public CheckBoxAdapter(List<MyDatabase> databaseList, Activity context) {
        super(context, R.layout.list_item, databaseList);
        this.databaseList = databaseList;
        this.context = context;
    }

    private static class MyDatabaseHolder {
        public TextView databaseName;
        public CheckBox checkBox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        MyDatabaseHolder holder = new MyDatabaseHolder();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);

            holder.databaseName = (TextView) v.findViewById(R.id.database_name);
            holder.checkBox = (CheckBox) v.findViewById(R.id.checkbox);

            holder.checkBox.setOnCheckedChangeListener((DatabaseActivity) context);

            v.setTag(holder); // important
        } else {
            holder = (MyDatabaseHolder) v.getTag();
        }

        MyDatabase myDatabase = databaseList.get(position);
        holder.databaseName.setText(myDatabase.getName());
        holder.checkBox.setSelected(myDatabase.isSelected());
        holder.checkBox.setTag(position);

        return v;
    }
}
