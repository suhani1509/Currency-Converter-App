package com.example.currencyconverterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private EditText etAmount;
    private Spinner spinnerFrom, spinnerTo;
    private Button btnConvert, btnSettings;
    private TextView tvResult;

    // Hardcoded Exchange Rates (Base: 1 USD)
    // USD=1.0, INR=83.0, JPY=150.0, EUR=0.92
    private final String[] currencies = {"USD", "INR", "JPY", "EUR"};
    private final double[] rates = {1.0, 83.0, 150.0, 0.92};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 1. Theme Check (setContentView se pehle karna zaroori hai)
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Views ko Initialize karein
        etAmount = findViewById(R.id.etAmount);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvert = findViewById(R.id.btnConvert);
        btnSettings = findViewById(R.id.btnSettings);
        tvResult = findViewById(R.id.tvResult);

        // 3. Spinner (Dropdown) mein data bharna
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, currencies);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // 4. Convert Button Click Listener
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateConversion();
            }
        });

        // 5. Settings Page par jaane ka logic
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_settings.class);
                startActivity(intent);
            }
        });
    }

    private void calculateConversion() {
        String input = etAmount.getText().toString();

        // Validation: Check karein ki amount khali to nahi
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter amount!", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(input);
        int fromIndex = spinnerFrom.getSelectedItemPosition();
        int toIndex = spinnerTo.getSelectedItemPosition();

        // Formula: (Amount / From_Rate) * To_Rate
        double result = (amount / rates[fromIndex]) * rates[toIndex];

        // Result dikhana (2 decimal places tak)
        tvResult.setText(String.format("Result: %.2f %s", result, currencies[toIndex]));
    }
}