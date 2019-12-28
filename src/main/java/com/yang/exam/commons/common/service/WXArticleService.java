package com.yang.exam.commons.common.service;


import com.yang.exam.commons.common.entity.SimpleArticle;
import com.yang.exam.commons.utils.HtmlEscape;
import com.yang.exam.commons.utils.SimpleHttpClient;
import com.yang.exam.commons.utils.URLUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WXArticleService {

    private static final Pattern WEIXIN_TITLE_PATTERN = Pattern
            .compile("<h2 class=\"rich_media_title([^>]+)>([^<]+)</h2>");

    public static SimpleArticle fetch(String url) throws IOException {
        if (!URLUtils.isValidHttpURL(url)) {
            return null;
        }
        SimpleHttpClient client = new SimpleHttpClient();
        client.setConnectTimeout(10);
        client.setReadTimeout(15);
        String body;
        try {
            body = client.get(url, null, null);
        } catch (IOException ie) {
            throw ie;
        }
        if (body == null) {
            throw new IOException("Failed to get content of url: " + url);
        }
        // 微信文章
        if (url.startsWith("https://mp.weixin.qq.com/s")) {
            return getWeixinArticle(body);
        }
        if (url.startsWith("http://mp.weixin.qq.com/s")) {
            url = url.replace("http", "https:");
            return getWeixinArticle(body);
        }
        return null;
    }

    private static SimpleArticle getWeixinArticle(String body) {
        SimpleArticle article = new SimpleArticle();
        {
            int tagStart = body.indexOf("<div class=\"rich_media_content");
            if (tagStart < 0) {
                return null;
            }
            final int contentStart = body.indexOf('>', tagStart);
            if (contentStart < 0) {
                return null;
            }
            int notBlankContentStart = contentStart + 1;
            for (int i = notBlankContentStart; i < body.length(); i++) {
                char c = body.charAt(i);
                if (!Character.isWhitespace(c)) {
                    notBlankContentStart = i;
                    break;
                }
            }
            final int contentEnd = body.indexOf("</div>", notBlankContentStart);
            if (contentEnd < 0) {
                return null;
            }
            int notBlankContentEnd = contentEnd;
            for (int i = contentEnd - 1; i >= notBlankContentStart; i--) {
                char c = body.charAt(i);
                if (!Character.isWhitespace(c)) {
                    notBlankContentEnd = i + 1;
                    break;
                }
            }
            String content = body.substring(notBlankContentStart, notBlankContentEnd);
            article.setContent(content);
        }
        {
            Matcher matcher = WEIXIN_TITLE_PATTERN.matcher(body);
            if (matcher.find()) {
                String title = matcher.group(2).trim();
                article.setTitle(HtmlEscape.escape(title));
            }
        }
        return article;
    }

}
