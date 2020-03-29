package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServerSettleAccountDetailAdapter extends ArrayAdapter<Order> {

    private int resourceId;

    public ServerSettleAccountDetailAdapter(Context context, int textViewResourceId, List<Order> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Order order=getItem(position);
        View view;
        ViewHolder viewHloder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHloder=new ViewHolder();
            viewHloder.order_name=view.findViewById(R.id.orderitem_name);
            viewHloder.order_num=view.findViewById(R.id.orderitem_num);
            viewHloder.order_price=view.findViewById(R.id.orderitem_price);
            view.setTag(viewHloder);
        }else {
            view=convertView;
            viewHloder=(ViewHolder) view.getTag();
        }
        viewHloder.order_name.setText(order.getName());
        viewHloder.order_num.setText("x"+order.getNum());
        viewHloder.order_price.setText("¥"+order.getPrice()*order.getNum());
        return view;
    }

    class ViewHolder{
        TextView order_name;
        TextView order_num;
        TextView order_price;
    }
}
