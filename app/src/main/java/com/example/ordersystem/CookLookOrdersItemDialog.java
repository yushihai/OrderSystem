package com.example.ordersystem;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CookLookOrdersItemDialog extends Dialog implements View.OnClickListener {

    SQLiteDatabase db;
    CookLookOrdersAdapter adapter;
    int position;
    List<Order> orderList=new ArrayList<Order>();
    List<OrderMessage> orderMessageList;
    OrderMessage orderMessage;

    public CookLookOrdersItemDialog(Context context,CookLookOrdersAdapter adapter,int position,List<OrderMessage> orderMessageList) {
        super(context);
        this.adapter=adapter;
        this.position=position;
        this.orderMessageList=orderMessageList;
        orderMessage=orderMessageList.get(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cook_lookorders_item_dialog);

        db=PublicData.dbHelper.getWritableDatabase();
        initOrders();

        TextView orderid=findViewById(R.id.cook_lookorders_item_orderid);
        TextView ordertime=findViewById(R.id.cook_lookorders_item_ordertime);
        TextView ordername=findViewById(R.id.cook_lookorders_item_orderusername);
        ListView listView=findViewById(R.id.cook_lookorders_item_listview);
        Button submit=findViewById(R.id.cook_lookorders_item_submit);
        CookLookOrdersItemDialogAdapter adapter=new CookLookOrdersItemDialogAdapter(getContext(),R.layout.cook_lookorders_item_dialog_item,orderList);

        if(orderMessage.getOrder_state().equals("1"))
            submit.setText("开始做餐");
        else if (orderMessage.getOrder_state().equals("2"))
            submit.setText("完成做餐");

        orderid.setText(orderMessage.getOrder_id());
        ordertime.setText(orderMessage.getOrder_time());
        ordername.setText(orderMessage.getOrder_name());
        listView.setAdapter(adapter);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        ContentValues values=new ContentValues();
        if(orderMessage.getOrder_state().equals("1")){
            orderMessage.setOrder_state("2");
            values.put("state","2");
            db.update("UserOrder",values,"orderid=?",new String[]{orderMessage.getOrder_id()});
            adapter.notifyDataSetChanged();
            dismiss();
        }else if(orderMessage.getOrder_state().equals("2")){
            values.put("state","3");
            db.update("UserOrder",values,"orderid=?",new String[]{orderMessage.getOrder_id()});
            orderMessageList.remove(position);
            adapter.notifyDataSetChanged();
            dismiss();
        }
    }

    private void initOrders(){
        Cursor cursor=db.query("Orders",null,"orderid=?",new String[]{orderMessage.getOrder_id()},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String dishname=cursor.getString(cursor.getColumnIndex("dishname"));
                int number=cursor.getInt(cursor.getColumnIndex("number"));
                Order order=new Order(dishname,number,0);
                orderList.add(order);
            }while (cursor.moveToNext());
        }
    }
}
