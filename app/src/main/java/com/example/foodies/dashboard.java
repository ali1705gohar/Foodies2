package com.example.foodies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class dashboard extends AppCompatActivity {

    String tmp_username;
    String tmp_email;
    SharedPreferences sp, sp1, sp2, sp_order;
    DatabaseHelper DB;
    View header;
    TextView header_user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        DB = new DatabaseHelper(this);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        MaterialToolbar materialToolbar = findViewById(R.id.tool_bar);
        sp = getSharedPreferences("login_user_pref", MODE_PRIVATE);
        sp1 = getSharedPreferences("profile_pref", MODE_PRIVATE);
        sp2 = getSharedPreferences("card_pref", MODE_PRIVATE);
        sp_order = getSharedPreferences("order", MODE_PRIVATE);

        tmp_email = sp.getString("user_email", "null");
        header = navigationView.getHeaderView(0);
        header_user_name = header.findViewById(R.id.txt_user_name_header);

        header_user_name.setText(sp1.getString("first_name", "null")
                + " " + sp1.getString("last_name", null));

        replaceFragments(new HomeFragment());

        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id) {

                    case R.id.btn_menu_cart:
                        Intent intent = new Intent(getApplicationContext(), cartActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.btn_menu_orders:

                        boolean isDelivered = sp_order.getBoolean("indelivery", false);
                        Log.d("SharedPreferences", "isDelivered: " + isDelivered);

                        if (isDelivered == true) {
                            Intent intent1 = new Intent(getApplicationContext(), completedActivity.class);
                            startActivity(intent1);
                        } else {
                            Intent intent1 = new Intent(getApplicationContext(), noOrder.class);
                            startActivity(intent1);
                        }

                        break;

                    case R.id.btn_menu_profile:
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putBoolean("is_created", false);
                        editor.commit();
                        editor.apply();
                        Intent intent1 = new Intent(getApplicationContext(), profileActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.btn_find_us:
                        Intent intent2 = new Intent(Intent.ACTION_VIEW);
                        intent2.setData(Uri.parse("https://goo.gl/maps/JNAx6jKQc7x69z1D7"));
                        startActivity(intent2);

                        break;
                    case R.id.btn_rate_us:
                        Intent intent3 = new Intent(getApplicationContext(), Rate_us.class);
                        startActivity(intent3);
                        break;

                    case R.id.btn_contact_us:
                        contact_us();
                        break;

                    case R.id.btn_whatsapp:

                        whatsapp("+923142461178");
                        break;


                    case R.id.btn_menu_logout:
                        logout_user();
                        break;

                    case R.id.btn_faqs:
                        Intent intent4=new Intent(getApplicationContext(), FaqActivity.class);
                        startActivity(intent4);
                        break;

                    default:
                        return true;
                }

                return true;
            }
        });

    }

    private void whatsapp(String number) {
        String url = "https://api.whatsapp.com/send?phone=" + number;
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.whatsapp");
        startActivity(intent);

    }

    private void contact_us() {

        String phoneNumber = "03145120078"; // Replace with the desired phone number

        Intent dialIntent = new Intent(Intent.ACTION_VIEW);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));

        if (dialIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(dialIntent);
        } else {
            // Handle the case where no dialer app is available on the device
            Toast.makeText(getApplicationContext(), "No dialer app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void showhistory(String tmp_email) {
        Cursor res = DB.getAllHistory(tmp_email);
        if (res.getCount() == 0) {
            showMessage("Error", "No Data");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Item Name: " + res.getString(1) + "\n");
            buffer.append("Quantity: " + res.getString(3) + "\n");
            buffer.append("Price per Item: " + res.getString(2) + "\n");
            buffer.append("Total Order Price: " + res.getString(4) + "\n\n");
        }

        showMessage("Previous Orders", buffer.toString());
    }


    public void showMessage(String title, String message) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getApplicationContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    private void logout_user() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sp.edit();
                SharedPreferences.Editor editor1 = sp1.edit();
                SharedPreferences.Editor editor2 = sp2.edit();
                editor2.clear();
                editor2.apply();
                editor1.clear();
                editor1.apply();
                editor.clear();
                editor.apply();
                DB.ClearCart();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void replaceFragments(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
}

