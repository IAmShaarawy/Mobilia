package net.elshaarawy.mobilia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OfferDetailsFragment extends Fragment {

    private static final String EXTRA_OFFER = "extra_offer";
    private static final String EXTRA_IS_LARGE = "extra_is_large";
    private OfferEntity mOfferEntity;
    private boolean isLarge;
    private AdView mAdView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mOfferEntity = bundle.getParcelable(EXTRA_OFFER);
        isLarge = bundle.getBoolean(EXTRA_IS_LARGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Handle null data
        if (mOfferEntity == null) {
            View nullView = inflater.inflate(R.layout.layout_null_fragment, container, false);
            TextView massage = (TextView) nullView.findViewById(R.id.fragment_null_text_view);
            massage.setText(getContext().getString(R.string.null_fragment_offer));
            return nullView;
        }
        View view = inflater.inflate(R.layout.fragment_offer_details, container, false);

        AppCompatActivity activity  = (AppCompatActivity) getActivity();

        CollapsingToolbarLayout coToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.d_offer_co_toolbar);
        coToolbar.setTitle(mOfferEntity.getTitle());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.d_offer_toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(!isLarge);
        setHasOptionsMenu(true);

        ImageView cover = (ImageView) view.findViewById(R.id.d_offer_img);
        Picasso.with(getContext())
                .load(mOfferEntity.getImage())
                .error(R.color.error)
                .placeholder(R.color.loading)
                .into(cover);

        SimpleDraweeView shopImg = (SimpleDraweeView) view.findViewById(R.id.d_offer_shop_image);
        shopImg.setImageURI(mOfferEntity.getShopEntity().getImage());

        TextView shopTitle = (TextView) view.findViewById(R.id.d_offer_shop_title);
        shopTitle.setText(mOfferEntity.getShopEntity().getTitle());
        shopTitle.setContentDescription(mOfferEntity.getShopEntity().getTitle());

        TextView offerBody = (TextView) view.findViewById(R.id.d_offer_body);
        offerBody.setText(mOfferEntity.getBody());
        offerBody.setContentDescription(mOfferEntity.getBody());

        mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return view;
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

    public static void attachMe(FragmentManager fragmentManager, String tag, int layout, OfferEntity offerEntity, boolean isLarge) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_OFFER, offerEntity);
        bundle.putBoolean(EXTRA_IS_LARGE, isLarge);
        OfferDetailsFragment detailsFragment = new OfferDetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment, tag)
                .commit();
    }

    public static void reattachMe(FragmentManager fragmentManager, String tag, int layout) {

        OfferDetailsFragment detailsFragment = (OfferDetailsFragment) fragmentManager.findFragmentByTag(tag);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment, tag)
                .commit();
    }
}
