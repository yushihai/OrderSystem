package com.example.ordersystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LookOrderFragment extends Fragment {

    View view;
    private List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.lookorder_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db=PublicData.dbHelper.getWritableDatabase();
        ((MainMenu)getActivity()).setToolbarTitle("查看订单");

        initOrderMessage();
        OrderMessageAdapter adapter=new OrderMessageAdapter(getContext(),R.layout.lookorder_item,orderMessageList);
        ListView listView=view.findViewById(R.id.lookorder_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderMessage orderMessage=orderMessageList.get(position);
                LookOrderItemDialog lookOrderItemDialog=new LookOrderItemDialog(getContext(),orderMessage.getOrder_id(),orderMessage.getOrder_time());
                lookOrderItemDialog.show();
            }
        });
    }

    private void initOrderMessage(){
        Cursor cursor=db.query("UserOrder",null,"user=?",new String[]{PublicData.user},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String orderid=cursor.getString(cursor.getColumnIndex("orderid"));
                int total=cursor.getInt(cursor.getColumnIndex("total"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                String state=cursor.getString(cursor.getColumnIndex("state"));
                if(!state.equals("4")) {
                    OrderMessage orderMessage = new OrderMessage(orderid, time, state, total,"");
                    orderMessageList.add(orderMessage);
                }
            }while (cursor.moveToNext());
        }
    }
}
