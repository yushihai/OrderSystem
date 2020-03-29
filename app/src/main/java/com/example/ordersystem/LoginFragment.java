package com.example.ordersystem;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private View view;
    Toolbar toolbar;
    CheckBox remember_pwd;
    EditText login_user,login_pwd;
    SQLiteDatabase db;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.login_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainMenu) getActivity()).setToolbarTitle("用户登录");
        db=PublicData.dbHelper.getWritableDatabase();

        login_user=view.findViewById(R.id.name_text);
        login_pwd=view.findViewById(R.id.pwd_text);
        Button regist=view.findViewById(R.id.regist);
        Button login=view.findViewById(R.id.login);
        toolbar=view.findViewById(R.id.toolbar);
        remember_pwd=view.findViewById(R.id.remember_pwd);

        regist.setOnClickListener(this);
        login.setOnClickListener(this);

        pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            login_user.setText(account);
            login_pwd.setText(password);
            remember_pwd.setChecked(true);
        }
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.regist:
                FragmentManager manager=getFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                RegistFragment registFragment=new RegistFragment();
                transaction.replace(R.id.frament,registFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.login:
                if (TextUtils.isEmpty(login_user.getText().toString().trim()))
                    Toast.makeText(getContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(login_pwd.getText().toString().trim()))
                    Toast.makeText(getContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                else {
                    String loginname=login_user.getText().toString();
                    String loginpwd=login_pwd.getText().toString();
                    Cursor cursor=db.query("User",null,"name=? and password=?",new String[]{loginname,loginpwd},null,null,null);
                    if(cursor.moveToFirst()){
                        if(remember_pwd.isChecked()){
                            editor=pref.edit();
                            editor.putBoolean("remember_password",true);
                            editor.putString("account",login_user.getText().toString());
                            editor.putString("password",login_pwd.getText().toString());
                            editor.commit();
                        }else {
                            editor=pref.edit();
                            editor.putBoolean("remember_password",false);
                            editor.commit();
                        }

                        PublicData.user=loginname;
                        int type=cursor.getInt(cursor.getColumnIndex("type"));
                        PublicData.login_type=type;
                        ((MainMenu)getActivity()).setLogin_hint("你好，"+loginname);
                        ((MainMenu)getActivity()).getHead_portrait().setImageResource(cursor.getInt(cursor.getColumnIndex("headportrait")));

                        FragmentManager fragmentManager=getFragmentManager();
                        transaction=fragmentManager.beginTransaction();
                        if(type==1) {
                            DishFragment dishFragment = new DishFragment();
                            transaction.replace(R.id.frament, dishFragment);
                            transaction.commit();
                            ((MainMenu) getActivity()).getDrawerLayout().openDrawer(Gravity.LEFT);
                        }else {
                            AdminFragment adminFragment=new AdminFragment();
                            transaction.replace(R.id.frament,adminFragment);
                            transaction.commit();
                        }
                    }else
                        Toast.makeText(getContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
