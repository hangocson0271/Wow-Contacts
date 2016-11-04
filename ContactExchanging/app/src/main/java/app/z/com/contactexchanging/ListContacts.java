package app.z.com.contactexchanging;

import android.content.Intent;
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

import app.z.com.contactexchanging.CustomContactList.MyArrayAdapter;

public class ListContacts extends AppCompatActivity implements View.OnClickListener {

    EditText search_badge_1;
    TextView txtBack2Pro5;
    ImageView searchContacts;
    MyArrayAdapter adapter;
    ListView lvContact =null;

    String[] lContact = {
            "Jacky Satan",
            "Hell Elo",

    } ;
    Integer[] imgID = {
            R.drawable.ic_contacts_1,
            R.drawable.ic_contacts_2,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contacts);
        
        search_badge_1 =(EditText)findViewById(R.id.search_badge_1);
        txtBack2Pro5 =(TextView)findViewById(R.id.back2Pro5);
        searchContacts =(ImageView)findViewById(R.id.imageView6);

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
                // When user changed the Text
                ListContacts.this.adapter.getFilter().filter(cs);
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
