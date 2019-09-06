package com.wagonfly.todolist;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.wagonfly.todolist.db";
    public static final int DB_VERSION = 4;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_STATUS = "value";
    }
}
