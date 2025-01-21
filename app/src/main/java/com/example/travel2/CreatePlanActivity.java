package com.example.travel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePlanActivity extends AppCompatActivity {
    private String TAG = "CreatePlanActivity";
    private EditText edit_createPlan_title;
    private Button btn_createPlan_start,btn_createPlan_mid,btn_createPlan_clean;
    private SharedPreferences getPrefs;
    private SharedPreferences.Editor editor;
    private String mainTitle,plan_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mainTitle = getPrefs.getString("mainTitle", "");
        plan_flag = getPrefs.getString("plan_flag", "0");
    }

    private void initView(){
        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        mainTitle = getPrefs.getString("mainTitle", "");
        plan_flag = getPrefs.getString("plan_flag", "0");

        edit_createPlan_title = (EditText) findViewById(R.id.edit_createPlan_title);
        btn_createPlan_start = (Button) findViewById(R.id.btn_createPlan_start);
        btn_createPlan_mid = (Button) findViewById(R.id.btn_createPlan_mid);
        btn_createPlan_clean = (Button) findViewById(R.id.btn_createPlan_clean);
        btn_createPlan_start.setOnClickListener(onClick);
        btn_createPlan_mid.setOnClickListener(onClick);
        edit_createPlan_title.setText(mainTitle);
        btn_createPlan_clean.setOnClickListener(onClick);
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btn_createPlan_start){
                Log.d(TAG, "onClick: btn_createPlan_start");
                Log.d(TAG, "onClick: plan_flag = "+plan_flag);
                if(plan_flag.equals("0")){
                    Intent intent = new Intent(getApplicationContext(), CreateStartPlanActivity.class);
                    editor = getPrefs.edit();
                    mainTitle =  edit_createPlan_title.getText().toString();
                    editor.putString("mainTitle", mainTitle);
                    editor.apply();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"起點已建立",Toast.LENGTH_SHORT).show();
                }
            }else if(v.getId() == R.id.btn_createPlan_mid){
                Log.d(TAG, "onClick: btn_createPlan_mid");
                Log.d(TAG, "onClick: plan_flag = "+plan_flag);
                if(plan_flag.equals("1")){
                    Intent intent = new Intent(getApplicationContext(), CreateMidPlanActivity.class);
                    editor = getPrefs.edit();
                    mainTitle =  edit_createPlan_title.getText().toString();
                    editor.putString("mainTitle", mainTitle);
                    editor.apply();
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"未建立起點",Toast.LENGTH_SHORT).show();
                }
            }else if(v.getId() == R.id.btn_createPlan_clean){
                Log.d(TAG, "onClick: btn_createPlan_clean");
                edit_createPlan_title.setText("");
                editor = getPrefs.edit();
                editor.putString("mainTitle", "");
                editor.putString("plan_flag", "0");
                editor.apply();
                finish();
            }
        }
    };
}