package net.elshaarawy.mobilia.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class FurnitureEntity implements Parcelable {
    public FurnitureEntity() {
    }

    protected FurnitureEntity(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FurnitureEntity> CREATOR = new Creator<FurnitureEntity>() {
        @Override
        public FurnitureEntity createFromParcel(Parcel in) {
            return new FurnitureEntity(in);
        }

        @Override
        public FurnitureEntity[] newArray(int size) {
            return new FurnitureEntity[size];
        }
    };
}
