package app.z.com.contactexchanging;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.io.File;

import app.z.com.contactexchanging.HttpHandler.HttpHandler;
import app.z.com.contactexchanging.Interface.Interface;

public class ProfileUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,NfcAdapter.CreateNdefMessageCallback  {

    private static int RESULT_LOAD_IMAGE = 1;

    EditText password;
    ProgressDialog pDialog;
    NfcAdapter mAdapter;
    IntentFilter[] mNdefExchangeFilters;
    private String url_get_token ;
    private String url_login ;
    private String key_wow,id_wow;

    //FloatingActionButton floatingActionButton;
    RoundedImageView riv;
    private String id;
    private String editUrl;
    //ImageView cameraChangeIV;
    TextView txtPhone,txtUser,txtEmail,txtbusiness,txtFullName;
    String Phone="",User="",Email="",Bussiness="",Password="";
    Switch switch1;
    Intent callerIntent;
    PendingIntent mNFCPending ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);

        riv = new RoundedImageView(this);

        mNFCPending = PendingIntent.getActivity(this,0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try {
            ndefDetected.addDataType("text/plain");
        } catch (IntentFilter.MalformedMimeTypeException e) {
        }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
        
        //riv.setImageDrawable();

        riv.setCornerRadius((float) 10);
        riv.setBorderWidth((float) 2);
        riv.setBorderColor(Color.DKGRAY);
        riv.mutateBackground(true);
        riv.setImageResource(R.drawable.avatar_1);
        riv.setOval(true);
        pDialog = new ProgressDialog(this);

        key_wow = getString(R.string.k_wow_contacts);
        id_wow = getString(R.string.id_wow_contatcs);
        url_get_token = getString(R.string.url_get_token);
        editUrl = getString(R.string.url_edit_user);

       // floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);

        password=(EditText)findViewById(R.id.password);
        txtFullName =(TextView)findViewById(R.id.fullName);
        txtUser =(TextView)findViewById(R.id.firstNamePro5);
        txtPhone = (TextView)findViewById(R.id.lastNamePro5);
        txtEmail =(TextView)findViewById(R.id.emailPro5);
        txtbusiness =(TextView)findViewById(R.id.business);

        //cameraChangeIV = (ImageView)findViewById(R.id.cameraChangeIV);

      /*  cameraChangeIV.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
*/
        switch1 = (Switch)findViewById(R.id.switch1);

        callerIntent=getIntent();
        Bundle packageFromCaller=
                callerIntent.getBundleExtra("MyPack");

        txtFullName.setText(packageFromCaller.getString("userName"));
        txtUser.setText(packageFromCaller.getString("userName"));
        txtEmail.setText(packageFromCaller.getString("userEmail"));
        txtbusiness.setText("null");
        txtPhone.setText(packageFromCaller.getString("userPhone"));

        Email = txtEmail.getText().toString();
        Phone = txtPhone.getText().toString();
        User = txtUser.getText().toString();
        Bussiness = txtbusiness.getText().toString();
        Password = packageFromCaller.getString("password");
        id = packageFromCaller.getString("id");
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true)NFC();
            }
        });

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


    void NFC(){
        NfcAdapter mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this,"This device haven't NFC"+"\n"+"plz try in other device",Toast.LENGTH_SHORT).show();
                switch1.setChecked(false);

            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings."+"\n"+"And try again", Toast.LENGTH_LONG).show();
               switch1.setChecked(false);

        }
        else {

            Toast.makeText(getBaseContext(),"NFC enable",Toast.LENGTH_SHORT).show();

            mAdapter.setNdefPushMessageCallback(this, this);
        }
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

    public class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                HttpHandler httpHandler = new HttpHandler(editUrl+"1371280208606265524");


                HttpHandler tk = new HttpHandler(url_get_token);

                tk.addParameter("client_id",id_wow);
                tk.addParameter("client_secret",key_wow);
                tk.addParameter("grant_type","client_credentials");

                tk.execute(HttpHandler.RequestMethod.POST);
                String jsonToken = tk.getResponse();

                Log.i("getJSONlo gin",jsonToken);

                String tokenJson,tokenType;
                JSONObject jsonObject = new JSONObject(jsonToken);
                tokenJson = jsonObject.optString("access_token");
                tokenType = jsonObject.optString("token_type");

                httpHandler.addHeader("Authorization",tokenType+" "+tokenJson);

                httpHandler.addParameter("password",Password);
                httpHandler.addParameter("email",Email);
                httpHandler.addParameter("first_name",User);
                httpHandler.addParameter("last_name",Phone);

                /*httpHandler.addParameter("password",userPass);
                httpHandler.addParameter("email",username);
*/

                httpHandler.execute(HttpHandler.RequestMethod.PUT);

                String jsonStatus =httpHandler.getResponse();
                JSONObject jsonObject1 = new JSONObject(jsonStatus);
                String status = jsonObject1.optString("status");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
                password.setText("");
                password.setEnabled(false);
          /*  Toast.makeText(getBaseContext(),showMess(),Toast.LENGTH_SHORT).show();*/
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          /*  case R.id.cameraChangeIV:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
          *//*  case R.id.fab:
                new GetContacts().execute();
                break;
*/
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String message = "WELCOME"+"\n"+User+"\n "+Phone+"\n "+Email;

        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());

        /* NdefRecord ndefRecord1 = NdefRecord.createTextRecord("text/plain",message);*/

        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

}

