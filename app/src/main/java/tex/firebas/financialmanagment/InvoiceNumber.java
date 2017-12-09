package tex.firebas.financialmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InvoiceNumber extends AppCompatActivity {

    Button addProduct ;
    TextView totalText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_number);

        addProduct = (Button)findViewById(R.id.addBtn);
        totalText = (TextView)findViewById(R.id.totalText);

    }
}
