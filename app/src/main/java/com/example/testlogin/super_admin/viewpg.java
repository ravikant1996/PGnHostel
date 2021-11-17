package com.example.testlogin.super_admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testlogin.ApiClient;
import com.example.testlogin.ApiInterface;
import com.example.testlogin.Models.Room;
import com.example.testlogin.Models.SignUpResponse;
import com.example.testlogin.Models.User;
import com.example.testlogin.R;
import com.example.testlogin.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class viewpg extends AppCompatActivity {

    int position;
    String Status;
    Button allotBed;
    String[] s;
    int aid, rid, bid, uid;
    ArrayList<String> brList;
    ArrayList<String> bedList;
    ArrayList<String> tenentList;
    TextView users, Beds, Vacent, Alloted, rooms, code, name, email, owner,
            location, phone;
    CardView usercard, roomcard, bedcard;
    Button addBed;
    SessionManager session;
    private List<Room> getRoom;
    private List<Room> getBed;
    private List<User> getTenent;
    ArrayAdapter<String> roomAdapter;
    ArrayAdapter<String> bedAdapter;
    ArrayAdapter<String> tenentAdapter;
    ArrayAdapter<String> statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpg);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // 5. get status value from bundle
        aid = bundle.getInt("position");
        String  pgname= bundle.getString("name");
        String  pgowner= bundle.getString("owner");
        String  pgmobile= bundle.getString("mobile");
        String  pgemail= bundle.getString("email");
        String  pglocation= bundle.getString("location");

        code= (TextView) findViewById(R.id.code_pg);
        name= (TextView) findViewById(R.id.name);
        phone= (TextView) findViewById(R.id.phone);
        email= (TextView) findViewById(R.id.email);
        location= (TextView) findViewById(R.id.location);
        owner= (TextView) findViewById(R.id.owner);

        users= (TextView) findViewById(R.id.users);
        Beds= (TextView) findViewById(R.id.beds);
        Beds.append("Total Beds ");
        Vacent= (TextView) findViewById(R.id.vacent);
        Vacent.append("Vacent ");
        Alloted= (TextView) findViewById(R.id.alloted);
        Alloted.append("Alloted ");

        rooms= (TextView) findViewById(R.id.rooms);

        usercard= (CardView) findViewById(R.id.usercard);
        roomcard= (CardView) findViewById(R.id.roomcard);
        bedcard= (CardView) findViewById(R.id.bedcard);

        code.setText(String.valueOf(aid));
        name.setText(pgname);
        phone.setText(String.valueOf(pgmobile));
        email.setText(pgemail);
        owner.setText(pgowner);
        location.setText(pglocation);

          getroomDetails();
          getbedDetails();
          gettenantDetails();
    }
    public void gettenantDetails(){
        ApiInterface api = ApiClient.getApiService();
        Call<User> userCall = api.user_count(aid);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.isSuccessful()) {                        //   String var=response.body().getResponse();
                        int totalusers = response.body().getTotal();
                        users.setText(String.valueOf(totalusers));
                        users.append(" Tenants");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("MainActivity", "Response is null" + t.getMessage());
                Toast.makeText(getApplicationContext(), "No response" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getroomDetails(){
        ApiInterface api = ApiClient.getApiService();
        Call<Room> userCall = api.room_bed_count(aid);
        userCall.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                try {
                    if (response.isSuccessful()) {                        //   String var=response.body().getResponse();
                        int totalroom = response.body().getNo_of_room();
                        rooms.setText(String.valueOf(totalroom));
                        rooms.append(" Rooms");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.d("MainActivity", "Response is null" + t.getMessage());
                Toast.makeText(getApplicationContext(), "No response" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void getbedDetails(){
        ApiInterface api = ApiClient.getApiService();
        Call<Room> userCall = api.room_bed_count(aid);
        userCall.enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                try {
                    if (response.isSuccessful()) {
                        //   String var=response.body().getResponse();
                        int totalbed = response.body().getNo_of_bed();
                        int alloted = response.body().getAllotedBed();
                        int vacent= response.body().getVacentBed();

                        Vacent.setText(String.valueOf(vacent));
                        Vacent.append("=> Vacent");
                        Alloted.setText(String.valueOf(alloted));
                        Alloted.append("=> Alloted");
                        Beds.setText(String.valueOf(totalbed));
                        Beds.append("=> Total");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Room> call, Throwable t) {
                Log.d("MainActivity", "Response is null" + t.getMessage());
                Toast.makeText(getApplicationContext(), "No response" + t.getMessage(), Toast.LENGTH_LONG).show();
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
