package com.example.encrypt;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText textInput, keyInput, outputText, decryptedText;
    Button encryptButton, decryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInput = findViewById(R.id.textInput);
        keyInput = findViewById(R.id.keyInput);
        outputText = findViewById(R.id.outputText);
        decryptedText = findViewById(R.id.decryptedText);
        encryptButton = findViewById(R.id.encryptButton);
        decryptButton = findViewById(R.id.decryptButton);

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = textInput.getText().toString();
                String key = keyInput.getText().toString();
                if (!text.isEmpty() && !key.isEmpty()) {
                    String encrypted = xorEncrypt(text, key);
                    outputText.setText(Base64.encodeToString(encrypted.getBytes(), Base64.DEFAULT));
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both text and key", Toast.LENGTH_SHORT).show();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encrypted = outputText.getText().toString();
                String key = keyInput.getText().toString();
                if (!encrypted.isEmpty() && !key.isEmpty()) {
                    byte[] decodedBytes = Base64.decode(encrypted, Base64.DEFAULT);
                    String decrypted = xorEncrypt(new String(decodedBytes), key);
                    decryptedText.setText(decrypted);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter both encrypted text and key", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String xorEncrypt(String text, String key) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            output.append((char) (text.charAt(i) ^ key.charAt(i % key.length())));
        }
        return output.toString();
    }
}