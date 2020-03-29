package com.example.ordersystem;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateDishItemFragment extends Fragment implements View.OnClickListener {

    View view;
    DatabaseHelper dbHelper;
    TextView updatedish_name;
    EditText updatedish_price;
    ImageView updatedish_image;
    Button updatedish_photo,updatedish_album;
    EditText updatedish_introduce;
    Button updatedish_submit,updatedish_back;
    RelativeLayout relativeLayout;
    int number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.updatedish_item_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper=((MainMenu)getActivity()).getDbHelper();
        PublicData.isStartPhoto=0;
        PublicData.isEndPhoto=0;

        updatedish_name=view.findViewById(R.id.updatedish_name);
        updatedish_price=view.findViewById(R.id.updatedish_price);
        updatedish_image=view.findViewById(R.id.updatedish_image);
        updatedish_photo=view.findViewById(R.id.updatedish_photo);
        updatedish_album=view.findViewById(R.id.updatedish_album);
        updatedish_introduce=view.findViewById(R.id.updatedish_introduce);
        updatedish_submit=view.findViewById(R.id.updatedish_submit);
        updatedish_back=view.findViewById(R.id.updatedish_back);
        relativeLayout=view.findViewById(R.id.updatedish_item_layout);

        SQLiteDatabase db=dbHelper.getWritableDatabase();
        try{
            Cursor cursor=db.query("Dish",null,"name=?",new String[]{PublicData.updatedish_name},null,null,null);
            cursor.moveToFirst();
            updatedish_name.setText(cursor.getString(cursor.getColumnIndex("name")));
            updatedish_price.setText(cursor.getInt(cursor.getColumnIndex("price"))+"");
            number=cursor.getInt(cursor.getColumnIndex("number"));
            updatedish_image.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + number+".jpg"));
            updatedish_introduce.setText(cursor.getString(cursor.getColumnIndex("introduce")));
        }catch (Exception e){
            e.printStackTrace();
        }

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                relativeLayout.setFocusable(true);
                relativeLayout.setFocusableInTouchMode(true);
                relativeLayout.requestFocus();
                return false;
            }
        });

        updatedish_photo.setOnClickListener(this);
        updatedish_album.setOnClickListener(this);
        updatedish_submit.setOnClickListener(this);
        updatedish_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.updatedish_photo:
                PublicData.isStartPhoto=1;
                ((MainMenu)getActivity()).takePhoto("temp",updatedish_image);
                break;
            case R.id.updatedish_album:
                PublicData.isStartPhoto=1;
                ((MainMenu)getActivity()).choosePhoto("temp",updatedish_image);
                break;
            case R.id.updatedish_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.updatedish_submit:
                AlertDialog dialog=new AlertDialog.Builder(getContext())
                        .setMessage("是否要修改？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name=updatedish_name.getText().toString();
                                int price=Integer.parseInt(updatedish_price.getText().toString());
                                String introduce=updatedish_introduce.getText().toString();
                                File file=new File(Environment.getExternalStorageDirectory(),number+".jpg");
                                if(PublicData.isEndPhoto==1&&PublicData.isStartPhoto==1) {
                                    try {
                                        if (file.exists())
                                            file.delete();
                                        file.createNewFile();
                                        FileOutputStream outputStream = new FileOutputStream(file);
                                        ((MainMenu) getActivity()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                SQLiteDatabase db=dbHelper.getWritableDatabase();
                                ContentValues values=new ContentValues();
                                values.put("price",price);
                                values.put("introduce",introduce);
                                db.update("Dish",values,"name=?",new String[]{name});
                                Toast.makeText(getContext(),"更新成功！",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                break;
            default:
                break;
        }
    }
}
