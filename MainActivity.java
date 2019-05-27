package com.example.android.coffeetime;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    boolean hascream;
    boolean haschocolatecream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when cream topping is added
     */
    public void creamtopping() {
        CheckBox creamcheck = (CheckBox) findViewById(R.id.creamcheck);
        hascream = creamcheck.isChecked();
    }

    /**
     * This method is called when chocolate topping is added
     */
    public void chococlatetopping() {
        CheckBox chocolatecheck = (CheckBox) findViewById(R.id.chocolatcheck);
        haschocolatecream = chocolatecheck.isChecked();
    }


    /**
     * This method is called when the plus button is pressed.
     */

    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(getApplicationContext(),"This is the maximum amount of Coffee u can order!! ", Toast.LENGTH_SHORT).show();
        return;}
        quantity = quantity + 1;
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(getResources().getText(R.string.welcome));
        welcome.setTextColor(getResources().getColor(R.color.welcome));
        welcome.setTextSize(20);
        TextView ordersummarytext = (TextView) findViewById(R.id.price);
        ordersummarytext.setText(getResources().getText(R.string.price));
        display(quantity);
        total(calculatePrice());
    }

    /**
     * This method is called when the minus button is pressed.
     */

    public void decrement(View view) {
        if(quantity==1){
            Toast.makeText(getApplicationContext(),"This is the minimum amount of Coffee u can order!! ", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText(getResources().getText(R.string.welcome));
        welcome.setTextColor(getResources().getColor(R.color.welcome));
        welcome.setTextSize(20);
        TextView ordersummarytext = (TextView) findViewById(R.id.price);
        ordersummarytext.setText(getResources().getText(R.string.price));
        display(quantity);
        total(calculatePrice());
    }


    /**
     * This method calculate price of coffee
     */

    private int calculatePrice() {
        int pricecream = 10;
        int pricechocolate = 25;
        int pricePerCup = 50;
        creamtopping();
        chococlatetopping();
        if(hascream){pricePerCup = pricePerCup + pricecream ;} // add the price of whipped cream
        if(haschocolatecream){ pricePerCup = pricePerCup + pricechocolate;} // add the price of chocolate topping
        return quantity * pricePerCup;
    }

    /**
     * This method is called when the order button is pressed.
     */
    public void order(View view) {
        ordersummary();
        bye();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void  display(int number) {
        TextView quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText("" + number);
    }

    /**
     * This method is for the total price value on the screen.
     */
    private void total(int number) {
        TextView total = (TextView) findViewById(R.id.total);
        total.setText(NumberFormat.getCurrencyInstance().format(number));
        total.setTextColor(getResources().getColor(R.color.total));
    }

    /**
     * This method displays the Thank you note on the screen.
     */
    private void bye() {
        TextView bill = (TextView) findViewById(R.id.welcome);
        bill.setText(getResources().getText(R.string.bye));
        bill.setTextSize(25);
        bill.setTextColor(Color.YELLOW);
    }

    /**
     * This method displays the order summary on the screen.
     */
    private void ordersummary() {
        EditText editname = (EditText) findViewById(R.id.name) ;
        String name = editname.getText().toString();
        String orderSummary=("Name : " + name + "\nAdd Whipped Cream : " + hascream + "\nAdd Chocolate : "+ haschocolatecream + "\nQuantity : " + quantity + "\nTotal : " + "Rs. " + calculatePrice() + "\nThank YOU!!!");
        Intent intent = new Intent((Intent.ACTION_SENDTO));
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "COFFEE ORDER for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

}

