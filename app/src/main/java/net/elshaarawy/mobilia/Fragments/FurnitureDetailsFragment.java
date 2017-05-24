package net.elshaarawy.mobilia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class FurnitureDetailsFragment extends Fragment {
    private static final String EXTRA_FURNITURE = "extra_furniture";
    private static final String EXTRA_IS_LARGE = "extra_is_large";
    private FurnitureEntity mFurnitureEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mFurnitureEntity = bundle.getParcelable(EXTRA_FURNITURE);
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

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(mFurnitureEntity.getTitle());
        //TODO Complete Furniture Details Fragment
        return view;
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
