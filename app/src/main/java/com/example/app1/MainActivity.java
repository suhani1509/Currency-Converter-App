package com.example.app1;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    Spinner spFrom, spTo;
    Button btnConvert;
    ImageButton btnReload;
    TextView tvResult;

    String[] currency = {"INR", "USD", "EUR", "JPY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI
        etAmount = findViewById(R.id.etAmount);
        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);
        btnConvert = findViewById(R.id.btnConvert);
        btnReload = findViewById(R.id.btnReload);
        tvResult = findViewById(R.id.tvResult);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currency
        );

        spFrom.setAdapter(adapter);
        spTo.setAdapter(adapter);

        // Convert button
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amountStr = etAmount.getText().toString();

                if (amountStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                double amount = Double.parseDouble(amountStr);

                String from = spFrom.getSelectedItem().toString();
                String to = spTo.getSelectedItem().toString();

                double result = convertCurrency(from, to, amount);

                tvResult.setText(String.format("%.2f", result));
            }
        });

        // Reload button (swap currencies)
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fromPos = spFrom.getSelectedItemPosition();
                int toPos = spTo.getSelectedItemPosition();

                spFrom.setSelection(toPos);
                spTo.setSelection(fromPos);
            }
        });
    }

    // Conversion logic (fixed rates)
    private double convertCurrency(String from, String to, double amount) {

        // Convert everything to INR first
        double inr = 0;

        switch (from) {
            case "INR":
                inr = amount;
                break;
            case "USD":
                inr = amount * 83;
                break;
            case "EUR":
                inr = amount * 90;
                break;
            case "JPY":
                inr = amount * 0.55;
                break;
        }

        // Convert INR to target currency
        switch (to) {
            case "INR":
                return inr;
            case "USD":
                return inr / 83;
            case "EUR":
                return inr / 90;
            case "JPY":
                return inr / 0.55;
        }

        return 0;
    }
}