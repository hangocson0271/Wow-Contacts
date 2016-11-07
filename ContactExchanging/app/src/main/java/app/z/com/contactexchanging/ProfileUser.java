package app.z.com.contactexchanging;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import app.z.com.contactexchanging.Interface.Interface;

public class ProfileUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,NfcAdapter.CreateNdefMessageCallback  {

    private static int RESULT_LOAD_IMAGE = 1;

    NfcAdapter mAdapter;
    RoundedImageView riv ;

    ImageView btnName,btnPhone,btnEmail,btnBussiness,cameraChangeIV;
    TextView txtPhone,txtUser,txtEmail,txtbusiness,txtFullName;
    String Phone="",User="",Email="",Bussiness="";
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_user);
        Log.i("1991","start");
       // pm = this.getPackageManager();
        txtFullName =(TextView)findViewById(R.id.fullName);
        txtUser =(TextView)findViewById(R.id.firstNamePro5);
        txtPhone = (TextView)findViewById(R.id.lastNamePro5);
        txtEmail =(TextView)findViewById(R.id.emailPro5);
        txtbusiness =(TextView)findViewById(R.id.business);

        riv= new RoundedImageView(this);

        Email = txtEmail.getText().toString();
        Phone = txtPhone.getText().toString();
        User = txtUser.getText().toString();
        Bussiness = txtbusiness.getText().toString();

        btnName = (ImageView)findViewById(R.id.btnEdName);
        btnPhone = (ImageView)findViewById(R.id.btnPhone);
        btnEmail = (ImageView)findViewById(R.id.btnEdEmail);
        btnBussiness = (ImageView)findViewById(R.id.btnEdBussiness);
        cameraChangeIV = (ImageView)findViewById(R.id.cameraChangeIV);

        btnName.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnBussiness.setOnClickListener(this);
       cameraChangeIV.setOnClickListener(this);

        switch1 = (Switch)findViewById(R.id.switch1);

        Intent callerIntent=getIntent();
        Bundle packageFromCaller=
                callerIntent.getBundleExtra("MyPack");

        txtFullName.setText(packageFromCaller.getString("userName"));
        txtUser.setText(packageFromCaller.getString("userName"));
        txtEmail.setText(packageFromCaller.getString("userEmail"));
        txtbusiness.setText("null");
        txtPhone.setText(packageFromCaller.getString("userPhone"));

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true)NFC();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("9772","mid");
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
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }
        else
        mAdapter.setNdefPushMessageCallback(this, this);
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

    /* TODO: CHANGE SCREEN
    * **/
    private void change2List(){

        Intent IT = new Intent(ProfileUser.this,ListContacts.class);
        startActivity(IT);
    }
    private  void change2Logout(){
        Intent IT1 = new Intent(ProfileUser.this,LoginActivity.class);
        startActivity(IT1);
    }

/*
*  switch button show dialog
* */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEdName:
                  ChangeViewTextName();
                break;
            case R.id.btnEdBussiness:
                ChangeViewBussiness();
                break;
            case R.id.btnEdEmail:
                ChangeViewEmail();
                break;
            case R.id.btnPhone:
                ChangeViewPhone();
                break;
            case R.id.cameraChangeIV:
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
        }
    }

    // Alert Dialog
    private Dialog ChangeViewTextName(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edAlerDialog);

        dialogBuilder.setTitle("Edit User Name");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String Name = edt.getText().toString();

                txtUser.setText(Name);
                txtFullName.setText(Name);

        }

        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create();
        return dialogBuilder.show() ;
    }

    private Dialog ChangeViewBussiness(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edAlerDialog);

        dialogBuilder.setTitle("Edit Business");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String Name = edt.getText().toString();
                txtbusiness.setText(Name);
            }

        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create();
        return dialogBuilder.show() ;
    }


    private Dialog ChangeViewEmail(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edAlerDialog);

        dialogBuilder.setTitle("Edit Email");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String Name = edt.getText().toString();
                txtEmail.setText(Name);
            }

        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create();
        return dialogBuilder.show() ;
    }

    private Dialog ChangeViewPhone(){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alertdialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edAlerDialog);
        edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogBuilder.setTitle("Edit Phone Number");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String Name = edt.getText().toString();

               txtPhone.setText(Name);

            }

        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.create();
        return dialogBuilder.show() ;
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
            riv.setImageBitmap(BitmapFactory.decodeFile(picturePath));

           /* riv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            riv.setImageResource(Integer.parseInt(picturePath));
   //  imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            */
        }


    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String message = txtUser+" "+txtPhone+" "+txtEmail;
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }
}
