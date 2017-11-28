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

public class CreateProduct extends AppCompatActivity {

    String price , type , stock , title ;

    EditText priceET , typeET , stockET , titleET ;
    Button saveBtn ;

    JSONParser     jsonParser      ;
    ProgressDialog progressDialog  ;
    int            value           ;
    JSONObject jsonObject          ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        jsonParser = new JSONParser();

        priceET = (EditText)findViewById(R.id.priceET);
        typeET  = (EditText)findViewById(R.id.typeET );
        stockET = (EditText)findViewById(R.id.stockET);
        titleET = (EditText)findViewById(R.id.titleET);

        saveBtn = (Button)findViewById(R.id.saveBtn  );


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceET.getText().toString().length() > 1 &&
                   typeET.getText().toString().length() > 1 &&
                   stockET.getText().toString().length() > 1 &&
                   titleET.getText().toString().length() > 1 ){

                   price =  priceET.getText().toString() ;
                   type  = typeET.getText().toString() ;
                   stock =  stockET.getText().toString() ;
                   title = titleET.getText().toString() ;

                   new AddProductTask().execute();


                }else{

                }
            }
        });
    }







    class AddProductTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute()              ;
            progressDialog = new ProgressDialog(CreateProduct.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>() ;
            list.add(new BasicNameValuePair("price" ,  price       )) ;
            list.add(new BasicNameValuePair("type"    ,  type      )) ;
            list.add(new BasicNameValuePair("stock"    ,  stock    )) ;
            list.add(new BasicNameValuePair("title"  ,  title      )) ;

            jsonObject = jsonParser.makeHttpRequest("http://192.168.1.105/FM/add_product.php" , "POST" , list);

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

                startActivity(new Intent(CreateProduct.this , Options.class));
                Toast.makeText(getApplicationContext() , "Product added successfully", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getApplicationContext() ,JSONParser.json , Toast.LENGTH_LONG).show();
                priceET.setText(JSONParser.json);

            }
            progressDialog.dismiss();
        }


    }
}
