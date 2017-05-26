package net.elshaarawy.mobilia.Adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

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
          holder.bindData(offerEntity.getShopEntity().getImage(),
                  offerEntity.getShopEntity().getTitle(),
                  offerEntity.getImage(),
                  offerEntity.getTitle());
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

        View container;
        SimpleDraweeView shopImgView;
        ImageView offerImgView;
        TextView shopTitleView,offerTitleView;
        public OfferHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            container = itemView;
            shopImgView = (SimpleDraweeView) itemView.findViewById(R.id.item_offer_shop_image);
            offerImgView = (ImageView) itemView.findViewById(R.id.item_offer_img);
            shopTitleView = (TextView) itemView.findViewById(R.id.item_offer_shop_title);
            offerTitleView = (TextView) itemView.findViewById(R.id.item_offer_title);
        }

        public void bindData(String shopImg, String shopTitle, String offerImg, String offerTitle) {
            shopImgView.setImageURI(shopImg);

            shopTitleView.setText(shopTitle);
            shopTitleView.setContentDescription(shopTitle);

            Picasso.with(container.getContext())
                    .load(offerImg)
                    .error(R.color.error)
                    .placeholder(R.color.loading)
                    .into(offerImgView);
            offerTitleView.setText(offerTitle);
            offerTitleView.setContentDescription(offerTitle);
        }

        @Override
        public void onClick(View v) {
            Pair<View, String>[] pairs = new Pair[2];
            pairs[0] = new Pair<>((View) shopImgView,shopImgView.getTransitionName());
            pairs[1] = new Pair<>((View) offerImgView,offerImgView.getTransitionName());
            mItemClickListener.onItemClick(mOfferEntities.get(getAdapterPosition()),pairs);
        }
    }

    public interface OffersItemClickListener {
        void onItemClick(OfferEntity offerEntity,Pair<View, String>[] sharedElements);
    }
}
