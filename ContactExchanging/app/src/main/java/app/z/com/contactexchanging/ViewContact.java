package app.z.com.contactexchanging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity implements View.OnClickListener{

    TextView change2List;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        change2List =(TextView)findViewById(R.id.change2List);
        delete = (Button)findViewById(R.id.deleteContact);

        change2List.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.change2List:
                back2List();
                break;
            case R.id.deleteContact:
                break;
        }
    }

    public void back2List(){
        Intent IT1 = new Intent(ViewContact.this,ListContacts.class);
        startActivity(IT1);
    }


}
