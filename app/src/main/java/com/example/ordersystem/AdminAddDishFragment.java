package com.example.ordersystem;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AdminAddDishFragment extends Fragment implements View.OnClickListener {

    View view;
    Button return_pre;
    EditText input_name,input_price,input_introduce;
    TextView number;
    ImageView photo;
    DatabaseHelper dbHelper;
    RelativeLayout relativeLayout;

    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.admin_adddish_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper=((MainMenu)getActivity()).getDbHelper();

        return_pre=view.findViewById(R.id.return_pre);
        input_name=view.findViewById(R.id.input_name);
        input_price=view.findViewById(R.id.input_price);
        input_introduce=view.findViewById(R.id.input_introduce);
        number=view.findViewById(R.id.number);
        photo=view.findViewById(R.id.photo);
        Button take_photo=view.findViewById(R.id.take_photo);
        Button choose_photo=view.findViewById(R.id.choose_photo);
        Button commit=view.findViewById(R.id.commit);

        return_pre.setOnClickListener(this);
        take_photo.setOnClickListener(this);
        choose_photo.setOnClickListener(this);
        commit.setOnClickListener(this);

        SharedPreferences pref=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        int num=pref.getInt("allDishNumber",-50)+1;
        number.setText(num+"");

        relativeLayout=view.findViewById(R.id.admin_adddish_layout);
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
        switch (v.getId()){
            case R.id.return_pre:
                getFragmentManager().popBackStack();
                break;
            case R.id.take_photo:
                ((MainMenu)getActivity()).takePhoto(number.getText().toString(),photo);
                break;
            case R.id.choose_photo:
                ((MainMenu)getActivity()).choosePhoto(number.getText().toString(),photo);
                break;
            case R.id.commit:
                click_commit();
                break;
        }
    }

    private void click_commit(){
        if(TextUtils.isEmpty(input_name.getText().toString()))
            Toast.makeText(getContext(),"菜名不能为空",Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(input_price.getText().toString()))
            Toast.makeText(getContext(),"价格不能为空",Toast.LENGTH_SHORT).show();
        else if(TextUtils.isEmpty(input_introduce.getText().toString()))
            Toast.makeText(getContext(),"简介不能为空",Toast.LENGTH_SHORT).show();
        else if(photo.getDrawable()==null)
            Toast.makeText(getContext(),"照片为空",Toast.LENGTH_SHORT).show();
        else{
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            try {
                String name=input_name.getText().toString();
                int price=Integer.parseInt(input_price.getText().toString());
                String introduce=input_introduce.getText().toString();
                int num=Integer.parseInt(number.getText().toString());
                int sale=0;

                ContentValues values=new ContentValues();
                values.put("name",name);
                values.put("price",price);
                values.put("introduce",introduce);
                values.put("number",num);
                values.put("sale",sale);
                db.insert("Dish",null,values);
                values.clear();

                SharedPreferences pref=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                int allDishNumber=pref.getInt("allDishNumber",-50);
                SharedPreferences.Editor editor=getActivity().getSharedPreferences("data",Context.MODE_PRIVATE).edit();
                editor.putInt("allDishNumber",allDishNumber+1);
                editor.commit();

                Toast.makeText(getContext(),"添加成功！",Toast.LENGTH_SHORT).show();
                input_name.setText("");
                input_price.setText("");
                input_introduce.setText("");
                number.setText((allDishNumber+2)+"");
                photo.setImageDrawable(null);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
