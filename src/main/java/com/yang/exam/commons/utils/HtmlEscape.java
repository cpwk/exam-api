package com.yang.exam.commons.utils;

import com.sunnysuperman.commons.util.FileUtil;
import com.sunnysuperman.commons.util.FileUtil.ReadLineHandler;

import java.util.HashMap;
import java.util.Map;

public class HtmlEscape {
    private static Map<String, String> MAPPING = new HashMap<>();

    static {
        try {
            FileUtil.read(R.getStream("html_escape.txt"), null, new ReadLineHandler() {

                @Override
                public boolean handle(String s, int line) throws Exception {
                    s = s.trim();
                    if (s.isEmpty()) {
                        return true;
                    }
                    int offset = s.indexOf('=');
                    if (offset < 0) {
                        throw new RuntimeException("Bad line: " + s);
                    }
                    String key = s.substring(0, offset);
                    String value = s.substring(offset + 1);
                    MAPPING.put(key, StringUtils.escapeSpecialChars(value));
                    return true;
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String findEscapeChar(String key) {
        return MAPPING.get(key);
    }

    public static String escape(String s) {
        if (s == null) {
            return null;
        }
        final int len = s.length();
        StringBuilder buf = new StringBuilder();
        boolean appended;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            appended = false;
            if (c == '&') {
                int escapeKeyEnd = s.indexOf(';', i + 1);
                if (escapeKeyEnd > 0 && escapeKeyEnd < i + 10) {
                    String escapeKey = s.substring(i + 1, escapeKeyEnd);
                    String escapeValue = findEscapeChar(escapeKey);
                    if (escapeValue != null) {
                        buf.append(escapeValue);
                        i = escapeKeyEnd;
                        appended = true;
                    }
                }
            }
            if (!appended) {
                buf.append(c);
            }
        }
        return buf.toString();
    }

}
