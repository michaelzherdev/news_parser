package com.mzherdev.parser.view;

import com.mzherdev.parser.Controller;
import com.mzherdev.parser.vo.News;

import java.util.List;

/**
 * Created by mzherdev on 27.09.16.
 */
public class View {

    Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void getNewsByIdEmulationMethod(Long id) {
        controller.onGetNewsById(id);
    }

    public void getNewsByCategoryEmulationMethod(String category) {
        controller.onGetNewsByCategory(category);
    }

    public void getNewsByDateEmulationMethod(Long date) {
        controller.onGetNewsByDate(date);
    }

    public void getNewsBetweenDatesEmulationMethod(Long from, Long to) {
        controller.onGetNewsBetweenDate(from, to);
    }

    public void searchFullTextEmulationMethod(String searchString) {
        controller.onSearchFullText(searchString);
    }

    public void newsCategorySelectEmulationMethod() {
        controller.onNewsSelect();
    }

    public void update(List<News> newsList) {
        System.out.println(newsList.size());
    }

    public void showNews(News news) {
        System.out.println(news);
    }

    public void showNewsList(List<News> newsList) {
        for (News news : newsList)
            System.out.println(news);
    }
}
