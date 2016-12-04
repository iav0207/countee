package ru.takoe.iav.countee.fragment.content.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 16.11.16.
 */
public class StringItemList {

    private final List<StringItem> items = new ArrayList<>();

    private final Map<String, StringItem> map = new HashMap<>();

    public static StringItemList fromStrings(String... itemNames) {
        StringItemList list = new StringItemList();
        for (String each : itemNames) {
            list.addItem(StringItem.fromString(each));
        }
        return list;
    }

    public static StringItemList fromStrings(Iterable<String> itemNames) {
        StringItemList list = new StringItemList();
        for (String each : itemNames) {
            list.addItem(StringItem.fromString(each));
        }
        return list;
    }

    private void addItem(StringItem item) {
        items.add(item);
        map.put(item.getId(), item);
    }

    public String[] toStringArray() {
        String[] array = new String[items.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = getItem(i).getContent();
        }
        return array;
    }

    public int size() {
        return items.size();
    }

    public StringItem getItem(String id) {
        return map.get(id);
    }

    public StringItem getItem(int position) {
        return items.get(position);
    }

}
