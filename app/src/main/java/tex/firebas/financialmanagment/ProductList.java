package tex.firebas.financialmanagment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {


    JSONParser jsonParser ;
    ProgressDialog progressDialog ;
    int value ;
    String[]  products ;
    ListView  proList ;
    int[] ids ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        jsonParser = new JSONParser();
        proList = (ListView)findViewById(R.id.proList);





        proList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ProductList.this , ProductDetails.class).putExtra("id" , ids[i]));
            }
        });


        new DisplayAllProducats().execute();
    }



    class DisplayAllProducats extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProductList.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();




            JSONObject jsonObject = jsonParser.makeHttpRequest("http://"+GLOBAL.url+"/FM/get_products.php" , "POST" , list);



            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    products = new String[jsonArray.length()];
                    ids      = new int[jsonArray.length()];

                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        products[i] = object.getString("title");
                        ids[i] = object.getInt("id");

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
             Log.e("json " , JSONParser.json);
            if(value == 1){
                Toast.makeText(getApplicationContext() , "Data retrieved" , Toast.LENGTH_SHORT).show();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductList.this , android.R.layout.simple_list_item_1 , android.R.id.text1 , products);
                proList.setAdapter(adapter);

            }else{
                Toast.makeText(getApplicationContext() , JSONParser.json , Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();
        }


    }
}
