package com.example.ordersystem;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminAddServerDialog extends Dialog implements View.OnClickListener {

    EditText addserver_account;
    EditText addserver_pwd;
    SQLiteDatabase db;

    public AdminAddServerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_addserver_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        addserver_account=findViewById(R.id.addserver_account);
        addserver_pwd=findViewById(R.id.addserver_pwd);
        Button addserver_cancel=findViewById(R.id.addserver_cancel);
        Button addserver_submit=findViewById(R.id.addserver_submit);

        addserver_cancel.setOnClickListener(this);
        addserver_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.addserver_cancel:
                dismiss();
                break;
            case R.id.addserver_submit:
                if(TextUtils.isEmpty(addserver_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(addserver_pwd.getText()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else{
                    ContentValues values=new ContentValues();
                    values.put("account",addserver_account.getText().toString().trim());
                    values.put("password",addserver_pwd.getText().toString().trim());
                    db.insert("Server",null,values);
                    values.clear();
                    dismiss();
                    Toast.makeText(getContext(),"添加成功！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
