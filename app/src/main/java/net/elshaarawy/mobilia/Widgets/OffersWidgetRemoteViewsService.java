package net.elshaarawy.mobilia.Widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OffersWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new OffersWidgetRemoteViewsFactory(getApplicationContext());
    }
}
