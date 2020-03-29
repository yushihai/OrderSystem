package com.example.ordersystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PublicData {
    //SharedPreferenced数据库名data
    //allDishNumber 菜编号
    //currentNumber 菜的种类

    protected static int visible=0;
    protected static String updatedish_name="";
    protected static Set<Integer> dishremove_id=new HashSet<Integer>();
    protected static int isStartPhoto=0;
    protected static int isEndPhoto=0;
    protected static DatabaseHelper dbHelper;
    protected static int login_type=0;   //身份
    protected static String user="";     //用户名
    protected static boolean flag=false;
    protected static int serverfindway;
    protected static String server_findvalue;
    //sqlite数据库
    //表Dish
}
