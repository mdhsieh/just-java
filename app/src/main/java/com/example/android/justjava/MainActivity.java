/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_text_input);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.toppings_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate, 5);
        String priceMessage = displayOrderSummary(name, hasWhippedCream, hasChocolate, price);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));

        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether ot not the user wants chocolate
     * @param pricePerCup is $5 per cup
     *
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, int pricePerCup)
    {
        int basePrice = pricePerCup;
        // add $1 if user wants whipped cream
        if (addWhippedCream)
        {
            basePrice = basePrice + 1;
        }
        // add $2 if user wants chocolate
        if (addChocolate)
        {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * Creates a summary text of the order.
     *
     * @param name the name of the user
     * @param addWhippedCream is whether or not the user wants whipped cream
     * @param addChocolate is whether ot not the user wants chocolate
     * @param price of the order
     * @return text summary
     */
    private String displayOrderSummary(String name, boolean addWhippedCream, boolean addChocolate, int price)
    {
        String yes = getString(R.string.yes);
        String no = getString(R.string.no);
        String orderSummary = getString(R.string.order_summary_name, name) + "\n";
        orderSummary += getString(R.string.order_summary_whipped_cream, (addWhippedCream? yes:no)) + "\n";
        orderSummary += getString(R.string.order_summary_chocolate, (addChocolate? yes:no)) + "\n";
        orderSummary += getString(R.string.order_summary_quantity, quantity) + "\n";
        orderSummary += getString(R.string.order_summary_price, "$" + price) + "\n";
        orderSummary = orderSummary + getString(R.string.thank_you);
        return orderSummary;
    }

    public void increment(View view) {
        if (quantity == 100)
        {
            Toast toast = Toast.makeText(this, getString(R.string.no_more_coffee), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1)
        {
            Toast toast = Toast.makeText(this, getString(R.string.no_less_coffee), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}
