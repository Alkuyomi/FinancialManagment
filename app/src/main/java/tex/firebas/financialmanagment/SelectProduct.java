package tex.firebas.financialmanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectProduct extends AppCompatActivity {

    Button selectBtn , upBtn , downBtn , saveBtn;
    TextView quanText ;
    int quan = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product);

        selectBtn = (Button)findViewById(R.id.selectBtn);
        upBtn = (Button)findViewById(R.id.upBtn);
        downBtn = (Button)findViewById(R.id.downBtn);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        quanText = (TextView)findViewById(R.id.quanText);

        quanText.setText(quan+"");



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GLOBAL.TOTAL_PRICE =  GLOBAL.SELECTED_PRODUCT_PRICE * quan;
                Log.i("Total : " ,  GLOBAL.TOTAL_PRICE+"");

                finish();
            }
        });

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectProduct.this , SelectProductList.class));
            }
        });

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quan++ ;
                quanText.setText(quan+"");
                GLOBAL.TOTAL_PRICE =  GLOBAL.SELECTED_PRODUCT_PRICE * quan;
                Log.i("Total : " ,  GLOBAL.TOTAL_PRICE+"");
            }
        });



        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quan != 0)
                    quan--;
                quanText.setText(quan+"");
                GLOBAL.TOTAL_PRICE = GLOBAL.SELECTED_PRODUCT_PRICE * quan;
                Log.i("Total : " ,  GLOBAL.TOTAL_PRICE+"");


            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(GLOBAL.SELECTED_PRODUCT_TITLE != null)
            selectBtn.setText(GLOBAL.SELECTED_PRODUCT_TITLE);

    }
}
