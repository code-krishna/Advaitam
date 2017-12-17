package Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import Fragments.DescriptionFragment;
import Fragments.OrganisersFragment;
import Fragments.ResultsFragment;

/**
 * Created by HP on 06-08-2017.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    CharSequence titles[];
    //Bundle bundle;
    String idName;

    public SimpleFragmentPagerAdapter(FragmentManager fm, CharSequence mTitles[],String idName) {
        super(fm);
        titles=mTitles;
        this.idName=idName;
    }

    @Override
    public Fragment getItem(int position) {	//0- based index position
        if (position == 0)
        {
            DescriptionFragment descriptionFragment= new DescriptionFragment();
            Bundle bundle=new Bundle();
            bundle.putString("KEY",idName);
            descriptionFragment.setArguments(bundle);
            return descriptionFragment;
        }
        else
        if (position == 1)
        {
            return new OrganisersFragment();
        }
        else
        {
            return new ResultsFragment();
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override public int getCount() {
        return 3; // no. of fragments –can use a final int for this.
    }
}