package com.example.ordersystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CookLookOrdersFragment extends Fragment{

    View view;
    SQLiteDatabase db;
    List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.cook_lookorders_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db=PublicData.dbHelper.getWritableDatabase();
        initOrderMessage();

        Button lookorders_return=view.findViewById(R.id.lookorders_return);
        ListView listView=view.findViewById(R.id.lookorders_listview);

        lookorders_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        final CookLookOrdersAdapter adapter=new CookLookOrdersAdapter(getContext(),R.layout.cook_lookorders_item,orderMessageList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CookLookOrdersItemDialog cookLookOrdersItemDialog=new CookLookOrdersItemDialog(getContext(),adapter,position,orderMessageList);
                cookLookOrdersItemDialog.show();
            }
        });
    }

    private void initOrderMessage(){
        //Cursor cursor=db.query("UserOrder",null,"state in ?",new String[]{"'1','2'"},null,null,null);
        Cursor cursor=db.rawQuery("select * from UserOrder where state in ('1','2')",null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("user"));
                String orderid=cursor.getString(cursor.getColumnIndex("orderid"));
                int total=cursor.getInt(cursor.getColumnIndex("total"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                String state=cursor.getString(cursor.getColumnIndex("state"));
                OrderMessage orderMessage=new OrderMessage(orderid,time,state,total,name);
                orderMessageList.add(orderMessage);
            }while (cursor.moveToNext());
        }
    }
}
