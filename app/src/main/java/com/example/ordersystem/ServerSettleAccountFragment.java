package com.example.ordersystem;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ServerSettleAccountFragment extends Fragment {

    View view;
    SQLiteDatabase db;
    private List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.server_settleaccount_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db=PublicData.dbHelper.getWritableDatabase();
        initOrderMessage();

        Button settleaccount_return=view.findViewById(R.id.settleaccount_return);
        ListView listView=view.findViewById(R.id.settleaccount_listview);
        final ServerSettleAccountAdapter adapter=new ServerSettleAccountAdapter(getContext(),R.layout.server_settleaccount_item,orderMessageList);

        listView.setAdapter(adapter);
        settleaccount_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        adapter.setOnClickListeber(new ServerSettleAccountAdapter.onClickListener() {
            @Override
            public void onClick(View view, final int position) {
                ImageView imageView=view.findViewById(R.id.server_settleaccount_image);
                Button cancel=view.findViewById(R.id.server_settleaccount_cancel);
                Button submit=view.findViewById(R.id.server_settleaccount_submit);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ServerSettleAccountDetailDialog serverSettleAccountDetailDialog=new ServerSettleAccountDetailDialog(getContext(),orderMessageList.get(position).getOrder_id());
                        serverSettleAccountDetailDialog.show();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String orderstate=orderMessageList.get(position).getOrder_state();
                        if(orderstate.equals("1")){
                            AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                                    .setMessage("确定取消吗？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            db.delete("UserOrder","orderid=?",new String[]{orderMessageList.get(position).getOrder_id()});
                                            Cursor cursor=db.query("Orders",null,"orderid=?",new String[]{orderMessageList.get(position).getOrder_id()},null,null,null);
                                            if(cursor.moveToFirst()){
                                                do{
                                                    String dishname=cursor.getString(cursor.getColumnIndex("dishname"));
                                                    int dishnum=cursor.getInt(cursor.getColumnIndex("number"));
                                                    db.execSQL("update Dish set sale=sale-"+dishnum+" where name='"+dishname+"'");
                                                }while (cursor.moveToNext());
                                            }
                                            db.delete("Orders","orderid=?",new String[]{orderMessageList.get(position).getOrder_id()});
                                            orderMessageList.remove(position);
                                            adapter.notifyDataSetChanged();
                                            Toast.makeText(getContext(),"取消成功",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).setCancelable(false)
                                    .show();
                        }else if (orderstate.equals("2")){
                            Toast.makeText(getContext(),"正在做餐中，请耐心等待",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(),"已完成做餐，无法取消",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String orderstate=orderMessageList.get(position).getOrder_state();
                        if(orderstate.equals("1")){
                            Toast.makeText(getContext(),"未做餐，无法结账",Toast.LENGTH_SHORT).show();
                        }else if (orderstate.equals("2")){
                            Toast.makeText(getContext(),"正在做餐中，请耐心等待",Toast.LENGTH_SHORT).show();
                        }else {
                            AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                                    .setMessage("确定结账吗？")
                                    .setCancelable(false)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String orderid=orderMessageList.get(position).getOrder_id();
                                            ContentValues values=new ContentValues();
                                            values.put("state","4");
                                            db.update("UserOrder",values,"orderid=?",new String[]{orderid});
                                            orderMessageList.remove(position);
                                            adapter.notifyDataSetChanged();
                                            Toast.makeText(getContext(),"结账成功",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }
                });
            }
        });
    }

    private void initOrderMessage(){
        Cursor cursor;
        if(PublicData.serverfindway==1)
            cursor=db.rawQuery("select * from UserOrder where user='"+PublicData.server_findvalue+"' and state in('1','2','3')",null);
            //cursor=db.query("UserOrder",null,"user=?",new String[]{PublicData.server_findvalue},null,null,null);
        else
            cursor=db.query("UserOrder",null,"orderid=?",new String[]{PublicData.server_findvalue},null,null,null);
        if(cursor.moveToFirst()) {
            do {
                String user = cursor.getString(cursor.getColumnIndex("user"));
                String orderid = cursor.getString(cursor.getColumnIndex("orderid"));
                int total = cursor.getInt(cursor.getColumnIndex("total"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String state = cursor.getString(cursor.getColumnIndex("state"));
                OrderMessage orderMessage = new OrderMessage(orderid, time, state, total, user);
                orderMessageList.add(orderMessage);
            } while (cursor.moveToNext());
        }
    }
}
