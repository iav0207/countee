package ru.takoe.iav.countee.view;

import android.view.View;
import android.widget.ScrollView;

/**
 * Created by takoe on 16.08.16.
 */
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