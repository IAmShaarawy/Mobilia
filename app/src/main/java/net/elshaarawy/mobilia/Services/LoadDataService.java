package net.elshaarawy.mobilia.Services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.elshaarawy.mobilia.Activities.SplashActivity;
import net.elshaarawy.mobilia.R;
import net.elshaarawy.mobilia.Widgets.OffersWidget;

import static net.elshaarawy.mobilia.Data.MobiliaContract.CategoriesColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.FurnitureColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ShopsColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.OffersColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_CATEGORIES;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_FURNITURE;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_SHOPS;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_OFFERS;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class LoadDataService extends Service {


    private static final String EXTRA_TO_UI = "extra_to_ui";
    private LoadTask mLoadTask;
    private boolean toUi, error;

    private ContentResolver mResolver;


    @Override
    public void onCreate() {
        super.onCreate();

        mLoadTask = new LoadTask();

        mResolver = getContentResolver();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        toUi = intent.getBooleanExtra(EXTRA_TO_UI, false);
        mLoadTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class LoadTask extends AsyncTask<String, Void, Boolean> {

        DatabaseReference databaseRef;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            databaseRef = FirebaseDatabase.getInstance().getReference();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //get categories
                    DataSnapshot categories = dataSnapshot.child(getString(R.string.fb_categories));
                    ContentValues[] mCategoriesContentValues = new ContentValues[(int) categories.getChildrenCount()];
                    ContentValues values;
                    int i = 0;
                    for (DataSnapshot snapshot : categories.getChildren()) {

                        values = new ContentValues();
                        values.put(CategoriesColumns._ID, Long.valueOf(snapshot.getKey()));
                        values.put(CategoriesColumns.TITLE, snapshot.getValue().toString());

                        mCategoriesContentValues[i++] = values;
                    }

                    mResolver.delete(CONTENT_CATEGORIES, "", null);
                    mResolver.bulkInsert(CONTENT_CATEGORIES, mCategoriesContentValues);

                    //get Furniture
                    DataSnapshot furniture = dataSnapshot.child(getString(R.string.fb_furniture));
                    ContentValues[] mFurnitureContentValues = new ContentValues[(int) furniture.getChildrenCount()];
                    ContentValues values1;
                    i = 0;

                    for (DataSnapshot snapshot : furniture.getChildren()) {

                        values1 = new ContentValues();
                        values1.put(FurnitureColumns._ID, Long.valueOf(snapshot.getKey()));
                        values1.put(FurnitureColumns.TITLE, snapshot.child(getString(R.string.fb_title)).getValue().toString());
                        values1.put(FurnitureColumns.CATEGORY_ID, Long.valueOf(snapshot.child(getString(R.string.fb_category)).getValue().toString()));
                        values1.put(FurnitureColumns.SHOP_ID, Long.valueOf(snapshot.child(getString(R.string.fb_shop)).getValue().toString()));
                        values1.put(FurnitureColumns.IMAGE, snapshot.child(getString(R.string.fb_img)).getValue().toString());
                        values1.put(FurnitureColumns.BODY, snapshot.child(getString(R.string.fb_body)).getValue().toString());

                        mFurnitureContentValues[i++] = values1;
                    }
                    mResolver.delete(CONTENT_FURNITURE, "", null);
                    mResolver.bulkInsert(CONTENT_FURNITURE, mFurnitureContentValues);


                    //get Shops
                    DataSnapshot shops = dataSnapshot.child(getString(R.string.fb_shops));
                    ContentValues[] mShopsContentValues = new ContentValues[(int) shops.getChildrenCount()];
                    ContentValues values2;
                    i = 0;

                    for (DataSnapshot snapshot : shops.getChildren()) {
                        values2 = new ContentValues();
                        values2.put(ShopsColumns._ID, Long.valueOf(snapshot.getKey()));
                        values2.put(ShopsColumns.TITLE, snapshot.child(getString(R.string.fb_title)).getValue().toString());
                        values2.put(ShopsColumns.CATEGORY_ID, Long.valueOf(snapshot.child(getString(R.string.fb_category)).getValue().toString()));
                        values2.put(ShopsColumns.IMAGE, snapshot.child(getString(R.string.fb_img)).getValue().toString());
                        values2.put(ShopsColumns.PHONE, snapshot.child(getString(R.string.fb_phone)).getValue().toString());
                        values2.put(ShopsColumns.WEBSITE, snapshot.child(getString(R.string.fb_website)).getValue().toString());
                        values2.put(ShopsColumns.ABOUT, snapshot.child(getString(R.string.fb_about)).getValue().toString());

                        mShopsContentValues[i++] = values2;
                    }
                    mResolver.delete(CONTENT_SHOPS, "", null);
                    mResolver.bulkInsert(CONTENT_SHOPS, mShopsContentValues);

                    //get Offers
                    DataSnapshot offers = dataSnapshot.child(getString(R.string.fb_furniture));
                    ContentValues[] mOffersContentValues = new ContentValues[(int) offers.getChildrenCount()];
                    ContentValues values3;
                    i = 0;
                    for (DataSnapshot snapshot : offers.getChildren()) {

                        values3 = new ContentValues();
                        values3.put(OffersColumns._ID, Long.valueOf(snapshot.getKey()));
                        values3.put(OffersColumns.TITLE, snapshot.child(getString(R.string.fb_title)).getValue().toString());
                        values3.put(OffersColumns.CATEGORY_ID, Long.valueOf(snapshot.child(getString(R.string.fb_category)).getValue().toString()));
                        values3.put(OffersColumns.SHOP_ID, Long.valueOf(snapshot.child(getString(R.string.fb_shop)).getValue().toString()));
                        values3.put(OffersColumns.IMAGE, snapshot.child(getString(R.string.fb_img)).getValue().toString());
                        values3.put(OffersColumns.BODY, snapshot.child(getString(R.string.fb_body)).getValue().toString());

                        mOffersContentValues[i++] = values3;
                    }
                    mResolver.delete(CONTENT_OFFERS, "", null);
                    mResolver.bulkInsert(CONTENT_OFFERS, mOffersContentValues);

                    error = false;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    error = true;
                }
            });
            return !error;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (toUi)
                SplashActivity.sendBroadCastToMe(LoadDataService.this, aBoolean);

            OffersWidget.sendBroadCastToMe(LoadDataService.this);

            LoadDataService.this.stopSelf();
        }
    }

    public static void startMe(Context context, boolean toUi) {
        Intent intent = new Intent(context, LoadDataService.class);
        intent.putExtra(EXTRA_TO_UI, toUi);
        context.startService(intent);
    }
}
