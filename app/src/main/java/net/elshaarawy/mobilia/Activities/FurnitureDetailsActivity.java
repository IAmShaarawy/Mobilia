package net.elshaarawy.mobilia.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.Fragments.FurnitureDetailsFragment;
import net.elshaarawy.mobilia.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FurnitureDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_FURNITURE = "extra_furniture";
    private FurnitureEntity mFurnitureEntity;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFurnitureEntity = getIntent().getParcelableExtra(EXTRA_FURNITURE);

        setContentView(R.layout.activity_furniture_details);
        if (findViewById(R.id.activity_furniture_details) != null) {
            CollapsingToolbarLayout coToolbar = (CollapsingToolbarLayout) findViewById(R.id.d_furniture_co_toolbar);
            coToolbar.setTitle(mFurnitureEntity.getTitle());

            Toolbar toolbar = (Toolbar) findViewById(R.id.d_furniture_toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            ImageView cover = (ImageView) findViewById(R.id.d_furniture_img);
            Picasso.with(this)
                    .load(mFurnitureEntity.getImage())
                    .error(R.color.error)
                    .placeholder(R.color.loading)
                    .into(cover);

            SimpleDraweeView shopImg = (SimpleDraweeView) findViewById(R.id.d_furniture_shop_image);
            shopImg.setImageURI(mFurnitureEntity.getShopEntity().getImage());

            TextView shopTitle = (TextView) findViewById(R.id.d_furniture_shop_title);
            shopTitle.setText(mFurnitureEntity.getShopEntity().getTitle());
            shopTitle.setContentDescription(mFurnitureEntity.getShopEntity().getTitle());

            TextView offerBody = (TextView) findViewById(R.id.d_furniture_body);
            offerBody.setText(mFurnitureEntity.getBody());
            offerBody.setContentDescription(mFurnitureEntity.getBody());

            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    public static void startMe(Context context, FurnitureEntity furnitureEntity, Pair<View,String> [] sharedElements) {
        Intent intent = new Intent(context, FurnitureDetailsActivity.class);
        intent.putExtra(EXTRA_FURNITURE, furnitureEntity);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation((Activity) context,sharedElements );
        context.startActivity(intent,optionsCompat.toBundle());
    }
}
