package com.example.ordersystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class ServerFragment extends Fragment implements View.OnClickListener {

    View view;
    EditText server_name;
    EditText server_orderid;
    SQLiteDatabase db;
    RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.server_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainMenu)getActivity()).setToolbarTitle("服务员中心");
        db=PublicData.dbHelper.getWritableDatabase();

        server_name=view.findViewById(R.id.server_name);
        server_orderid=view.findViewById(R.id.server_orderid);
        Button server_namefind=view.findViewById(R.id.server_namefind);
        Button server_orderidfind=view.findViewById(R.id.server_orderidfind);
        Button server_logout=view.findViewById(R.id.server_logout);
        relativeLayout=view.findViewById(R.id.server_layout);

        server_namefind.setOnClickListener(this);
        server_orderidfind.setOnClickListener(this);
        server_logout.setOnClickListener(this);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                relativeLayout.setFocusable(true);
                relativeLayout.setFocusableInTouchMode(true);
                relativeLayout.requestFocus();
                return false;
            }
        });
    }

    @Override
    public void onClick(View v){
        FragmentTransaction transaction;
        FragmentManager manager;
        Cursor cursor;
        switch (v.getId()){
            case R.id.server_namefind:
                cursor=db.query("User",null,"name=?",new String[]{server_name.getText().toString()},null,null,null);
                if(cursor.moveToNext()){
                    cursor=db.rawQuery("select * from UserOrder where user='"+server_name.getText().toString()+"' and state in ('1','2','3')",null);
                    if(cursor.moveToNext()){
                        PublicData.serverfindway=1;
                        PublicData.server_findvalue=server_name.getText().toString();
                        manager=getFragmentManager();
                        transaction=manager.beginTransaction();
                        ServerSettleAccountFragment serverSettleAccountFragment=new ServerSettleAccountFragment();
                        transaction.replace(R.id.frament,serverSettleAccountFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else {
                        Toast.makeText(getContext(), "无可结账单", Toast.LENGTH_SHORT).show();
                        server_name.setText("");
                        server_orderid.setText("");
                    }
                }else {
                    Toast.makeText(getContext(), "该账号不存在", Toast.LENGTH_SHORT).show();
                    server_name.setText("");
                    server_orderid.setText("");
                }
                break;
            case R.id.server_orderidfind:
                cursor=db.query("UserOrder",null,"orderid=?",new String[]{server_orderid.getText().toString()},null,null,null);
                if(cursor.moveToNext()){
                    cursor=db.rawQuery("select * from UserOrder where orderid='"+server_orderid.getText().toString()+"' and state in ('1','2','3')",null);
                    if(cursor.moveToNext()){
                        PublicData.serverfindway=2;
                        PublicData.server_findvalue=server_orderid.getText().toString();
                        manager=getFragmentManager();
                        transaction=manager.beginTransaction();
                        ServerSettleAccountFragment serverSettleAccountFragment=new ServerSettleAccountFragment();
                        transaction.replace(R.id.frament,serverSettleAccountFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else {
                        Toast.makeText(getContext(), "无可结账单", Toast.LENGTH_SHORT).show();
                        server_name.setText("");
                        server_orderid.setText("");
                    }
                }else {
                    Toast.makeText(getContext(), "该单号不存在", Toast.LENGTH_SHORT).show();
                    server_name.setText("");
                    server_orderid.setText("");
                }
                break;
            case R.id.server_logout:
                PublicData.login_type=0;
                PublicData.user="";
                ((MainMenu)getActivity()).login_hint.setText("你好，请登录");
                ((MainMenu)getActivity()).getDrawerLayout().openDrawer(Gravity.LEFT);
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                DishFragment dishFragment=new DishFragment();
                transaction.replace(R.id.frament,dishFragment);
                transaction.commit();
                break;
        }
    }
}
