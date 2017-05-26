package net.elshaarawy.mobilia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class FurnitureDetailsFragment extends Fragment {
    private static final String EXTRA_FURNITURE = "extra_furniture";
    private static final String EXTRA_IS_LARGE = "extra_is_large";
    private FurnitureEntity mFurnitureEntity;
    private boolean isLarge;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mFurnitureEntity = bundle.getParcelable(EXTRA_FURNITURE);
        isLarge = bundle.getBoolean(EXTRA_IS_LARGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Handle null data
        if (mFurnitureEntity == null) {
            View nullView = inflater.inflate(R.layout.layout_null_fragment, container, false);
            TextView massage = (TextView) nullView.findViewById(R.id.fragment_null_text_view);
            massage.setText(getContext().getString(R.string.null_fragment_furniture));
            return nullView;
        }


        View view = inflater.inflate(R.layout.fragment_furniture_details, container, false);

        AppCompatActivity activity  = (AppCompatActivity) getActivity();

        CollapsingToolbarLayout coToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.d_furniture_co_toolbar);
        coToolbar.setTitle(mFurnitureEntity.getTitle());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.d_furniture_toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(!isLarge);
        setHasOptionsMenu(true);

        ImageView cover = (ImageView) view.findViewById(R.id.d_furniture_img);
        Picasso.with(getContext())
                .load(mFurnitureEntity.getImage())
                .error(R.color.error)
                .placeholder(R.color.loading)
                .into(cover);

        SimpleDraweeView shopImg = (SimpleDraweeView) view.findViewById(R.id.d_furniture_shop_image);
        shopImg.setImageURI(mFurnitureEntity.getShopEntity().getImage());

        TextView shopTitle = (TextView) view.findViewById(R.id.d_furniture_shop_title);
        shopTitle.setText(mFurnitureEntity.getShopEntity().getTitle());
        shopTitle.setContentDescription(mFurnitureEntity.getShopEntity().getTitle());

        TextView offerBody = (TextView) view.findViewById(R.id.d_furniture_body);
        offerBody.setText(mFurnitureEntity.getBody());
        offerBody.setContentDescription(mFurnitureEntity.getBody());

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

    public static void attachMe(FragmentManager fragmentManager, String tag,int layout, FurnitureEntity furnitureEntity, boolean isLarge) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_FURNITURE, furnitureEntity);
        bundle.putBoolean(EXTRA_IS_LARGE, isLarge);
        FurnitureDetailsFragment detailsFragment = new FurnitureDetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment,tag)
                .commit();
        Log.d("LKJ","attach");
    }
    public static void reattachMe(FragmentManager fragmentManager, String tag,int layout) {

        FurnitureDetailsFragment detailsFragment = (FurnitureDetailsFragment) fragmentManager.findFragmentByTag(tag);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment,tag)
                .commit();

        Log.d("LKJ","reattach");
    }
}
