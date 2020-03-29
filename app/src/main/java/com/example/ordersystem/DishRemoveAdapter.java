package com.example.ordersystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DishRemoveAdapter extends ArrayAdapter<String> {

    private int resourceId;
    private onClickListener mOnClickListeber=null;

    public DishRemoveAdapter(Context context, int textViewResourceId, List<String> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String removeDish=getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.textView=view.findViewById(R.id.dishremove_name);
            viewHolder.checkBox=view.findViewById(R.id.dishremove_checkbox);
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.textView.setText(removeDish);
        if(PublicData.visible==0) {
            viewHolder.checkBox.setVisibility(View.INVISIBLE);
            viewHolder.checkBox.setChecked(false);
        }
        else {
            viewHolder.checkBox.setVisibility(View.VISIBLE);
            if(PublicData.dishremove_id.contains(position))
                viewHolder.checkBox.setChecked(true);
            else
                viewHolder.checkBox.setChecked(false);
        }
        Log.d("dd",PublicData.dishremove_id.size()+"");

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
        TextView textView;
        CheckBox checkBox;
    }
}
