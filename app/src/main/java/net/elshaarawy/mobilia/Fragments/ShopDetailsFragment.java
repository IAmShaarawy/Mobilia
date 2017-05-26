package net.elshaarawy.mobilia.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class ShopDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String EXTRA_SHOP = "extra_shop";
    private static final String EXTRA_IS_LARGE = "extra_is_large";
    private static final String ABOUT_TAG = "about_tag";
    private static final String OFFERS_TAG = "offers_tag";
    private ShopEntity mShopEntity;
    private boolean isLarge, offersSelected;

    private SimpleDraweeView mShopImg;
    private TextView mTitle, about, offers;
    private Button mPhone, mWebSite;

    private FragmentManager mFragmentManager;
    private AdView mAdView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mShopEntity = bundle.getParcelable(EXTRA_SHOP);
        isLarge = bundle.getBoolean(EXTRA_IS_LARGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Handle null data
        if (mShopEntity == null) {
            View nullView = inflater.inflate(R.layout.layout_null_fragment, container, false);
            TextView massage = (TextView) nullView.findViewById(R.id.fragment_null_text_view);
            massage.setText(getContext().getString(R.string.null_fragment_shop));
            return nullView;
        }
        View view = inflater.inflate(R.layout.fragment_shop_detials, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_shop_details);
        if (!isLarge) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            activity.getSupportActionBar().setTitle("");
            setHasOptionsMenu(true);
        } else {
            toolbar.setVisibility(View.GONE);
        }

        mShopImg = (SimpleDraweeView) view.findViewById(R.id.d_shop_image);
        mTitle = (TextView) view.findViewById(R.id.d_shop_title);
        mPhone = (Button) view.findViewById(R.id.d_shop_phone);
        mWebSite = (Button) view.findViewById(R.id.d_shop_web);
        mPhone.setOnClickListener(this);
        mWebSite.setOnClickListener(this);
        mTitle.setText(mShopEntity.getTitle());
        mShopImg.setImageURI(mShopEntity.getImage());

        about = (TextView) view.findViewById(R.id.shop_about_tab);
        about.setOnClickListener(this);

        offers = (TextView) view.findViewById(R.id.shop_offers_tab);
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

        mFragmentManager = getChildFragmentManager();

        mFragmentManager
                .beginTransaction()
                .replace(R.id.about_offers_container, offersFragment, OFFERS_TAG)
                .detach(offersFragment)
                .replace(R.id.about_offers_container, aboutFragment, ABOUT_TAG)
                .commit();
        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
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
                getActivity().onBackPressed();
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
            Toast.makeText(getContext(), getString(R.string.shop_invalid_website), Toast.LENGTH_LONG).show();
        }
    }

    private void aboutClick() {
        if (offersSelected) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                about.setBackgroundColor(getContext().getColor(R.color.colorAccent));
                offers.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
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
                offers.setBackgroundColor(getContext().getColor(R.color.colorAccent));
                about.setBackgroundColor(getContext().getColor(R.color.colorPrimary));
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

    public static void attachMe(FragmentManager fragmentManager, String tag, int layout, ShopEntity shopEntity, boolean isLarge) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SHOP, shopEntity);
        bundle.putBoolean(EXTRA_IS_LARGE, isLarge);
        ShopDetailsFragment detailsFragment = new ShopDetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment, tag)
                .commit();
    }

    public static void reattachMe(FragmentManager fragmentManager, String tag, int layout) {

        ShopDetailsFragment detailsFragment = (ShopDetailsFragment) fragmentManager.findFragmentByTag(tag);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment, tag)
                .commit();
    }
}
