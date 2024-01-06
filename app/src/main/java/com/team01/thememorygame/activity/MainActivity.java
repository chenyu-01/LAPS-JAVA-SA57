package com.team01.thememorygame.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.team01.thememorygame.ImageAdapter;
import com.team01.thememorygame.ImageModel;
import com.team01.thememorygame.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    EditText meditText;
    ProgressBar mprogressBar;
    GridView mgridView;
    Button mbutton;
    String searchUrl;
    ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meditText = findViewById(R.id.inputUrl);
        mprogressBar=findViewById(R.id.progressBar);
        mgridView =findViewById(R.id.gridView);
        mbutton = findViewById(R.id.button);
//        searchUrl= "https://stocksnap.io";

        mbutton.setOnClickListener(this);
    }


    protected void clearImages(){
        List <ImageModel> emptyList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this,emptyList);
        mgridView.setAdapter(imageAdapter);
    };

    protected void fetchImages(String Url){
        mprogressBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Background thread work
                List<ImageModel> imageModelList = fetchFromWebsite(Url);

                // Post results back to the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mprogressBar.setVisibility(View.GONE);
                        if(imageModelList != null && !imageModelList.isEmpty()){
                            imageAdapter = new ImageAdapter(MainActivity.this, imageModelList);
                            mgridView.setAdapter(imageAdapter);
                            mgridView.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to load images", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();

    }
    protected List<ImageModel> fetchFromWebsite(String url) {
        List<ImageModel> imageModelList = new ArrayList<>();

        try {
            // Connect to the website and parse its HTML
            Document doc = Jsoup.connect(url).get();

            // Select all image elements from the HTML
            Elements images = doc.select("img");

            // Loop through each element and get the src attribute
            for (Element img : images) {
                if (imageModelList.size() >= 20) break; // Limit to 20 images
                String imageUrl = img.absUrl("src"); // Get absolute URL of the image
                if (!imageUrl.endsWith(".jpg") && !imageUrl.endsWith(".png")) continue; // Ignore if not a JPG or PNG image
                imageModelList.add(new ImageModel(imageUrl));
            }

        } catch (Exception e) {
            Log.e("FetchFromWebsite", "Error fetching images", e);
        }

        Log.d("FetchFromWebsite", "Fetched " + imageModelList.size() + " image URLs");
        return imageModelList;
    }



    @Override
    public void onClick(View v) {
        clearImages();
        fetchImages(meditText.getText().toString());
    }
}