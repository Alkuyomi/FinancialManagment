package tex.firebas.financialmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Product extends AppCompatActivity {
    Button createProductBtn , listProductBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        createProductBtn = (Button)findViewById(R.id.createProductBtn);
        listProductBtn = (Button)findViewById(R.id.listProductBtn);


        createProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Product.this , CreateProduct.class));
            }
        });

    }
}
