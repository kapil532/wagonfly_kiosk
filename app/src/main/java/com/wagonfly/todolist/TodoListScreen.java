package com.wagonfly.todolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wagonfly.R;

import java.util.ArrayList;

import kartify.model.Beneficiary;
import kartify.sql.DatabaseHelper;
import kartify_base.BaseActivity;

/**
 * Created by Kapil Katiyar on 6/6/2018.
 */

public class TodoListScreen extends BaseActivity implements UpateUi {
    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private TodoListAdapter mAdapter;
    FloatingActionButton clear_data;
     Button  add_button;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_screen);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
       /* help =(TextView)findViewById(R.id.help);
        help.setText(title_text);*/
        TextView title =(TextView)findViewById(R.id.title);
        title.setText("ToDo List");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });
        mHelper = new TaskDbHelper(this);


        mTaskListView = (ListView) findViewById(R.id.list_todo);

        add_button = (Button) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskEditText = new EditText(TodoListScreen.this);
                AlertDialog dialog = new AlertDialog.Builder(TodoListScreen.this)
                        .setTitle(getResources().getString(R.string.add_new_item))
                       // .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(taskEditText.getText().toString().length()>0)
                                {
                                    String task = String.valueOf(taskEditText.getText());
                                    TaskDbHelper taskD = new TaskDbHelper(TodoListScreen.this);
                                    taskD.addBeneficiary(taskEditText.getText().toString());
                                    updateUI();
                                }
                                else
                                {
                                    Toast.makeText(TodoListScreen.this,getResources().getString(R.string.please_add_item),Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
        clear_data = (FloatingActionButton) findViewById(R.id.clear_data);

        updateUI();
    }


    private void updateUI()
    {

        TaskDbHelper dataBase = new TaskDbHelper(this);
        ArrayList<TodoListPojo> taskList = (ArrayList<TodoListPojo>) dataBase.getTodoList();
        if(taskList.size()>0)
        {
            clear_data.setAlpha(1f);
            clear_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TodoListScreen.this);
                    builder.setTitle(getResources().getString(R.string.confirm));
                    builder.setMessage(getResources().getString(R.string.delete_confirm));
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            TaskDbHelper taskD = new TaskDbHelper(TodoListScreen.this);
                            taskD.deleteList();
                            updateUI();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    });

                    builder.show();

                }
            });
        }
        else
        {
            clear_data.setAlpha(.5f);
            clear_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        mAdapter = new TodoListAdapter(taskList, this, this);
        mTaskListView.setAdapter(mAdapter);
    }

    @Override
    public void update() {

        updateUI();
    }
}
