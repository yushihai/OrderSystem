package com.example.ordersystem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ConsumeRecordFragment extends Fragment {

    View view;
    SQLiteDatabase db;
    List<OrderMessage> orderMessageList=new ArrayList<OrderMessage>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.consumerecord_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainMenu)getActivity()).setToolbarTitle("消费记录");
        db=PublicData.dbHelper.getWritableDatabase();
        initOrderMessage();

        ListView listView=view.findViewById(R.id.consumerecord_listview);
        ConsumeRecordAdapter adapter=new ConsumeRecordAdapter(getContext(),R.layout.consumerecord_item,orderMessageList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConsumeRecordDialog dialog=new ConsumeRecordDialog(getContext(),orderMessageList.get(position).getOrder_id());
                dialog.show();
            }
        });
    }

    private void initOrderMessage(){
        Cursor cursor=db.query("UserOrder",null,"user=? and state=?",new String[]{PublicData.user,"4"},null,null,null);
        if(cursor.moveToFirst()){
            do{
                String user=cursor.getString(cursor.getColumnIndex("user"));
                String orderid=cursor.getString(cursor.getColumnIndex("orderid"));
                int total=cursor.getInt(cursor.getColumnIndex("total"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                String state=cursor.getString(cursor.getColumnIndex("state"));
                OrderMessage orderMessage=new OrderMessage(orderid,time,state,total,user);
                orderMessageList.add(orderMessage);
            }while (cursor.moveToNext());
        }
    }
}
