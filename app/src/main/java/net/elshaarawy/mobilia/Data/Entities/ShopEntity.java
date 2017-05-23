package net.elshaarawy.mobilia.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class ShopEntity implements Parcelable {

    private long id ;
    private String title;
    private long categoryId;
    private String image,phone,website,about;


    public ShopEntity(long id, String title, long categoryId, String image, String phone, String website, String about) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.image = image;
        this.phone = phone;
        this.website = website;
        this.about = about;
    }

    protected ShopEntity(Parcel in) {
        id = in.readLong();
        title = in.readString();
        categoryId = in.readLong();
        image = in.readString();
        phone = in.readString();
        website = in.readString();
        about = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(categoryId);
        dest.writeString(image);
        dest.writeString(phone);
        dest.writeString(website);
        dest.writeString(about);
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
