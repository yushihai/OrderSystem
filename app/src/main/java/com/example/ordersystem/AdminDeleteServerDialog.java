package com.example.ordersystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminDeleteServerDialog extends Dialog implements View.OnClickListener {

    EditText deleteserver_account;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    public AdminDeleteServerDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_deleteserver_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        dbHelper=PublicData.dbHelper;
        db=dbHelper.getWritableDatabase();

        deleteserver_account=findViewById(R.id.deleteserver_account);
        Button deleteserver_cancel=findViewById(R.id.deleteserver_cancel);
        Button deleteserver_submit=findViewById(R.id.deleteserver_submit);

        deleteserver_cancel.setOnClickListener(this);
        deleteserver_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.deleteserver_cancel:
                dismiss();
                break;
            case R.id.deleteserver_submit:
                if(TextUtils.isEmpty(deleteserver_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else{
                    Cursor cursor=db.query("Server",null,"account=?",new String[]{deleteserver_account.getText().toString().trim()},null,null,null);
                    if(cursor.moveToFirst()) {
                        AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                                .setMessage("确定要删除吗？")
                                .setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.delete("Server", "account=?", new String[]{deleteserver_account.getText().toString().trim()});
                                        dismiss();
                                        Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("取消", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                    else {
                        Toast.makeText(getContext(), "没有该服务员！", Toast.LENGTH_SHORT).show();
                        deleteserver_account.setText("");
                    }
                }
                break;
        }
    }
}
