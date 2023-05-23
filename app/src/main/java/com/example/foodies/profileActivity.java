package com.example.foodies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class profileActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneEditText, addressEditText;
    private Spinner countrySpinner, citySpinner;
    private Button saveButton;

    private ArrayAdapter<String> countryAdapter, cityAdapter;
    private String[] countries = {"Pakistan", "India", "Bangladesh"};
    private String[][] cities = {
            {"Khanewal", "Attock", "Lahore", "Multan", "Islamabad"},
            {"Mumbai", "Delhi", "Bangalore", "Kolkata", "Chennai"},
            {"Dhaka", "Chittagong", "Khulna", "Rajshahi", "Sylhet"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstNameEditText = findViewById(R.id.profile_first_name);
        lastNameEditText = findViewById(R.id.profile_last_name);
        phoneEditText = findViewById(R.id.editTextPhone);
        addressEditText = findViewById(R.id.profile_address);
        countrySpinner = findViewById(R.id.spinner_country);
        citySpinner = findViewById(R.id.spinner_city);
        saveButton = findViewById(R.id.btn_save);

        SharedPreferences sp = getSharedPreferences("profile_pref", MODE_PRIVATE);
        boolean is_created = sp.getBoolean("is_created", false);
        if (is_created) {
            Intent intent = new Intent(getApplicationContext(), dashboard.class);
            startActivity(intent);
            finish();
            return;
        }

        // Populate the country spinner with data
        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);

        // Set up the city spinner adapter
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        // Country spinner selection listener
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Clear previous city selections
                cityAdapter.clear();

                // Retrieve the selected country
                String country = parent.getItemAtPosition(position).toString();

                // Get the index of the selected country
                int countryIndex = getCountryIndex(country);

                // Populate the city spinner based on the selected country
                if (countryIndex != -1) {
                    String[] selectedCities = cities[countryIndex];
                    for (String city : selectedCities) {
                        cityAdapter.add(city);
                    }
                }

                // Notify the adapter about the data change
                cityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        firstNameEditText.setText(sp.getString("first_name", ""));
        lastNameEditText.setText(sp.getString("last_name", ""));
        phoneEditText.setText(sp.getString("contact", ""));
        addressEditText.setText(sp.getString("address",""));



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the user-entered information
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String country = countrySpinner.getSelectedItem().toString();
                String city = citySpinner.getSelectedItem().toString();

                // Validate input fields
                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(profileActivity.this, "Please enter your first name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(profileActivity.this, "Please enter your last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(profileActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(profileActivity.this, "Please enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Store the retrieved information in SharedPreferences
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("is_created",true);
                editor.putString("first_name", firstName);
                editor.putString("last_name", lastName);
                editor.putString("contact", phone);
                editor.putString("address", address);
                editor.putString("country", country);
                editor.putString("city", city);
                editor.apply();

                // For example, you can display a toast message with the entered details
                String message = "Name: " + firstName + " " + lastName + "\nPhone: " + phone + "\nAddress: " +
                        address + "\nCountry: " + country + "\nCity: " + city;
                Toast.makeText(profileActivity.this, message, Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(getApplicationContext(),dashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private int getCountryIndex(String country) {
        for (int i = 0; i < countries.length; i++) {
            if (countries[i].equals(country)) {
                return i;
            }
        }
        return -1;
    }
}

