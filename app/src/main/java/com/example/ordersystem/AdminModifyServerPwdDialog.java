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

public class AdminModifyServerPwdDialog extends Dialog implements View.OnClickListener {

    EditText modifyserverpwd_account;
    EditText modifyserverpwd_pwd;
    SQLiteDatabase db;

    public AdminModifyServerPwdDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_modifyserverpwd_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        modifyserverpwd_account=findViewById(R.id.modifyserverpwd_account);
        modifyserverpwd_pwd=findViewById(R.id.modifyserverpwd_pwd);
        Button modifyserverpwd_cancel=findViewById(R.id.modifyserverpwd_cancel);
        Button modifyserverpwd_submit=findViewById(R.id.modifyserverpwd_submit);

        modifyserverpwd_cancel.setOnClickListener(this);
        modifyserverpwd_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.modifyserverpwd_cancel:
                dismiss();
                break;
            case R.id.modifyserverpwd_submit:
                if(TextUtils.isEmpty(modifyserverpwd_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if(TextUtils.isEmpty(modifyserverpwd_pwd.getText()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else{
                    String account=modifyserverpwd_account.getText().toString();
                    String pwd=modifyserverpwd_pwd.getText().toString();
                    Cursor cursor=db.query("Server",null,"account=?",new String[]{account},null,null,null);
                    if(cursor.moveToFirst()){
                        ContentValues values=new ContentValues();
                        values.put("password",pwd);
                        db.update("Server",values,"account=?",new String[]{account});
                        dismiss();
                        Toast.makeText(getContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        modifyserverpwd_account.setText("");
                        modifyserverpwd_pwd.setText("");
                        Toast.makeText(getContext(),"该账号不存在！",Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}
