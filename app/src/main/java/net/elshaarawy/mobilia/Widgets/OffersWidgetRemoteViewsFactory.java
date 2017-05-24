package net.elshaarawy.mobilia.Widgets;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import net.elshaarawy.mobilia.Activities.OfferDetailsActivity;
import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

import java.io.IOException;

import static net.elshaarawy.mobilia.Data.MobiliaContract.ProviderUris.CONTENT_OFFERS;
import static net.elshaarawy.mobilia.Data.MobiliaContract.ShopsColumns;
import static net.elshaarawy.mobilia.Data.MobiliaContract.OffersColumns;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OffersWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor mCursor;

    public OffersWidgetRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mCursor = mContext.getContentResolver().query(CONTENT_OFFERS, null, null, null, null);
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_widget_offer);

        mCursor.moveToPosition(mCursor.getCount()-position-1);

        String title = mCursor.getString(mCursor.getColumnIndex(OffersColumns.TITLE));
        String image = mCursor.getString(mCursor.getColumnIndex(OffersColumns.IMAGE));
        String shop = mCursor.getString(mCursor.getColumnIndex(ShopsColumns.TITLE));


        views.setTextViewText(R.id.item_widget_title, title);
        views.setTextViewText(R.id.item_widget_shop, shop);

        try {
            views.setImageViewBitmap(R.id.item_widget_img, Picasso.with(mContext)
                    .load(image)
                    .error(R.color.error)
                    .placeholder(R.color.loading)
                    .get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
