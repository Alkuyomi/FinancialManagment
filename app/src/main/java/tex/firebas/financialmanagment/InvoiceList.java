package tex.firebas.financialmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InvoiceList extends AppCompatActivity {


    JSONParser jsonParser          ;
    ProgressDialog progressDialog  ;
    int       value                ;
    String[]  invoices             ;
    int[]     ids                  ;
    ListView  invList              ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_list);

         jsonParser = new JSONParser();
         invList = (ListView)findViewById(R.id.invList);



        invList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(InvoiceList.this , InvoiceDetails.class).putExtra("id" , ids[i]));
            }
        });


        new DisplayAllInvoices().execute();
    }



    class DisplayAllInvoices extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(InvoiceList.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();




            JSONObject jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/get_invoices.php" , "POST" , list);



            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                    JSONArray jsonArray = jsonObject.getJSONArray("invoices");
                    invoices = new String[jsonArray.length()];
                    ids      = new int[jsonArray.length()]   ;

                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        invoices[i] = object.getString("name");
                        ids[i]      = object.getInt("id");

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
                Toast.makeText(getApplicationContext() , "Data retrieved" , Toast.LENGTH_SHORT).show();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(InvoiceList.this , android.R.layout.simple_list_item_1 , android.R.id.text1 , invoices);
                invList.setAdapter(adapter);

            }else{
                Toast.makeText(getApplicationContext() , JSONParser.json , Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();
        }


    }
}
