/*
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int numberOfCoffee = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**TextView textView = new TextView(this);
         textView.setText("Happy");
         textView.setTextSize(86);
         setContentView(textView);*/
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        String endMessage = createOrderSummary(price);
        displayMessage(endMessage);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (numberOfCoffee >= 100) {
            numberOfCoffee = 100;
            Toast.makeText(this, "You Can Just Have 100 Coffees in Single Order!!", Toast.LENGTH_SHORT).show();
        } else {
            ++numberOfCoffee;
        }
        display(numberOfCoffee);
        displayPrice(numberOfCoffee * 15);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (numberOfCoffee <= 0) {
            numberOfCoffee = 0;
            Toast.makeText(this, "You Can't drink Imaginary Coffees in This Hotel!!", Toast.LENGTH_SHORT).show();
        } else {
            --numberOfCoffee;
        }
        display(numberOfCoffee);
        displayPrice(numberOfCoffee * 15);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * @return total price
     */
    private int calculatePrice() {
        int price = numberOfCoffee * 15;
        return price;
    }

    private String createOrderSummary(int price) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox hasWhippingCream = (CheckBox) findViewById(R.id.checkbox_whipping_cream);
        boolean addWhippingCream = hasWhippingCream.isChecked();

        CheckBox hasChocolate = (CheckBox) findViewById(R.id.checkbox_chocolate);
        boolean addChocolate = hasChocolate.isChecked();

        if (price > 0 && addWhippingCream == true) {
            price += 3 * numberOfCoffee;
        }
        if (price > 0 && addChocolate == true) {
            price += 2 * numberOfCoffee;
        }

        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipping Cream: " + addWhippingCream;
        priceMessage += "\nAdd Chocolate: " + addChocolate;
        priceMessage += "\nQuantity: " + numberOfCoffee + "\nTotal: ₹" + price + "\nThank You";


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "A Coffee Order From " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        return priceMessage;
    }


}
