package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderMessageAdapter extends ArrayAdapter<OrderMessage> {

    private int resourceId;

    public OrderMessageAdapter(Context context, int textViewResourceId, List<OrderMessage> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        OrderMessage orderMessage=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.order_time=view.findViewById(R.id.lookorder_time);
            viewHolder.order_state=view.findViewById(R.id.lookorder_state);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        String state=orderMessage.getOrder_state();
        viewHolder.order_time.setText(orderMessage.getOrder_time());
        if(state.equals("1"))
            viewHolder.order_state.setText("未做餐");
        else if (state.equals("2"))
            viewHolder.order_state.setText("正在做餐");
        else
            viewHolder.order_state.setText("完成做餐");
        return view;
    }

    class ViewHolder{
        TextView order_time;
        TextView order_state;
    }
}
