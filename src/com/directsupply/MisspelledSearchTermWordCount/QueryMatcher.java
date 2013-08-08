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
        String cleanLine = cleanUrl(line);
        Matcher matcher = getAdvantageMatcher(cleanLine);
        if (matcher.find()) {
            return extractQuery(cleanLine, matcher).toLowerCase().trim();
        }
        matcher = getClassicMatcher(cleanLine);
        if (matcher.find()) {
            return extractQuery(cleanLine, matcher).toLowerCase().trim();
        }
        matcher = getClassicFoodMatcher(cleanLine);
        if (matcher.find()) {
            return extractQuery(cleanLine, matcher).toLowerCase().trim();
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

    private static Matcher getClassicFoodMatcher(String line) {
        Pattern pattern = Pattern.compile("(?<=SearchString\\=)[^&]*$");
        return pattern.matcher(line);
    }
    private static String cleanUrl(String url) {
        return url.replace("%u2122", "(TM)");
    }
}
