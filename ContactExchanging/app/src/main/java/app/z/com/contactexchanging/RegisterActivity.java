package app.z.com.contactexchanging;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import app.z.com.contactexchanging.HttpHandler.HttpHandler;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnRegister;
    TextView txtLogin,showErr;

    ProgressDialog progressDialog;
    EditText edEmail,edPassword,edName,edPhone,edRePassword;

    String url_get_token;
    String url_register ;
    String key_wow,id_wow;

    String messErr;
    String userName,userPassword,userRePassword,userPhone,userEmail;
    String a ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        url_get_token = getString(R.string.url_get_token);
        url_register =getString(R.string.url_create_register);
        key_wow = getString(R.string.k_wow_contacts);
        id_wow = getString(R.string.id_wow_contatcs);

        txtLogin =(TextView)findViewById(R.id.back2Login);
        btnRegister =(Button)findViewById(R.id.btnRegister);

        showErr = (TextView)findViewById(R.id.showErr);
        edEmail = (EditText)findViewById(R.id.email_register) ;
        edName =(EditText)findViewById(R.id.user_name);
        edPassword =(EditText)findViewById(R.id.user_password_register);
        edRePassword =(EditText)findViewById(R.id.user_RePassword);
        edPhone =(EditText)findViewById(R.id.phone_register);

        txtLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                checkRegister();
                break;
            case R.id.back2Login:
                change2Login();
                break;
        }
    }

    private void change2Login(){
        Intent IT = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(IT);

    }


    private void checkRegister(){
     userName = edName.getText().toString();
     userEmail =edEmail.getText().toString();
     userPassword =edPassword.getText().toString();
     userRePassword =edRePassword.getText().toString();
     userPhone = edPhone.getText().toString();
        if (userPhone.equals(a)==true||userName.equals(a)==true||userPassword.equals(a)==true||userRePassword.equals(a)==true||userEmail.equals(a)==true){
            showErr.setText("Can't be blank if you want register!");
        }
        else {
            if (userPassword.equals(userRePassword)==false){
                showErr.setText("Password and RePassword not same!");
            }

            else {
                RegiterAcc();
            }
        }
    }
    private void RegiterAcc(){
        new GetContacts().execute();

    }
    public String showMess123(){
        return messErr;
    }

    public class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try {

                HttpHandler httpHandler = new HttpHandler(url_register);
                HttpHandler tk = new HttpHandler(url_get_token);

                tk.addParameter("client_id",id_wow);
                tk.addParameter("client_secret",key_wow);
                tk.addParameter("grant_type","client_credentials");

                tk.execute(HttpHandler.RequestMethod.POST);
                String jsonToken = tk.getResponse();
                String tokenJson,tokenType;
                JSONObject jsonObject = new JSONObject(jsonToken);
                tokenJson = jsonObject.optString("access_token");
                tokenType = jsonObject.optString("token_type");

                httpHandler.addHeader("Authorization",tokenType+" "+tokenJson);

                httpHandler.addParameter("first_name",userName);
                httpHandler.addParameter("last_name",userPhone);
                httpHandler.addParameter("password",userPassword);
                httpHandler.addParameter("email",userEmail);

                httpHandler.execute(HttpHandler.RequestMethod.POST);

                String jsonUser = httpHandler.getResponse();
                JSONObject jsonUsers = new JSONObject(jsonUser);
                String checkDone = jsonUsers.optString("status");

                if(checkDone.equals("true")==true){

                    change2Login();

                }
                else {
                    String failed = jsonUsers.optString("errors");
                    messErr = failed;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (progressDialog.isShowing())
                progressDialog.dismiss();
                showErr.setText(showMess123());
        }
    }

}
