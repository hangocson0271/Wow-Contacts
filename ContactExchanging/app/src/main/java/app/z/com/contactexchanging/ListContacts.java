package app.z.com.contactexchanging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import app.z.com.contactexchanging.CustomContactList.MyArrayAdapter;

public class ListContacts extends AppCompatActivity implements View.OnClickListener {

    TextView txtBack2Pro5;
    ImageView searchContacts;

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
                break;
        }
    }

    private void back2Pro5(){
        Intent IT = new Intent(ListContacts.this,ProfileUser.class);
        startActivity(IT);
    }

    private void customListView(){
        MyArrayAdapter adapter = new
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

    }

}
