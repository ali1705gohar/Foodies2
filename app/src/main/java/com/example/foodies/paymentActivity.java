package com.example.foodies;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class paymentActivity extends AppCompatActivity {
    private EditText txtPaymentName, txtPaymentContact, txtPaymentAddress, txtPaymentCardNumber, txtPaymentDay, txtPaymentMonth, txtPaymentYear, txtPaymentCVC, txtPaymentHolderName;
    private RadioGroup radioGroup;
    private Button btnConfirmOrder;
    private Switch swit_save;
    LinearLayout card_detail_layout;
    private static final String CHANNEL_ID = "my_channel";

    String paymentType = "no";
    String name, address, contact, cvc, holderName, cardNumber, day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Find all the views by their IDs
        txtPaymentName = findViewById(R.id.txt_payment_name);
        txtPaymentContact = findViewById(R.id.txt_payment_contact);
        txtPaymentAddress = findViewById(R.id.txt_payment_address);
        txtPaymentCardNumber = findViewById(R.id.txt_payment_cardnumber);
        txtPaymentDay = findViewById(R.id.txt_payment_day);
        txtPaymentMonth = findViewById(R.id.txt_payment_month);
        txtPaymentYear = findViewById(R.id.txt_payment_year);
        txtPaymentCVC = findViewById(R.id.txt_payment_CVC);
        txtPaymentHolderName = findViewById(R.id.txt_payment_holder_name);
        radioGroup = findViewById(R.id.radio_group_payment);
        btnConfirmOrder = findViewById(R.id.btn_confrim_order);
        card_detail_layout = findViewById(R.id.layout_card_details);
        swit_save = findViewById(R.id.switch_save);

        //TEXT IN INPUTS
        SharedPreferences sp = getSharedPreferences("profile_pref", MODE_PRIVATE);
        SharedPreferences sp1 = getSharedPreferences("card_pref", MODE_PRIVATE);
        txtPaymentName.setText(sp.getString("first_name", "null")
                + " " + sp.getString("last_name", null));

        txtPaymentContact.setText(sp.getString("contact", null));
        txtPaymentAddress.setText(sp.getString("address", null));

        txtPaymentCardNumber.setText(sp1.getString("card_number", ""));
        txtPaymentHolderName.setText(sp1.getString("holder_name", ""));
        txtPaymentDay.setText(sp1.getString("day", ""));
        txtPaymentYear.setText(sp1.getString("year", ""));
        txtPaymentMonth.setText(sp1.getString("month", ""));
        txtPaymentCVC.setText(sp1.getString("cvc", ""));

        createNotificationChannel();

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform actions when the button is clicked
                confirmOrder();
            }
        });

        // Register radio group listener
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Perform actions when the radio button selection changes
                handlePaymentOptionChange(checkedId);
            }
        });

        swit_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor1 = sp1.edit();
                    editor1.putString("card_number", txtPaymentCardNumber.getText().toString());
                    editor1.putString("holder_name", txtPaymentHolderName.getText().toString());
                    editor1.putString("cvc", txtPaymentCVC.getText().toString());
                    editor1.putString("day", txtPaymentDay.getText().toString());
                    editor1.putString("month", txtPaymentMonth.getText().toString());
                    editor1.putString("year", txtPaymentYear.getText().toString());
                    editor1.apply();
                    Toast.makeText(paymentActivity.this, "Card details saved", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case when the switch is unchecked
                }
            }
        });
    }

    // Method to handle the click event of the "Confirm Order" button
    private void confirmOrder() {
        // Retrieve the input values
        name = txtPaymentName.getText().toString();
        contact = txtPaymentContact.getText().toString();
        address = txtPaymentAddress.getText().toString();

        // Validate the input values
        if (name.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            // Display an error message if any field is empty
            Toast.makeText(paymentActivity.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(paymentType=="no"){
            Toast.makeText(this, "Please select Payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        String message = "Name: " + name +
                "\nContact: " + contact +
                "\nAddress: " + address +
                "\nPayment Type: " + paymentType +
                "\n\nAre you sure to confirm the order?";

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(paymentActivity.this);
        builder.setTitle("Confirm Order")
                .setMessage(message)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), completedActivity.class);
                        startActivity(intent);
                        finish();

                        // Create an intent to open the completedActivity when the notification is clicked
                        Intent intent3 = new Intent(getApplicationContext(), completedActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                        // Build the notification
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                .setSmallIcon(R.drawable.delivary)
                                .setContentTitle("Order Confirmed")
                                .setContentText("Your order has been confirmed.\nYour order is on the way.")
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        // Show the notification
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.notify(0, notificationBuilder.build());

                        DatabaseHelper DB = new DatabaseHelper(getApplicationContext());
                        DB.ClearCart();

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Method to handle the selection change of the payment options (radio buttons)
    private void handlePaymentOptionChange(int checkedId) {
        // Perform actions based on the selected payment option
        switch (checkedId) {
            case R.id.radio_cash_on_delivery:
                paymentType = "cash on delivery";
                card_detail_layout.setVisibility(View.GONE);
                break;
            case R.id.radio_online_payment:
                paymentType = "online payment";
                card_detail_layout.setVisibility(View.VISIBLE);

                // Move the assignment statements before the isEmpty() checks
                cardNumber = txtPaymentCardNumber.getText().toString();
                day = txtPaymentDay.getText().toString();
                month = txtPaymentMonth.getText().toString();
                year = txtPaymentYear.getText().toString();
                cvc = txtPaymentCVC.getText().toString();
                holderName = txtPaymentHolderName.getText().toString();

                if (cardNumber.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty() || cvc.isEmpty() || holderName.isEmpty()) {
                    Toast.makeText(paymentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cardNumber.length() != 14) {
                    Toast.makeText(paymentActivity.this, "Card number must be 14 digits.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate day range
                int dayValue = Integer.parseInt(day);
                if (dayValue < 1 || dayValue > 31) {
                    Toast.makeText(paymentActivity.this, "Invalid day. Enter a value between 1 and 31.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate month range
                int monthValue = Integer.parseInt(month);
                if (monthValue < 1 || monthValue > 12) {
                    Toast.makeText(paymentActivity.this, "Invalid month. Enter a value between 1 and 12.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate year length
                if (year.length() != 4) {
                    Toast.makeText(paymentActivity.this, "Year must be 4 digits.", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
