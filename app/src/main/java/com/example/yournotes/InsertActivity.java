package com.example.yournotes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.yournotes.databinding.ActivityInsertBinding;

public class InsertActivity extends AppCompatActivity {
    ActivityInsertBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String type = getIntent().getStringExtra("type");
        if (type != null && type.equals("update")) {
            setTitle("Update");
            binding.titlle.setText(getIntent().getStringExtra("title"));
            binding.dessc.setText(getIntent().getStringExtra("desc")); // Set description
            final int id = getIntent().getIntExtra("id", 0);
            binding.submit.setText("Update Note");
            binding.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.titlle.getText().toString());
                    intent.putExtra("desc", binding.dessc.getText().toString()); // Pass description
                    intent.putExtra("id", id);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        } else {
            setTitle("Add Note");
            binding.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title", binding.titlle.getText().toString());
                    intent.putExtra("desc", binding.dessc.getText().toString()); // Pass description
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
    }


}
