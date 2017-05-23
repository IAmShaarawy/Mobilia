package net.elshaarawy.mobilia.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.R;

import java.util.List;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ShopHolder> {

    private List<ShopEntity> mShopEntities;
    private ShopItemClickListener mItemClickListener;

    public ShopsAdapter(List<ShopEntity> mShopEntities, ShopItemClickListener mItemClickListener) {
        this.mShopEntities = mShopEntities;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public ShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_shop, parent, false);
        return new ShopHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopHolder holder, int position) {
        ShopEntity shopEntity = mShopEntities.get(position);
        // TODO holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mShopEntities.size();
    }

    public void resetData(List<ShopEntity> shopEntities) {
        mShopEntities.clear();
        mShopEntities.addAll(shopEntities);
        this.notifyDataSetChanged();
    }

    class ShopHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ShopHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bindData(String shopImg, String shopTitle, String shopPhone) {
            //TODO bind Shop Item Data
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(mShopEntities.get(getAdapterPosition()));
        }
    }

    public interface ShopItemClickListener {
        void onItemClick(ShopEntity shopEntity);
    }
}
