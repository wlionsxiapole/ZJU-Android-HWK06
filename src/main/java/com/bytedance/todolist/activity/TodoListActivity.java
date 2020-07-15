package com.bytedance.todolist.activity;

import android.content.Intent;
import android.os.Bundle;

import com.bytedance.todolist.database.TodoListDao;
import com.bytedance.todolist.database.TodoListDatabase;
import com.bytedance.todolist.database.TodoListEntity;
import com.bytedance.todolist.database.TodoListOperator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.bytedance.todolist.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    private TodoListAdapter mAdapter;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_activity_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoListAdapter(new TodoListOperator()
        {
            @Override
            public void deleteEntity(TodoListEntity entity) {
                TodoListActivity.this.deleteEntity(entity);
            }

            @Override
            public void updateEntity(TodoListEntity entity) {
                TodoListActivity.this.updateEntity(entity);
            }
        });
        recyclerView.setAdapter(mAdapter);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                startActivityForResult(new Intent(TodoListActivity.this, ExtraActivity.class), 1);
            }
        });

        mFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                        dao.deleteAll();
                        for (int i = 0; i < 20; ++i) {
                            dao.addTodo(new TodoListEntity("This is " + i + " item", new Date(System.currentTimeMillis()),false,1));
                        }
                        Snackbar.make(mFab, R.string.hint_insert_complete, Snackbar.LENGTH_SHORT).show();
                    }
                }.start();
                return true;
            }
        });
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        new Thread() {
            @Override
            public void run() {
                TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                final List<TodoListEntity> entityList = dao.loadAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setData(entityList);
                    }
                });
            }
        }.start();
    }

    private void deleteEntity(final TodoListEntity entity){
        new Thread(){
            @Override
            public void run()
            {
                TodoListDatabase.inst(getApplicationContext()).todoListDao().delete(entity);
                loadFromDatabase();
            }
        }.start();
    }

    private void updateEntity(final TodoListEntity entity){
        new Thread()
        {
            @Override
            public void run()
            {
                TodoListDatabase.inst(getApplicationContext()).todoListDao().update(entity);
                loadFromDatabase();
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 1) {
            new Thread() {
                @Override
                public void run() {
                    TodoListDao dao = TodoListDatabase.inst(TodoListActivity.this).todoListDao();
                    assert data != null;
                    dao.addTodo(new TodoListEntity(data.getStringExtra("content"),new Date(System.currentTimeMillis()),data.getBooleanExtra("done",false),data.getIntExtra("priority",1)));
                    loadFromDatabase();
                }
            }.start();
        }
    }
}
