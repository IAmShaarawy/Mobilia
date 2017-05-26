package net.elshaarawy.mobilia.Adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

import java.util.List;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class FurnitureAdapter extends RecyclerView.Adapter<FurnitureAdapter.FurnitureHolder> {

    private List<FurnitureEntity> mFurnitureEntities;
    private FurnitureItemClickListener mItemClickListener;

    public FurnitureAdapter(List<FurnitureEntity> mFurnitureEntities, FurnitureItemClickListener mItemClickListener) {
        this.mFurnitureEntities = mFurnitureEntities;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public FurnitureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_furniture, parent, false);
        return new FurnitureHolder(view);
    }

    @Override
    public void onBindViewHolder(FurnitureHolder holder, int position) {
        FurnitureEntity furnitureEntity = mFurnitureEntities.get(position);
        ShopEntity shopEntity = furnitureEntity.getShopEntity();
          holder.bindData(furnitureEntity.getImage(),
                  furnitureEntity.getTitle(),
                  shopEntity.getImage(),
                  shopEntity.getTitle());
    }

    @Override
    public int getItemCount() {
        return mFurnitureEntities.size();
    }

    public void resetData(List<FurnitureEntity> furnitureEntities) {
        mFurnitureEntities.clear();
        mFurnitureEntities.addAll(furnitureEntities);
        this.notifyDataSetChanged();
    }

    class FurnitureHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View container;
        ImageView furnitureImgView;
        SimpleDraweeView shopImgView;
        TextView furnitureTitleView, shopTitleView;
        public FurnitureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            container = itemView;
            furnitureImgView = (ImageView) itemView.findViewById(R.id.item_furniture_image);
            shopImgView = (SimpleDraweeView) itemView.findViewById(R.id.item_furniture_shop_image);
            furnitureTitleView = (TextView) itemView.findViewById(R.id.item_furniture_title);
            shopTitleView = (TextView) itemView.findViewById(R.id.item_furniture_shop);
        }

        public void bindData(String furnitureImg, String furnitureTitle, String shopImg, String shopTitle) {
            Picasso.with(container.getContext())
                    .load(furnitureImg)
                    .placeholder(R.color.loading)
                    .error(R.color.error)
                    .into(furnitureImgView);

            furnitureTitleView.setText(furnitureTitle);
            furnitureTitleView.setContentDescription(furnitureTitle);

            shopImgView.setImageURI(shopImg);

            shopTitleView.setText(shopTitle);
            shopTitleView.setContentDescription(shopTitle);
        }

        @Override
        public void onClick(View v) {
            Pair<View,String> [] pairs = new Pair[2];
            pairs[0] = new Pair<>((View) furnitureImgView,furnitureImgView.getTransitionName());
            pairs[1] = new Pair<>((View) shopImgView,shopImgView.getTransitionName());
            mItemClickListener.onItemClick(mFurnitureEntities.get(getAdapterPosition()),pairs);
        }
    }

    public interface FurnitureItemClickListener {
        void onItemClick(FurnitureEntity furnitureEntity,Pair<View,String>[] sharedElement);
    }
}
