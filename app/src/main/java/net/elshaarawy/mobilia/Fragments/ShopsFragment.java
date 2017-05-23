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
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Data.MobiliaContract;
import net.elshaarawy.mobilia.R;

import java.util.ArrayList;
import java.util.List;

import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_CATEGORIES;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_SHOPS;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ShopsColumns;

/**
 * Created by elshaarawy on 22-May-17.
 */

public class ShopsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CATEGORIES_LOADER_ID = 20;
    private static final int SHOPS_LOADER_ID = 21;
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
        View view = inflater.inflate(R.layout.fragment_shops,container,false);

        mLoaderManager.restartLoader(CATEGORIES_LOADER_ID,null,this);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID,0);
        mLoaderManager.restartLoader(SHOPS_LOADER_ID,bundle,this);

        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursor;
        switch (id){
            case CATEGORIES_LOADER_ID:
                cursor = new CursorLoader(getContext(),CONTENT_CATEGORIES,null,null,null,null);
                break;
            case SHOPS_LOADER_ID:
                Long cat_id = args.getLong(EXTRA_CATEGORY_ID);
                if (cat_id ==0){
                    cursor = new CursorLoader(getContext(),CONTENT_SHOPS,null,null,null,null);
                }else {
                    String selection = ShopsColumns.TABLE_NAME+"."+ ShopsColumns.CATEGORY_ID+"=?";
                    String[] selectionArgs  = new String[]{String.valueOf(cat_id)};
                    cursor = new CursorLoader(getContext(),CONTENT_SHOPS,null,selection,selectionArgs,null);
                }
                break;
            default:
                cursor = null;
        }
        return cursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()){
            case CATEGORIES_LOADER_ID:
                int categoriesRowsCount = data.getCount();
                int categoriesColumnsCount = data.getColumnCount();
                List<CategoryEntity> categoryEntities;
                if (categoriesRowsCount>=0&& categoriesColumnsCount>0){
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
                    Log.d("LOP",categoryEntities.size()+"sC");
                }
                break;
            case SHOPS_LOADER_ID:
                int shopsRowsCount = data.getCount();
                int shopsColumnsCount = data.getColumnCount();
                List<ShopEntity> shopEntities;
                if (shopsRowsCount>=0&&shopsColumnsCount>0){
                    shopEntities = new ArrayList<>(shopsRowsCount);
                    ShopEntity shopEntity;
                    for (int i = 0; i < shopsRowsCount; i++) {
                        data.moveToPosition(i);
                        shopEntity = new ShopEntity(
                                data.getLong(ShopsColumns.INDEX_ID),
                                data.getString(ShopsColumns.INDEX_TITLE),
                                data.getLong(ShopsColumns.INDEX_CATEGORY_ID),
                                data.getString(ShopsColumns.INDEX_IMAGE),
                                data.getString(ShopsColumns.INDEX_PHONE),
                                data.getString(ShopsColumns.INDEX_WEBSITE),
                                data.getString(ShopsColumns.INDEX_ABOUT));
                        shopEntities.add(shopEntity);
                    }
                    Log.d("LOP",shopEntities.size()+"sS");
                }

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
