package ru.takoe.iav.countee.view;

import android.view.View;
import android.widget.ScrollView;

public class ViewScroller {

    public static void scrollToBottom(final ScrollView scrollView) {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

}
