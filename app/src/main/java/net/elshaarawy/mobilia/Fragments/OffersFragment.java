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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import net.elshaarawy.mobilia.Adapters.CategoriesAdapter;
import net.elshaarawy.mobilia.Adapters.FurnitureAdapter;
import net.elshaarawy.mobilia.Adapters.OffersAdapter;
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
import static net.elshaarawy.mobilia.Data.MobiliaContract.ShopsColumns;

/**
 * Created by elshaarawy on 22-May-17.
 */

public class OffersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemSelectedListener {

    private static final int CATEGORIES_LOADER_ID = 30;
    private static final int OFFERS_LOADER_ID = 31;
    private static final String EXTRA_CATEGORY_ID = "extra_category_id";
    private static final String KEY_OFFERS_STATE = "key_offers_state";
    private static final String KEY_OFFERS_CATEGORIES_STATE = "key_offers_categories_state";

    private LoaderManager mLoaderManager;


    private Spinner mCategoriesSpinner;
    private CategoriesAdapter mCategoriesAdapter;
    private List<CategoryEntity> mCategoryEntities;
    private RecyclerView mOffersRecyclerView;
    private OffersAdapter mOffersAdapter;
    private OffersAdapter.OffersItemClickListener mItemClickListener;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mOffersLayoutState, mCategoriesState;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mItemClickListener = (OffersAdapter.OffersItemClickListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLoaderManager = getLoaderManager();

        mLoaderManager.initLoader(CATEGORIES_LOADER_ID, null, this);

        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, 0);
        mLoaderManager.initLoader(OFFERS_LOADER_ID, bundle, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        mCategoryEntities = new ArrayList<>();
        mCategoriesSpinner = (Spinner) view.findViewById(R.id.spinner_offers);
        mCategoriesSpinner.setOnItemSelectedListener(this);
        mCategoriesAdapter = new CategoriesAdapter(mCategoryEntities);
        mCategoriesSpinner.setAdapter(mCategoriesAdapter);

        if (view.findViewById(R.id.recyclerView_offers) != null) {
            mOffersRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_offers);
            mGridLayoutManager = new GridLayoutManager(getContext(), 1);

        } else if (view.findViewById(R.id.recyclerView_offers_land) != null) {
            mOffersRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_offers_land);
            mGridLayoutManager = new GridLayoutManager(getContext(), 1);

        } else if (view.findViewById(R.id.recyclerView_offers_xl) != null) {
            mOffersRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_offers_xl);
            mGridLayoutManager = new GridLayoutManager(getContext(), 4);

        }

        mOffersRecyclerView.setLayoutManager(mGridLayoutManager);

        mOffersAdapter = new OffersAdapter(new ArrayList<OfferEntity>(), mItemClickListener);
        mOffersRecyclerView.setAdapter(mOffersAdapter);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCategoriesSpinner != null) {
            mCategoriesState = mCategoriesSpinner.onSaveInstanceState();
            mOffersLayoutState = mGridLayoutManager.onSaveInstanceState();

            outState.putParcelable(KEY_OFFERS_STATE, mOffersLayoutState);
            outState.putParcelable(KEY_OFFERS_CATEGORIES_STATE, mCategoriesState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            mCategoriesState = savedInstanceState.getParcelable(KEY_OFFERS_CATEGORIES_STATE);

            mOffersLayoutState = savedInstanceState.getParcelable(KEY_OFFERS_STATE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCategoriesState != null) {
            mCategoriesSpinner.onRestoreInstanceState(mCategoriesState);
        }

        if (mOffersLayoutState != null) {
            mGridLayoutManager.onRestoreInstanceState(mOffersLayoutState);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_CATEGORY_ID, mCategoryEntities.get(position).getId());
        mLoaderManager.restartLoader(OFFERS_LOADER_ID, bundle, this);
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
            case OFFERS_LOADER_ID:
                int offersRowsCount = data.getCount();
                int offersColumnsCount = data.getColumnCount();
                List<OfferEntity> offerEntities;
                if (offersRowsCount >= 0 && offersColumnsCount > 0) {
                    offerEntities = new ArrayList<>(offersRowsCount);
                    OfferEntity offerEntity;
                    for (int i = 0; i < offersRowsCount; i++) {
                        data.moveToPosition(offersRowsCount-i-1);
                        offerEntity = new OfferEntity(
                                data.getLong(data.getColumnIndex(OffersColumns._ID)),
                                data.getString(data.getColumnIndex(OffersColumns.TITLE)),
                                data.getLong(data.getColumnIndex(OffersColumns.CATEGORY_ID)),
                                data.getLong(data.getColumnIndex(OffersColumns.SHOP_ID)),
                                data.getString(data.getColumnIndex(OffersColumns.IMAGE)),
                                data.getString(data.getColumnIndex(OffersColumns.BODY)),
                                new ShopEntity(
                                        data.getLong(data.getColumnIndex(ShopsColumns._ID)),
                                        data.getString(data.getColumnIndex(ShopsColumns.TITLE)),
                                        data.getLong(data.getColumnIndex(ShopsColumns.CATEGORY_ID)),
                                        data.getString(data.getColumnIndex(ShopsColumns.IMAGE)),
                                        data.getString(data.getColumnIndex(ShopsColumns.PHONE)),
                                        data.getString(data.getColumnIndex(ShopsColumns.WEBSITE)),
                                        data.getString(data.getColumnIndex(ShopsColumns.ABOUT))));
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
