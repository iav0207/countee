package ru.takoe.iav.countee.fragment.content.addcost;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.service.CostOutputService;
import ru.takoe.iav.countee.application.CounteeApp;

public class CreateCostPagerAdapter extends FragmentStatePagerAdapter {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);

    @Inject CostOutputService costOutputService;

    private final int pagesCount;

    public CreateCostPagerAdapter(FragmentManager fm) {
        super(fm);
        CounteeApp.getInstance()
                .getApplicationComponent()
                .injectInto(this);
        pagesCount = costOutputService.getMonthsCount() + 1;
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
