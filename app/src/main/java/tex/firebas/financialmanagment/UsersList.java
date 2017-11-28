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

public class UsersList extends AppCompatActivity {




    JSONParser jsonParser          ;
    ProgressDialog progressDialog  ;
    int       value                ;
    String[]  users             ;
    int[]     ids                  ;
    ListView userList              ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);



        jsonParser = new JSONParser();
        userList = (ListView)findViewById(R.id.userList);



        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(UsersList.this , ManageUsers.class).putExtra("id" , ids[i]));
            }
        });


        new UsersList.DisplayAllInvoices().execute();
    }



    class DisplayAllInvoices extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UsersList.this);
            progressDialog.setTitle("wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            List<NameValuePair> list = new ArrayList<NameValuePair>();




            JSONObject jsonObject = jsonParser.makeHttpRequest("http://"+GLOBAL.url+"/FM/get_users.php" , "POST" , list);



            try{
                if(jsonObject != null && !jsonObject.isNull("value")){
                    value = jsonObject.getInt("value");
                    JSONArray jsonArray = jsonObject.getJSONArray("users");
                    users = new String[jsonArray.length()];
                    ids      = new int[jsonArray.length()]   ;

                    for(int i = 0 ; i < jsonArray.length() ; i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        users[i] = object.getString("name");
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
            Log.e("json " , JSONParser.json);
            if(value == 1){
                Toast.makeText(getApplicationContext() , "Data retrieved" , Toast.LENGTH_SHORT).show();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(UsersList.this , android.R.layout.simple_list_item_1 , android.R.id.text1 , users);
                userList.setAdapter(adapter);

            }else{
                Toast.makeText(getApplicationContext() , JSONParser.json , Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();
        }


    }
}
