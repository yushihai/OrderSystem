package com.example.ordersystem;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText regist_user,regist_pwd,regist_confirm,regist_qq,regist_email;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.regist_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db=PublicData.dbHelper.getWritableDatabase();
        ((MainMenu)getActivity()).setToolbarTitle("注册");

        regist_user=view.findViewById(R.id.regist_userinput);
        regist_pwd=view.findViewById(R.id.regist_pwdinput);
        regist_confirm=view.findViewById(R.id.regist_confirminput);
        regist_qq=view.findViewById(R.id.regist_qqinput);
        regist_email=view.findViewById(R.id.regist_emailinput);
        Button regist_consumer=view.findViewById(R.id.regist_consumerregist);
        Button regist_manager=view.findViewById(R.id.regist_managerregist);

        regist_consumer.setOnClickListener(this);
        regist_manager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (TextUtils.isEmpty(regist_user.getText().toString().trim()))
            Toast.makeText(getContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(regist_pwd.getText().toString().trim()))
            Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(regist_confirm.getText().toString().trim()))
            Toast.makeText(getContext(),"密码确认不能为空",Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(regist_qq.getText().toString().trim()))
                Toast.makeText(getContext(),"QQ不能为空",Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty(regist_email.getText().toString().trim()))
            Toast.makeText(getContext(),"邮箱不能为空",Toast.LENGTH_SHORT).show();
        else if(!(regist_pwd.getText().toString().equals(regist_confirm.getText().toString())))
            Toast.makeText(getContext(),"两次输入的密码不同",Toast.LENGTH_SHORT).show();
        else {
            String user=regist_user.getText().toString();
            String pwd=regist_pwd.getText().toString();
            String qq=regist_qq.getText().toString();
            String email=regist_email.getText().toString();

            ContentValues values=new ContentValues();
            values.put("name",user);
            values.put("password",pwd);
            values.put("qq",qq);
            values.put("email",email);
            values.put("headportrait",R.mipmap.headportrait_default);
            switch (v.getId()) {
                case R.id.regist_consumerregist:
                    values.put("type",1);
                    db.insert("User",null,values);
                    getFragmentManager().popBackStack();
                    Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.regist_managerregist:
                    values.put("type",2);
                    db.insert("User",null,values);
                    getFragmentManager().popBackStack();
                    Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
