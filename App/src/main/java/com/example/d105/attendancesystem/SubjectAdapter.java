package com.example.d105.attendancesystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Krishan on 11-04-2017.
 */

public class SubjectAdapter extends BaseAdapter {
    ArrayList<Subject> subject_list;
    Context context;
    public SubjectAdapter(Context contex, ArrayList<Subject> subject_list)
    {
        this.context=contex;
        this.subject_list=subject_list;
    }
    @Override
    public int getCount() {
        return subject_list.size();
    }

    @Override
    public Object getItem(int position) {
        return subject_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView  = inflater.inflate(R.layout.row_list, null);
            viewHolder= new ViewHolder();
            viewHolder.tv_subject_day=(TextView) convertView.findViewById(R.id.tv_subject_day);
            viewHolder.tv_subject_name=(TextView) convertView.findViewById(R.id.tv_subject_name);
            viewHolder.tv_subject_time=(TextView) convertView.findViewById(R.id.tv_subject_time);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        Subject subject=subject_list.get(position);
        viewHolder.tv_subject_day.setText(subject.getDay());
        viewHolder.tv_subject_name.setText(subject.getSubject());
        viewHolder.tv_subject_time.setText(subject.getTime());
        return convertView;
    }
    public static class ViewHolder{
        TextView tv_subject_name;
        TextView tv_subject_day;
        TextView tv_subject_time;
    }
}
