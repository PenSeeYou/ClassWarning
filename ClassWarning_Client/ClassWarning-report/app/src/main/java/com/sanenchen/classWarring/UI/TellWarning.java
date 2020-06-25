package com.sanenchen.classWarring.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanenchen.classWarring.getThings.getDataJson;
import com.sanenchen.classWarring.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class TellWarning extends AppCompatActivity {

    String TitleSt, ItemWarnSt, StudentSt, FunSt, TimeSt, BeizhuSt = null;
    String MysqlID;
    public static final int ListViewSet = 1;
    ProgressDialog progressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_warning);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        MysqlID = intent.getStringExtra("MysqlID");

        progressDialog = new ProgressDialog(TellWarning.this);
        progressDialog.setTitle("加载数据中");
        progressDialog.setMessage("正在加载数据，请稍后...");
        progressDialog.setCancelable(false);
        //progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                getDataJson getDataJson = new getDataJson();
                String jsonData = getDataJson.getSearchReply("2", null, MysqlID, null);

                try {
                    JSONArray jsonArray = new JSONArray(jsonData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TitleSt = jsonObject.getString("WarringT");
                        ItemWarnSt = jsonObject.getString("WarringWhat");
                        StudentSt = jsonObject.getString("WarringStudent");
                        FunSt = jsonObject.getString("HowToWarring");
                        TimeSt = jsonObject.getString("StartAlone") + "至" + jsonObject.getString("EndAlone");
                        BeizhuSt = jsonObject.getString("Beizhu");
                    }
                } catch (Exception e) {

                }

                Message message = new Message();
                message.what = ListViewSet;
                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case ListViewSet:
                    TextView TitleS = findViewById(R.id.TitleS);
                    TextView ItemWarnS = findViewById(R.id.ItemWarnS);
                    TextView StudentS = findViewById(R.id.StudentS);
                    TextView FunS = findViewById(R.id.FunS);
                    TextView TimeS = findViewById(R.id.TimeS);
                    TextView BeizhuS = findViewById(R.id.BeizhuS);
                    LinearLayout linearLayout = findViewById(R.id.TimeZoneF);
                    if (FunSt.equals("回家反省")) {
                        linearLayout.setVisibility(LinearLayout.VISIBLE);
                    }


                    TitleS.setText(TitleSt);
                    ItemWarnS.setText(ItemWarnSt);
                    StudentS.setText(StudentSt);
                    FunS.setText(FunSt);
                    TimeS.setText(TimeSt);
                    if (BeizhuSt == null) {
                        BeizhuS.setText("无备注");
                    } else {
                        BeizhuS.setText(BeizhuSt);
                    }

                    progressDialog.dismiss();
                    break;

            }
        }
    };
}