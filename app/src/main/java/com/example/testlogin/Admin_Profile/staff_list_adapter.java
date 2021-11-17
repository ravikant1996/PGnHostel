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

public class staff_list_adapter extends RecyclerView.Adapter<staff_list_adapter.ViewHolder> {
    public List<User> userList;
    static Context context;


    public staff_list_adapter(Context context, List<User> userList){
        this.userList = new ArrayList<User>();
        this.context = context;
        this.userList = userList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, email, phone, stafftype, address, joiningDate;

        public ViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.mname);
            email = (TextView) view.findViewById(R.id.memail);
            phone = (TextView) view.findViewById(R.id.mphone);
            stafftype = (TextView) view.findViewById(R.id.staffType);
            address = (TextView) view.findViewById(R.id.Address);
            joiningDate = (TextView) view.findViewById(R.id.Date);
        }
    }
    @Override
    public staff_list_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_list_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User repo = userList.get(position);

        holder.name.setText(String.valueOf(repo.getuName()));
        holder.email.setText(String.valueOf(repo.getuEmail()));
        holder.phone.setText(String.valueOf(repo.getUphoneno()));
        holder.stafftype.setText(String.valueOf(repo.getSphoneno()));
        holder.address.setText(String.valueOf(repo.getCity()));
        holder.joiningDate.setText(String.valueOf(repo.getState()));

    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

}
