package com.bytedance.todolist.database;

public interface TodoListOperator {
    void deleteEntity(TodoListEntity entity);

    void updateEntity(TodoListEntity entity);
}