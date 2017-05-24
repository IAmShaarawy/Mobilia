package net.elshaarawy.mobilia.Fragments;

import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.elshaarawy.mobilia.Adapters.CategoriesAdapter;
import net.elshaarawy.mobilia.Adapters.ShopsAdapter;
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

public class ShopsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    private static final int CATEGORIES_LOADER_ID = 20;
    private static final int SHOPS_LOADER_ID = 21;
    private static final String EXTRA_CATEGORY_ID = "extra_category_id";
    private static final String KEY_SHOPS_STATE = "key_shops_state";
    private static final String KEY_SHOPS_CATEGORIES_STATE = "key_shops_categories_state";
    private LoaderManager mLoaderManager;

    private Spinner mCategoriesSpinner;
    private CategoriesAdapter mCategoriesAdapter;
    private List<CategoryEntity> mCategoryEntities;
    private RecyclerView mShopsRecyclerView;
    private ShopsAdapter mShopsAdapter;
    private ShopsAdapter.ShopItemClickListener mItemClickListener;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mShopsLayoutState, mCategoriesState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mItemClickListener = (ShopsAdapter.ShopItemClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoaderManager = getLoaderManager();

        mLoaderManager.initLoader(CATEGORIES_LOADER_ID, null, this);

        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, 0);
        mLoaderManager.initLoader(SHOPS_LOADER_ID, bundle, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops, container, false);

        mCategoryEntities = new ArrayList<>();
        mCategoriesSpinner = (Spinner) view.findViewById(R.id.spinner_shops);
        mCategoriesSpinner.setOnItemSelectedListener(this);
        mCategoriesAdapter = new CategoriesAdapter(mCategoryEntities);
        mCategoriesSpinner.setAdapter(mCategoriesAdapter);

        if (view.findViewById(R.id.recyclerView_shops) != null) {
            mShopsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_shops);
            mGridLayoutManager = new GridLayoutManager(getContext(), 2);

        } else if (view.findViewById(R.id.recyclerView_shops_land) != null) {
            mShopsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_shops_land);
            mGridLayoutManager = new GridLayoutManager(getContext(), 2);

        } else if (view.findViewById(R.id.recyclerView_shops_xl) != null) {
            mShopsRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_shops_xl);
            mGridLayoutManager = new GridLayoutManager(getContext(), 4);
        }

        mShopsRecyclerView.setLayoutManager(mGridLayoutManager);

        mShopsAdapter = new ShopsAdapter(new ArrayList<ShopEntity>(), mItemClickListener);
        mShopsRecyclerView.setAdapter(mShopsAdapter);

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCategoriesSpinner != null) {
            mCategoriesState = mCategoriesSpinner.onSaveInstanceState();
            mShopsLayoutState = mGridLayoutManager.onSaveInstanceState();

            outState.putParcelable(KEY_SHOPS_STATE, mShopsLayoutState);
            outState.putParcelable(KEY_SHOPS_CATEGORIES_STATE, mCategoriesState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            mCategoriesState = savedInstanceState.getParcelable(KEY_SHOPS_CATEGORIES_STATE);

            mShopsLayoutState = savedInstanceState.getParcelable(KEY_SHOPS_STATE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCategoriesState != null) {
            mCategoriesSpinner.onRestoreInstanceState(mCategoriesState);
        }

        if (mShopsLayoutState != null) {
            mGridLayoutManager.onRestoreInstanceState(mShopsLayoutState);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, mCategoryEntities.get(position).getId());
        mLoaderManager.restartLoader(SHOPS_LOADER_ID, bundle, this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursor;
        switch (id) {
            case CATEGORIES_LOADER_ID:
                cursor = new CursorLoader(getContext(), CONTENT_CATEGORIES, null, null, null, null);
                break;
            case SHOPS_LOADER_ID:
                Long cat_id = args.getLong(EXTRA_CATEGORY_ID);
                if (cat_id == 0) {
                    cursor = new CursorLoader(getContext(), CONTENT_SHOPS, null, null, null, null);
                } else {
                    String selection = ShopsColumns.TABLE_NAME + "." + ShopsColumns.CATEGORY_ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(cat_id)};
                    cursor = new CursorLoader(getContext(), CONTENT_SHOPS, null, selection, selectionArgs, null);
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
                    //first Item all
                    categoryEntities.add(new CategoryEntity(0, getString(R.string.all_categories)));
                    for (int i = 0; i < categoriesRowsCount; i++) {
                        data.moveToPosition(i);
                        categoryEntity = new CategoryEntity(
                                data.getLong(MobiliaContract.CategoriesColumns.INDEX_ID),
                                data.getString(MobiliaContract.CategoriesColumns.INDEX_TITLE));
                        categoryEntities.add(categoryEntity);
                    }
                    mCategoryEntities.clear();
                    mCategoryEntities.addAll(categoryEntities);
                    mCategoriesAdapter.resetData(categoryEntities);
                }
                break;
            case SHOPS_LOADER_ID:
                int shopsRowsCount = data.getCount();
                int shopsColumnsCount = data.getColumnCount();
                List<ShopEntity> shopEntities;
                if (shopsRowsCount >= 0 && shopsColumnsCount > 0) {
                    shopEntities = new ArrayList<>(shopsRowsCount);
                    ShopEntity shopEntity;
                    for (int i = 0; i < shopsRowsCount; i++) {
                        data.moveToPosition(i);
                        shopEntity = new ShopEntity(
                                data.getLong(data.getColumnIndex(ShopsColumns._ID)),
                                data.getString(data.getColumnIndex(ShopsColumns.TITLE)),
                                data.getLong(data.getColumnIndex(ShopsColumns.CATEGORY_ID)),
                                data.getString(data.getColumnIndex(ShopsColumns.IMAGE)),
                                data.getString(data.getColumnIndex(ShopsColumns.PHONE)),
                                data.getString(data.getColumnIndex(ShopsColumns.WEBSITE)),
                                data.getString(data.getColumnIndex(ShopsColumns.ABOUT)));
                        shopEntities.add(shopEntity);
                    }
                    mShopsAdapter.resetData(shopEntities);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
