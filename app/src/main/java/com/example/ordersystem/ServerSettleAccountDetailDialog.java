package com.example.ordersystem;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ServerSettleAccountDetailDialog extends Dialog {

    SQLiteDatabase db;
    String orderid;
    private List<Order> orderList=new ArrayList<Order>();

    public ServerSettleAccountDetailDialog(Context context,String orderid) {
        super(context);
        this.orderid=orderid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_settleaccount_detail_dialog);

        db=PublicData.dbHelper.getWritableDatabase();
        initOrders();

        ListView listView=findViewById(R.id.settleaccount_detail_listview);
        ServerSettleAccountDetailAdapter adapter=new ServerSettleAccountDetailAdapter(getContext(),R.layout.order_item,orderList);
        Button submit=findViewById(R.id.settleaccount_detail_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        listView.setAdapter(adapter);
    }

    private void initOrders(){
        Cursor cursor=db.query("Orders",null,"orderid=?",new String[]{orderid},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String dishname=cursor.getString(cursor.getColumnIndex("dishname"));
                int number=cursor.getInt(cursor.getColumnIndex("number"));
                int initprice=cursor.getInt(cursor.getColumnIndex("unitprice"));
                Order order=new Order(dishname,number,initprice);
                orderList.add(order);
            }while (cursor.moveToNext());
        }
    }
}
