package tex.firebas.financialmanagment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDetails extends AppCompatActivity {

    TextView companyText , dataText , timeText , numberText , typeText , amountText , nameText , totalText ;

    JSONParser jsonParser          ;
    ProgressDialog progressDialog  ;
    int       value                ;
    String[]  companies  , times , numbers , types , names , totals ;
    int[]      data , amounts ;


    int id = 2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);

        jsonParser = new JSONParser();

        companyText = (TextView) findViewById(R.id.compText  );
        dataText    = (TextView) findViewById(R.id.dataText  );
        timeText    = (TextView) findViewById(R.id.timeText  );
        numberText  = (TextView) findViewById(R.id.numberText);
        typeText    = (TextView) findViewById(R.id.typeText  );
        amountText  = (TextView) findViewById(R.id.amountText);
        nameText    = (TextView) findViewById(R.id.nameText  );
        totalText   = (TextView) findViewById(R.id.totalText );
        
        new DisplayAllInvoices().execute() ;



    }


    class DisplayAllInvoices extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InvoiceDetails.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("id" ,  id+""       )) ;



            JSONObject jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/get_invoice_details.php" , "POST" , list);


            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                    JSONArray jsonArray = jsonObject.getJSONArray("invoices");

                    companies = new String[jsonArray.length()];
                    times     = new String[jsonArray.length()];
                    numbers   = new String[jsonArray.length()];
                    types     = new String[jsonArray.length()];
                    names     = new String[jsonArray.length()];
                    totals    = new String[jsonArray.length()];

                    data      = new int[jsonArray.length()]   ;
                    amounts   = new int[jsonArray.length()]   ;




                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject object = jsonArray.getJSONObject(i);



                        companies[i] = object.getString("company");
                        times[i]     = object.getString("time");
                        numbers[i]  = object.getString("number");
                        types[i]    = object.getString("type");
                        names[i]    = object.getString("name");
                        totals[i]    = object.getString("total");

                        data[i]      = object.getInt("data");
                        amounts[i]  = object.getInt("amount");




                    }
                }else{
                    value = 0 ;
                }
            }catch(Exception e){
                Log.d("ERROR : " , e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(value == 1){
                Toast.makeText(getApplicationContext() , "Done ..." , Toast.LENGTH_SHORT).show();

                companyText.setText("Company name : "+companies[0]);
                dataText.setText   ("Data         : "+data[0]);
                timeText.setText   ("Time         : "+times[0]);
                numberText.setText ("Number       : "+numbers[0]);
                typeText.setText   ("Type         : "+types[0]);
                amountText.setText ("Amount       : "+amounts[0]);
                nameText.setText   ("Name         : "+names[0]);
                totalText.setText  ("Total        :   "+totals[0]);

            }else{
                Toast.makeText(getApplicationContext() , JSONParser.json , Toast.LENGTH_SHORT).show();

            }
            Toast.makeText(getApplicationContext() , JSONParser.json , Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }


    }
}
