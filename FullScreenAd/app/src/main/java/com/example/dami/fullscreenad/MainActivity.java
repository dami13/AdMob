package com.example.dami.fullscreenad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static final String TOAST_TEXT = "onAdFailedToLoad";
    private Button mShowAdButton;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, getString(R.string.app_ads_id));

        mShowAdButton =(Button) findViewById(R.id.run_ads_button);
        mShowAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        mInterstitialAd = newInterstitialAd();
    }

    public InterstitialAd newInterstitialAd(){
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.my_app_full_screen_ad_id));
        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Toast.makeText(getApplicationContext(), TOAST_TEXT, Toast.LENGTH_LONG).show();
                mShowAdButton.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                mShowAdButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                mShowAdButton.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Ad Closed", Toast.LENGTH_SHORT).show();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadInterstitialAd(){
        mShowAdButton.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }
}
