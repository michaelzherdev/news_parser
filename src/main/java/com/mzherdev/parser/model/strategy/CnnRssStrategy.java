package com.mzherdev.parser.model.strategy;

import com.mzherdev.parser.model.Strategy;
import com.mzherdev.parser.vo.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mzherdev on 27.09.2016.
 */
public class CnnRssStrategy implements Strategy {
    private static final String URL = "http://edition.cnn.com/services/rss/";
    private static final String URL_FORMAT = "http://rss.cnn.com/rss/%s.rss";

    public List<News> getNews() {

        List<News> newsList = new ArrayList<News>();

        try {
            for (CnnRssType newsType : CnnRssType.values()) {
                Document doc = getDocument(newsType.toString());

                Elements elements = doc.getElementsByTag("rss").first()
                        .getElementsByTag("channel").first()
                        .getElementsByTag("item");
                if (elements.size() == 0)
                    break;

                for (Element element : elements) {
                    Elements descElements = element.getElementsByTag("description"); //ads, quizes are not parsed
                    if (descElements == null || descElements.size() == 0)
                        continue;

                    Elements dateElements = element.getElementsByTag("pubdate"); //ads, quizes are not parsed
                    if (dateElements == null || dateElements.isEmpty()) {
                        continue;
                    }

                    Elements imageElements = element.getElementsByTag("media:group");
                    if (imageElements == null || imageElements.isEmpty()) {
                        continue;
                    }

                    News news = new News();
                    news.setTitle(element.getElementsByTag("title").first().text().replace("<![CDATA[", "").replace("]]>", ""));
                    String text = descElements.first().text();
                    news.setText(text == null ? "" : text.contains("<img") ? text.substring(0, text.indexOf("<")) : text);
                    news.setImage(imageElements.first().getElementsByTag("media:content").first().attr("url"));
                    news.setCategory(getNewsType(newsType));
                    news.setUrl(element.getElementsByTag("guid").first().text());

                    String date = dateElements.text();
                    news.setDate(new Date(date));

                    newsList.add(news);
                }
            }
        } catch (IOException e) {
        }
        return newsList;
    }

    protected Document getDocument(String newsCategory) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, newsCategory))
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                .referrer(URL).get();
    }

    private String getNewsType(CnnRssType type) {
        switch (type) {
            case edition:
            case edition_world:
            case edition_africa:
            case edition_americas:
            case edition_asia:
            case edition_europe:
            case edition_us:
                return RiaNewsType.world.toString();
            case edition_football:
            case edition_sport:
                return RiaNewsType.sport.toString();
            case edition_space:
            case edition_technology:
                return RiaNewsType.science.toString();
            case edition_entertainment:
                return RiaNewsType.culture.toString();
            case money_news_international:
                return RiaNewsType.economy.toString();
            case edition_meast:
                return "meast";
            case edition_travel:
                return "travel";
            default:
                return "";
        }
    }
}
