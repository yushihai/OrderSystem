package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ConsumeRecordAdapter extends ArrayAdapter<OrderMessage> {

    private int resourceId;

    public ConsumeRecordAdapter(Context context, int textViewResourceId, List<OrderMessage> objects){
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
            viewHolder.time=view.findViewById(R.id.consumerecord_time);
            viewHolder.total=view.findViewById(R.id.consumerecord_total);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.time.setText(orderMessage.getOrder_time());
        viewHolder.total.setText(orderMessage.getOrder_total()+"");
        return view;
    }

    class ViewHolder{
        TextView time;
        TextView total;
    }
}
