package com.wagonfly.todolist;

import android.app.Activity;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.wagonfly.R;

import java.util.ArrayList;

/**
 * Created by Kapil Katiyar on 6/6/2018.
 */

public class TodoListAdapter extends BaseAdapter {

    ArrayList<TodoListPojo> arrayList;
    Activity activity;
    UpateUi handler;


    public TodoListAdapter(ArrayList<TodoListPojo> arrayList, Activity activity, UpateUi handler) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView taskName;
        CheckBox taskDelete;
        ImageView delete_button;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_todo, null);
            holder = new ViewHolder();

            holder.taskName = (TextView) convertView.findViewById(R.id.task_title);
            holder.taskDelete = (CheckBox) convertView.findViewById(R.id.task_delete);
            holder.delete_button = (ImageView) convertView.findViewById(R.id.delete_button);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.taskName.setText(arrayList.get(i).getTask());
        holder.taskName.setTag(i);

        Log.d("UPDATE LIST", "LISTTT" + arrayList.get(i).getStatus() + arrayList.get(i).getTask());
        if (arrayList.get(i).getStatus().equalsIgnoreCase("1")) {
            holder.taskDelete.setChecked(true);
            holder.taskName.setPaintFlags(holder.taskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.taskName.setPaintFlags(0);
            holder.taskDelete.setChecked(false);
        }

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskDbHelper taskD = new TaskDbHelper(activity);
                taskD.deleteTask(arrayList.get((int) holder.taskName.getTag()).getId());

                Log.d("UPDATE LIST", "LISTTT-->" + holder.taskName.getTag());
                handler.update();
            }
        });


        holder.taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (holder.taskDelete.isChecked()) {
                    TaskDbHelper taskD = new TaskDbHelper(activity);
                    taskD.updateTable(arrayList.get((int) holder.taskName.getTag()).getId(), "1");

                    Log.d("UPDATE LIST", "LISTTT-->" + holder.taskName.getTag());
                    handler.update();
                } else {
                    TaskDbHelper taskD = new TaskDbHelper(activity);
                    taskD.updateTable(arrayList.get((int) holder.taskName.getTag()).getId(), "0");
                    handler.update();
                }
            }
        });

        return convertView;
    }
}
