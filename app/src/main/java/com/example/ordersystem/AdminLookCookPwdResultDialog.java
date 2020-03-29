package com.example.ordersystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminLookCookPwdResultDialog extends Dialog {

    TextView lookcookpwdresult_account;
    TextView lookcookpwdresult_pwd;

    public AdminLookCookPwdResultDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_lookcookpwdresult_dialog);

        lookcookpwdresult_account=findViewById(R.id.lookcookpwdresult_account);
        lookcookpwdresult_pwd=findViewById(R.id.lookcookpwdresult_pwd);
        Button lookcookpwdresult_ok=findViewById(R.id.lookcookpwdresult_submit);

        lookcookpwdresult_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    protected void setAccount(String account){
        lookcookpwdresult_account.setText(account);
    }

    protected void setPwd(String pwd){
        lookcookpwdresult_pwd.setText(pwd);
    }
}
