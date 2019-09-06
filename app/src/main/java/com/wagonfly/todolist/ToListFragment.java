package com.wagonfly.todolist;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wagonfly.R;

import java.util.ArrayList;

/**
 * Created by Kapil Katiyar on 6/7/2018.
 */

public class ToListFragment extends Fragment implements UpateUi {

    ListView mTaskListView;
    private TodoListAdapter mAdapter;
    FloatingActionButton add_button;
    FloatingActionButton clear_data;
    Button add_item;
    CardView top;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {



        View view = inflater.inflate(R.layout.todo_list_screen, container, false);
        mTaskListView = (ListView) view.findViewById(R.id.list_todo);
        top =(CardView)view.findViewById(R.id.top);
        top.setVisibility(View.GONE);
        //add_button=(FloatingActionButton)view.findViewById(R.id.add_button);
        add_item=(Button)view.findViewById(R.id.add_button);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskEditText = new EditText(getActivity());
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(getResources().getString(R.string.add_new_item))
                       // .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if(taskEditText.getText().toString().length()>0)
                                {
                                    String task = String.valueOf(taskEditText.getText());
                                    TaskDbHelper taskD = new TaskDbHelper(getContext());
                                    taskD.addBeneficiary(taskEditText.getText().toString());
                                    updateUI();
                                }
                                else
                                {
                                    Toast.makeText(getContext(),getResources().getString(R.string.please_add_item),Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
        clear_data=(FloatingActionButton)view.findViewById(R.id.clear_data);
       // add_button.setVisibility(View.GONE);
        clear_data.setVisibility(View.GONE);




        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI() {

        TaskDbHelper dataBase = new TaskDbHelper(getActivity());
        ArrayList<TodoListPojo> taskList = (ArrayList<TodoListPojo>) dataBase.getTodoList();
        mAdapter = new TodoListAdapter(taskList, getActivity(), this);
        mTaskListView.setAdapter(mAdapter);
    }

    @Override
    public void update() {

        updateUI();
    }
}
