package app.z.com.contactexchanging;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class ViewContact extends AppCompatActivity implements View.OnClickListener{

    TextView fullname,name,email,phone;
    RoundedImageView avatar1;
    String names,phone1,email1;
    int avatar;
    TextView change2List;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        Intent i = getIntent();
        names = i.getStringExtra("Name");
        avatar = i.getIntExtra("Avatar", avatar);
        phone1 = i.getStringExtra("Phone");
        email1 = i.getStringExtra("Email");

        avatar1 = (RoundedImageView)findViewById(R.id.imageView12);
        fullname =(TextView)findViewById(R.id.fullName2);
        name = (TextView)findViewById(R.id.firstNamePro52);
        email = (TextView)findViewById(R.id.emailPro52);
        phone =(TextView)findViewById(R.id.lastNamePro52);

        fullname.setText(names);
        name.setText(names);
        phone.setText(phone1);
        email.setText(email1);

        avatar1.setImageResource(avatar);

        change2List =(TextView)findViewById(R.id.change2List2);
        delete = (Button)findViewById(R.id.deleteContact);

        change2List.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.change2List2:
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
