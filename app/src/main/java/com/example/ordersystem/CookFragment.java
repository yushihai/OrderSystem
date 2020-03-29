package com.example.ordersystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CookFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.cook_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainMenu)getActivity()).setToolbarTitle("小厨师中心");

        Button lookallorder=view.findViewById(R.id.cook_lookallorder);
        Button logout=view.findViewById(R.id.cook_logout);

        lookallorder.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        FragmentTransaction transaction;
        FragmentManager manager;
        switch (v.getId()){
            case R.id.cook_lookallorder:
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                CookLookOrdersFragment cookLookOrdersFragment=new CookLookOrdersFragment();
                transaction.replace(R.id.frament,cookLookOrdersFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.cook_logout:
                PublicData.login_type=0;
                PublicData.user="";
                ((MainMenu)getActivity()).login_hint.setText("你好，请登录");
                ((MainMenu)getActivity()).getDrawerLayout().openDrawer(Gravity.LEFT);
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                DishFragment dishFragment=new DishFragment();
                transaction.replace(R.id.frament,dishFragment);
                transaction.commit();
                break;
        }
    }
}
