package net.elshaarawy.mobilia.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OfferEntity implements Parcelable {
    protected OfferEntity(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OfferEntity> CREATOR = new Creator<OfferEntity>() {
        @Override
        public OfferEntity createFromParcel(Parcel in) {
            return new OfferEntity(in);
        }

        @Override
        public OfferEntity[] newArray(int size) {
            return new OfferEntity[size];
        }
    };
}
