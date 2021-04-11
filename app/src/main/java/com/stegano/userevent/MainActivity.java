package com.stegano.userevent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * 버튼을 이용한 사용자 입력 처리
     * 텍스트, 에디트텍스트, 버튼, setOnClickListener, AlertDialog,
     * ProgressDialog, DatePickerDialog, TimePickerDialog, Toast,
     * Noti 등 사용
     */

    private TextView textView;
    private EditText editText;
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        // 익명 클래스로 작성된 리스너 객체 연결
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                Toast t = Toast.makeText(getApplicationContext(), input, Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_VERTICAL, 0, 500);
                t.show();

                showDialog(input);
            }
        });
    }

    public void onButtonClicked(View v) {
        String input = editText.getText().toString();
        textView.setText(input);
    }

    public void showDialog(String input) {  // 다이얼로그
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  // Builder 객체 생성
        builder.setMessage(input);
        builder.setTitle("대화 상자 타이틀");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "확인을 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });  // 확인 버튼
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "취소를 눌렀습니다.", Toast.LENGTH_SHORT).show();
            }
        });  // 취소 버튼
        AlertDialog alertDialog = builder.create();  // AlertDialog 객체 생성
        alertDialog.show();  // 다이얼로그 출력
    }

    public void showProgressDialog() {  // 프로그래스 다이얼로그
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("ProgressDialog");
        progressDialog.setMessage("진행 중...");
        progressDialog.show();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.cancel();  // Thread.sleep(ms) ms시간 만큼 지연 후 닫기
            }
        });
        thread.start();
    }

    public void showDateDialog() {  // 날짜 선택 다이얼로그
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String getDate = year + "년 " + month + "월 " + dayOfMonth + "일";
                Toast.makeText(getApplicationContext(), getDate, Toast.LENGTH_SHORT).show();
            }
        }, 2021, 0, 1);  // 처음 보이는 날짜
        datePickerDialog.setTitle("DatePickerDialog");
        datePickerDialog.setMessage("날짜를 선택하세요.");
        datePickerDialog.show();
    }

    public void showDateDialog2() {  // 시간 선택 다이얼로그
        TimePickerDialog datePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String getDate = hourOfDay + "시 " + minute + "분";
                Toast.makeText(getApplicationContext(), getDate, Toast.LENGTH_SHORT).show();
            }
        }, 1, 1, true);  // true : 24시간제, false : 12시간제
        datePickerDialog.setTitle("DatePickerDialog");
        datePickerDialog.setMessage("날짜를 선택하세요.");
        datePickerDialog.show();
    }


}