package com.example.ordersystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminLookServerPwdResultDialog extends Dialog {

    TextView lookserverpwdresult_account;
    TextView lookserverpwdresult_pwd;

    public AdminLookServerPwdResultDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lookserverpwdresult_dialog);

        lookserverpwdresult_account=findViewById(R.id.lookserverpwdresult_account);
        lookserverpwdresult_pwd=findViewById(R.id.lookserverpwdresult_pwd);
        Button lookserverpwdresult_ok=findViewById(R.id.lookserverpwdresult_submit);

        lookserverpwdresult_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    protected void setAccount(String account){
        lookserverpwdresult_account.setText(account);
    }

    protected void setPwd(String pwd){
        lookserverpwdresult_pwd.setText(pwd);
    }
}
