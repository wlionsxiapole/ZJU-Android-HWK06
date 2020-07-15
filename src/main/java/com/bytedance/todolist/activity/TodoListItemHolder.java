package com.bytedance.todolist.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.todolist.R;
import com.bytedance.todolist.database.TodoListEntity;
import com.bytedance.todolist.database.TodoListOperator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangrui.sh
 * @since Jul 11, 2020
 */
public class TodoListItemHolder extends RecyclerView.ViewHolder {
    private TextView mContent;
    private TextView mTimestamp;

    private CheckBox mcheckbox;
    private ImageButton mdelete;
    private final TodoListOperator operator;

    public TodoListItemHolder(@NonNull View itemView, TodoListOperator operator) {
        super(itemView);
        mContent = itemView.findViewById(R.id.content_TV);
        mTimestamp = itemView.findViewById(R.id.timestamp_TV);

        mcheckbox = itemView.findViewById(R.id.select_CB);
        mdelete = itemView.findViewById(R.id.delete_button);
        this.operator = operator;
    }

    public void bind(final TodoListEntity entity) {
        if(entity==null) return;
        mcheckbox.setOnCheckedChangeListener(null);
        if(entity.getDone()) {
            mcheckbox.setChecked(true);
            mContent.setPaintFlags(mContent.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            mcheckbox.setChecked(false);
            mContent.setPaintFlags(mContent.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }


        mContent.setText(entity.getContent());
        mTimestamp.setText(formatDate(entity.getTime()));

        mcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                entity.setDone(b);
                operator.updateEntity(entity);
            }
        });
        mdelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                operator.deleteEntity(entity);
            }
        });
        switch (entity.getPriority())
        {
            case 3 :
                itemView.setBackgroundColor(Color.parseColor("#ff1493"));
                break;
            case 2 :
                itemView.setBackgroundColor(Color.parseColor("#ff80bf"));
                break;
            default :
                itemView.setBackgroundColor(Color.parseColor("#ffd9e6"));
        }
        if(entity.getDone()) itemView.setBackgroundColor(Color.WHITE);

    }

    private String formatDate(Date date) {
        DateFormat format = SimpleDateFormat.getDateInstance();
        return format.format(date);
    }
}
