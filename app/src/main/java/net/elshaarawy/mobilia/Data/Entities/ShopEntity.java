package net.elshaarawy.mobilia.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class ShopEntity implements Parcelable {
    protected ShopEntity(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopEntity> CREATOR = new Creator<ShopEntity>() {
        @Override
        public ShopEntity createFromParcel(Parcel in) {
            return new ShopEntity(in);
        }

        @Override
        public ShopEntity[] newArray(int size) {
            return new ShopEntity[size];
        }
    };
}
