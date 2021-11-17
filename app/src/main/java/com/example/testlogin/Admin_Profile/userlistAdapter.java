package com.example.testlogin.Admin_Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testlogin.Models.User;
import com.example.testlogin.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class userlistAdapter extends RecyclerView.Adapter<userlistAdapter.ViewHolder> {

    public List<User> userList;
    static Context context;

    public userlistAdapter(Context context, List<User> userList){
        this.userList = new ArrayList<User>();
        this.context = context;
        this.userList = userList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, id, email, phone, bed;
      public ViewHolder(View view) {
          super(view);
          name = (TextView) view.findViewById(R.id.username);
          id = (TextView) view.findViewById(R.id.userid);
          email = (TextView) view.findViewById(R.id.useremail);
          phone = (TextView) view.findViewById(R.id.userphone);
          bed = (TextView) view.findViewById(R.id.bedno);
      }

    }
    @Override
    public userlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User repo = userList.get(position);
        holder.id.setText(String.valueOf(repo.getUid()));
        holder.name.setText(String.valueOf(repo.getuName()));
        holder.email.setText(String.valueOf(repo.getuEmail()));
        holder.phone.setText(String.valueOf(repo.getUphoneno()));
        holder.bed.setText(String.valueOf(repo.getUbedno()));


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

}

