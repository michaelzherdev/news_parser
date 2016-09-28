package com.mzherdev.parser.model.strategy;

import com.mzherdev.parser.model.Strategy;
import com.mzherdev.parser.vo.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzherdev on 27.09.16.
 */
public class RiaStrategy implements Strategy {
    private static final String URL = "https://ria.ru";
    private static final String URL_FORMAT = "https://ria.ru/%s/";

    public List<News> getNews() {

        List<News> newsList = new ArrayList<News>();

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
                for (RiaNewsType newsType : RiaNewsType.values()) {
                    String newsCategory = newsType.toString();
                    Document doc = getDocument(newsCategory);
                    Elements elements = doc.select("div.b-list__item");
                    if (elements.size() == 0)
                        break;

                    for (Element element : elements) {
                        News news = new News();
                        news.setTitle(element.select("span.b-list__item-title").first().tagName("span").text());
                        news.setText(element.select("div.b-list__item-announce").first().getElementsByTag("span").text());
                        news.setImage(element.select("span.b-list__item-img-ind").first().getElementsByTag("img").attr("src"));
                        news.setCategory(newsCategory);
                        news.setUrl(URL + element.select("a").first().attr("href"));

                        String date = element.select("div.b-list__item-date").first().tagName("span").text();
                        String time = element.select("div.b-list__item-time").first().tagName("span").text();
                        news.setDate(dateFormat.parse(date + " " + time));

                        newsList.add(news);
                    }
                }
        } catch (IOException e) {
        } catch (ParseException e) {
        }
        return newsList;
    }

    protected Document getDocument(String newsCategory) throws IOException {
        return Jsoup.connect(String.format(URL_FORMAT, newsCategory))
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36")
                .referrer(URL + "/").get();
    }
}
