package com.directsupply.MisspelledSearchTermWordCount;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryMatcher {
    /*
        Matches textToMatch in q=(textToMatch)
     */
    public static String extractSearchQuery(String line) throws UnsupportedEncodingException {
        Matcher matcher = getAdvantageMatcher(line);
        if (matcher.find()) {
            return extractQuery(line, matcher).toLowerCase();
        }
        // classic records can be encoded twice
        matcher = getClassicMatcher(line);
        if (matcher.find()) {
            return extractQuery(line, matcher).toLowerCase();
        }

        return null;
    }

    private static String extractQuery(String line, Matcher matcher) throws UnsupportedEncodingException {
        return URLDecoder.decode(line.substring(matcher.start(), matcher.end()), "UTF-8");
    }

    private static Matcher getAdvantageMatcher(String line) {
        Pattern pattern = Pattern.compile("(?<=&q\\=\\()[^&]*(?=\\)&)");
        return pattern.matcher(line);
    }

    private static Matcher getClassicMatcher(String line) {
        Pattern pattern = Pattern.compile("(?<=Term\\=)[^&]*(?=&)");
        return pattern.matcher(line);
    }
}
