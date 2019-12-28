package app.z.com.contactexchanging;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;


import app.z.com.contactexchanging.CustomContactList.ListViewAdapter;
import app.z.com.contactexchanging.CustomContactList.person;

public class ListContacts extends AppCompatActivity implements View.OnClickListener {

    String name="",phone="",email="";
    ImageView searchbt;
    TextView back2pro;
    ListView list;
    ListViewAdapter adapter;
    EditText editsearch;
    String[] names;
    int[] photos;
    ArrayList<person> arraylist = new ArrayList<person>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_contacts);

        names = new String[]{"Jack Hell", "Simmi", "Holy Men"};

        back2pro = (TextView)findViewById(R.id.back2Pro5);
        searchbt =(ImageView)findViewById(R.id.imageView6);

        back2pro.setOnClickListener(this);
        searchbt.setOnClickListener(this);

        photos = new int[]{R.drawable.ic_contacts_1, R.drawable.ic_contacts_2,
                R.drawable.ic_contacts_1};

        list = (ListView) findViewById(R.id.listViewContact);


        for (int i = 0; i < names.length; i++)
        {
            person wp = new person(names[i],photos[i]);
            arraylist.add(wp);
        }
        adapter = new ListViewAdapter(this, arraylist);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            /*    AlertDialog alertDialog = new AlertDialog.Builder(ListContacts.this).create();
                alertDialog.setTitle(names[position]);
                alertDialog.setMessage("Name: "+names[position]+"\n"
                                       +"PhoneNumber: "+phone+"\n"
                                       +"Email: "+email
                );

                alertDialog.show();
            */
                AlertDialog alertDialog = new AlertDialog.Builder(ListContacts.this).create();
                alertDialog.setTitle(names[position]);

                alertDialog.setMessage("Name: "+names[position]+"\n"
                        +"Phone: "+phone+"\n"
                        +"Email: "+email
                );
                alertDialog.show();
                Toast.makeText(getBaseContext(),names[position],Toast.LENGTH_SHORT).show();
            }
        });
        editsearch = (EditText)findViewById(R.id.search_badge_1);
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }


        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0];
            String[] txtPack = new String(message.getRecords()[0].getPayload()).split(" ");

            Toast.makeText(getBaseContext(),txtPack[0]+"\n "+txtPack[1]+"\n "+txtPack[2],Toast.LENGTH_SHORT).show();

            name =txtPack[0];
            phone=txtPack[1];
            email=txtPack[2];

            names[3] = txtPack[0];
            photos[3]=photos[0];


        } else
            Toast.makeText(getBaseContext(),"Waiting for NDEF Message",Toast.LENGTH_SHORT).show();
}

    private void back2Pro5() {
        this.finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back2Pro5:

                back2Pro5();
                break;
            case R.id.imageView6:

                editsearch.setEnabled(true);
                break;
}
    }
}
