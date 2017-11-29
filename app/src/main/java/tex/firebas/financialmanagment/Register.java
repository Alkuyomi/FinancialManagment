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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {
    Button registerUserBtn , deleteUserBtn ;
    EditText emailText , nameText  , mobileText , userPassText , confPassText ;

    JSONParser jsonParser ;
    ProgressDialog progressDialog ;
    int value  ;

    String sName , sEmail , sPassword , sMobile ;

    JSONObject jsonObject ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        jsonParser = new JSONParser();

        registerUserBtn = (Button)findViewById(R.id.registerUserbtn)   ;
        deleteUserBtn = (Button)findViewById(R.id.deleteUserBtn)   ;
        emailText =       (EditText)findViewById(R.id.emailText    )   ;
        nameText =        (EditText)findViewById(R.id.nameText     )   ;
        mobileText =      (EditText)findViewById(R.id.mobileText   )   ;
        userPassText =    (EditText)findViewById(R.id.userPassText )   ;
        confPassText =    (EditText)findViewById(R.id.confPassText )   ;

        deleteUserBtn.setVisibility(View.INVISIBLE);


        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(     emailText   .getText().toString().length() > 1 &&
                        nameText    .getText().toString().length() > 1 &&
                        mobileText  .getText().toString().length() > 1 &&
                        userPassText.getText().toString().length() > 1 &&
                        confPassText.getText().toString().length() > 1 &&
                        userPassText.getText().toString().equals(confPassText.getText().toString()) ){

                        sEmail    = emailText.getText().toString()    ;
                        sName     = nameText.getText().toString()     ;
                        sMobile   = mobileText.getText().toString()   ;
                        sPassword = userPassText.getText().toString() ;
                        new AddUserTask().execute();

                }else{
                    Toast.makeText(Register.this , "Wrong Data" , Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    class AddUserTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute()              ;
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name"     ,  sName )   ) ;
            list.add(new BasicNameValuePair("email"    ,  sEmail)   ) ;
            list.add(new BasicNameValuePair("mobile"   ,  sMobile)   ) ;
            list.add(new BasicNameValuePair("password" ,  sPassword)) ;

             jsonObject = jsonParser.makeHttpRequest("http://"+GLOBAL.url+"/FM/add_user.php" , "POST" , list);

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

                startActivity(new Intent(Register.this , Options.class));
                Toast.makeText(getApplicationContext() , "Welcome "+sName, Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext() ,JSONParser.json , Toast.LENGTH_LONG).show();
                nameText.setText(JSONParser.json);

            }
            progressDialog.dismiss();
        }


    }



}
