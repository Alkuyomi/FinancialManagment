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

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class CreateInvoice extends AppCompatActivity   implements CalendarDatePickerDialogFragment.OnDateSetListener , RadialTimePickerDialogFragment.OnTimeSetListener {

    JSONParser     jsonParser      ;
    ProgressDialog progressDialog  ;
    int            value           ;
    JSONObject jsonObject          ;

    Button saveBtn , numberBtn , dateBtn , timeBtn;
    EditText companyET , dataET   , typeET , amountET , nameET , totalET ;
    String company , data , time , number = "", type , amount , name , total ;

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String FRAG_TAG_TIME_PICKER = "fragment_time_picker_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);


        jsonParser = new JSONParser();

        companyET = (EditText) findViewById(R.id.compET  );
        typeET    = (EditText) findViewById(R.id.typeET  );
        amountET  = (EditText) findViewById(R.id.amountET);
        nameET    = (EditText) findViewById(R.id.nameET  );
        totalET   = (EditText) findViewById(R.id.totalET );

        saveBtn   = (Button)findViewById(R.id.saveBtn    );
        dateBtn   = (Button)findViewById(R.id.dateBtn    );
        numberBtn  = (Button) findViewById(R.id.numberBtn);
        timeBtn    = (Button) findViewById(R.id.timeBtn  );


        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(CreateInvoice.this);
                rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });


        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(CreateInvoice.this);
                cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
            }
        });

        numberBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(CreateInvoice.this , SelectProduct.class));
           }
       });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(companyET.getText().toString().length() > 1 &&
                        time.length()   > 1 &&
                        data.length()   > 1 &&
                typeET.getText().toString().length()   > 1 &&
                amountET.getText().toString().length() > 1 &&
                nameET.getText().toString().length()   > 1 &&
                totalET.getText().toString().length()  > 1 ){

                    company = companyET.getText().toString();
                    data    = dataET.getText().toString()   ;
                    time    = timeBtn.getText().toString()   ;
                    type    = typeET.getText().toString()   ;
                    amount  = amountET.getText().toString() ;
                    name    = nameET.getText().toString()   ;
                    total   = totalET.getText().toString()  ;

                    new AddInvoiceTask().execute();

                }
            }
        });
    }





    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear+1;
        dateBtn.setText(year + "-" + month + "-" + dayOfMonth);
        data = year + "-" + month + "-" + dayOfMonth ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CalendarDatePickerDialogFragment calendarDatePickerDialogFragment = (CalendarDatePickerDialogFragment) getSupportFragmentManager()
                .findFragmentByTag(FRAG_TAG_DATE_PICKER);
        if (calendarDatePickerDialogFragment != null) {
            calendarDatePickerDialogFragment.setOnDateSetListener(this);
        }


        RadialTimePickerDialogFragment rtpd = (RadialTimePickerDialogFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG_TIME_PICKER);
        if (rtpd != null) {
            rtpd.setOnTimeSetListener(this);
        }
    }


    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        timeBtn.setText(hourOfDay+":"+minute);
        time = hourOfDay+":"+minute ;
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

    @Override
    protected void onStart() {
        super.onStart();
        totalET.setText(GLOBAL.TOTAL_PRICE+"");

    }
}
