package com.example.testlogin.Admin_Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.testlogin.Models.User;
import com.example.testlogin.R;
import com.example.testlogin.SessionManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapterPG extends RecyclerView.Adapter<MyAdapterPG.ViewHolder> {

    public List<User> userList;
    static Context context;

    public MyAdapterPG(Context context, List<User> userList) {
        this.userList = new ArrayList<User>();
        this.context = context;
        this.userList = userList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView id, name, owner, mobile, email, loc;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.code);
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.emailId);
            loc = (TextView) view.findViewById(R.id.loca);
            owner = (TextView) view.findViewById(R.id.owner);
         //   details = (Button) view.findViewById(R.id.details);

          //  details.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SessionManager session = new SessionManager(context);
                    int pgcode= Integer.parseInt(id.getText().toString());
                    String pgname= name.getText().toString();
                    String Email= email.getText().toString();
                    session.createIdSession(pgcode);
                    session.createSwitchLoginSession(pgname, Email);
                  /*  FragmentManager fragmentManager =  ((AppCompatActivity)context).getSupportFragmentManager(); // this is basically context of the class
                    dashboard_admin fragment = new dashboard_admin();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.admin_main_content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/
                    Intent intent1=new Intent(context,adminProfile.class);
                    context.startActivity(intent1);
                    ((Activity)context).finish();
                }
            });

        }
     /*   @Override
        public void onClick(View v) {
          *//*  String pgcode= id.getText().toString();
            FragmentManager fragmentManager =  ((AppCompatActivity)context).getSupportFragmentManager(); // this is basically context of the class
            admin_details_reg_frag fragment = new admin_details_reg_frag();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           *//**//*
            Bundle bundle=new Bundle();
            bundle.putInt("code", Integer.parseInt(pgcode));
            fragment.setArguments(bundle);
            *//**//*
            fragmentTransaction.replace(R.id.admin_main_content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*//*

            Intent intent = new Intent(context, admin_details_filling.class);
            String pgcode= id.getText().toString();
            Bundle extras = new Bundle();
            extras.putInt("position", Integer.parseInt(pgcode));
            intent.putExtras(extras);
            context.startActivity(intent);
        }*/
    }

    @Override
    public MyAdapterPG.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pid_admin_list, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyAdapterPG.ViewHolder holder, int position) {
        User repo = userList.get(position);
        holder.id.setText(String.valueOf(repo.getaid()));
        holder.name.setText(String.valueOf(repo.getaName()));
        holder.owner.setText(String.valueOf(repo.getAowner()));
        holder.email.setText(String.valueOf(repo.getaEmail()));
        holder.loc.setText(String.valueOf(repo.getAlocation()));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}
