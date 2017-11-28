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

public class CreateInvoice extends AppCompatActivity {

    JSONParser     jsonParser      ;
    ProgressDialog progressDialog  ;
    int            value           ;
    JSONObject jsonObject          ;

    Button saveBtn ;
    EditText companyET , dataET , timeET , numberET , typeET , amountET , nameET , totalET ;
    String company , data , time , number , type , amount , name , total ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);


        jsonParser = new JSONParser();

        companyET = (EditText) findViewById(R.id.compET  );
        dataET    = (EditText) findViewById(R.id.dataET  );
        timeET    = (EditText) findViewById(R.id.timeET  );
        numberET  = (EditText) findViewById(R.id.numberET);
        typeET    = (EditText) findViewById(R.id.typeET  );
        amountET  = (EditText) findViewById(R.id.amountET);
        nameET    = (EditText) findViewById(R.id.nameET  );
        totalET   = (EditText) findViewById(R.id.totalET );

        saveBtn   = (Button)findViewById(R.id.saveBtn    );


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyET.getText().toString().length() > 1 &&
                dataET.getText().toString().length()   > 1 &&
                timeET.getText().toString().length()   > 1 &&
                numberET.getText().toString().length() > 1 &&
                typeET.getText().toString().length()   > 1 &&
                amountET.getText().toString().length() > 1 &&
                nameET.getText().toString().length()   > 1 &&
                totalET.getText().toString().length()  > 1 ){

                    company = companyET.getText().toString();
                    data    = dataET.getText().toString()   ;
                    time    = timeET.getText().toString()   ;
                    number  = numberET.getText().toString() ;
                    type    = typeET.getText().toString()   ;
                    amount  = amountET.getText().toString() ;
                    name    = nameET.getText().toString()   ;
                    total   = totalET.getText().toString()  ;

                    new AddInvoiceTask().execute();

                }
            }
        });
    }




    class AddInvoiceTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute()              ;
            progressDialog = new ProgressDialog(CreateInvoice.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>() ;
            list.add(new BasicNameValuePair("company" ,  company )) ;
            list.add(new BasicNameValuePair("data"    ,  data    )) ;
            list.add(new BasicNameValuePair("time"    ,  time    )) ;
            list.add(new BasicNameValuePair("number"  ,  number  )) ;
            list.add(new BasicNameValuePair("type"    ,  type    )) ;
            list.add(new BasicNameValuePair("amount"  ,  amount  )) ;
            list.add(new BasicNameValuePair("name"    ,  name    )) ;
            list.add(new BasicNameValuePair("total"   ,  total   )) ;

            jsonObject = jsonParser.makeHttpRequest("http://"+GLOBAL.url+"/FM/add_invoice.php" , "POST" , list);

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

                startActivity(new Intent(CreateInvoice.this , Options.class));
                Toast.makeText(getApplicationContext() , "Invoice added successfully", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext() ,JSONParser.json , Toast.LENGTH_LONG).show();
                companyET.setText(JSONParser.json);

            }
            progressDialog.dismiss();
        }


    }
}
