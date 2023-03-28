package algonquin.cst2355.torunse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2355.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    protected String cityName;
    protected String stringURL;
    protected String imageUrl;
    protected String description;
    protected String iconName;
    protected Bitmap image;
    protected double current;
    protected double max;
    protected double min;
    protected double humidity;
    protected int vis;
    protected String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //        setContentView(R.layout.activity_main);
        binding.editCity.setText("Ottawa");

        binding.ForecastButton.setOnClickListener(click -> {
            cityName = binding.editCity.getText().toString();
            RequestQueue queue = Volley.newRequestQueue(this);
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

//this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL,
                    null, (response) -> {

                try {
                    JSONObject coord = response.getJSONObject("coord");
                    JSONArray weatherArray = response.getJSONArray("weather");
                    JSONObject position0 = weatherArray.getJSONObject(0);
                    description = position0.getString("description");
                    iconName = position0.getString("icon");

                    JSONObject mainObject = response.getJSONObject("main");
                    current = mainObject.getDouble("temp");
                    min = mainObject.getDouble("temp_min");
                    max = mainObject.getDouble("temp_max");
                    humidity = mainObject.getInt("humidity");

                    vis = response.getInt("visibility");
                    name = response.getString("name");


                    imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";

                    String pathname = getFilesDir() + "/" + iconName + ".png";
                    File file = new File(pathname);

                    if (file.exists()) {
                        image = BitmapFactory.decodeFile(pathname);
                    } else {
//                        ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
//                            @Override
//                            public void onResponse(Bitmap bitmap) {
//                                try {
//                                    // Do something with loaded bitmap...
//                                    image = bitmap;
//                                    image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
//
//
//
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
//                        });

                        ImageRequest imgReq = new ImageRequest(imageUrl, (bitmap) -> {
                            try {
                                // Do something with loaded bitmap...
                                image = bitmap;
                                image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                                binding.icon.setImageBitmap(image);
                                binding.icon.setVisibility(View.VISIBLE);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {});

                        queue.add(imgReq);
                    };


//
//                    FileOutputStream fOut = null;
//                    try {
//                        fOut = openFileOutput( iconName + ".png", Context.MODE_PRIVATE);
//
//                        if(image != null){
//
//                            image.compress(Bitmap.CompressFormat.PNG, 100, fOut);}
//                        fOut.flush();
//                        fOut.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    runOnUiThread(() -> {

                        binding.temp.setText("The current temperature is " + current);
                        binding.temp.setVisibility(View.VISIBLE);

                        binding.minTemp.setText("The min temperature is " + min);
                        binding.minTemp.setVisibility(View.VISIBLE);

                        binding.maxTemp.setText("The max temperature is " + max);
                        binding.maxTemp.setVisibility(View.VISIBLE);

                        binding.humitidy.setText("The current humidity is " + humidity);
                        binding.humitidy.setVisibility(View.VISIBLE);

                        binding.icon.setImageBitmap(image);
                        binding.icon.setVisibility(View.VISIBLE);

                        binding.description.setText(description);
                        binding.description.setVisibility(View.VISIBLE);

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            },
                    (error) -> {

                    });



            queue.add(request);


        });

    }
}


