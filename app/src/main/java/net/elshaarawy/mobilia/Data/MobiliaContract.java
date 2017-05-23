package net.elshaarawy.mobilia.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class MobiliaContract {
    public static final String AUTHORITY = "net.elshaarawy.mobilia";

    public static final Uri BASE_CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).build();

    public interface CategoriesColumns extends BaseColumns {
        String TABLE_NAME = "categories";

        String _ID = "c_id";
        String TITLE = "c_title";

        int INDEX_ID = 0;
        int INDEX_TITLE = 1;
    }

    public interface FurnitureColumns extends BaseColumns {

        String TABLE_NAME = "furniture";

        String _ID = "f_id";
        String TITLE = "f_title";
        String CATEGORY_ID = "f_category_id";
        String SHOP_ID = "f_shop_id";
        String IMAGE = "f_image";
        String BODY = "f_body";

        int INDEX_ID = 0;
        int INDEX_TITLE = 1;
        int INDEX_CATEGORY_ID = 2;
        int INDEX_SHOP_ID = 3;
        int INDEX_IMAGE = 4;
        int INDEX_BODY = 5;
    }

    public interface ShopsColumns extends BaseColumns {

        String TABLE_NAME = "shops";

        String _ID = "s_id";
        String TITLE = "s_title";
        String CATEGORY_ID = "s_category_id";
        String IMAGE = "s_image";
        String PHONE = "s_phone";
        String WEBSITE = "s_website";
        String ABOUT = "s_about";

        int INDEX_ID = 0;
        int INDEX_TITLE = 1;
        int INDEX_CATEGORY_ID = 2;
        int INDEX_IMAGE = 3;
        int INDEX_PHONE = 4;
        int INDEX_WEBSITE = 5;
        int INDEX_ABOUT = 6;

    }

    public interface OffersColumns extends BaseColumns {

        String TABLE_NAME = "offers";

        String _ID = "o_id";
        String TITLE = "o_title";
        String CATEGORY_ID = "o_category_id";
        String SHOP_ID = "o_shop_id";
        String IMAGE = "o_image";
        String BODY = "o_body";

        int INDEX_ID = 0;
        int INDEX_TITLE = 1;
        int INDEX_CATEGORY_ID = 2;
        int INDEX_SHOP_ID = 3;
        int INDEX_IMAGE = 4;
        int INDEX_BODY = 5;
    }


    public interface Paths {
        String CATEGORIES = "categories";
        String FURNITURE = "furniture";
        String SHOPS = "shops";
        String OFFERS = "offers";
        String SHOPS_OFFERS = "shops_offers";
    }

    public interface ProviderUris {
        Uri CONTENT_CATEGORIES = BASE_CONTENT_URI.buildUpon().appendPath(Paths.CATEGORIES).build();
        Uri CONTENT_FURNATURE = BASE_CONTENT_URI.buildUpon().appendPath(Paths.FURNITURE).build();
        Uri CONTENT_SHOPS = BASE_CONTENT_URI.buildUpon().appendPath(Paths.SHOPS).build();
        Uri CONTENT_OFFERS = BASE_CONTENT_URI.buildUpon().appendPath(Paths.OFFERS).build();
        Uri CONTENT_SHOPS_OFFERS = BASE_CONTENT_URI.buildUpon().appendPath(Paths.SHOPS_OFFERS).build();
    }

    public interface MatchingCodes {
        int CATEGORIES = 1000;
        int FURNITURE = 1010;
        int SHOPS = 1020;
        int OFFERS = 1030;
        int SHOPS_OFFERS = 1040;
    }

    public interface MimeTypes {
        String CATEGORIES_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+AUTHORITY+"/"+Paths.CATEGORIES;
        String FURNITURE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+AUTHORITY+"/"+Paths.FURNITURE;
        String SHOPS_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+AUTHORITY+"/"+Paths.SHOPS;
        String OFFERS_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+AUTHORITY+"/"+Paths.OFFERS;
        String SHOPS_OFFERS_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+AUTHORITY+"/"+Paths.SHOPS_OFFERS;
    }

}
