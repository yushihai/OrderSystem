package com.example.ordersystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminDeleteCookDialog extends Dialog implements View.OnClickListener {

    EditText deletecook_account;
    SQLiteDatabase db;

    public AdminDeleteCookDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_deletecook_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        db=PublicData.dbHelper.getWritableDatabase();

        deletecook_account=findViewById(R.id.deletecook_account);
        Button deletecook_cancel=findViewById(R.id.deletecook_cancel);
        Button deletecook_submit=findViewById(R.id.deletecook_submit);

        deletecook_cancel.setOnClickListener(this);
        deletecook_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.deletecook_cancel:
                dismiss();
                break;
            case R.id.deletecook_submit:
                if(TextUtils.isEmpty(deletecook_account.getText()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else{
                    Cursor cursor=db.query("Cook",null,"account=?",new String[]{deletecook_account.getText().toString().trim()},null,null,null);
                    if(cursor.moveToFirst()) {
                        AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                                .setMessage("确定要删除吗？")
                                .setPositiveButton("确定", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.delete("Cook", "account=?", new String[]{deletecook_account.getText().toString().trim()});
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
                        Toast.makeText(getContext(), "该厨师不存在！", Toast.LENGTH_SHORT).show();
                        deletecook_account.setText("");
                    }
                }
                break;
        }
    }
}
