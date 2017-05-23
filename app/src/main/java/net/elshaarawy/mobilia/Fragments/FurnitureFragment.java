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
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Data.MobiliaContract;
import net.elshaarawy.mobilia.R;

import java.util.ArrayList;
import java.util.List;

import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_CATEGORIES;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_FURNATURE;
import static net.elshaarawy.mobilia.Data.MobiliaContract.FurnitureColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.CategoriesColumns;

/**
 * Created by elshaarawy on 22-May-17.
 */

public class FurnitureFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CATEGORIES_LOADER_ID = 10;
    private static final int FURNITURE_LOADER_ID = 11;
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
        View view = inflater.inflate(R.layout.fragment_furniture, container, false);

        mLoaderManager.restartLoader(CATEGORIES_LOADER_ID, null, this);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, 0);
        mLoaderManager.restartLoader(FURNITURE_LOADER_ID, bundle, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursor;
        switch (id) {
            case CATEGORIES_LOADER_ID:
                cursor = new CursorLoader(getContext(), CONTENT_CATEGORIES, null, null, null, null);
                break;
            case FURNITURE_LOADER_ID:
                Long cat_id = args.getLong(EXTRA_CATEGORY_ID);
                if (cat_id == 0) {
                    cursor = new CursorLoader(getContext(), CONTENT_FURNATURE, null, null, null, null);
                } else {
                    String selection = FurnitureColumns.TABLE_NAME + "." + FurnitureColumns.CATEGORY_ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(cat_id)};
                    cursor = new CursorLoader(getContext(), CONTENT_FURNATURE, null, selection, selectionArgs, null);
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
                                data.getLong(CategoriesColumns.INDEX_ID),
                                data.getString(CategoriesColumns.INDEX_TITLE));
                        categoryEntities.add(categoryEntity);
                    }
                    //TODO reset Adapter
                    Log.d("LOP", categoryEntities.size() + "fC");
                }
                break;
            case FURNITURE_LOADER_ID:
                int furnitureRowsCount = data.getCount();
                int furnitureColumnsCount = data.getColumnCount();
                List<FurnitureEntity> furnitureEntities;
                if (furnitureRowsCount >= 0 && furnitureColumnsCount > 0) {
                    furnitureEntities = new ArrayList<>(furnitureRowsCount);
                    FurnitureEntity furnitureEntity;
                    for (int i = 0; i < furnitureRowsCount; i++) {
                        data.moveToPosition(i);
                        furnitureEntity = new FurnitureEntity(
                                data.getLong(FurnitureColumns.INDEX_ID),
                                data.getString(FurnitureColumns.INDEX_TITLE),
                                data.getLong(FurnitureColumns.INDEX_CATEGORY_ID),
                                data.getLong(FurnitureColumns.INDEX_SHOP_ID),
                                data.getString(FurnitureColumns.INDEX_IMAGE),
                                data.getString(FurnitureColumns.INDEX_BODY),
                                new ShopEntity(
                                        data.getLong(MobiliaContract.ShopsColumns.INDEX_ID),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_TITLE),
                                        data.getLong(MobiliaContract.ShopsColumns.INDEX_CATEGORY_ID),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_IMAGE),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_PHONE),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_WEBSITE),
                                        data.getString(MobiliaContract.ShopsColumns.INDEX_ABOUT)));
                        furnitureEntities.add(furnitureEntity);
                    }
                    //TODO reset Adapter
                    Log.d("LOP", furnitureEntities.size() + "fF");
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
