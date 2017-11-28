package tex.firebas.financialmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageUsers extends AppCompatActivity {

    Button registerUserBtn ;
    EditText emailText , nameText  , mobileText , userPassText , confPassText ;

    JSONParser jsonParser ;
    ProgressDialog progressDialog ;
    int value  , id = 0;

    String[] names , emails  , passwords ;
    String sName , sEmail , sPassword , sMobile ;

    int[] mobiles ;
    JSONObject jsonObject ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        id = getIntent().getIntExtra("id" , 0);

        jsonParser = new JSONParser();

        registerUserBtn = (Button)findViewById(R.id.registerUserbtn)   ;
        emailText =       (EditText)findViewById(R.id.emailText    )   ;
        nameText  =        (EditText)findViewById(R.id.nameText     )   ;
        mobileText =      (EditText)findViewById(R.id.mobileText   )   ;
        userPassText =    (EditText)findViewById(R.id.userPassText )   ;
        confPassText =    (EditText)findViewById(R.id.confPassText )   ;

        confPassText.setVisibility(View.INVISIBLE);

        userPassText.setInputType(0);

        registerUserBtn.setText("Save");

        new GetUserInfo().execute();
        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(     emailText   .getText().toString().length() > 1 &&
                        nameText    .getText().toString().length() > 1 &&
                        mobileText  .getText().toString().length() > 1 &&
                        userPassText.getText().toString().length() > 1

                        ){

                    sEmail    = emailText.getText().toString()    ;
                    sName     = nameText.getText().toString()     ;
                    sMobile   = mobileText.getText().toString()   ;
                    sPassword = userPassText.getText().toString() ;
                    new ManageUsers.EditUserTask().execute();

                }else{
                    Toast.makeText(ManageUsers.this , "Error 1" , Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    class EditUserTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute()              ;
            progressDialog = new ProgressDialog(ManageUsers.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("id"     ,  id+"" )   ) ;
            list.add(new BasicNameValuePair("name"     ,  sName )   ) ;
            list.add(new BasicNameValuePair("email"    ,  sEmail)   ) ;
            list.add(new BasicNameValuePair("mobile"   ,  sEmail)   ) ;
            list.add(new BasicNameValuePair("password" ,  sPassword)) ;

            jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/edit_user.php" , "POST" , list);

            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                }else{
                    value = 3 ;
                }
            }catch(Exception e){
                Log.d("ERROR : " , e.getMessage());
            }
            return null;
        }

        @Override        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(value == 1){

                startActivity(new Intent(ManageUsers.this , Options.class));
                Toast.makeText(getApplicationContext() , "Data updated ", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext() ,JSONParser.json , Toast.LENGTH_LONG).show();
                nameText.setText(JSONParser.json);

            }
            progressDialog.dismiss();
        }


    }




    class GetUserInfo extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute()              ;
            progressDialog = new ProgressDialog(ManageUsers.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("id"     ,  id+"" )   ) ;


            jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/get_user_info.php" , "POST" , list);

            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                    JSONArray jsonArray = jsonObject.getJSONArray("users");

                    names = new String[jsonArray.length()];
                    emails = new String[jsonArray.length()];
                    passwords = new String[jsonArray.length()];

                    mobiles      = new int[jsonArray.length()]   ;

                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        names[i] = object.getString("name");
                        emails[i] = object.getString("email");
                        passwords[i] = object.getString("password");

                        mobiles[i]      = object.getInt("mobile");

                    }
                }else{
                    value = 3 ;
                }
            }catch(Exception e){
                Log.d("ERROR : " , e.getMessage());
            }
            return null;
        }

        @Override        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(value == 1){

                nameText.setText(names[0]);
                emailText.setText(emails[0]);
                userPassText.setText(passwords[0]);
                mobileText.setText(mobiles[0]+"");

                Toast.makeText(getApplicationContext() , "Data retrieved "+sName, Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext() ,JSONParser.json , Toast.LENGTH_LONG).show();
                nameText.setText(JSONParser.json);

            }
            progressDialog.dismiss();
        }


    }

}
