package com.tdr.familytreasure.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.tdr.familytreasure.R;
import com.tdr.familytreasure.fragment.TabHomeFragment;
import com.tdr.familytreasure.fragment.TabLoveFragment;
import com.tdr.familytreasure.fragment.TabMeFragment;
import com.tdr.familytreasure.fragment.TabMsgFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2017/3/2 15:55
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FragmentUtil {
    private static Map<Integer, Fragment> fragmentMap = new HashMap<>();

    public static Fragment switchFragment(FragmentManager fragmentManager, Fragment currentFragment, Fragment
            newFragment) {
        FragmentTransaction mTransaction = fragmentManager.beginTransaction();
        if (!newFragment.isAdded()) {
            mTransaction.hide(currentFragment).add(R.id.fl_new_content, newFragment).commit();
        } else {
            mTransaction.hide(currentFragment).show(newFragment).commit();
        }
        return newFragment;
    }


    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentMap.get(position);
        if (fragment != null) {
            return fragment;
        } else {
            switch (position) {
                case 0:
                    fragment = new TabHomeFragment();
                    break;
                case 1:
                    fragment = new TabLoveFragment();
                    break;
                case 2:
                    fragment = new TabMsgFragment();
                    break;
                case 3:
                    fragment = new TabMeFragment();
                    break;
                default:
                    break;
            }
            fragmentMap.put(position, fragment);
            return fragment;
        }
    }
}
