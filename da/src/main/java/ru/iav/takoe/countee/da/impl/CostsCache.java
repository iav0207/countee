package ru.iav.takoe.countee.da.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import ru.iav.takoe.countee.da.Cache;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.utils.DateUtils.month;
import static ru.iav.takoe.countee.utils.DateUtils.now;
import static ru.iav.takoe.countee.utils.ObjectUtils.defensiveCopy;

public class CostsCache implements Cache {

    private final Multimap<DateTime, Cost> data;

    private final List<Cost> list;

    public CostsCache() {
        data = LinkedHashMultimap.create();
        list = new LinkedList<>();
    }

    @Override
    public void invalidate() {
        synchronized (this) {
            clearAll();
        }
    }

    boolean isEmpty() {
        synchronized (this) {
            return data.isEmpty() && list.isEmpty();
        }
    }

    List<Cost> getAllCosts() {
        synchronized (this) {
            return defensiveCopy(list);
        }
    }

    List<Cost> getCostsForThisMonth() {
        synchronized (this) {
            return defensiveCopy(data.get(month(now())));
        }
    }

    void put(Collection<Cost> allCosts) {
        synchronized (this) {
            clearAll();
            for (Cost each : allCosts) {
                data.put(month(each.getTimestamp()), each);
                list.add(each);
            }
        }
    }

    private void clearAll() {
        data.clear();
        list.clear();
    }

}
