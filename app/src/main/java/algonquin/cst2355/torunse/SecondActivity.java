package algonquin.cst2355.torunse;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import algonquin.cst2355.torunse.databinding.ActivityMainBinding;
import algonquin.cst2355.torunse.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivitySecondBinding

        ActivitySecondBinding binding = ActivitySecondBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

     //   SharedPreferences.Editor editor= prefs.edit();
      //  editor.putString("PhoneNumber",binding.editTextPhone.getText().toString());

      //  String emailAddress = prefs.getString("LoginName", "");
      //  binding.editTextEmail.setText(emailAddress);

        SharedPreferences.Editor editor= prefs.edit();


        String phoneNumber = prefs.getString("PhoneNumber","");
        binding.editTextPhone.setText(phoneNumber);






        editor.apply();

        //setContentView(R.layout.activity_second);

        //How do we get the table?
//        Intent dataFormPage1 = getIntent(); // will have "Name" and "Age" variables
//
//        String name = dataFormPage1.getStringExtra("Name");
//                         // if "age" is missing, return 0
//        int age = dataFormPage1.getIntExtra("Age", 0);
//
//        Log.d("Second activity", "Age is "+ age + "and Name is " + name);

        binding.button2.setOnClickListener( clk -> {
            //go back:
            //send data to first page:
            String num = binding.editTextPhone.getText().toString();
   //         editor.putString("PhoneNumber",num);
     //       editor.apply();
            Intent dial = new Intent(Intent.ACTION_CALL, Uri.parse(num));
         //   Intent call = new Intent(Intent.ACTION_DIAL);
        //    String phoneNumber = prefs.getString("PhoneNumber")
       //     call.setData(Uri.parse("telL" + num));
          //  startActivity(call);


            if (dial.resolveActivity(getPackageManager()) != null) {
                startActivity(dial);
            } else {
                Toast.makeText(this, "Cannot make phone calls on this device", Toast.LENGTH_SHORT).show();
            }

        });


        binding.button.setOnClickListener( clk -> {
            //go back:
            //send data to first page:
            Intent dataTable = new Intent();// no directions from/to
            finish(); //returns onpause(), onStop()

        });


        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

            @Override // the camera has disappeared

            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == Activity.RESULT_OK) {

                    Intent data = result.getData(); //get the picture

                    Bitmap thumbnail = data.getParcelableExtra("data");

                    FileOutputStream fOut = null;

                    File file = new File( getFilesDir(), "Picture.png");

                    if(file.exists())
                    {
                        Bitmap theImage = BitmapFactory.decodeFile("Picture.png");

                        binding.imageView.setImageBitmap( theImage);
                    }

                    try {
                        fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                        fOut.flush();

                        fOut.close();

                    }
                    catch(IOException ioe) {}

                    int i = 0;
                }


                }

            }
        );


  //  Intent call = new Intent(Intent.ACTION_DIAL);
    //    call.setData(Uri.parse("telL" + phoneNumber));

    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        binding.button3.setOnClickListener( click -> {
        //use apps on the phone
        cameraResult.launch(cameraIntent);
        //should return a picture
    });






    }
}