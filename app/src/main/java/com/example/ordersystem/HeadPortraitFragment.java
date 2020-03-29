package com.example.ordersystem;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HeadPortraitFragment extends Fragment{

    View view;
    private List<HeadPortrait> headPortraitList=new ArrayList<HeadPortrait>();
    ImageView head_portrait;
    ImageView imageView;
    Bitmap bitmap;
    DrawerLayout drawerLayout;
    private int[] hp;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.headportrait_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        hp=new int[]{R.mipmap.headportrait_1,R.mipmap.headportrait_2,R.mipmap.headportrait_3,R.mipmap.headportrait_4,R.mipmap.headportrait_5,R.mipmap.headportrait_6,
                    R.mipmap.headportrait_7,R.mipmap.headportrait_8,R.mipmap.headportrait_9,R.mipmap.headportrait_10,R.mipmap.headportrait_11,R.mipmap.headportrait_12,
                    R.mipmap.headportrait_13,R.mipmap.headportrait_14,R.mipmap.headportrait_15,R.mipmap.headportrait_16,R.mipmap.headportrait_17,R.mipmap.headportrait_18,
                    R.mipmap.headportrait_19,R.mipmap.headportrait_20,R.mipmap.headportrait_21,R.mipmap.headportrait_22,R.mipmap.headportrait_23,R.mipmap.headportrait_24};
        db=PublicData.dbHelper.getWritableDatabase();

        head_portrait=((MainMenu)getActivity()).getHead_portrait();
        drawerLayout=((MainMenu)getActivity()).getDrawerLayout();

        initHeadPortraits();
        HeadPortraitAdapter adapter=new HeadPortraitAdapter(getContext(),R.layout.headportrait_item,headPortraitList);
        ListView listView=view.findViewById(R.id.headportrait_listview);
        listView.setAdapter(adapter);

        adapter.setOnClickListeber(new HeadPortraitAdapter.onClickListener() {
            @Override
            public void onClick(View view,final int position) {
                ImageView hp1=view.findViewById(R.id.headportrait_one);
                ImageView hp2=view.findViewById(R.id.headportrait_two);
                ImageView hp3=view.findViewById(R.id.headportrait_three);
                hp1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v,position,0);
                    }
                });
                hp2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v,position,1);
                    }
                });
                hp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        click(v,position,2);
                    }
                });
            }
        });
    }

    private void click(final View v,final int row,final int column){
        if(PublicData.login_type==1||PublicData.login_type==2) {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("友情提示")
                    .setMessage("确定更换头像吗")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues values = new ContentValues();
                            values.put("headportrait", hp[row * 3 + column]);
                            db.update("User", values, "name=?", new String[]{PublicData.user});

                            imageView = (ImageView) v;
                            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            head_portrait.setImageBitmap(bitmap);
                            drawerLayout.openDrawer(Gravity.LEFT);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(true).show();
        }
    }

    private void initHeadPortraits(){
        HeadPortrait headPortrait1=new HeadPortrait(R.mipmap.headportrait_1,R.mipmap.headportrait_2,R.mipmap.headportrait_3);
        headPortraitList.add(headPortrait1);
        HeadPortrait headPortrait2=new HeadPortrait(R.mipmap.headportrait_4,R.mipmap.headportrait_5,R.mipmap.headportrait_6);
        headPortraitList.add(headPortrait2);
        HeadPortrait headPortrait3=new HeadPortrait(R.mipmap.headportrait_7,R.mipmap.headportrait_8,R.mipmap.headportrait_9);
        headPortraitList.add(headPortrait3);
        HeadPortrait headPortrait4=new HeadPortrait(R.mipmap.headportrait_10,R.mipmap.headportrait_11,R.mipmap.headportrait_12);
        headPortraitList.add(headPortrait4);
        HeadPortrait headPortrait5=new HeadPortrait(R.mipmap.headportrait_13,R.mipmap.headportrait_14,R.mipmap.headportrait_15);
        headPortraitList.add(headPortrait5);
        HeadPortrait headPortrait6=new HeadPortrait(R.mipmap.headportrait_16,R.mipmap.headportrait_17,R.mipmap.headportrait_18);
        headPortraitList.add(headPortrait6);
        HeadPortrait headPortrait7=new HeadPortrait(R.mipmap.headportrait_19,R.mipmap.headportrait_20,R.mipmap.headportrait_21);
        headPortraitList.add(headPortrait7);
        HeadPortrait headPortrait8=new HeadPortrait(R.mipmap.headportrait_22,R.mipmap.headportrait_23,R.mipmap.headportrait_24);
        headPortraitList.add(headPortrait8);
    }
}
