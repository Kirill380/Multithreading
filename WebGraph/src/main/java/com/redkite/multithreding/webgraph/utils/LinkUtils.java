package com.redkite.multithreding.webgraph.utils;

import java.util.regex.Pattern;

public class LinkUtils {

    private Pattern findAnchor = Pattern.compile("<a.*href=\"(.+?)\".*?>");

    String getAddress(String line) {
        return null;
    }

    boolean isRelative() {
        return true;
    }

    boolean isFullAddress() {
        return true;
    }

    boolean isCorrectAddress() {
        return true;
    }
}
