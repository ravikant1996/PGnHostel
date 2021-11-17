package com.example.testlogin.Admin_Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
import com.example.testlogin.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class changePg extends AppCompatActivity {

    private MyAdapterPG dataAdapter;
    private List<User> userList;
    private RecyclerView mRecyclerViewPg;
    private ProgressBar mProgressBar;
    LinearLayoutManager layoutManager;
    SessionManager sessionManager;
    int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pg);

        sessionManager = new SessionManager(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMap<String, Integer> userID = sessionManager.getPid_admin();
        pid = userID.get(SessionManager.KEY_PID_ID);

        userList = new ArrayList<>();
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar_pg);
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface api = ApiClient.getApiService();
        Call<SignUpResponse> call = api.get_pid_All_Admin(pid);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                mProgressBar.setVisibility(View.GONE);
                userList = (ArrayList<User>) response.body().getResult();
                Log.d("TAG","Response = "+userList);

                mRecyclerViewPg =(RecyclerView) findViewById(R.id.recycler_view_pg);
                mRecyclerViewPg.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(changePg.this);
                mRecyclerViewPg.setLayoutManager(layoutManager);
                mRecyclerViewPg.setItemAnimator(new DefaultItemAnimator());
                dataAdapter = new MyAdapterPG(changePg.this, userList);
                mRecyclerViewPg.setAdapter(dataAdapter);
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(changePg.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
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