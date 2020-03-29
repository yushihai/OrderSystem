package com.example.ordersystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ServerSettleAccountAdapter extends ArrayAdapter<OrderMessage> {

    private int resourceId;
    private onClickListener mOnClickListeber=null;

    public ServerSettleAccountAdapter(Context context, int textViewResourceId, List<OrderMessage> objects){
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
            viewHolder.orderid=view.findViewById(R.id.server_settleaccount_id);
            viewHolder.ordername=view.findViewById(R.id.server_settleaccount_name);
            viewHolder.orderstate=view.findViewById(R.id.server_settleaccount_state);
            viewHolder.ordertotal=view.findViewById(R.id.server_settleaccount_total);
            viewHolder.ordertime=view.findViewById(R.id.server_settleaccount_time);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.orderid.setText(orderMessage.getOrder_id());
        viewHolder.ordername.setText(orderMessage.getOrder_name());
        viewHolder.ordertotal.setText("¥"+orderMessage.getOrder_total());
        viewHolder.ordertime.setText(orderMessage.getOrder_time());
        String state=orderMessage.getOrder_state();
        if(state.equals("1"))
            viewHolder.orderstate.setText("未做餐");
        else if (state.equals("2"))
            viewHolder.orderstate.setText("正在做餐");
        else
            viewHolder.orderstate.setText("完成做餐");

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
        TextView orderid;
        TextView ordername;
        TextView orderstate;
        TextView ordertotal;
        TextView ordertime;
    }
}
