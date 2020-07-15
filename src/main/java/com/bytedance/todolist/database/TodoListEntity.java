package com.bytedance.todolist.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
@Entity(tableName = "todo")
public class TodoListEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long mId;

    @ColumnInfo(name = "content")
    private String mContent;

    @ColumnInfo(name = "time")
    private Date mTime;

    @ColumnInfo(name = "done")
    private boolean mDone;

    @ColumnInfo(name = "priority")
    private int mPriority;



    public TodoListEntity(String mContent, Date mTime, boolean mDone, int mPriority) {
        this.mContent = mContent;
        this.mTime = mTime;

        this.mDone = mDone;
        this.mPriority = mPriority;
    }





    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date mTime) {
        this.mTime = mTime;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public boolean getDone() { return mDone; }

    public void setDone(boolean mDone) { this.mDone = mDone; }

    public int getPriority() { return mPriority; }

    public void setPriority(int mPriority) { this.mPriority = mPriority; }
}
