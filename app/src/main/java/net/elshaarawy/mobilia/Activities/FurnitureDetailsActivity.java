package net.elshaarawy.mobilia.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.elshaarawy.mobilia.Data.Entities.FurnitureEntity;
import net.elshaarawy.mobilia.Fragments.FurnitureDetailsFragment;
import net.elshaarawy.mobilia.R;

public class FurnitureDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_FURNITURE = "extra_furniture";
    private FurnitureEntity mFurnitureEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFurnitureEntity = getIntent().getParcelableExtra(EXTRA_FURNITURE);

        setContentView(R.layout.activity_furniture_details);
        if (findViewById(R.id.furniture_details_container) != null) {
            FurnitureDetailsFragment.attachMe(getSupportFragmentManager(),
                    R.id.furniture_details_container,
                    mFurnitureEntity, false);
        } else {
            //TODO Handle large screen
        }
    }

    public static void startMe(Context context, FurnitureEntity furnitureEntity) {
        Intent intent = new Intent(context, FurnitureDetailsActivity.class);
        intent.putExtra(EXTRA_FURNITURE, furnitureEntity);
        context.startActivity(intent);
    }
}
