package com.example.testlogin.Admin_Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testlogin.ApiClient;
import com.example.testlogin.ApiInterface;
import com.example.testlogin.Models.Room;
import com.example.testlogin.Models.RoomResponse;
import com.example.testlogin.R;
import com.example.testlogin.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bed_mgmt_fragment extends Fragment {
    EditText  bedNo;
    TextView b1;
    Spinner room_spinner;
    String Status;
    int getcount;
    int rid;
    int count;
    String[] s;
    int aid;
    ArrayList<String> brList;
    Button addBed;
    SessionManager session;
    private List<Room> getRoom;
    ArrayAdapter<String> a;
    ProgressBar progressBar;

    public bed_mgmt_fragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bed_mgmt_fragment, container, false);

        getActivity().setTitle("Add Bed");

        session = new SessionManager(getContext());
        HashMap<String, Integer> userID = session.getUserIDs();
        aid = userID.get(SessionManager.KEY_ID);

        brList = new ArrayList<>();
        getRoom = new ArrayList<>();
        bedNo=view.findViewById(R.id.bed);
        b1=view.findViewById(R.id.b1);
        room_spinner=view.findViewById(R.id.room_spinner);
        addBed=view.findViewById(R.id.addBBtn);
        progressBar=view.findViewById(R.id.b_pro);
        progressBar.setVisibility(View.INVISIBLE);
        getroomDetails();
        spinnerRoom();
     //   getbedcount();
     //   buttonclick();
        addBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        return view;
    }

    public void getroomDetails() {
        a = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, brList);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        room_spinner.setAdapter(a);
        ApiInterface api = ApiClient.getApiService();
        Call<RoomResponse> userCall = api.getroomdetails(aid);
        userCall.enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                //   if(response.isSuccessful()){
                getRoom = response.body().getResult();
                s = new String[getRoom.size()];
                brList.clear();
                brList.add("Select Room No");
                for (int i = 0; i < getRoom.size(); i++) {
                    s[i] = getRoom.get(i).getRoomNo();
                    String roomNo= getRoom.get(i).getRoomNo();
                    brList.add(roomNo);
                }
                a.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                Log.d("MainActivity", "Response is null" + t.getMessage());
                Toast.makeText(getActivity(), "No response" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void validate(){
        if(b1.getText().toString().length() == 0){
            b1.setError("Select Room No");
            Toast.makeText(getActivity(), "Select Room No", Toast.LENGTH_SHORT).show();
            return;
        }else if(bedNo.getText().toString().length() == 0){
            bedNo.setError("Enter Bed No");
            Toast.makeText(getActivity(), "Enter Bed No", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
            addBed.setEnabled(false);
            createBed();
        }
    }
    public void createBed(){
        try {
            if (count == getcount) {
                    Toast.makeText(getActivity(), "Room full", Toast.LENGTH_LONG).show();
                    addBed.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
            } else {
                if (count > getcount) {
                    createBedField();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createBedField() {

        String bed_no = bedNo.getText().toString();
        ApiInterface api = ApiClient.getApiService();
        Call<Room> userCall = api.Add_bed(bed_no, rid, aid);
        userCall.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if(response.body().getResponse().equals("inserted")){
                    Log.e("response", response.body().getResponse());
                    bedNo.setText("");
                    addBed.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Successfully Inserted", Toast.LENGTH_LONG).show();
                    closeFragment();
                } else if (response.body().getResponse().equals("exists")) {
                    addBed.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Room No is exixted", Toast.LENGTH_LONG).show();
                } else {
                    addBed.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                addBed.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "No response", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void spinnerRoom() {
        room_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               /* int[] arr = new int[getRoom.size()];
                int[] noOfBed= new int[getRoom.size()];
                int pos = room_spinner.getSelectedItemPosition();

              //  Log.e("poss", "" + pos);
                for (int i = 0; i < getRoom.size(); i++) {
                    noOfBed[i]= getRoom.get(i).getNo_of_bed();
                    arr[i] = getRoom.get(i).getRoomId();
                    if (pos == i) {
                        rid = arr[i];
                        count= noOfBed[i];
                        getbedcount();
                        room_spinner.setSelection(pos);
                    }
                }*/
                int[] arr = new int[getRoom.size()];
                int[] noOfBed= new int[getRoom.size()];
                String [] test= new String[getRoom.size()];
                String pos = String.valueOf(room_spinner.getSelectedItem());

                for (int i = 0; i < getRoom.size(); i++) {
                    test[i]=getRoom.get(i).getRoomNo();
                    noOfBed[i]= getRoom.get(i).getNo_of_bed();
                    arr[i] = getRoom.get(i).getRoomId();
                    if (pos.equals(test[i])) {
                        rid = arr[i];
                        count= noOfBed[i];
                        b1.setText(test[i]);
                        getbedcount();
                       // room_spinner.setSelection(pos);
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
           //     Toast.makeText(getActivity(), "Please Select the Room!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getbedcount(){
        ApiInterface api = ApiClient.getApiService();
        Call<Room> userCall = api.room_count(rid, aid);
        userCall.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    getcount = response.body().getNo_of_bed();
                    Log.e("count beds", "" + getcount);
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Toast.makeText(getContext(),
                        "No response", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void closeFragment() {
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().detach(bed_mgmt_fragment.this).attach(bed_mgmt_fragment.this).commit();
        }
    }
}
