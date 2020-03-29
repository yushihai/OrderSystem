package com.example.ordersystem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdminServerManageFragment extends Fragment implements View.OnClickListener {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.admin_servermanage_fragment,container,false);
        this.view=view;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button serverreturn=view.findViewById(R.id.servermanage_return);
        Button addserver=view.findViewById(R.id.servermanage_addserver);
        Button deleteserver=view.findViewById(R.id.servermanage_deleteserver);
        Button lookserverpwd=view.findViewById(R.id.servermanage_lookserverpwd);
        Button modifyserverpwd=view.findViewById(R.id.servermanage_modifyserverpwd);

        serverreturn.setOnClickListener(this);
        addserver.setOnClickListener(this);
        deleteserver.setOnClickListener(this);
        lookserverpwd.setOnClickListener(this);
        modifyserverpwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.servermanage_return:
                getFragmentManager().popBackStack();
                break;
            case R.id.servermanage_addserver:
                AdminAddServerDialog adminAddServerDialog=new AdminAddServerDialog(getContext());
                adminAddServerDialog.show();
                break;
            case R.id.servermanage_deleteserver:
                AdminDeleteServerDialog adminDeleteServerDialog=new AdminDeleteServerDialog(getContext());
                adminDeleteServerDialog.show();
                break;
            case R.id.servermanage_lookserverpwd:
                AdminLookServerPwdDialog adminLookServerPwdDialog=new AdminLookServerPwdDialog(getContext());
                AdminLookServerPwdResultDialog adminLookServerPwdResultDialog=new AdminLookServerPwdResultDialog(getContext());
                adminLookServerPwdDialog.setAdminLookServerPwdResultDialog(adminLookServerPwdResultDialog);
                adminLookServerPwdDialog.show();
                break;
            case R.id.servermanage_modifyserverpwd:
                AdminModifyServerPwdDialog adminModifyServerPwdDialog=new AdminModifyServerPwdDialog(getContext());
                adminModifyServerPwdDialog.show();
                break;
            default:
                break;
        }
    }
}
