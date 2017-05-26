package net.elshaarawy.mobilia.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Fragments.ShopDetailsAboutFragment;
import net.elshaarawy.mobilia.Fragments.ShopDetailsFragment;
import net.elshaarawy.mobilia.Fragments.ShopDetailsOfferFragment;
import net.elshaarawy.mobilia.R;

public class ShopDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_SHOP = "extra_shop";
    private static final String ABOUT_TAG = "about_tag";
    private static final String OFFERS_TAG = "offers_tag";
    private ShopEntity mShopEntity;
    private boolean offersSelected;

    private SimpleDraweeView mShopImg;
    private TextView mTitle, about, offers;
    private Button mPhone, mWebSite;

    private FragmentManager mFragmentManager;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        mShopEntity = getIntent().getParcelableExtra(EXTRA_SHOP);

        if (findViewById(R.id.shop_details_container) != null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shop_details);


            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            getSupportActionBar().setTitle("");


            mShopImg = (SimpleDraweeView) findViewById(R.id.d_shop_image);
            mTitle = (TextView) findViewById(R.id.d_shop_title);
            mPhone = (Button) findViewById(R.id.d_shop_phone);
            mWebSite = (Button) findViewById(R.id.d_shop_web);
            mPhone.setOnClickListener(this);
            mWebSite.setOnClickListener(this);
            mTitle.setText(mShopEntity.getTitle());
            mShopImg.setImageURI(mShopEntity.getImage());

            about = (TextView) findViewById(R.id.shop_about_tab);
            about.setOnClickListener(this);

            offers = (TextView) findViewById(R.id.shop_offers_tab);
            offers.setOnClickListener(this);

            //add about and offers fragments

            Bundle bundle1 = new Bundle();
            bundle1.putLong(getString(R.string.key_shop_offers), mShopEntity.getId());
            ShopDetailsOfferFragment offersFragment = new ShopDetailsOfferFragment();
            offersFragment.setArguments(bundle1);

            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.key_shop_about), mShopEntity);
            ShopDetailsAboutFragment aboutFragment = new ShopDetailsAboutFragment();
            aboutFragment.setArguments(bundle);

            mFragmentManager = getSupportFragmentManager();

            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.about_offers_container, offersFragment, OFFERS_TAG)
                    .detach(offersFragment)
                    .replace(R.id.about_offers_container, aboutFragment, ABOUT_TAG)
                    .commit();
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d_shop_phone:
                call(mShopEntity.getPhone());

                break;
            case R.id.d_shop_web:
                openWebsite(mShopEntity.getWebsite());

                break;
            case R.id.shop_about_tab:
                aboutClick();

                break;
            case R.id.shop_offers_tab:
                offersClick();

                break;
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

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                "tel", phone, null));
        startActivity(intent);
    }

    private void openWebsite(String website) {
        Intent fIntent = new Intent(Intent.ACTION_VIEW);
        try {
            fIntent.setData(Uri.parse(website));
            startActivity(fIntent);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.shop_invalid_website), Toast.LENGTH_LONG).show();
        }
    }

    private void aboutClick() {
        if (offersSelected) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                about.setBackgroundColor(getColor(R.color.colorAccent));
                offers.setBackgroundColor(getColor(R.color.colorPrimary));
            } else {
                about.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                offers.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            mFragmentManager
                    .beginTransaction()
                    .detach(mFragmentManager.findFragmentByTag(OFFERS_TAG))
                    .attach(mFragmentManager.findFragmentByTag(ABOUT_TAG))
                    .commit();
            offersSelected = false;
        }
    }

    private void offersClick() {
        if (!offersSelected) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                offers.setBackgroundColor(getColor(R.color.colorAccent));
                about.setBackgroundColor(getColor(R.color.colorPrimary));
            } else {
                offers.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                about.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            mFragmentManager
                    .beginTransaction()
                    .detach(mFragmentManager.findFragmentByTag(ABOUT_TAG))
                    .attach(mFragmentManager.findFragmentByTag(OFFERS_TAG))
                    .commit();
            offersSelected = true;
        }
    }

    public static void startMe(Context context, ShopEntity shopEntity, Pair<View,String>[] sharedElements) {
        Intent intent = new Intent(context, ShopDetailsActivity.class);
        intent.putExtra(EXTRA_SHOP, shopEntity);
        ActivityOptionsCompat optionsCompat =  ActivityOptionsCompat
                .makeSceneTransitionAnimation((Activity)context,sharedElements);
        context.startActivity(intent,optionsCompat.toBundle());
    }
}
