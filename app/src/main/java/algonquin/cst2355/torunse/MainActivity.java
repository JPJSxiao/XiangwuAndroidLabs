package algonquin.cst2355.torunse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import algonquin.cst2355.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
   // private ActivityMainBinding aBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       // viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        //setContentView(R.layout.activity_main);

      //
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);


        String emailAddress = prefs.getString("LoginName", "");
        binding.editTextEmail.setText(emailAddress);

        SharedPreferences.Editor editor= prefs.edit();

     //   editor.putString("LoginName",binding.editTextEmail.getText().toString());
       // editor.apply();




        Log.w(TAG, "In onCreate() - Loading widgest");
        binding.loginButton.setOnClickListener(btn ->{
            // parameter(1,2) 1. where you are now. 2. which activity do you want next
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);



            editor.putString("LoginName",binding.editTextEmail.getText().toString());
            editor.apply();
            String email = binding.editTextEmail.getText().toString();
            //pass some data:
           intent.putExtra("Name",email);
           intent.putExtra("Age", 30);


            startActivity(intent);


        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "The application is now visible on screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "The application is responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "The application is not responding to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "The application is now invisible on screen");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "The application is now destoryed");
    }





}