package com.directsupply.MisspelledSearchTermWordCount;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryMatcher {
    /*
        Matches textToMatch in q=(textToMatch)
     */
    public static String extractSearchQuery(String line) {
        Pattern pattern = Pattern.compile("(?<=&q\\=\\().*(?=\\)&)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return line.substring(matcher.start(), matcher.end());
        }
        return null;
    }
}
