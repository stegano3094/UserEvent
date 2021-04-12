package com.stegano.userevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UserEventNextActivity extends Activity {
    private TextView textView;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  // 저장된 상태가 있다면 복구하는 과정을 수행함

        setContentView(R.layout.activity_user_event_next);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        Intent intent = getIntent();  // Intent를 전달받아 객체를 저장함
        String input = intent.getStringExtra("input");  // input 키를 통해 문자열을 찾아서 저장함
        textView.setText(input);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = editText.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("result", result);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
