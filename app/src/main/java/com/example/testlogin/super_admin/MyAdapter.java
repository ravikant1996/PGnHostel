package com.example.testlogin.super_admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testlogin.Models.User;
import com.example.testlogin.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public List<User> userList;
    static Context context;

    MyAdapter( Context context, List<User> userList) {
        this.userList = new ArrayList<User>();
        this.context = context;
        this.userList = userList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView id,  name, owner, mobile, email, loc;

        public ViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.pg_code);
            name = (TextView) view.findViewById(R.id.pg_name);
            owner = (TextView) view.findViewById(R.id.owner);
            mobile = (TextView) view.findViewById(R.id.mobile);
            email = (TextView) view.findViewById(R.id.email);
            loc = (TextView) view.findViewById(R.id.location);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, viewpg.class);
            String pgcode= id.getText().toString();
            String pgname= name.getText().toString();
            String pgowner= owner.getText().toString();
            String Mobile= mobile.getText().toString();
            String Email= email.getText().toString();
            String loca= loc.getText().toString();
            Bundle extras = new Bundle();
            extras.putInt("position", Integer.parseInt(pgcode));
            extras.putString("name", pgname);
            extras.putString("owner", pgowner);
            extras.putString("mobile", Mobile);
            extras.putString("email", Email);
            extras.putString("location", loca);
          //  intent.putExtras(extras, id.getText().toString());
            intent.putExtras(extras);
            context.startActivity(intent);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_list_layout, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User repo = userList.get(position);
        holder.id.setText(String.valueOf(repo.getaid()));
        holder.name.setText(String.valueOf(repo.getaName()));
        holder.owner.setText(String.valueOf(repo.getAowner()));
        holder.mobile.setText(String.valueOf(repo.getAphoneno()));
        holder.email.setText(String.valueOf(repo.getaEmail()));
        holder.loc.setText(String.valueOf(repo.getAlocation()));


    }

    @Override
    public int getItemCount() {
            return userList.size();
    }

}
