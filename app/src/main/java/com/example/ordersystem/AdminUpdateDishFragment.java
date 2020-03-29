package com.example.ordersystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminUpdateDishFragment extends Fragment implements View.OnClickListener {

    View view;
    private List<String> updateDishNameList=new ArrayList<String>();
    ListView listView;
    DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.admin_updatedish_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper=((MainMenu)getActivity()).getDbHelper();

        initUpdateDishList();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,updateDishNameList);
        listView=view.findViewById(R.id.updatedish_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PublicData.updatedish_name=updateDishNameList.get(position);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                UpdateDishItemFragment updateDishItemFragment=new UpdateDishItemFragment();
                transaction.replace(R.id.frament,updateDishItemFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        Button updatedish_return=view.findViewById(R.id.updatedish_return);
        updatedish_return.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.updatedish_return:
                getFragmentManager().popBackStack();
                break;
        }
    }

    private void initUpdateDishList(){
        updateDishNameList.clear();
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        try {
            Cursor cursor=db.query("Dish",new String[]{"name"},null,null,null,null,null);
            if(cursor.moveToFirst()){
                do{
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    updateDishNameList.add(name);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
