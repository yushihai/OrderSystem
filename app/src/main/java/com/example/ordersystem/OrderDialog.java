package com.example.ordersystem;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDialog extends Dialog implements View.OnClickListener {

    private TextView order_total;
    private List<Order> orderList=new ArrayList<Order>();
    private List<Dish> list;
    private String total_price;
    private SQLiteDatabase db;
    private FragmentManager fragmentManager;

    public OrderDialog(Context context, List<Dish> list, String total_price, FragmentManager fragmentManager) {
        super(context);
        this.list=list;
        this.total_price=total_price;
        this.fragmentManager=fragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_dialog);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
        db=PublicData.dbHelper.getWritableDatabase();

        initOrders();
        OrderAdapter adapter=new OrderAdapter(getContext(),R.layout.order_item,orderList);
        ListView listView=findViewById(R.id.order_listview);
        listView.setAdapter(adapter);

        order_total=findViewById(R.id.order_total);
        Button order_cancel=findViewById(R.id.order_cancel);
        Button order_submit=findViewById(R.id.order_submit);

        order_total.setText(total_price);
        order_cancel.setOnClickListener(this);
        order_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.order_cancel:
                dismiss();
                break;
            case R.id.order_submit:
                long order_number=System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date data=new Date(order_number);
                ContentValues values=new ContentValues();

                for(int i=0;i<orderList.size();i++){
                    Order order=orderList.get(i);
                    Cursor cursor=db.query("Dish",new String[]{"sale"},"name=?",new String[]{order.getName()},null,null,null);
                    cursor.moveToFirst();
                    int dish_sale=cursor.getInt(cursor.getColumnIndex("sale"));
                    values.put("sale",dish_sale+order.getNum());
                    db.update("Dish",values,"name=?",new String[]{order.getName()});
                    values.clear();

                    values.put("orderid",order_number+"");
                    values.put("dishname",order.getName());
                    values.put("number",order.getNum());
                    values.put("unitprice",order.getPrice());
                    db.insert("Orders",null,values);
                    values.clear();
                }
                values.put("user",PublicData.user);
                values.put("orderid",order_number+"");
                values.put("total",Integer.parseInt(total_price));
                values.put("time",simpleDateFormat.format(data));
                values.put("state","1");
                db.insert("UserOrder",null,values);
                dismiss();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                DishFragment dishFragment=new DishFragment();
                transaction.replace(R.id.frament,dishFragment);
                transaction.commit();
                Toast.makeText(getContext(),"下单成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void initOrders(){
        for (int i=0;i<list.size();i++){
            Dish dish=list.get(i);
            if(dish.getNum()!=0){
                Order order=new Order(dish.getName(),dish.getNum(),dish.getPrice());
                orderList.add(order);
            }
        }
    }
}
