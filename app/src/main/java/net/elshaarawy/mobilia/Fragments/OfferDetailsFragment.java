package net.elshaarawy.mobilia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OfferDetailsFragment extends Fragment {

    private static final String EXTRA_OFFER = "extra_offer";
    private static final String EXTRA_IS_LARGE = "extra_is_large";
    private OfferEntity mOfferEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mOfferEntity = bundle.getParcelable(EXTRA_OFFER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Handle null data
        if (mOfferEntity==null){
            View nullView = inflater.inflate(R.layout.layout_null_fragment, container, false);
            TextView massage = (TextView) nullView.findViewById(R.id.fragment_null_text_view);
            massage.setText(getContext().getString(R.string.null_fragment_offer));
            return nullView;
        }
        View view = inflater.inflate(R.layout.fragment_offer_details, container, false);
        //TODO Complete Offer Details Fragment
        return view;
    }

    public static void attachMe(FragmentManager fragmentManager, int layout, OfferEntity offerEntity, boolean isLarge) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_OFFER, offerEntity);
        bundle.putBoolean(EXTRA_IS_LARGE, isLarge);
        OfferDetailsFragment detailsFragment = new OfferDetailsFragment();
        detailsFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(layout, detailsFragment)
                .commit();
    }
}
