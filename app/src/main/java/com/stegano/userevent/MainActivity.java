package com.stegano.userevent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

    private static final String NOTIFICATION_CHANNEL_ID = "channel1_ID";  // 채널id
    private static final String NOTIFICATION_CHANNEL_NAME = "channel1";  // 채널명

    private TextView textView;
    private EditText editText;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
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

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    public void onButtonClicked(View v) {  // (Button) findViewById(R.id.button)
        String input = editText.getText().toString();
        textView.setText(input);

        // 화면 이동
        //startActivity(new Intent(MainActivity.this, UserEventNextActivity.class));
        Intent intent = new Intent(MainActivity.this, UserEventNextActivity.class);
        intent.putExtra("input", input);
        //startActivity(intent);
        // setResult()의 결과를 받아오기 위해서는 startActivityForResult()를 사용해야한다
        startActivityForResult(intent,1);
        // 반환된 결과는 onActivityResult를 Override 해서 받아올 수 있다.
    }

    @Override  // ctrl + o 를 누르고 onActivityResult를 적으면 자동완성됨
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {  // 요청 코드 번호
            if(resultCode == Activity.RESULT_OK) {  // 요청 코드 번호의 결과가 성공이면
                String result = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            } else if(resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "결과 처리를 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }
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

    public void showNotification() {
        // 알림매니저 객체를 생성 -> 채널id, 채널명, 중요도로 알림채널 객체를 생성 -> 알림매니저를 이요해서 채널생성 ->

        // 시스템으로부터 NotificationManager를 반환받음
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // AOS8.0(오레오) 이상
            int importance = NotificationManager.IMPORTANCE_DEFAULT;  // 채널의 중요도(0~4)

            // NotificationChannel 객체를 생성할 때 채널ID, 채널명, 중요도를 넣어줘야한다
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);  // 알림 채널 객체 생성

            notificationManager.createNotificationChannel(notificationChannel);  // 채널 생성
        }

        int notificationId = 0;
        String editInput = editText.getText().toString();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("알림 도착");
        builder.setContentText(editInput);
        notificationManager.notify(notificationId, builder.build());  // NotificationManager를 사용하여 화면에 알림을 표시
    }
}