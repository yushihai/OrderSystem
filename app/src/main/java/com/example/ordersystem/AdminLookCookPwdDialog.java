package com.example.ordersystem;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLookCookPwdDialog extends Dialog implements View.OnClickListener {

    EditText lookcookpwd_account;
    SQLiteDatabase db;
    AdminLookCookPwdResultDialog adminLookCookPwdResultDialog;

    public AdminLookCookPwdDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lookcookpwd_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        lookcookpwd_account=findViewById(R.id.lookcookpwd_account);
        Button lookcookpwd_cancel=findViewById(R.id.lookcookpwd_cancel);
        Button lookcookpwd_submit=findViewById(R.id.lookcookpwd_submit);

        lookcookpwd_cancel.setOnClickListener(this);
        lookcookpwd_submit.setOnClickListener(this);
    }

    protected void setAdminLookCookPwdResultDialog(AdminLookCookPwdResultDialog dialog){
        adminLookCookPwdResultDialog=dialog;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.lookcookpwd_cancel:
                dismiss();
                break;
            case R.id.lookcookpwd_submit:
                if(TextUtils.isEmpty(lookcookpwd_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else{
                    Cursor cursor=db.query("Cook",null,"account=?",new String[]{lookcookpwd_account.getText().toString().trim()},null,null,null);
                    if(cursor.moveToFirst()) {
                        String account=cursor.getString(cursor.getColumnIndex("account"));
                        String pwd=cursor.getString(cursor.getColumnIndex("password"));
                        if(Build.VERSION.SDK_INT>=21)
                            adminLookCookPwdResultDialog.create();
                        adminLookCookPwdResultDialog.setAccount(account);
                        adminLookCookPwdResultDialog.setPwd(pwd);
                        adminLookCookPwdResultDialog.show();
                        dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "没有该厨师！", Toast.LENGTH_SHORT).show();
                        lookcookpwd_account.setText("");
                    }
                }
                break;
        }
    }
}
