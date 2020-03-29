package com.example.ordersystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CookLoginDialog extends Dialog implements View.OnClickListener {

    EditText cooklogin_account;
    EditText cooklogin_pwd;
    SQLiteDatabase db;
    TextView loginhint;
    FragmentManager manager;
    DrawerLayout drawerLayout;

    public CookLoginDialog(Context context, TextView loginhint, FragmentManager manager, DrawerLayout drawerLayout) {
        super(context);
        this.manager=manager;
        this.loginhint=loginhint;
        this.drawerLayout=drawerLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cooklogin_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        cooklogin_account=findViewById(R.id.cooklogin_account);
        cooklogin_pwd=findViewById(R.id.cooklogin_pwd);
        Button cooklogin_cancel=findViewById(R.id.cooklogin_cancel);
        Button cooklogin_submit=findViewById(R.id.cooklogin_submit);

        cooklogin_cancel.setOnClickListener(this);
        cooklogin_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.cooklogin_cancel:
                dismiss();
                break;
            case R.id.cooklogin_submit:
                if(TextUtils.isEmpty(cooklogin_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(cooklogin_pwd.getText()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else{
                    String account=cooklogin_account.getText().toString();
                    String pwd=cooklogin_pwd.getText().toString();
                    Cursor cursor=db.query("Cook",null,"account=? and password=?",new String[]{account,pwd},null,null,null);
                    if(cursor.moveToFirst()){
                        PublicData.login_type=4;
                        PublicData.user=account;
                        loginhint.setText(account);
                        dismiss();
                        drawerLayout.closeDrawers();
                        FragmentTransaction transaction;
                        transaction=manager.beginTransaction();
                        CookFragment cookFragment=new CookFragment();
                        transaction.replace(R.id.frament,cookFragment);
                        transaction.commit();
                    }else {
                        cooklogin_account.setText("");
                        cooklogin_pwd.setText("");
                        Toast.makeText(getContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
