package net.elshaarawy.mobilia.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.R;

import java.util.List;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferHolder> {
    List<OfferEntity> mOfferEntities;
    OffersItemClickListener mItemClickListener;

    public OffersAdapter(List<OfferEntity> mOfferEntities, OffersItemClickListener mItemClickListener) {
        this.mOfferEntities = mOfferEntities;
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_offer, parent, false);
        return new OfferHolder(view);
    }

    @Override
    public void onBindViewHolder(OfferHolder holder, int position) {
        OfferEntity offerEntity = mOfferEntities.get(position);
        // TODO holder.bindData();
    }

    @Override
    public int getItemCount() {
        return mOfferEntities.size();
    }

    public void resetData(List<OfferEntity> offerEntities) {
        mOfferEntities.clear();
        mOfferEntities.addAll(offerEntities);
        this.notifyDataSetChanged();
    }

    class OfferHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public OfferHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bindData(String shopImg, String shopTitle, String offerImg, String offerTitle) {
            //TODO bind Offer Item Data
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(mOfferEntities.get(getAdapterPosition()));
        }
    }

    public interface OffersItemClickListener {
        void onItemClick(OfferEntity offerEntity);
    }
}
