package net.elshaarawy.mobilia.Data;

import android.net.Uri;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class MobiliaContract {
    public static final String AUTHORITY = "net.elshaarawy.mobilia";

    public static final Uri BASE_CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).build();

    public interface Paths{
        //TODO declare paths Strings
    }

    public interface ProviderUris{
        //TODO Build Mobilia Provider Uris from Paths and authorities
    }

    public interface MatchingCodes{
        //TODO declare Matching codes Integers for Uri Matcher
    }

    public interface MimeTypes {
        //TODO declare MimeTypes Strings of Mobilia Provider
    }

}
