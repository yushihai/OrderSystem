package com.example.ordersystem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainMenu extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView;
    ImageView head_portrait;
    TextView login_hint;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Bitmap bitmap;
    private DatabaseHelper dbHelper;
    FragmentManager fragmentManager;

    public static final int TAKE_PHOTO_1=1;
    public static final int CROP_PHOTO_1=2;
    public static final int TAKE_PHOTO_2=3;
    public static final int CROP_PHOTO_2=4;
    private Uri imageUri;
    private Bitmap pre_bitmap;
    private String number;
    private ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        dbHelper=new DatabaseHelper(this,"OrderSystem.db",null,2);
        PublicData.dbHelper=dbHelper;
        fragmentManager=getSupportFragmentManager();

        if(ContextCompat.checkSelfPermission(MainMenu.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainMenu.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        head_portrait=headerView.findViewById(R.id.head_portrait);
        login_hint=headerView.findViewById(R.id.login_hint);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);

        toolbar.setTitle("菜单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);

        head_portrait.setOnClickListener(this);
        login_hint.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        DishFragment dishFragment=new DishFragment();
        transaction.replace(R.id.frament,dishFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v){
        if(PublicData.login_type==0)
            loginHint();
        else {
            switch (v.getId()){
                case R.id.head_portrait:
                    drawerLayout.closeDrawers();
                    setToolbarTitle("更换头像");
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    HeadPortraitFragment headPortraitFragment = new HeadPortraitFragment();
                    transaction.replace(R.id.frament, headPortraitFragment);
                    transaction.commit();
                    break;
                case R.id.login_hint:
                    AlertDialog dialog=new AlertDialog.Builder(this)
                            .setMessage("确定要注销吗？")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PublicData.user="";
                                    PublicData.login_type=0;
                                    FragmentManager manager = getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    DishFragment dishFragment=new DishFragment();
                                    transaction.replace(R.id.frament,dishFragment);
                                    transaction.commit();
                                    head_portrait.setImageResource(R.mipmap.headportrait_default);
                                    login_hint.setText("你好，请登录");
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                    break;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        FragmentManager manager;
        FragmentTransaction transaction;
        switch (item.getItemId()){
            case R.id.nav_menu:
                manager=getSupportFragmentManager();
                transaction=manager.beginTransaction();
                manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                DishFragment dishFragment=new DishFragment();
                transaction.replace(R.id.frament,dishFragment);
                transaction.commit();
                drawerLayout.closeDrawers();
                break;
            case R.id.nav_order:
                if(PublicData.login_type==1) {
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    LookOrderFragment lookOrderFragment = new LookOrderFragment();
                    transaction.replace(R.id.frament, lookOrderFragment);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                }else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("无法访问！")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                break;
            case R.id.nav_consume:
                if(PublicData.login_type==1) {
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ConsumeRecordFragment consumeRecordFragment = new ConsumeRecordFragment();
                    transaction.replace(R.id.frament, consumeRecordFragment);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                }else {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("无法访问！")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                break;
            case R.id.nav_cook:
                if(PublicData.login_type==1||PublicData.login_type==2||PublicData.login_type==3){
                    AlertDialog dialog=new AlertDialog.Builder(this)
                            .setMessage("是否退出当前账号进行登录？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PublicData.user="";
                                    PublicData.login_type=0;
                                    FragmentManager manager = getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    DishFragment dishFragment1=new DishFragment();
                                    transaction.replace(R.id.frament,dishFragment1);
                                    transaction.commit();
                                    head_portrait.setImageResource(R.mipmap.headportrait_default);
                                    login_hint.setText("你好，请登录");
                                    cookDialog();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(PublicData.login_type==0)
                    cookDialog();
                else {
                    drawerLayout.closeDrawers();
                    manager=getSupportFragmentManager();
                    transaction=manager.beginTransaction();
                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    CookFragment cookFragment=new CookFragment();
                    transaction.replace(R.id.frament,cookFragment);
                    transaction.commit();
                }
                break;
            case R.id.nav_admin:
                if(PublicData.login_type==0)
                    loginHint();
                else if (PublicData.login_type==1||PublicData.login_type==3||PublicData.login_type==4) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setMessage("无法访问！")
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else {
                    setToolbarTitle("管理员中心");
                    manager = getSupportFragmentManager();
                    transaction = manager.beginTransaction();
                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    AdminFragment adminFragment = new AdminFragment();
                    transaction.replace(R.id.frament, adminFragment);
                    transaction.commit();
                    drawerLayout.closeDrawers();
                }
                break;
            case R.id.nav_server:
                if(PublicData.login_type==1||PublicData.login_type==2||PublicData.login_type==4){
                    AlertDialog dialog=new AlertDialog.Builder(this)
                            .setMessage("是否退出当前账号进行登录？")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PublicData.user="";
                                    PublicData.login_type=0;
                                    FragmentManager manager = getSupportFragmentManager();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    DishFragment dishFragment1=new DishFragment();
                                    transaction.replace(R.id.frament,dishFragment1);
                                    transaction.commit();
                                    head_portrait.setImageResource(R.mipmap.headportrait_default);
                                    login_hint.setText("你好，请登录");
                                    serverDialog();
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(PublicData.login_type==0)
                    serverDialog();
                else {
                    drawerLayout.closeDrawers();
                    manager=getSupportFragmentManager();
                    transaction=manager.beginTransaction();
                    manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    ServerFragment serverFragment=new ServerFragment();
                    transaction.replace(R.id.frament,serverFragment);
                    transaction.commit();
                }
                break;
        }
        return false;
    }

    protected void cookDialog(){
        CookLoginDialog cookLoginDialog=new CookLoginDialog(this,login_hint,fragmentManager,drawerLayout);
        cookLoginDialog.show();
    }

    protected void serverDialog(){
        ServerLoginDialog serverLoginDialog=new ServerLoginDialog(this,login_hint,fragmentManager,drawerLayout);
        serverLoginDialog.show();
    }

    protected void takePhoto(String num,ImageView photo){
        number=num;
        this.photo=photo;
        File outputImage=new File(Environment.getExternalStorageDirectory(),num+".jpg");
        try{
            if(outputImage.exists())
                outputImage.delete();
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        imageUri=Uri.fromFile(outputImage);
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO_1);
    }

    protected void choosePhoto(String num,ImageView photo){
        number=num;
        this.photo=photo;
        File outputImage=new File(Environment.getExternalStorageDirectory(),num+".jpg");
        try{
            if(outputImage.exists())
                outputImage.delete();
            outputImage.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        imageUri=Uri.fromFile(outputImage);
        Intent intent=new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        intent.putExtra("crop",true);
        intent.putExtra("scale",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case TAKE_PHOTO_1:
                if(resultCode==RESULT_OK){
                    Intent intent=new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO_1);
                }
                break;
            case CROP_PHOTO_1:
                if(resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                        this.bitmap=bitmap;
                        PublicData.isEndPhoto=1;
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            case TAKE_PHOTO_2:
                if(resultCode==RESULT_OK){
                    imageUri = data.getData();
                    try {
                        pre_bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    Intent intent=new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    startActivityForResult(intent,CROP_PHOTO_2);
                }
                break;
            case CROP_PHOTO_2:
                if(resultCode==RESULT_OK){
                    try {
                        Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        photo.setImageBitmap(bitmap);
                        this.bitmap=bitmap;
                        File file=new File(Environment.getExternalStorageDirectory(),number+".jpg");
                        try{
                            if(file.exists())
                                file.delete();
                            file.createNewFile();
                            FileOutputStream outputStream=new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream);
                            outputStream.flush();
                            outputStream.close();
                            PublicData.isEndPhoto=1;
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                        MediaStore.Images.Media.insertImage(getContentResolver(),pre_bitmap,"ordersystem"+number,"ordersystem"+number);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void loginHint(){
        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle("友情提示")
                .setMessage("您还没有登录")
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        drawerLayout.closeDrawers();
                        FragmentManager manager=getSupportFragmentManager();
                        FragmentTransaction transaction=manager.beginTransaction();
                        LoginFragment loginFragment=new LoginFragment();
                        transaction.replace(R.id.frament,loginFragment);
                        transaction.commit();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false).show();
    }

    /*@Override
    public void onBackPressed(){
        if(PublicData.flag)
            super.onBackPressed();
        else {
            Toast.makeText(this,"再次点击退出",Toast.LENGTH_SHORT).show();
            PublicData.flag=true;
            new Thread(){
                @Override
                public void run(){
                    try {
                        Thread.sleep(3000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    PublicData.flag=false;
                }
            }.start();
        }
    }*/

    protected void setToolbarTitle(String title){
        toolbar.setTitle(title);
    }

    protected ImageView getHead_portrait(){
        return head_portrait;
    }

    protected DrawerLayout getDrawerLayout(){
        return drawerLayout;
    }

    protected Bitmap getBitmap(){
        return bitmap;
    }

    protected DatabaseHelper getDbHelper(){
        return dbHelper;
    }

    protected void setLogin_hint(String hint){
        login_hint.setText(hint);
    }
}
