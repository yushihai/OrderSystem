package com.example.ordersystem;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DishFragment extends Fragment implements View.OnClickListener {

    View view;
    private List<Dish> dishList=new ArrayList<Dish>();
    SQLiteDatabase db;
    TextView dish_totalprice;
    int total_price=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.dish_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db=PublicData.dbHelper.getWritableDatabase();
        ((MainMenu)getActivity()).setToolbarTitle("菜单");

        initDish();
        DishAdapter adapter=new DishAdapter(getContext(),R.layout.dish_item,dishList);
        ListView listView=view.findViewById(R.id.dish_list);
        listView.setAdapter(adapter);

        dish_totalprice=view.findViewById(R.id.dish_totalprice);
        dish_totalprice.setText(total_price+"");
        Button dish_settleaccount=view.findViewById(R.id.dish_settleaccount);
        dish_settleaccount.setOnClickListener(this);

        adapter.setOnClickListeber(new DishAdapter.onClickListener() {
            @Override
            public void onClick(View view, final int position) {
                final ImageView dish_sub=view.findViewById(R.id.dish_sub);
                final TextView dish_num=view.findViewById(R.id.dish_num);
                ImageView dish_add=view.findViewById(R.id.dish_add);

                dish_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num=Integer.parseInt(dish_num.getText().toString());
                        if(num==0) {
                            dish_num.setVisibility(View.VISIBLE);
                            dish_sub.setVisibility(View.VISIBLE);
                        }
                        Dish dish=dishList.get(position);
                        dish.setNum(num+1);
                        total_price+=dish.getPrice();
                        dish_totalprice.setText(total_price+"");
                        dish_num.setText((num+1)+"");
                    }
                });
                dish_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num=Integer.parseInt(dish_num.getText().toString());
                        if(num==1) {
                            dish_num.setVisibility(View.INVISIBLE);
                            dish_sub.setVisibility(View.INVISIBLE);
                        }
                        Dish dish=dishList.get(position);
                        total_price-=dish.getPrice();
                        dish_totalprice.setText(total_price+"");
                        dish.setNum(num-1);
                        dish_num.setText((num-1)+"");
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v){
        if(dish_totalprice.getText().toString().equals("0"))
            Toast.makeText(getContext(),"订单为空",Toast.LENGTH_SHORT).show();
        else{
            OrderDialog orderDialog=new OrderDialog(getContext(),dishList,dish_totalprice.getText().toString(),getFragmentManager());
            orderDialog.show();
        }
    }

    private void initDish(){
        try {
            Cursor cursor = db.query("Dish", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int price = cursor.getInt(cursor.getColumnIndex("price"));
                    String introduce = cursor.getString(cursor.getColumnIndex("introduce"));
                    int number = cursor.getInt(cursor.getColumnIndex("number"));
                    Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + number+".jpg");
                    int sale=cursor.getInt(cursor.getColumnIndex("sale"));
                    Dish dish = new Dish(name, price, bitmap, introduce,sale,0);
                    dishList.add(dish);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
