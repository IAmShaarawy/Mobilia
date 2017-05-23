package net.elshaarawy.mobilia.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.elshaarawy.mobilia.R;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigation;
    private static final String KEY_HOME = "home_key";
    private static final String KEY_SHOPS = "shops_key";
    private static final String KEY_OFFERS = "offers_key";
    private static final String SIK_NAV_ID = "navigation Id";
    private MainFragmentCallbacks mMainFragmentCallbacks;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainFragmentCallbacks = (MainFragmentCallbacks) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(SIK_NAV_ID)) {
                int navId = savedInstanceState.getInt(SIK_NAV_ID);
                switchNavigationID(navId, 0);
                mMainFragmentCallbacks.onNavigationItemSelected(navId);
            }
        } else {
            initFragments(getFragmentManager(),R.id.main_fragment_container);
            mMainFragmentCallbacks.onNavigationItemSelected(R.id.navigation_home);
        }

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == navigation.getSelectedItemId())
            return true;
        mMainFragmentCallbacks.onNavigationItemSelected(item.getItemId());
        return switchNavigationID(item.getItemId(), navigation.getSelectedItemId());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SIK_NAV_ID, navigation.getSelectedItemId());
    }

    private boolean initFragments(android.support.v4.app.FragmentManager fragmentManager,int container) {
        Fragment[] fragments = {new FurnitureFragment(), new ShopsFragment(),
                new OffersFragment()};
        fragmentManager
                .beginTransaction()
                .add(container, fragments[1], KEY_SHOPS)
                .detach(fragments[1])
                .add(container, fragments[2], KEY_OFFERS)
                .detach(fragments[2])
                .add(container, fragments[0], KEY_HOME)
                .commit();

        return true;
    }

    private boolean navigationManipulator(android.support.v4.app.FragmentManager fragmentManager,
                                          String currentFragmentKey, String targetFragmentKey) {
        switch (targetFragmentKey) {
            case KEY_HOME:
            case KEY_SHOPS:
            case KEY_OFFERS:

                fragmentManager
                        .beginTransaction()
                        .detach(fragmentManager.findFragmentByTag(currentFragmentKey))
                        .attach(fragmentManager.findFragmentByTag(targetFragmentKey))
                        .commit();
                break;

            default:
                throw new RuntimeException(String.format("No such a fragment with key : %s",
                        targetFragmentKey));
        }
        return true;
    }

    private boolean switchNavigationID(int _id, int selectedId) {
        String currentFragmentKey, targetFragmentKey;
        switch (selectedId) {
            case R.id.navigation_home:
                currentFragmentKey = KEY_HOME;
                break;
            case R.id.navigation_shops:
                currentFragmentKey = KEY_SHOPS;
                break;
            case R.id.navigation_offers:
                currentFragmentKey = KEY_OFFERS;
                break;
            default:
                currentFragmentKey = KEY_HOME;
        }
        switch (_id) {
            case R.id.navigation_home:
                targetFragmentKey = KEY_HOME;
                break;
            case R.id.navigation_shops:
                targetFragmentKey = KEY_SHOPS;
                break;
            case R.id.navigation_offers:
                targetFragmentKey = KEY_OFFERS;
                break;
            default:
                targetFragmentKey = KEY_HOME;
        }
        navigationManipulator(getFragmentManager(), currentFragmentKey, targetFragmentKey);
        return true;
    }

    public interface MainFragmentCallbacks{
        void onNavigationItemSelected(int id);
    }

}
