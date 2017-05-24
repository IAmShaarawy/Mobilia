package net.elshaarawy.mobilia.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.elshaarawy.mobilia.Data.Entities.CategoryEntity;
import net.elshaarawy.mobilia.R;

import java.util.List;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class CategoriesAdapter extends BaseAdapter {

    private List<CategoryEntity> mCategoryEntities;

    public CategoriesAdapter(List<CategoryEntity> mCategoryEntities) {
        this.mCategoryEntities = mCategoryEntities;
    }

    @Override
    public int getCount() {
        return mCategoryEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCategoryEntities.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CategoryEntity categoryEntity = mCategoryEntities.get(position);
         holder.bind(categoryEntity.getTitle());
        return convertView;
    }

    public void resetData(List<CategoryEntity> categoryEntities) {
        mCategoryEntities.clear();
        mCategoryEntities.addAll(categoryEntities);
        this.notifyDataSetChanged();
    }

    class ViewHolder {
        TextView textView;
        public ViewHolder(View view) {
            textView = (TextView) view;
        }

        public void bind(String text) {
            textView.setText(text);
            textView.setContentDescription(text);
        }
    }
}
