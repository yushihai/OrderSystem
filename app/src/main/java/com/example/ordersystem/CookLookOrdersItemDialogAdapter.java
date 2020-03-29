package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CookLookOrdersItemDialogAdapter extends ArrayAdapter<Order> {

    private int resourceId;

    public CookLookOrdersItemDialogAdapter(Context context, int textViewResourceId, List<Order> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.dishname=view.findViewById(R.id.cook_lookorders_item_dialog_name);
            viewHolder.dishnum=view.findViewById(R.id.cook_lookorders_item_dialog_num);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.dishname.setText(order.getName());
        viewHolder.dishnum.setText("x"+order.getNum());
        return view;
    }

    class ViewHolder{
        TextView dishname;
        TextView dishnum;
    }
}
