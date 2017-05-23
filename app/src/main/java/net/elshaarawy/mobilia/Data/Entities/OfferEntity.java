package net.elshaarawy.mobilia.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OfferEntity implements Parcelable {
    private long id;
    private String title;
    private long categoryId,shopId;
    private String image,body;

    public OfferEntity(long id, String title, long categoryId, long shopId, String image, String body) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.shopId = shopId;
        this.image = image;
        this.body = body;
    }

    protected OfferEntity(Parcel in) {
        id = in.readLong();
        title = in.readString();
        categoryId = in.readLong();
        shopId = in.readLong();
        image = in.readString();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(categoryId);
        dest.writeLong(shopId);
        dest.writeString(image);
        dest.writeString(body);
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
