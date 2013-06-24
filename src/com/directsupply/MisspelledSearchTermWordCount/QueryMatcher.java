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
        Pattern pattern = Pattern.compile("(?<=&q\\=\\()[^&]*(?=\\)&)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return URLDecoder.decode(line.substring(matcher.start(), matcher.end()), "UTF-8");
        }
        return null;
    }
}
