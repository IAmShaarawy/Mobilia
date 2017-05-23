package net.elshaarawy.mobilia.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.mobilia.Data.Entities.CategoryEntity;
import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Data.MobiliaContract;
import net.elshaarawy.mobilia.R;

import java.util.ArrayList;
import java.util.List;

import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_CATEGORIES;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_OFFERS;
import static net.elshaarawy.mobilia.Data.MobiliaContract.OffersColumns;

/**
 * Created by elshaarawy on 22-May-17.
 */

public class OffersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CATEGORIES_LOADER_ID = 30;
    private static final int OFFERS_LOADER_ID = 31;
    private static final String EXTRA_CATEGORY_ID = "extra_category_id";
    private LoaderManager mLoaderManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoaderManager = getLoaderManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        mLoaderManager.restartLoader(CATEGORIES_LOADER_ID, null, this);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, 0);
        mLoaderManager.restartLoader(OFFERS_LOADER_ID, bundle, this);

        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursor;
        switch (id) {
            case CATEGORIES_LOADER_ID:
                cursor = new CursorLoader(getContext(), CONTENT_CATEGORIES, null, null, null, null);
                break;
            case OFFERS_LOADER_ID:
                Long cat_id = args.getLong(EXTRA_CATEGORY_ID);
                if (cat_id == 0) {
                    cursor = new CursorLoader(getContext(), CONTENT_OFFERS, null, null, null, null);
                } else {
                    String selection = OffersColumns.TABLE_NAME + "." + OffersColumns.CATEGORY_ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(cat_id)};
                    cursor = new CursorLoader(getContext(), CONTENT_OFFERS, null, selection, selectionArgs, null);
                }
                break;
            default:
                cursor = null;
        }
        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case CATEGORIES_LOADER_ID:
                int categoriesRowsCount = data.getCount();
                int categoriesColumnsCount = data.getColumnCount();
                List<CategoryEntity> categoryEntities;
                if (categoriesRowsCount >= 0 && categoriesColumnsCount > 0) {
                    categoryEntities = new ArrayList<>(categoriesRowsCount);
                    CategoryEntity categoryEntity;
                    for (int i = 0; i < categoriesRowsCount; i++) {
                        data.moveToPosition(i);
                        categoryEntity = new CategoryEntity(
                                data.getLong(MobiliaContract.CategoriesColumns.INDEX_ID),
                                data.getString(MobiliaContract.CategoriesColumns.INDEX_TITLE));
                        categoryEntities.add(categoryEntity);
                    }
                    //TODO reset Adapter
                    Log.d("LOP", categoryEntities.size() + "oC");
                }
                break;
            case OFFERS_LOADER_ID:
                int offersRowsCount = data.getCount();
                int offersColumnsCount = data.getColumnCount();
                List<OfferEntity> offerEntities;
                if (offersRowsCount >= 0 && offersColumnsCount > 0) {
                    offerEntities = new ArrayList<>(offersRowsCount);
                    OfferEntity offerEntity;
                    for (int i = 0; i < offersRowsCount; i++) {
                        data.moveToPosition(i);
                        offerEntity = new OfferEntity(
                                data.getLong(OffersColumns.INDEX_ID),
                                data.getString(OffersColumns.INDEX_TITLE),
                                data.getLong(OffersColumns.INDEX_CATEGORY_ID),
                                data.getLong(OffersColumns.INDEX_SHOP_ID),
                                data.getString(OffersColumns.INDEX_IMAGE),
                                data.getString(OffersColumns.INDEX_BODY),
                                new ShopEntity(
                                        data.getLong(MobiliaContract.ShopsColumns.INDEX_ID),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_TITLE),
                                        data.getLong(MobiliaContract.ShopsColumns.INDEX_CATEGORY_ID),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_IMAGE),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_PHONE),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_WEBSITE),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_ABOUT)));
                        offerEntities.add(offerEntity);
                    }
                    //TODO reset Adapter
                    Log.d("LOP", offerEntities.size() + "oO");
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
