package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CookLookOrdersAdapter extends ArrayAdapter<OrderMessage> {

    private int resourceId;

    public CookLookOrdersAdapter(Context context, int textViewResourceId, List<OrderMessage> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderMessage orderMessage=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.orderid=view.findViewById(R.id.cook_lookorders_id);
            viewHolder.orderstate=view.findViewById(R.id.cook_lookorders_state);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.orderid.setText(orderMessage.getOrder_id());
        if(orderMessage.getOrder_state().equals("1"))
            viewHolder.orderstate.setText("未做餐");
        else if (orderMessage.getOrder_state().equals("2"))
            viewHolder.orderstate.setText("正在做餐");
        return view;
    }

    class ViewHolder{
        TextView orderid;
        TextView orderstate;
    }
}
