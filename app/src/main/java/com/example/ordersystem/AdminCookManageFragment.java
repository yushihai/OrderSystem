package com.example.ordersystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminCookManageFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.admin_cookmanage_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        Button cookmanage_return=view.findViewById(R.id.cookmanage_return);
        Button cookmanage_addcook=view.findViewById(R.id.cookmanage_addcook);
        Button cookmanage_deletecook=view.findViewById(R.id.cookmanage_deletecook);
        Button cookmanage_lookcookpwd=view.findViewById(R.id.cookmanage_lookcookpwd);
        Button cook_manage_modifycookpwd=view.findViewById(R.id.cookmanage_modifycookpwd);

        cookmanage_return.setOnClickListener(this);
        cookmanage_addcook.setOnClickListener(this);
        cookmanage_deletecook.setOnClickListener(this);
        cookmanage_lookcookpwd.setOnClickListener(this);
        cook_manage_modifycookpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.cookmanage_return:
                getFragmentManager().popBackStack();
                break;
            case R.id.cookmanage_addcook:
                AdminAddCookDialog adminAddCookDialog=new AdminAddCookDialog(getContext());
                adminAddCookDialog.show();
                break;
            case R.id.cookmanage_deletecook:
                AdminDeleteCookDialog adminDeleteCookDialog=new AdminDeleteCookDialog(getContext());
                adminDeleteCookDialog.show();
                break;
            case R.id.cookmanage_lookcookpwd:
                AdminLookCookPwdDialog adminLookCookPwdDialog=new AdminLookCookPwdDialog(getContext());
                AdminLookCookPwdResultDialog adminLookCookPwdResultDialog=new AdminLookCookPwdResultDialog(getContext());
                adminLookCookPwdDialog.setAdminLookCookPwdResultDialog(adminLookCookPwdResultDialog);
                adminLookCookPwdDialog.show();
                break;
            case R.id.cookmanage_modifycookpwd:
                AdminModifyCookPwdDialog adminModifyCookPwdDialog=new AdminModifyCookPwdDialog(getContext());
                adminModifyCookPwdDialog.show();
                break;
        }
    }
}