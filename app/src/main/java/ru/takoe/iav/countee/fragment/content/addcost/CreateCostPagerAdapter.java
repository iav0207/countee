package ru.takoe.iav.countee.fragment.content.addcost;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.CostOutputService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by takoe on 03.09.16.
 */
public class CreateCostPagerAdapter extends FragmentStatePagerAdapter {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);

    private int pagesCount;

    public CreateCostPagerAdapter(FragmentManager fm) {
        super(fm);
        pagesCount = CostOutputService.getInstance().getMonthsCount() + 1;
    }

    @Override
    public Fragment getItem(int position) {
        return CreateCostPageFragment.newInstance(monthsAgo(position));
    }

    @Override
    public int getCount() {
        return pagesCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dateFormat.format(month(position));
    }

    private Date month(int position) {
        return new DateTime().minusMonths(monthsAgo(position)).toDate();
    }

    private int monthsAgo(int position) {
        return pagesCount < 1 ? 0 : pagesCount - (position + 1);
    }

}
