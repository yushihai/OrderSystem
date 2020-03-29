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

public class AdminAddCookDialog extends Dialog implements View.OnClickListener {

    EditText addcook_account;
    EditText addcook_pwd;
    SQLiteDatabase db;

    public AdminAddCookDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_addcook_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        addcook_account=findViewById(R.id.addcook_account);
        addcook_pwd=findViewById(R.id.addcook_pwd);
        Button addcook_cancel=findViewById(R.id.addcook_cancel);
        Button addcook_submit=findViewById(R.id.addcook_submit);

        addcook_cancel.setOnClickListener(this);
        addcook_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.addcook_cancel:
                dismiss();
                break;
            case R.id.addcook_submit:
                if(TextUtils.isEmpty(addcook_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(addcook_pwd.getText()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else{
                    ContentValues values=new ContentValues();
                    values.put("account",addcook_account.getText().toString().trim());
                    values.put("password",addcook_pwd.getText().toString().trim());
                    db.insert("Cook",null,values);
                    values.clear();
                    dismiss();
                    Toast.makeText(getContext(),"添加成功！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
