package net.elshaarawy.mobilia.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import net.elshaarawy.mobilia.Activities.SplashActivity;
import net.elshaarawy.mobilia.R;
import net.elshaarawy.mobilia.Utils.PreferenceUtil;

import static net.elshaarawy.mobilia.Utils.PreferenceUtil.DefaultKeys;

/**
 * Implementation of App Widget functionality.
 */
public class OffersWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.offers_widget);
        PreferenceUtil preferenceUtil = new PreferenceUtil(context, DefaultKeys.DEFAULT_SHARED_PREFERENCE);

        if (preferenceUtil.getBoolean(DefaultKeys.PREF_IS_FIRST_TIME)) {
            // TODO views.setRemoteAdapter(R.id.widget_list,new Intent(context,OffersWidgetRemoteViewsService.class));
            views.setViewVisibility(R.id.widget_list, View.VISIBLE);
            views.setViewVisibility(R.id.widget_placeholder, View.GONE);
        } else {
            views.setViewVisibility(R.id.widget_list, View.GONE);
            views.setViewVisibility(R.id.widget_placeholder, View.VISIBLE);

            Intent intent = new Intent(context, SplashActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            views.setOnClickPendingIntent(R.id.widget_placeholder, pendingIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, OffersWidget.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_list);
            this.onUpdate(context, mgr, mgr.getAppWidgetIds(cn));
        }
    }

    public static void sendBroadCastToMe(Context context) {
        Intent intent = new Intent(context, OffersWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        context.sendBroadcast(intent);
    }
}

