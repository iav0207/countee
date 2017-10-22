package ru.iav.takoe.countee.json;

import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;

import static java.util.regex.Pattern.compile;

class DateFormatUtil {

    private DateFormatUtil() {
    }

    private static final Map<Pattern, String> DATE_FORMAT_REGEXPS = ImmutableMap.<Pattern, String>builder()
            .put(
                    compile("^\\d{8}$"),
                    "yyyyMMdd")
            .put(
                    compile("^\\d{1,2}-\\d{1,2}-\\d{4}$"),
                    "dd-MM-yyyy")
            .put(
                    compile("^\\d{4}-\\d{1,2}-\\d{1,2}$"),
                    "yyyy-MM-dd")
            .put(
                    compile("^\\d{1,2}/\\d{1,2}/\\d{4}$"),
                    "MM/dd/yyyy")
            .put(
                    compile("^\\d{4}/\\d{1,2}/\\d{1,2}$"),
                    "yyyy/MM/dd")
            .put(
                    compile("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$"),
                    "dd MMM yyyy")
            .put(
                    compile("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$"),
                    "dd MMMM yyyy")
            .put(
                    compile("^\\d{12}$"),
                    "yyyyMMddHHmm")
            .put(
                    compile("^\\d{8}\\s\\d{4}$"),
                    "yyyyMMdd HHmm")
            .put(
                    compile("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$"),
                    "dd-MM-yyyy HH:mm")
            .put(
                    compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$"),
                    "yyyy-MM-dd HH:mm")
            .put(
                    compile("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$"),
                    "MM/dd/yyyy HH:mm")
            .put(
                    compile("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$"),
                    "yyyy/MM/dd HH:mm")
            .put(
                    compile("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$"),
                    "dd MMM yyyy HH:mm")
            .put(
                    compile("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$"),
                    "dd MMMM yyyy HH:mm")
            .put(
                    compile("^\\d{14}$"),
                    "yyyyMMddHHmmss")
            .put(
                    compile("^\\d{8}\\s\\d{6}$"),
                    "yyyyMMdd HHmmss")
            .put(
                    compile("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "dd-MM-yyyy HH:mm:ss")
            .put(
                    compile("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "yyyy-MM-dd HH:mm:ss")
            .put(
                    compile("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "MM/dd/yyyy HH:mm:ss")
            .put(
                    compile("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "yyyy/MM/dd HH:mm:ss")
            .put(
                    compile("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "dd MMM yyyy HH:mm:ss")
            .put(
                    compile("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "dd MMMM yyyy HH:mm:ss")
            .put(
                    compile("^[a-z]{3}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$"),
                    "MMM dd, yyyy HH:mm:ss")
            .put(
                    compile("^[a-z]{3}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s[a-z]{2}$"),
                    "MMM dd, yyyy HH:mm:ss a")
            .build();

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     *
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    static String determineDateFormat(String dateString) {
        for (Map.Entry<Pattern, String> e : DATE_FORMAT_REGEXPS.entrySet()) {
            if (matchesIgnoreCase(e.getKey(), dateString)) {
                return e.getValue();
            }
        }
        return null; // Unknown format.
    }

    private static boolean matchesIgnoreCase(Pattern p, String s) {
        return p.matcher(s.toLowerCase()).matches();
    }
}
