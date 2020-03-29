package com.example.ordersystem;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ConsumeRecordDialog extends Dialog {

    String orderid;
    SQLiteDatabase db;
    private List<Order> orderList=new ArrayList<Order>();

    public ConsumeRecordDialog(Context context,String orderid) {
        super(context);
        this.orderid=orderid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumerecord_dialog);

        db=PublicData.dbHelper.getWritableDatabase();
        initOrders();

        ServerSettleAccountDetailAdapter adapter=new ServerSettleAccountDetailAdapter(getContext(),R.layout.order_item,orderList);
        ListView listView=findViewById(R.id.consumerecord_dialog_listview);
        TextView title=findViewById(R.id.consumerecord_dialog_orderid);

        listView.setAdapter(adapter);
        title.setText(orderid);
    }

    private void initOrders(){
        Cursor cursor=db.query("Orders",null,"orderid=?",new String[]{orderid},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("dishname"));
                int number=cursor.getInt(cursor.getColumnIndex("number"));
                int unitprice=cursor.getInt(cursor.getColumnIndex("unitprice"));
                Order order=new Order(name,number,unitprice);
                orderList.add(order);
            }while (cursor.moveToNext());
        }
    }
}
