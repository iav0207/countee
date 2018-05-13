package ru.takoe.iav.countee.android.util;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

import static java.lang.Character.isWhitespace;

public class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {
    @Override
    public int findTokenStart(CharSequence text, int cursor) {
        int i = cursor;

        while (i > 0 && !isWhitespace(text.charAt(i - 1))) {
            i--;
        }
        while (i < cursor && isWhitespace(text.charAt(i))) {
            i++;
        }

        return i;
    }

    @Override
    public int findTokenEnd(CharSequence text, int cursor) {
        for (int i = cursor; i < text.length(); i++) {
            if (isWhitespace(text.charAt(i))) {
                return i;
            }
        }
        return text.length();
    }

    @Override
    public CharSequence terminateToken(CharSequence text) {
        int i = text.length();

        while (i > 0 && isWhitespace(text.charAt(i - 1))) {
            i--;
        }

        if (i > 0 && isWhitespace(text.charAt(i - 1))) {
            return text;
        } else if (text instanceof Spanned) {
            SpannableString sp = new SpannableString(text + " ");
            TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                    Object.class, sp, 0);
            return sp;
        } else {
            return text + " ";
        }
    }
}
