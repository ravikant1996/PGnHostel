package com.example.testlogin.super_admin;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.testlogin.ApiClient;
import com.example.testlogin.ApiInterface;
import com.example.testlogin.Models.SignUpResponse;
import com.example.testlogin.Models.User;
import com.example.testlogin.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminList extends AppCompatActivity {

    private MyAdapter dataAdapter;
    private List<User> userList;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userList = new ArrayList<>();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getApiService();
        Call<SignUpResponse> call = api.getAll_Admin();
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                mProgressBar.setVisibility(View.VISIBLE);
                try {
                    userList = (ArrayList<User>) response.body().getResult();

                    mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    mRecyclerView.setHasFixedSize(true);
                    layoutManager = new LinearLayoutManager(AdminList.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    dataAdapter = new MyAdapter(AdminList.this, userList);
                    mRecyclerView.setAdapter(dataAdapter);
                    mProgressBar.setVisibility(View.GONE);
                }catch (Exception e){
                    Toast.makeText(AdminList.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(AdminList.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
