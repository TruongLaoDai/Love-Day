package com.example.loveday.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.loveday.databinding.ActivitySetupBinding;
import com.example.loveday.model.SetUp;
import com.example.loveday.viewmodel.SetupViewModel;
import com.example.loveday.R;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySetupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_setup);
        SetupViewModel setupViewModel = new ViewModelProvider(this).get(SetupViewModel.class);

        // get data from database and setup values for edittext
        setupViewModel.getValues(this);
        binding.setModel(setupViewModel);

        // handle event when press button save
        binding.btnSave.setOnClickListener(v -> {
            Intent i = new Intent(SetupActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            SetUp setUp = setupViewModel.saveUser();
            if (setUp.getBoyName().isEmpty() || setUp.getBoyAge().isEmpty() || setUp.getGirlName().isEmpty() || setUp.getGirlAge().isEmpty() || setUp.getDay().isEmpty() || setUp.getMonth().isEmpty() || setUp.getYear().isEmpty()) {
                Toast.makeText(this, "Do not empty", Toast.LENGTH_SHORT).show();
            } else if (setUp.getTotalDay() < 0) {
                Toast.makeText(this, "Invalid date", Toast.LENGTH_SHORT).show();
            } else {
                setupViewModel.saveDatabase(getApplicationContext());
                bundle.putSerializable("model", setUp);
                i.putExtras(bundle);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SetupActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}