package com.bytedance.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.bytedance.todolist.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ExtraActivity extends AppCompatActivity {
    private EditText context;
    private RadioGroup priority;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extra_activity_layout);
        setTitle("Take a note");

        Button confirm = findViewById(R.id.confirm_button);
        context = findViewById(R.id.et_context);
        priority = findViewById(R.id.radio_group);

        RadioButton low_btn = findViewById(R.id.low_button);
        low_btn.setChecked(true);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str;
                str = context.getText().toString();
                if(str.isEmpty())
                {
                    context.setError("内容不能为空！");
                    return;
                }

                int priority = getPriority();

                Intent intent = new Intent();
                intent.putExtra("content", str);
                intent.putExtra("done", false);
                intent.putExtra("priority", priority);
                setResult(1, intent);
                finish();
            }
        });
    }
    private int getPriority(){
        switch (priority.getCheckedRadioButtonId()) {
            case R.id.low_button:
                return 1;
            case R.id.medium_button:
                return 2;
            default:
                return 3;
        }
    }
}
