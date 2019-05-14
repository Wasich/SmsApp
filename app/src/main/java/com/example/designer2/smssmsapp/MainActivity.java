package com.example.designer2.smssmsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;
    private static final int My_permission_send_sms  = 0;
    EditText txtmsg,mob;
    Button sendbtn;
    String sms,mobile;
    String Sms,Phone;
    List<DBModel> dbModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbModelList = new ArrayList<DBModel>();
        txtmsg = findViewById(R.id.msg);
        mob = findViewById(R.id.mobile);
        sendbtn = findViewById(R.id.sms_send);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendMessage();
                sms = txtmsg.getText().toString();
                mobile = mob.getText().toString();

                if (sms.equals("")||mobile.equals("")){
                    Toast.makeText(MainActivity.this,"please fill all the fileds",Toast.LENGTH_SHORT).show();
                }else {
                    mDatabaseHelper = new DatabaseHelper(MainActivity.this);
                    mDatabaseHelper.insertIntoDB(mobile,sms);

                }
                txtmsg.setText("");
                mob.setText("");



            }
        });




        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS))
            {

            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},My_permission_send_sms);
            }

        }














    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.rv:
                Intent i  =  new Intent(getApplicationContext(),Records.class);
                getApplicationContext().startActivity(i);


                return true;

        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult (int requestCode,String Permissions[], int[] grantreults)
    {
        switch (requestCode){
            case My_permission_send_sms:
            {
                if (grantreults.length>0 && grantreults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"permission accessed",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this,"allow permission first",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
       protected void sendMessage(){
        Sms = txtmsg.getText().toString();
        Phone = mob.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(Phone,null,Sms,null,null);
        Toast.makeText(this,"Sms send",Toast.LENGTH_SHORT).show();
    }
}
