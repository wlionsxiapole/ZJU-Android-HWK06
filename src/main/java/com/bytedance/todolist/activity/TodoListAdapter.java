package com.bytedance.todolist.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;
import com.bytedance.todolist.database.TodoListOperator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListAdapter extends RecyclerView.Adapter<TodoListItemHolder> {
    private List<TodoListEntity> mDatas;
    private final TodoListOperator operator;

    public TodoListAdapter(TodoListOperator operator){
        mDatas = new ArrayList<>();
        this.operator = operator;
    }
    @NonNull
    @Override
    public TodoListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoListItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item_layout, parent, false),operator);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListItemHolder holder, int position) {
        holder.bind(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @MainThread
    public void setData(List<TodoListEntity> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    public void setList(List<TodoListEntity> list) {
        if(list == null) return;

        Collections.sort(list, new Comparator<TodoListEntity>() {
            @Override
            public int compare(TodoListEntity a1, TodoListEntity a2) {

                if(a1.getDone() && !a2.getDone()) return 1;
                if(a2.getDone() && !a1.getDone()) return -1;

                return Integer.compare(a2.getPriority(), a1.getPriority());
            }
        });
        mDatas = list;
        notifyDataSetChanged();
    }
}
