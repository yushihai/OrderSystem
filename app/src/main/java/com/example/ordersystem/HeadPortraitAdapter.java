package com.example.ordersystem;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class HeadPortraitAdapter extends ArrayAdapter<HeadPortrait>{

    private int resourceId;
    private onClickListener mOnClickListeber=null;

    public HeadPortraitAdapter(Context context, int textViewResourceId, List<HeadPortrait> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        HeadPortrait headPortrait=getItem(position);
        View view;
        ViewHolder holder;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            holder=new ViewHolder();
            holder.imageView1=view.findViewById(R.id.headportrait_one);
            holder.imageView2=view.findViewById(R.id.headportrait_two);
            holder.imageView3=view.findViewById(R.id.headportrait_three);

            view.setTag(holder);
        }
        else {
            view=convertView;
            holder=(ViewHolder)view.getTag();
        }
        holder.imageView1.setImageResource(headPortrait.getHeadportrait1());
        holder.imageView2.setImageResource(headPortrait.getHeadportrait2());
        holder.imageView3.setImageResource(headPortrait.getHeadportrait3());

        if(mOnClickListeber!=null){
            mOnClickListeber.onClick(view,position);
        }

        return view;
    }

    public interface onClickListener{
        void onClick(View view,int position);
    }

    public void setOnClickListeber(onClickListener mOnClickListeber){
        this.mOnClickListeber=mOnClickListeber;
    }

    class ViewHolder{
        ImageView imageView1;
        ImageView imageView2;
        ImageView imageView3;
    }
}
