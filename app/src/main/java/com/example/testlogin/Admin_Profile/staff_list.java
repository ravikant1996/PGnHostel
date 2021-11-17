package com.example.testlogin.Admin_Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class staff_list extends Fragment {

    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    staff_list_adapter adapter;
    private List<User> userList;
    SessionManager session;
    int aid;

    public staff_list() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Staff List");
        View view = inflater.inflate(R.layout.fragment_staff_list, container, false);

        session = new SessionManager(getActivity());
        HashMap<String, Integer> userID = session.getUserIDs();
        aid = userID.get(SessionManager.KEY_ID);
        userList = new ArrayList<User>();
        progressBar = view.findViewById(R.id.progress_bar_manager);
        recyclerView = view.findViewById(R.id.recycler_view_manager);

        getManagersList();
        return view;
    }
    public void getManagersList(){
        ApiInterface api = ApiClient.getApiService();
        Call<SignUpResponse> call = api.getStaffList(aid);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                try {
                    if(response.isSuccessful()) {
                        userList = (ArrayList<User>) response.body().getResult();
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        adapter = new staff_list_adapter(getActivity(), userList);
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.INVISIBLE);
                        //   adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "rp :" +
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
