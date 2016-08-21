package ru.takoe.iav.countee.fragment.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by takoe on 21.08.16.
 */
public class SettingsFragmentContent {


    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Map<String, Item> ITEM_MAP = new HashMap<>();

    static {
        addItem(item("doWrite", "Write data"));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Item item(String id, String content) {
        return new Item(id, content, "");
    }

    public static class Item {

        public final String id;
        public final String content;
        public final String details;

        public Item(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }

}
