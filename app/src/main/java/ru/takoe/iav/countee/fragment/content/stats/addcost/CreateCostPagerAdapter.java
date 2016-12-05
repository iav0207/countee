package ru.takoe.iav.countee.fragment.content.stats.addcost;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import ru.iav.takoe.countee.service.CostOutputService;

/**
 * Created by takoe on 03.09.16.
 */
public class CreateCostPagerAdapter extends FragmentStatePagerAdapter {

    private int pagesCount;

    public CreateCostPagerAdapter(FragmentManager fm) {
        super(fm);
        pagesCount = CostOutputService.getInstance().getMonthsCount() + 1;
    }

    @Override
    public Fragment getItem(int position) {
        return CreateCostPageFragment.newInstance(pagesCount < 1 ? 0 : pagesCount - (position + 1));
    }

    @Override
    public int getCount() {
        return pagesCount;
    }

}
