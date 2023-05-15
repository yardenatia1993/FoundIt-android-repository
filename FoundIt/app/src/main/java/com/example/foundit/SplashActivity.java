package com.example.foundit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000; // This is 3 seconds.
    private final int ITEM_DISPLAY_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            RelativeLayout layout = findViewById(R.id.splashLayout);
            ImageView logo = findViewById(R.id.imageViewLogo);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_out);

            // Start the animation
            logo.startAnimation(anim);
            ImageView carKey = findViewById(R.id.imageViewCarKey);
            ImageView creditCard = findViewById(R.id.imageViewCreditCard);
            ImageView wallet = findViewById(R.id.imageViewWallet);
            loadImageAndSetPosition(R.drawable.car_key, carKey, logo, layout, 0);
            loadImageAndSetPosition(R.drawable.credit_card, creditCard, logo, layout, ITEM_DISPLAY_DELAY);
            loadImageAndSetPosition(R.drawable.wallet, wallet, logo, layout, ITEM_DISPLAY_DELAY * 2);


            /* New Handler to start the MainActivity
             * and close this Splash-Screen after some seconds.*/
            new Handler().postDelayed(() -> {
                /* Create an Intent that will start the MainActivity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    private void loadImageAndSetPosition(int drawableId, ImageView imageView, ImageView logo, RelativeLayout layout, int delay) {
        new Handler().postDelayed(() -> {
            // Load the image into the ImageView and shape it as a circle
            Glide.with(SplashActivity.this)
                    .load(drawableId)
                    .apply(RequestOptions.circleCropTransform())
                    .override(150, 150)
                    .into(imageView);

            // Set a random position for the ImageView around the logo
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            imageView.setLayoutParams(params);

            // Set an offset from the logo's position
            int offset = new Random().nextInt(100) + 350; // Random offset
            double angle = Math.toRadians(new Random().nextInt(360)); // Random angle
            float offsetX = (float) (offset * Math.cos(angle));
            float offsetY = (float) (offset * Math.sin(angle));
            imageView.setTranslationX(offsetX);
            imageView.setTranslationY(offsetY);

            // Make the ImageView visible
            imageView.setVisibility(ImageView.VISIBLE);
        }, delay);
    }
}