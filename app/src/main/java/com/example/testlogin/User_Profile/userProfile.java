package com.example.testlogin.User_Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testlogin.Admin_Profile.adminProfile;
import com.example.testlogin.R;
import com.example.testlogin.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Objects;

public class userProfile extends AppCompatActivity {
    TextView Name, Id, Email;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv_User;

    // Session Manager Class
    SessionManager session;
    TextView userName;
    CircleImageView cvimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        session = new SessionManager(getApplicationContext());

        dl = findViewById(R.id.activity_user_profile);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
      /*  Id= findViewById(R.id.id);
        Name= findViewById(R.id.name);
        Email= findViewById(R.id.email);
*/
        session.isLoggedIn();
        session.checkLogin();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        //id
        String  id= user.get(SessionManager.KEY_ID);
        // name
        String name = user.get(SessionManager.KEY_NAME);
        // email
        String email = user.get(SessionManager.KEY_EMAIL);

        // displaying user data
     /*   Id.setText(Html.fromHtml("User Code: <b>" + id + "</b>"));
        Name.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        Email.setText(Html.fromHtml("Email: <b>" + email + "</b>"));
*/
        //     valuefromIntent();
        nv_User = findViewById(R.id.nv_user);
        View headerView = nv_User.inflateHeaderView(R.layout.user_navigation_header);
        cvimage = (CircleImageView) headerView.findViewById(R.id.user_profile_image);

        userName = (TextView) headerView.findViewById(R.id.userName);
        userName.setText(Html.fromHtml("<b>" + name + "</b>"));

        nv_User.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(userProfile.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(userProfile.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.logout:
                        session.logoutUser();
                        Toast.makeText(userProfile.this, "Thank You!",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        // disable going back to the super_admin
        moveTaskToBack(true);
       /* if (dl.isDrawerOpen(GravityCompat.START)) {
            // if you want to handle DrawerLayout
            dl.closeDrawer(GravityCompat.START);
        } else {
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                getFragmentManager().popBackStack();
            }
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
 /*
    public void valuefromIntent(){

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        Id.setText(id);
        Name.setText(name);
        Email.setText(email);
    }

  */

}
