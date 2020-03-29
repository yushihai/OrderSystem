package com.example.ordersystem;

import android.app.Dialog;
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

public class ServerLoginDialog extends Dialog implements View.OnClickListener {

    EditText serverlogin_account;
    EditText serverlogin_pwd;
    SQLiteDatabase db;
    TextView loginhint;
    FragmentManager manager;
    DrawerLayout drawerLayout;

    public ServerLoginDialog(Context context, TextView loginhint, FragmentManager manager, DrawerLayout drawerLayout) {
        super(context);
        this.manager=manager;
        this.loginhint=loginhint;
        this.drawerLayout=drawerLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverlogin_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        serverlogin_account=findViewById(R.id.serverlogin_account);
        serverlogin_pwd=findViewById(R.id.serverlogin_pwd);
        Button serverlogin_cancel=findViewById(R.id.serverlogin_cancel);
        Button serverlogin_submit=findViewById(R.id.serverlogin_submit);

        serverlogin_cancel.setOnClickListener(this);
        serverlogin_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.serverlogin_cancel:
                dismiss();
                break;
            case R.id.serverlogin_submit:
                if(TextUtils.isEmpty(serverlogin_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(serverlogin_pwd.getText()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else{
                    String account=serverlogin_account.getText().toString();
                    String pwd=serverlogin_pwd.getText().toString();
                    Cursor cursor=db.query("Server",null,"account=? and password=?",new String[]{account,pwd},null,null,null);
                    if(cursor.moveToFirst()){
                        PublicData.login_type=3;
                        PublicData.user=account;
                        loginhint.setText(account);
                        dismiss();
                        drawerLayout.closeDrawers();
                        FragmentTransaction transaction;
                        transaction=manager.beginTransaction();
                        ServerFragment serverFragment=new ServerFragment();
                        transaction.replace(R.id.frament,serverFragment);
                        transaction.commit();
                    }else {
                        serverlogin_account.setText("");
                        serverlogin_pwd.setText("");
                        Toast.makeText(getContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
