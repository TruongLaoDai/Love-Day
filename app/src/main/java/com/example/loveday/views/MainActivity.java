package com.example.loveday.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.loveday.database.MySharedPreferences;
import com.example.loveday.databinding.ActivityMainBinding;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.loveday.R;
import com.example.loveday.model.SetUp;
import com.example.loveday.viewmodel.MainViewModel;
import com.example.loveday.model.PathUtil;
import com.example.loveday.viewmodel.UrlImageViewModel;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        UrlImageViewModel urlImageViewModel = new ViewModelProvider(this).get(UrlImageViewModel.class);

        // request permission access gallery
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        // initialize data from database
        mainViewModel.getValues(this);
        urlImageViewModel.getUrlImage(this);
        binding.setModel(mainViewModel);
        binding.setUrl(urlImageViewModel);

        //get Data from SetupActivity
        Intent intent = getIntent();
        SetUp setUp = (SetUp) intent.getSerializableExtra("model");
        if (setUp != null) {
            mainViewModel.callData(setUp);
        }

        // handle event when press button setting
        binding.ibSetting.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, SetupActivity.class);
            startActivity(i);
            finish();
        });

        //setting image boy
        binding.ibBoy.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
        });

        // setting image girl
        binding.ibGirl.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_PICK);
            i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            binding.ibBoy.setImageURI(data.getData());
            MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
            String filePath = null;
            try {
                filePath = PathUtil.getPath(this, data.getData());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mySharedPreferences.putImageBoy("urlImageBoy", filePath);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            binding.ibGirl.setImageURI(data.getData());
            MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
            String filePath = null;
            try {
                filePath = PathUtil.getPath(this, data.getData());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            mySharedPreferences.putImageGirl("urlImageGirl", filePath);
        }
    }
}