package com.example.foodies;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class completedActivity extends AppCompatActivity {
    SharedPreferences.Editor editor;
    SharedPreferences sp;

    // Notification channel constants
    private static final String CHANNEL_ID = "order_notification";
    private static final String CHANNEL_NAME = "Order Notification";
    private static final String CHANNEL_DESCRIPTION = "Notification for order status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        TextView txtDelivered = findViewById(R.id.txt_delivered);
        Button btnCancelOrder = findViewById(R.id.btn_cancel_order);

        sp = getSharedPreferences("order", MODE_PRIVATE);

        editor = sp.edit();
        editor.putBoolean("indelivery", true);
        editor.commit();
        boolean isDelivered = sp.getBoolean("indelivery", false);
        Log.d("SharedPreferences", "isDelivered: " + isDelivered);

        txtDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("Received Order", "Are you sure you received the order?");
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog("Cancel Order", "Are you sure you want to cancel the order?");
            }
        });
    }

    private void showConfirmationDialog(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(completedActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (title.equals("Received Order")) {
                            // Handle received order confirmation
                            editor.putBoolean("indelivery", false);
                            editor.commit();
                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                            startActivity(intent);
                            finish();

                            // Show order received notification
                            showNotification("Order Received", "Your order has been successfully received.");
                            Toast.makeText(completedActivity.this, "Order Received!", Toast.LENGTH_SHORT).show();
                        } else if (title.equals("Cancel Order")) {
                            // Handle cancel order confirmation
                            editor.putBoolean("indelivery", false);
                            editor.commit();

                            Intent intent = new Intent(getApplicationContext(), dashboard.class);
                            startActivity(intent);
                            finish();

                            // Show order canceled notification
                            showNotification("Order Canceled", "Your order has been canceled.");
                            Toast.makeText(completedActivity.this, "Order Canceled!", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    private void showNotification(String title, String message) {
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.delivary)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
