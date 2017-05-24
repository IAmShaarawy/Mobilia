package net.elshaarawy.mobilia.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import net.elshaarawy.mobilia.Adapters.FurnitureAdapter;
import net.elshaarawy.mobilia.Adapters.OffersAdapter;
import net.elshaarawy.mobilia.Adapters.ShopsAdapter;
import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Fragments.FurnitureDetailsFragment;
import net.elshaarawy.mobilia.Fragments.MainFragment;
import net.elshaarawy.mobilia.Fragments.OfferDetailsFragment;
import net.elshaarawy.mobilia.Fragments.ShopDetailsFragment;
import net.elshaarawy.mobilia.R;

public class MainActivity
        extends AppCompatActivity
        implements MainFragment.MainFragmentCallbacks,
        FurnitureAdapter.FurnitureItemClickListener,
        ShopsAdapter.ShopItemClickListener,
        OffersAdapter.OffersItemClickListener{

    private static final String TAG_MAIN_FRAGMENT = "tag_main_fragment";

    private static final String EXTRA_FURNITURE = "extra_furniture";
    private static final String EXTRA_TAG_FURNITURE = "furniture_tag_activity";
    private static final String EXTRA_SHOPS = "extra_shops";
    private static final String EXTRA_TAG_SHOPS = "shops_tag_activity";
    private static final String EXTRA_OFFERS = "extra_offers";
    private static final String EXTRA_TAG_OFFERS = "offers_tag_activity";

    private String mFurnitureTag;
    private String mShopsTag;
    private String mOffersTag;

    private final FragmentManager mFragmentManager = getSupportFragmentManager();

    private FurnitureEntity mFurnitureEntity;
    private ShopEntity mShopEntity;
    private OfferEntity mOfferEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mainFragment, TAG_MAIN_FRAGMENT)
                    .commit();
            mFurnitureTag = "furniture_tag";
            mShopsTag = "shop_tag";
            mOffersTag = "offer_tag";
        } else {

            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT), TAG_MAIN_FRAGMENT)
                    .commit();


            mFurnitureTag = savedInstanceState.getString(EXTRA_TAG_FURNITURE);
            mFurnitureEntity  = savedInstanceState.getParcelable(EXTRA_FURNITURE);

            mShopsTag = savedInstanceState.getString(EXTRA_TAG_SHOPS);
            mShopEntity = savedInstanceState.getParcelable(EXTRA_SHOPS);

            mOffersTag = savedInstanceState.getString(EXTRA_TAG_OFFERS);
            mOfferEntity = savedInstanceState.getParcelable(EXTRA_OFFERS);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(EXTRA_TAG_FURNITURE, mFurnitureTag);
        outState.putParcelable(EXTRA_FURNITURE, mFurnitureEntity);

        outState.putString(EXTRA_TAG_SHOPS,mShopsTag);
        outState.putParcelable(EXTRA_SHOPS,mShopEntity);

        outState.putString(EXTRA_TAG_OFFERS,mOffersTag);
        outState.putParcelable(EXTRA_OFFERS,mOfferEntity);
    }

    @Override
    public void onNavigationItemSelected(int id) {

        if (findViewById(R.id.main_details_container_land) != null) {
            switchDetailsFragment(id, R.id.main_details_container_land);

        } else if (findViewById(R.id.main_details_container_xl) != null) {
            switchDetailsFragment(id, R.id.main_details_container_xl);
        }
    }

    private void switchDetailsFragment(int id, int layout) {
        switch (id) {

            case R.id.navigation_home:
                if (mFragmentManager.findFragmentByTag(mFurnitureTag) == null)
                    FurnitureDetailsFragment.attachMe(mFragmentManager, mFurnitureTag, layout, mFurnitureEntity, true);
                else FurnitureDetailsFragment.reattachMe(mFragmentManager, mFurnitureTag, layout);
                break;

            case R.id.navigation_shops:
                if (mFragmentManager.findFragmentByTag(mShopsTag) == null)
                    ShopDetailsFragment.attachMe(mFragmentManager, mShopsTag, layout, mShopEntity, true);
                else ShopDetailsFragment.reattachMe(mFragmentManager, mShopsTag, layout);
                break;

            case R.id.navigation_offers:
                if (mFragmentManager.findFragmentByTag(mOffersTag) == null)
                    OfferDetailsFragment.attachMe(mFragmentManager, mOffersTag, layout, mOfferEntity, true);
                else OfferDetailsFragment.reattachMe(mFragmentManager, mOffersTag, layout);
                break;
        }
    }

    @Override
    public void onItemClick(FurnitureEntity furnitureEntity) {

        mFurnitureEntity = furnitureEntity;
        mFurnitureTag = furnitureEntity.getImage() + furnitureEntity.getTitle();

        if (findViewById(R.id.main_details_container_land) != null) {

            if (mFragmentManager.findFragmentByTag(mFurnitureTag) == null)
                FurnitureDetailsFragment.attachMe(mFragmentManager, mFurnitureTag, R.id.main_details_container_land, furnitureEntity, true);
            else
                FurnitureDetailsFragment.reattachMe(mFragmentManager, mFurnitureTag, R.id.main_details_container_land);


        } else if (findViewById(R.id.main_details_container_xl) != null) {


            if (mFragmentManager.findFragmentByTag(mFurnitureTag) == null)
                FurnitureDetailsFragment.attachMe(mFragmentManager, mFurnitureTag, R.id.main_details_container_xl, furnitureEntity, true);
            else
                FurnitureDetailsFragment.reattachMe(mFragmentManager, mFurnitureTag, R.id.main_details_container_xl);

        } else {

            FurnitureDetailsActivity.startMe(this, furnitureEntity);

        }
    }

    @Override
    public void onItemClick(ShopEntity shopEntity) {
        mShopEntity = shopEntity;
        mShopsTag = shopEntity.getImage() + shopEntity.getTitle();

        if (findViewById(R.id.main_details_container_land) != null) {
            if (mFragmentManager.findFragmentByTag(mShopsTag) == null)
                ShopDetailsFragment.attachMe(mFragmentManager, mShopsTag, R.id.main_details_container_land, shopEntity, true);
            else
                ShopDetailsFragment.reattachMe(mFragmentManager, mShopsTag, R.id.main_details_container_land);

        } else if (findViewById(R.id.main_details_container_xl) != null) {


            if (mFragmentManager.findFragmentByTag(mShopsTag) == null)
                ShopDetailsFragment.attachMe(mFragmentManager, mShopsTag, R.id.main_details_container_xl, shopEntity, true);
            else
                ShopDetailsFragment.reattachMe(mFragmentManager, mShopsTag, R.id.main_details_container_xl);

        } else {
            ShopDetailsActivity.startMe(this, shopEntity);
        }
    }

    @Override
    public void onItemClick(OfferEntity offerEntity) {
        mOfferEntity = offerEntity;
        mOffersTag = offerEntity.getImage() + offerEntity.getTitle();

        if (findViewById(R.id.main_details_container_land) != null) {


            if (mFragmentManager.findFragmentByTag(mOffersTag) == null)
                OfferDetailsFragment.attachMe(mFragmentManager, mOffersTag, R.id.main_details_container_land, offerEntity, true);
            else
                OfferDetailsFragment.reattachMe(mFragmentManager, mOffersTag, R.id.main_details_container_land);


        } else if (findViewById(R.id.main_details_container_xl) != null) {


            if (mFragmentManager.findFragmentByTag(mOffersTag) == null)
                OfferDetailsFragment.attachMe(mFragmentManager, mOffersTag, R.id.main_details_container_xl, offerEntity, true);
            else
                OfferDetailsFragment.reattachMe(mFragmentManager, mOffersTag, R.id.main_details_container_xl);

        } else {

            OfferDetailsActivity.startMe(this, offerEntity);

        }
    }
}