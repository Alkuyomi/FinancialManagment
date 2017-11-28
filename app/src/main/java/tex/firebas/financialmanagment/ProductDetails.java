package tex.firebas.financialmanagment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    JSONParser jsonParser          ;
    ProgressDialog progressDialog  ;
    int       value , id           ;
    String[]  titles  , stocks  , types  ;
    int[]    prices;
    ListView proDetailList ;

    List<String> items ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);



        items = new ArrayList<String>();

        id = 1;

        jsonParser = new JSONParser();

        proDetailList = (ListView)findViewById(R.id.proDetailList);



        new ProductDetails.DisplayAllInvoices().execute() ;



    }


    class DisplayAllInvoices extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductDetails.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("id" ,  id+""       )) ;



            JSONObject jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/get_product_details.php" , "POST" , list);


            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                    JSONArray jsonArray = jsonObject.getJSONArray("products");

                    titles = new String[jsonArray.length()];
                    prices     = new int[jsonArray.length()];
                    stocks   = new String[jsonArray.length()];
                    types     = new String[jsonArray.length()];






                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject object = jsonArray.getJSONObject(i);



                        titles[i] = object.getString("title");
                        prices[i]     = object.getInt("price");
                        stocks[i]  = object.getString("stock");
                        types[i]    = object.getString("type");





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
                Toast.makeText(getApplicationContext() , "Data retrieved " , Toast.LENGTH_SHORT).show();


                items.add("Title                       "+titles[0]);
                items.add("Price                      "+prices[0]);
                items.add("Stock                      "+stocks[0]);
                items.add("Type                       "+types[0]);


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductDetails.this , android.R.layout.simple_list_item_1 , android.R.id.text1 , items);
                proDetailList.setAdapter(adapter);

            }else{
                Toast.makeText(getApplicationContext() , JSONParser.json , Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();
        }


    }
}
