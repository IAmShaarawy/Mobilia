package net.elshaarawy.mobilia.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.elshaarawy.mobilia.Fragments.FurnitureDetailsFragment;
import net.elshaarawy.mobilia.Fragments.FurnitureFragment;
import net.elshaarawy.mobilia.Fragments.MainFragment;
import net.elshaarawy.mobilia.Fragments.OfferDetailsFragment;
import net.elshaarawy.mobilia.Fragments.OffersFragment;
import net.elshaarawy.mobilia.Fragments.ShopDetailsFragment;
import net.elshaarawy.mobilia.Fragments.ShopsFragment;
import net.elshaarawy.mobilia.R;

public class MainActivity extends AppCompatActivity implements MainFragment.MainFragmentCallbacks {


    private static final String TAG_MAIN_FRAGMENT = "tag_main_fragment";
    private final FragmentManager mFragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mainFragment, TAG_MAIN_FRAGMENT)
                    .commit();
        } else {
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT), TAG_MAIN_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public void onNavigationItemSelected(int id) {
        if (findViewById(R.id.main_details_container_land) !=null){
            switchDetailsFragment(id,R.id.main_details_container_land);
        }

        else if (findViewById(R.id.main_details_container_xl) !=null){
            switchDetailsFragment(id,R.id.main_details_container_xl);
        }
    }


    private void switchDetailsFragment(int id,int layout){
        switch (id){
            case R.id.navigation_home:
                FurnitureDetailsFragment.attachMe(mFragmentManager,layout,null,true);
                break;
            case R.id.navigation_shops:
                ShopDetailsFragment.attachMe(mFragmentManager,layout,null,true);
                break;
            case R.id.navigation_offers:
                OfferDetailsFragment.attachMe(mFragmentManager,layout,null,true);
                break;
        }
    }

}
