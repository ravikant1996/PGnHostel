package com.example.testlogin.PG_manager_profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testlogin.R;
import com.example.testlogin.SessionManager;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class pgManagerProfile extends AppCompatActivity {
    TextView Name, Id, Email;

    private DrawerLayout dl_manager;
    private ActionBarDrawerToggle t_manager;
    private NavigationView nv_PgManager;
    TextView managerName;
    CircleImageView circleImageView;

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_manager_profile);

        session = new SessionManager(getApplicationContext());

        dl_manager = findViewById(R.id.activity_pg_manager_profile);
        t_manager = new ActionBarDrawerToggle(this, dl_manager,R.string.Open, R.string.Close);

        dl_manager.addDrawerListener(t_manager);
        t_manager.syncState();

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
       /* Id.setText(Html.fromHtml("PG Code: <b>" + id + "</b>"));
        Name.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        Email.setText(Html.fromHtml("Email: <b>" + email + "</b>"));*/

        //     valuefromIntent();

        nv_PgManager = findViewById(R.id.nv_pgmanager);
        View headerView = nv_PgManager.inflateHeaderView(R.layout.pg_manager_navigation_header);
        circleImageView = (CircleImageView) headerView.findViewById(R.id.manager_profile_image);

        managerName = (TextView) headerView.findViewById(R.id.managerName);
        managerName.setText(Html.fromHtml("<b>" + name + "</b>"));

        nv_PgManager.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                        .beginTransaction();
                switch(id)
                {
                    case R.id.acc:
                        Toast.makeText(pgManagerProfile.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.add_user:
                      /*  user_reg_frag addUser = new user_reg_frag();
                        fragmentTransaction.replace(R.id.user_main_content, addUser);
                        fragmentTransaction.commit();
                        dl_manager.closeDrawer(GravityCompat.START);*/
                        break;
                    case R.id.logout:
                        session.logoutUser();
                        Toast.makeText(pgManagerProfile.this, "Thank You!",Toast.LENGTH_SHORT).show();break;
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t_manager.onOptionsItemSelected(item))
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
