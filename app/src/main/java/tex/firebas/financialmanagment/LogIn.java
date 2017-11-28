package tex.firebas.financialmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LogIn extends AppCompatActivity {

    EditText usenameText , passText ;
    Button   loginUserBtn           ;

    JSONParser jsonParser ;
    ProgressDialog progressDialog ;
    String name ,password ;
    int value ;

    static int admin = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        jsonParser = new JSONParser() ;

        loginUserBtn = (Button)findViewById(R.id.loginUserbtn)  ;
        usenameText  = (EditText)findViewById(R.id.usernameText);
        passText     = (EditText)findViewById(R.id.passText)    ;


        loginUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usenameText.getText().toString().length() > 1 && passText.getText().toString().length() > 1){
                    name = usenameText.getText().toString() ;
                    password = passText.getText().toString();
                    new LogTask().execute();
                }else{
                    Toast.makeText(LogIn.this , "Error 1" , Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    class LogTask extends AsyncTask<String , String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LogIn.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {


            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("name" ,name ));
            list.add(new BasicNameValuePair("password" , password));


            JSONObject jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/get_user.php" , "POST" , list);
            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value  = jsonObject.getInt("value");
                    admin  =  jsonObject.getInt("admin");
                }else{
                    value = 3 ;

                }

            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(value == 1){
                Toast.makeText(LogIn.this , "success ..."+admin , Toast.LENGTH_LONG).show();


                startActivity(new Intent(LogIn.this , Options.class));


            }else{
                Toast.makeText(LogIn.this , "Error : "+value, Toast.LENGTH_LONG).show();
                Log.e("json" , JSONParser.json);
            }


        }
    }



}
