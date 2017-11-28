package tex.firebas.financialmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Options extends AppCompatActivity {
    Button invoiceBtn , productBtn , reportBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        invoiceBtn = (Button)findViewById(R.id.invoiceBtn);
        productBtn = (Button)findViewById(R.id.productBtn);
        reportBtn = (Button)findViewById(R.id.reportBtn);



        invoiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Options.this , Invoice.class));
            }
        });
    }
}
