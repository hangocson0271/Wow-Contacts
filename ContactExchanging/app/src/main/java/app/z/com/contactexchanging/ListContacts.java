package app.z.com.contactexchanging;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

import app.z.com.contactexchanging.CustomContactList.MyArrayAdapter;

public class ListContacts extends AppCompatActivity implements View.OnClickListener {

    BluetoothAdapter mBtAdapter;
    EditText search_badge_1;
    TextView txtBack2Pro5;
    ImageView searchContacts;
    MyArrayAdapter adapter;
    ListView lvContact =null;

    Handler bluetoothIn;

    final int handlerState = 0;                        //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();


    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;


    String[] lContact = {
            "Jacky Satan",
            "Hell Elo",
            "Jimmy Cho"

    } ;
    Integer[] imgID =  {
            R.drawable.ic_contacts_1,
            R.drawable.ic_contacts_2,
            R.drawable.ic_contacts_1
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);

        search_badge_1 =(EditText)findViewById(R.id.search_badge_1);
        txtBack2Pro5 =(TextView)findViewById(R.id.back2Pro5);
        searchContacts =(ImageView)findViewById(R.id.imageView6);

        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0];
            String txt[] = new String(message.getRecords()[0].getPayload()).split(" ");
            //mTextView.setText(new String(message.getRecords()[0].getPayload()));
            lContact[2] = txt[0];
            imgID[2] = imgID[0];
            for(int i=0;i<=lContact.length+1;i++){
                if(lContact[i]==""){
                    lContact[i]=txt[0];
                    imgID[i]=imgID[0];
                    Toast.makeText(getBaseContext(),"Name : "+txt[0]+"\n"+"Phone :"
                            +txt[1]+"\n"+"Email :"
                            +txt[2],Toast.LENGTH_SHORT)
                            .show();
                }
            }
        } else
            Toast.makeText(getBaseContext(),"Waiting for NDEF Message",Toast.LENGTH_SHORT).show();

        lvContact=(ListView) findViewById(R.id.listViewContact);
        customListView();

        txtBack2Pro5.setOnClickListener(this);
        searchContacts.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back2Pro5:
                back2Pro5();
                break;
            case R.id.imageView6:
                search();
                break;
        }
    }

    private void back2Pro5(){
        this.finish();
    }

    private void customListView(){
         adapter = new
                MyArrayAdapter(ListContacts.this, lContact, imgID);
        lvContact=(ListView)findViewById(R.id.listViewContact);
        lvContact.setAdapter(adapter);
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ListContacts.this, "You Clicked at " +lContact[+ position], Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void search(){
        search_badge_1.setEnabled(true);
        search_badge_1.callOnClick();

        search_badge_1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                ListContacts.this.adapter.getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
    }

