package com.Softwillow.MarkArt.Ads;

import android.content.Context;
import android.widget.Toast;

import com.Softwillow.MarkArt.Firebase.Error_UI.FireDatabaseCatchError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;

public class AdInterstitial {
    private InterstitialAd mInterstitialAd;
    private Context context;

    public AdInterstitial(Context context) {
        this.context = context;
    }


    public void setMInterstitialAd(String unitAD) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(unitAD);

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
        if (mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
            listener();
        }

    }
    private void listener (){
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                Toast.makeText(context, "loaded", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                FireDatabaseCatchError.getInstance().setError("adInterstitial",adError.getMessage());
                Toast.makeText(context, "AdFailedToLoad\n"+adError.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Toast.makeText(context, "AdOpened", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Toast.makeText(context, "onAdLeftApplication", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                Toast.makeText(context, "closed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
