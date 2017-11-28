package tex.firebas.financialmanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductDetails extends AppCompatActivity {

    TextView priceText , typeText , stockText , titleText ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        priceText = (TextView)findViewById(R.id.priceText);
        typeText  = (TextView)findViewById(R.id.typeText );
        stockText = (TextView)findViewById(R.id.stockText);
        titleText = (TextView)findViewById(R.id.titleText);
    }
}
