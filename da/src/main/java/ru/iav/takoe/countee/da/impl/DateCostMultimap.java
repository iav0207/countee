package ru.iav.takoe.countee.da.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultimap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.comparator.CostIntegralComparator;

/**
 * Wrapper class.
 */
@ParametersAreNonnullByDefault
public class DateCostMultimap implements Multimap<DateTime, Cost> {

    private static final Comparator<Cost> costValueComparator = new CostIntegralComparator();

    private final Multimap<DateTime, Cost> multimap = TreeMultimap.create(
            DateTimeComparator.getInstance(),
            costValueComparator
    );

    @Override
    public int size() {
        return multimap.size();
    }

    @Override
    public boolean isEmpty() {
        return multimap.isEmpty();
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return multimap.containsKey(key);
    }

    @Override
    public boolean containsValue(@Nullable Object value) {
        return multimap.containsValue(value);
    }

    @Override
    public boolean containsEntry(@Nullable Object key, @Nullable Object value) {
        return multimap.containsEntry(key, value);
    }

    @Override
    public boolean put(@Nullable DateTime key, @Nullable Cost value) {
        return multimap.put(key, value);
    }

    @Override
    public boolean remove(@Nullable Object key, @Nullable Object value) {
        return multimap.remove(key, value);
    }

    @Override
    public boolean putAll(@Nullable DateTime key, Iterable<? extends Cost> values) {
        return multimap.putAll(key, values);
    }

    @Override
    public boolean putAll(Multimap<? extends DateTime, ? extends Cost> multimap) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Cost> replaceValues(@Nullable DateTime key, Iterable<? extends Cost> values) {
        return multimap.replaceValues(key, values);
    }

    @Override
    public Collection<Cost> removeAll(@Nullable Object key) {
        return multimap.removeAll(key);
    }

    @Override
    public void clear() {
        multimap.clear();
    }

    @Override
    public Collection<Cost> get(@Nullable DateTime key) {
        return multimap.get(key);
    }

    @Override
    public Set<DateTime> keySet() {
        return multimap.keySet();
    }

    @Override
    public Multiset<DateTime> keys() {
        return multimap.keys();
    }

    @Override
    public Collection<Cost> values() {
        return multimap.values();
    }

    @Override
    public Collection<Map.Entry<DateTime, Cost>> entries() {
        return multimap.entries();
    }

    @Override
    public Map<DateTime, Collection<Cost>> asMap() {
        return multimap.asMap();
    }
}
