package com.example.ordersystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminDishRemoveFragment extends Fragment implements View.OnClickListener {

    View view;
    private List<String> dishRemoveList=new ArrayList<String>();
    private List<String> removeList=new ArrayList<String>();
    DatabaseHelper dbHelper;
    DishRemoveAdapter adapter;
    Button dishremove_delete,dishremove_submit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.admin_removedish_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper=((MainMenu)getActivity()).getDbHelper();

        PublicData.visible=0;
        PublicData.dishremove_id.clear();
        initDishRemoveList();
        adapter=new DishRemoveAdapter(getContext(),R.layout.removedish_item,dishRemoveList);
        ListView listView=view.findViewById(R.id.removedish_listview);
        listView.setAdapter(adapter);

        Button dishremove_return=view.findViewById(R.id.removedish_return);
        dishremove_delete=view.findViewById(R.id.removedish_delete);
        dishremove_submit=view.findViewById(R.id.removedish_submit);

        adapter.setOnClickListeber(new DishRemoveAdapter.onClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final CheckBox checkBox=view.findViewById(R.id.dishremove_checkbox);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(checkBox.isChecked()){
                            removeList.add(dishRemoveList.get(position));
                            PublicData.dishremove_id.add(position);
                        }
                        else{
                            removeList.remove(dishRemoveList.get(position));
                            PublicData.dishremove_id.remove(position);
                        }
                    }
                });
            }
        });
        dishremove_return.setOnClickListener(this);
        dishremove_delete.setOnClickListener(this);
        dishremove_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.removedish_return:
                getFragmentManager().popBackStack();
                break;
            case R.id.removedish_delete:
                if(PublicData.visible==0) {
                    PublicData.visible = 1;
                    adapter.notifyDataSetChanged();
                    dishremove_delete.setText("取消");
                    dishremove_submit.setVisibility(View.VISIBLE);
                }
                else {
                    PublicData.visible = 0;
                    PublicData.dishremove_id.clear();
                    adapter.notifyDataSetChanged();
                    dishremove_delete.setText("删除");
                    dishremove_submit.setVisibility(View.INVISIBLE);
                    removeList.clear();
                }
                break;
            case R.id.removedish_submit:
                removedish_submit_button();
        }
    }

    private void removedish_submit_button(){
        if(!removeList.isEmpty()) {
            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setMessage("是否删除？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                String str="";
                                for(int i=0;i<removeList.size()-1;i++)
                                    str=str+"'"+removeList.get(i)+"',";
                                str=str+"'"+removeList.get(removeList.size()-1)+"'";
                                db.execSQL("delete from Dish where name in ("+str+")");
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            removeList.clear();
                            PublicData.visible=0;
                            PublicData.dishremove_id.clear();
                            initDishRemoveList();
                            adapter.notifyDataSetChanged();
                            dishremove_submit.setVisibility(View.INVISIBLE);
                            dishremove_delete.setText("删除");
                            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    }

    private void initDishRemoveList(){
        dishRemoveList.clear();
        try {
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            Cursor cursor=db.query("Dish",new String[]{"name"},null,null,null,null,null);
            if(cursor.moveToFirst()){
                do{
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    dishRemoveList.add(name);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
