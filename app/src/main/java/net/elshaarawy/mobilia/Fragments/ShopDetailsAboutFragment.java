package net.elshaarawy.mobilia.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

import java.util.List;

/**
 * Created by elshaarawy on 26-May-17.
 */

public class ShopDetailsAboutFragment extends Fragment {
    private ShopEntity mShopEntity;
    private TextView mBody;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mShopEntity = bundle.getParcelable(getString(R.string.key_shop_about));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about,container,false);

        mBody = (TextView) view.findViewById(R.id.d_client_about_body);
        mBody.setText(mShopEntity.getAbout());
        mBody.setContentDescription(mShopEntity.getAbout());

        return view;
    }
}
