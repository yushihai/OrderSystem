package com.example.ordersystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLookServerPwdDialog extends Dialog implements View.OnClickListener {

    EditText lookserverpwd_account;
    SQLiteDatabase db;
    AdminLookServerPwdResultDialog adminLookServerPwdResultDialog;

    public AdminLookServerPwdDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lookserverpwd_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        lookserverpwd_account=findViewById(R.id.lookserverpwd_account);
        Button lookserverpwd_cancel=findViewById(R.id.lookserverpwd_cancel);
        Button lookserverpwd_submit=findViewById(R.id.lookserverpwd_submit);

        lookserverpwd_cancel.setOnClickListener(this);
        lookserverpwd_submit.setOnClickListener(this);
    }

    protected void setAdminLookServerPwdResultDialog(AdminLookServerPwdResultDialog dialog){
        adminLookServerPwdResultDialog=dialog;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.lookserverpwd_cancel:
                dismiss();
                break;
            case R.id.lookserverpwd_submit:
                if(TextUtils.isEmpty(lookserverpwd_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else{
                    Cursor cursor=db.query("Server",null,"account=?",new String[]{lookserverpwd_account.getText().toString().trim()},null,null,null);
                    if(cursor.moveToFirst()) {
                        String account=cursor.getString(cursor.getColumnIndex("account"));
                        String pwd=cursor.getString(cursor.getColumnIndex("password"));
                        if(Build.VERSION.SDK_INT>=21)
                            adminLookServerPwdResultDialog.create();
                        adminLookServerPwdResultDialog.setAccount(account);
                        adminLookServerPwdResultDialog.setPwd(pwd);
                        adminLookServerPwdResultDialog.show();
                        dismiss();
                    }
                    else {
                        Toast.makeText(getContext(), "没有该服务员！", Toast.LENGTH_SHORT).show();
                        lookserverpwd_account.setText("");
                    }
                }
                break;
        }
    }
}
