package ru.takoe.iav.countee.fragment.content.common;

public class StringItem {

    public final String id;
    public final String content;
    public final String details;

    public StringItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    public static StringItem fromString(String itemName) {
        return new StringItem(itemName, itemName, "");
    }

    @Override
    public String toString() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }
}
