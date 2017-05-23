package net.elshaarawy.mobilia.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.elshaarawy.mobilia.Data.Entities.ShopEntity;
import net.elshaarawy.mobilia.Fragments.ShopDetailsFragment;
import net.elshaarawy.mobilia.R;

public class ShopDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_SHOP = "extra_shop";
    private ShopEntity mShopEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        mShopEntity = getIntent().getParcelableExtra(EXTRA_SHOP);

        if (findViewById(R.id.shop_details_container) != null) {
            ShopDetailsFragment.attachMe(getSupportFragmentManager(),
                    R.id.shop_details_container,
                    mShopEntity, false);
        } else {
            //TODO Handle large screen
        }
    }

    public static void startMe(Context context, ShopEntity shopEntity) {
        Intent intent = new Intent(context, ShopDetailsActivity.class);
        intent.putExtra(EXTRA_SHOP, shopEntity);
        context.startActivity(intent);
    }
}
