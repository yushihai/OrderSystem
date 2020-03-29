package com.example.ordersystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.admin_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainMenu)getActivity()).setToolbarTitle("管理员中心");

        Button serverManage=view.findViewById(R.id.admin_servermanage);
        Button cookManage=view.findViewById(R.id.admin_cookmanage);
        Button addDish=view.findViewById(R.id.admin_adddish);
        Button deleteDish=view.findViewById(R.id.admin_deletedish);
        Button updateDish=view.findViewById(R.id.admin_updatedish);

        serverManage.setOnClickListener(this);
        cookManage.setOnClickListener(this);
        addDish.setOnClickListener(this);
        deleteDish.setOnClickListener(this);
        updateDish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        FragmentManager manager;
        FragmentTransaction transaction;
        switch (v.getId()){
            case R.id.admin_servermanage:
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                AdminServerManageFragment adminServerManageFragment=new AdminServerManageFragment();
                transaction.replace(R.id.frament,adminServerManageFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.admin_cookmanage:
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                AdminCookManageFragment adminCookManageFragment=new AdminCookManageFragment();
                transaction.replace(R.id.frament,adminCookManageFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.admin_adddish:
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                AdminAddDishFragment fragment=new AdminAddDishFragment();
                transaction.replace(R.id.frament,fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.admin_deletedish:
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                AdminDishRemoveFragment adminDishRemoveFragment =new AdminDishRemoveFragment();
                transaction.replace(R.id.frament, adminDishRemoveFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.admin_updatedish:
                manager=getFragmentManager();
                transaction=manager.beginTransaction();
                AdminUpdateDishFragment adminUpdateDishFragment=new AdminUpdateDishFragment();
                transaction.replace(R.id.frament,adminUpdateDishFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
