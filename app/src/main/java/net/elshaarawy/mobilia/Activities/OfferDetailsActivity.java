package net.elshaarawy.mobilia.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.Fragments.OfferDetailsFragment;
import net.elshaarawy.mobilia.R;

public class OfferDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_OFFER = "extra_offer";
    private OfferEntity mOfferEntity;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        mOfferEntity = getIntent().getParcelableExtra(EXTRA_OFFER);

        if (findViewById(R.id.offer_details_container) != null) {

            CollapsingToolbarLayout coToolbar = (CollapsingToolbarLayout) findViewById(R.id.d_offer_co_toolbar);
            coToolbar.setTitle(mOfferEntity.getTitle());

            Toolbar toolbar = (Toolbar) findViewById(R.id.d_offer_toolbar);
           setSupportActionBar(toolbar);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            ImageView cover = (ImageView) findViewById(R.id.d_offer_img);
            Picasso.with(this)
                    .load(mOfferEntity.getImage())
                    .error(R.color.error)
                    .placeholder(R.color.loading)
                    .into(cover);

            SimpleDraweeView shopImg = (SimpleDraweeView) findViewById(R.id.d_offer_shop_image);
            shopImg.setImageURI(mOfferEntity.getShopEntity().getImage());

            TextView shopTitle = (TextView) findViewById(R.id.d_offer_shop_title);
            shopTitle.setText(mOfferEntity.getShopEntity().getTitle());
            shopTitle.setContentDescription(mOfferEntity.getShopEntity().getTitle());

            TextView offerBody = (TextView) findViewById(R.id.d_offer_body);
            offerBody.setText(mOfferEntity.getBody());
            offerBody.setContentDescription(mOfferEntity.getBody());

            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
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

    public static void startMe(Context context, OfferEntity offerEntity, Pair<View, String>[] sharedElements) {
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(EXTRA_OFFER, offerEntity);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation((Activity)context,sharedElements);
        context.startActivity(intent,optionsCompat.toBundle());
    }
}
