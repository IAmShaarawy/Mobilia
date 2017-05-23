package net.elshaarawy.mobilia.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
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
        // TODO holder.bindData();
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

        public FurnitureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bindData(String furnitureImg, String furnitureTitle, String shopImg, String shopTitle) {
            //TODO bind Furniture Item Data
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(mFurnitureEntities.get(getAdapterPosition()));
        }
    }

    public interface FurnitureItemClickListener {
        void onItemClick(FurnitureEntity furnitureEntity);
    }
}
