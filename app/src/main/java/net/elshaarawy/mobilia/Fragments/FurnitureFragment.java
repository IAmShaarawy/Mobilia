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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.elshaarawy.mobilia.Adapters.CategoriesAdapter;
import net.elshaarawy.mobilia.Adapters.FurnitureAdapter;
import net.elshaarawy.mobilia.Data.Entities.CategoryEntity;
import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

import java.util.ArrayList;
import java.util.List;

import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_CATEGORIES;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_FURNITURE;
import static net.elshaarawy.mobilia.Data.MobiliaContract.FurnitureColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.CategoriesColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ShopsColumns;

/**
 * Created by elshaarawy on 22-May-17.
 */

public class FurnitureFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    private static final int CATEGORIES_LOADER_ID = 10;
    private static final int FURNITURE_LOADER_ID = 11;
    private static final String EXTRA_CATEGORY_ID = "extra_category_id";
    private static final String KEY_FURNITURE = "key_furniture";
    private static final String KEY_CATEGORIES = "key_categories";
    private LoaderManager mLoaderManager;

    private Spinner mCategoriesSpinner;
    private CategoriesAdapter mCategoriesAdapter;
    private List<CategoryEntity> mCategoryEntities;
    private RecyclerView mFurnitureRecyclerView;
    private FurnitureAdapter mFurnitureAdapter;
    private FurnitureAdapter.FurnitureItemClickListener mItemClickListener;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private Parcelable mFurnitureLayoutState, mCategoriesState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mItemClickListener = (FurnitureAdapter.FurnitureItemClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoaderManager = getLoaderManager();

        mLoaderManager.initLoader(CATEGORIES_LOADER_ID, null, this);

        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, 0);
        mLoaderManager.initLoader(FURNITURE_LOADER_ID, bundle, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_furniture, container, false);

        mCategoryEntities = new ArrayList<>();
        mCategoriesSpinner = (Spinner) view.findViewById(R.id.spinner_categories);
        mCategoriesSpinner.setOnItemSelectedListener(this);
        mCategoriesAdapter = new CategoriesAdapter(mCategoryEntities);
        mCategoriesSpinner.setAdapter(mCategoriesAdapter);

        if (view.findViewById(R.id.recyclerView_furniture) != null) {
            mFurnitureRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_furniture);
            mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);


        } else if (view.findViewById(R.id.recyclerView_furniture_land) != null) {
            mFurnitureRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_furniture_land);
            mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);


        } else if (view.findViewById(R.id.recyclerView_furniture_xl) != null) {
            mFurnitureRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_furniture_xl);
            mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        }

        mFurnitureRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        mFurnitureAdapter = new FurnitureAdapter(new ArrayList<FurnitureEntity>(), mItemClickListener);
        mFurnitureRecyclerView.setAdapter(mFurnitureAdapter);


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCategoriesSpinner != null) {
            mCategoriesState = mCategoriesSpinner.onSaveInstanceState();
            mFurnitureLayoutState = mStaggeredGridLayoutManager.onSaveInstanceState();

            outState.putParcelable(KEY_FURNITURE, mFurnitureLayoutState);
            outState.putParcelable(KEY_CATEGORIES, mCategoriesState);
        }


    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null ) {

            mCategoriesState = savedInstanceState.getParcelable(KEY_CATEGORIES);

            mFurnitureLayoutState = savedInstanceState.getParcelable(KEY_FURNITURE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCategoriesState != null) {
            mCategoriesSpinner.onRestoreInstanceState(mCategoriesState);
        }

        if (mFurnitureLayoutState != null) {
            mStaggeredGridLayoutManager.onRestoreInstanceState(mFurnitureLayoutState);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, mCategoryEntities.get(position).getId());
        mLoaderManager.restartLoader(FURNITURE_LOADER_ID, bundle, this);
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
            case FURNITURE_LOADER_ID:
                Long cat_id = args.getLong(EXTRA_CATEGORY_ID);
                if (cat_id == 0) {
                    cursor = new CursorLoader(getContext(), CONTENT_FURNITURE, null, null, null, null);
                } else {
                    String selection = FurnitureColumns.TABLE_NAME + "." + FurnitureColumns.CATEGORY_ID + "=?";
                    String[] selectionArgs = new String[]{String.valueOf(cat_id)};
                    cursor = new CursorLoader(getContext(), CONTENT_FURNITURE, null, selection, selectionArgs, null);
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
                                data.getLong(CategoriesColumns.INDEX_ID),
                                data.getString(CategoriesColumns.INDEX_TITLE));
                        categoryEntities.add(categoryEntity);
                    }

                    mCategoryEntities.clear();
                    mCategoryEntities.addAll(categoryEntities);
                    mCategoriesAdapter.resetData(categoryEntities);
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
                                data.getLong(data.getColumnIndex(FurnitureColumns._ID)),
                                data.getString(data.getColumnIndex(FurnitureColumns.TITLE)),
                                data.getLong(data.getColumnIndex(FurnitureColumns.CATEGORY_ID)),
                                data.getLong(data.getColumnIndex(FurnitureColumns.SHOP_ID)),
                                data.getString(data.getColumnIndex(FurnitureColumns.IMAGE)),
                                data.getString(data.getColumnIndex(FurnitureColumns.BODY)),
                                new ShopEntity(
                                        data.getLong(data.getColumnIndex(ShopsColumns._ID)),
                                        data.getString(data.getColumnIndex(ShopsColumns.TITLE)),
                                        data.getLong(data.getColumnIndex(ShopsColumns.CATEGORY_ID)),
                                        data.getString(data.getColumnIndex(ShopsColumns.IMAGE)),
                                        data.getString(data.getColumnIndex(ShopsColumns.PHONE)),
                                        data.getString(data.getColumnIndex(ShopsColumns.WEBSITE)),
                                        data.getString(data.getColumnIndex(ShopsColumns.ABOUT))));
                        furnitureEntities.add(furnitureEntity);
                    }

                    mFurnitureAdapter.resetData(furnitureEntities);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
