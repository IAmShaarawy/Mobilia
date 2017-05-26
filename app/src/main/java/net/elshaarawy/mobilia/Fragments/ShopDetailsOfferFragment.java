package net.elshaarawy.mobilia.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.mobilia.Adapters.OffersAdapter;
import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Data.MobiliaContract;
import net.elshaarawy.mobilia.R;

import java.util.ArrayList;
import java.util.List;

import static net.elshaarawy.mobilia.Data.MobiliaContract.OffersColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_OFFERS;

/**
 * Created by elshaarawy on 26-May-17.
 */

public class ShopDetailsOfferFragment extends Fragment implements OffersAdapter.OffersItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String KEY_OFFERS_STATE = "key_offers_state";
    private static final int LOADER_ID = 40;
    private static final String EXTRA_SHOP_ID = "extra_shop_id";
    private RecyclerView mOffersRecyclerView;
    private OffersAdapter mOffersAdapter;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mOffersLayoutState;
    private LoaderManager mLoaderManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        long shopId = bundle.getLong(getString(R.string.key_shop_offers));
        mLoaderManager = getLoaderManager();
        Bundle bundleLoader = new Bundle();
        bundleLoader.putLong(EXTRA_SHOP_ID, shopId);
        mLoaderManager.initLoader(LOADER_ID, bundleLoader, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_offers, container, false);

        mOffersRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_offers);
        mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mOffersRecyclerView.setLayoutManager(mGridLayoutManager);
        mOffersAdapter = new OffersAdapter(new ArrayList<OfferEntity>(), this);
        mOffersRecyclerView.setAdapter(mOffersAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mOffersLayoutState != null)
            mOffersLayoutState = mGridLayoutManager.onSaveInstanceState();
        outState.putParcelable(KEY_OFFERS_STATE, mOffersLayoutState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            if (mOffersLayoutState != null)
                mOffersLayoutState = savedInstanceState.getParcelable(KEY_OFFERS_STATE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mOffersLayoutState != null) {
            mGridLayoutManager.onRestoreInstanceState(mOffersLayoutState);
        }
    }

    @Override
    public void onItemClick(OfferEntity offerEntity) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID:
                long shopId = args.getLong(EXTRA_SHOP_ID);
                String selection = OffersColumns.TABLE_NAME + "." + OffersColumns.SHOP_ID + "=?";
                String selectionArgs[] = new String[]{String.valueOf(shopId)};
                return new CursorLoader(getContext(), CONTENT_OFFERS, null, selection, selectionArgs, null);
            default:
                return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_ID:
                int offersRowsCount = data.getCount();
                int offersColumnsCount = data.getColumnCount();
                List<OfferEntity> offerEntities;
                if (offersRowsCount >= 0 && offersColumnsCount > 0) {
                    offerEntities = new ArrayList<>(offersRowsCount);
                    OfferEntity offerEntity;
                    for (int i = 0; i < offersRowsCount; i++) {
                        data.moveToPosition(offersRowsCount - i - 1);
                        offerEntity = new OfferEntity(
                                data.getLong(data.getColumnIndex(OffersColumns._ID)),
                                data.getString(data.getColumnIndex(OffersColumns.TITLE)),
                                data.getLong(data.getColumnIndex(OffersColumns.CATEGORY_ID)),
                                data.getLong(data.getColumnIndex(OffersColumns.SHOP_ID)),
                                data.getString(data.getColumnIndex(OffersColumns.IMAGE)),
                                data.getString(data.getColumnIndex(OffersColumns.BODY)),
                                new ShopEntity(
                                        data.getLong(data.getColumnIndex(MobiliaContract.ShopsColumns._ID)),
                                        data.getString(data.getColumnIndex(MobiliaContract.ShopsColumns.TITLE)),
                                        data.getLong(data.getColumnIndex(MobiliaContract.ShopsColumns.CATEGORY_ID)),
                                        data.getString(data.getColumnIndex(MobiliaContract.ShopsColumns.IMAGE)),
                                        data.getString(data.getColumnIndex(MobiliaContract.ShopsColumns.PHONE)),
                                        data.getString(data.getColumnIndex(MobiliaContract.ShopsColumns.WEBSITE)),
                                        data.getString(data.getColumnIndex(MobiliaContract.ShopsColumns.ABOUT))));
                        offerEntities.add(offerEntity);
                    }
                    mOffersAdapter.resetData(offerEntities);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
