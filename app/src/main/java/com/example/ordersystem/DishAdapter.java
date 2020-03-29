package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {

    private int resourceId;
    private onClickListener mOnClickListeber=null;

    public DishAdapter(Context context, int textViewResourceId, List<Dish> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Dish dish=getItem(position);
        View view;
        ViewHolder viewHolder;

        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.dishImage=view.findViewById(R.id.dish_image);
            viewHolder.dishName=view.findViewById(R.id.dish_name);
            viewHolder.dishPrice=view.findViewById(R.id.dish_price);
            viewHolder.dishIntroduce=view.findViewById(R.id.dish_introduce);
            viewHolder.dishNum=view.findViewById(R.id.dish_num);
            viewHolder.dishSale=view.findViewById(R.id.dish_sale);
            viewHolder.dishAdd=view.findViewById(R.id.dish_add);
            viewHolder.dishSub=view.findViewById(R.id.dish_sub);
            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.dishName.setText(dish.getName());
        viewHolder.dishPrice.setText(dish.getPrice()+"");
        viewHolder.dishImage.setImageBitmap(dish.getImage());
        viewHolder.dishIntroduce.setText(dish.getDescription());
        viewHolder.dishSale.setText("销量"+dish.getSale());
        viewHolder.dishNum.setText(dish.getNum()+"");

        if(PublicData.login_type!=1){
            viewHolder.dishSub.setVisibility(View.INVISIBLE);
            viewHolder.dishNum.setVisibility(View.INVISIBLE);
            viewHolder.dishAdd.setVisibility(View.INVISIBLE);
        }else {
            if (dish.getNum() == 0) {
                viewHolder.dishSub.setVisibility(View.INVISIBLE);
                viewHolder.dishNum.setVisibility(View.INVISIBLE);
                viewHolder.dishAdd.setVisibility(View.VISIBLE);
            } else {
                viewHolder.dishSub.setVisibility(View.VISIBLE);
                viewHolder.dishNum.setVisibility(View.VISIBLE);
                viewHolder.dishAdd.setVisibility(View.VISIBLE);
            }
        }

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
        ImageView dishImage;
        TextView dishName;
        TextView dishPrice;
        TextView dishIntroduce;
        TextView dishSale;
        TextView dishNum;
        ImageView dishAdd;
        ImageView dishSub;
    }
}
