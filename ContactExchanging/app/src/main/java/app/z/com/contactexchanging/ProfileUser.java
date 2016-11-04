package app.z.com.contactexchanging;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfileUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtPhone,txtUser,txtEmail,txtbusiness;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_user);

        loginActivity = new LoginActivity();

        txtUser =(TextView)findViewById(R.id.firstNamePro5);
        txtPhone = (TextView)findViewById(R.id.lastNamePro5);
        txtEmail =(TextView)findViewById(R.id.emailPro5);
        txtbusiness =(TextView)findViewById(R.id.business);

        txtUser.setText(loginActivity.getUsername());
        txtEmail.setText(loginActivity.getUserEmail());
        txtbusiness.setText("null");
        txtPhone.setText(loginActivity.getUserPhone());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.profile_user, menu);
        return true;
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_list_contacts:
                change2List();
                break;
            case R.id.nav_logout:
                change2Logout();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void change2List(){

        Intent IT = new Intent(ProfileUser.this,ListContacts.class);

        startActivity(IT);
    }
    private  void change2Logout(){
        Intent IT1 = new Intent(ProfileUser.this,LoginActivity.class);
        startActivity(IT1);
    }
}
