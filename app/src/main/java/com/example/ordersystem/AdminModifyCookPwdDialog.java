package com.example.ordersystem;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminModifyCookPwdDialog extends Dialog implements View.OnClickListener {

    EditText modifycookpwd_account;
    EditText modifycookpwd_pwd;
    SQLiteDatabase db;

    public AdminModifyCookPwdDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modifycookpwd_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        modifycookpwd_account=findViewById(R.id.modifycookpwd_account);
        modifycookpwd_pwd=findViewById(R.id.modifycookpwd_pwd);
        Button modifycookpwd_cancel=findViewById(R.id.modifycookpwd_cancel);
        Button modifycookpwd_submit=findViewById(R.id.modifycookpwd_submit);

        modifycookpwd_cancel.setOnClickListener(this);
        modifycookpwd_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.modifycookpwd_cancel:
                dismiss();
                break;
            case R.id.modifycookpwd_submit:
                if(TextUtils.isEmpty(modifycookpwd_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(modifycookpwd_pwd.getText()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else{
                    String account=modifycookpwd_account.getText().toString();
                    String pwd=modifycookpwd_pwd.getText().toString();
                    Cursor cursor=db.query("Cook",null,"account=?",new String[]{account},null,null,null);
                    if(cursor.moveToFirst()){
                        ContentValues values=new ContentValues();
                        values.put("password",pwd);
                        db.update("Cook",values,"account=?",new String[]{account});
                        dismiss();
                        Toast.makeText(getContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        modifycookpwd_account.setText("");
                        modifycookpwd_pwd.setText("");
                        Toast.makeText(getContext(),"该账号不存在！",Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}
