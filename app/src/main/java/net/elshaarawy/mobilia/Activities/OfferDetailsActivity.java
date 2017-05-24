package net.elshaarawy.mobilia.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.elshaarawy.mobilia.Data.Entities.OfferEntity;
import net.elshaarawy.mobilia.Fragments.OfferDetailsFragment;
import net.elshaarawy.mobilia.R;

public class OfferDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_OFFER = "extra_offer";
    private OfferEntity mOfferEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        mOfferEntity = getIntent().getParcelableExtra(EXTRA_OFFER);

        if (findViewById(R.id.offer_details_container) != null) {
            OfferDetailsFragment.attachMe(getSupportFragmentManager(),null,
                    R.id.offer_details_container,
                    mOfferEntity, false);
        } else {
            finish();
        }
    }

    public static void startMe(Context context, OfferEntity offerEntity) {
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(EXTRA_OFFER, offerEntity);
        context.startActivity(intent);
    }
}
