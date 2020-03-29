package com.example.ordersystem;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LookOrderItemDialog extends Dialog {

    SQLiteDatabase db;
    String orderid;
    String ordertime;
    private List<Order> orderList=new ArrayList<Order>();

    public LookOrderItemDialog(Context context,String orderid,String ordertime) {
        super(context);
        this.orderid=orderid;
        this.ordertime=ordertime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lookorder_item_dialog);

        db=PublicData.dbHelper.getWritableDatabase();
        initOrder();

        TextView lookorder_id=findViewById(R.id.lookorder_item_orderid);
        TextView lookorder_time=findViewById(R.id.lookorder_item_ordertime);
        ListView lookorder_listview=findViewById(R.id.lookorder_item_listview);
        OrderAdapter adapter=new OrderAdapter(getContext(),R.layout.order_item,orderList);
        Button lookorder_submit=findViewById(R.id.lookorder_item_submit);

        lookorder_id.setText(orderid);
        lookorder_time.setText(ordertime);
        lookorder_listview.setAdapter(adapter);
        lookorder_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initOrder(){
        Cursor cursor=db.query("Orders",null,"orderid=?",new String[]{orderid},null,null,null);
        if(cursor.moveToNext()){
            do{
                String order_name=cursor.getString(cursor.getColumnIndex("dishname"));
                int order_number=cursor.getInt(cursor.getColumnIndex("number"));
                int order_price=cursor.getInt(cursor.getColumnIndex("unitprice"));
                Order order=new Order(order_name,order_number,order_price);
                orderList.add(order);
            }while (cursor.moveToNext());
        }
    }
}
