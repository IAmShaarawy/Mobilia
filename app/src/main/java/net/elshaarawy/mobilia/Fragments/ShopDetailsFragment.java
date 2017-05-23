package net.elshaarawy.mobilia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class ShopDetailsFragment extends Fragment {
    private static final String EXTRA_SHOP = "extra_shop";
    private static final String EXTRA_IS_LARGE = "extra_is_large";
    private ShopEntity mShopEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        mShopEntity = bundle.getParcelable(EXTRA_SHOP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Handle null data
        if (mShopEntity==null){
            View nullView = inflater.inflate(R.layout.layout_null_fragment, container, false);
            TextView massage = (TextView) nullView.findViewById(R.id.fragment_null_text_view);
            massage.setText(getContext().getString(R.string.null_fragment_shop));
            return nullView;
        }
        View view = inflater.inflate(R.layout.fragment_shop_detials, container, false);
        //TODO Complete Shop Details Fragment
        return view;
    }

    public static void attachMe(FragmentManager fragmentManager, int layout, ShopEntity shopEntity,boolean isLarge) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SHOP, shopEntity);
        bundle.putBoolean(EXTRA_IS_LARGE,isLarge);
        ShopDetailsFragment detailsFragment = new ShopDetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment)
                .commit();
    }
}
