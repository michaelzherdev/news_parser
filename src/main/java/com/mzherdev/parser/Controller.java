package com.mzherdev.parser;

import com.mzherdev.parser.model.Model;

/**
 * Created by mzherdev on 27.09.16.
 */
public class Controller {

    Model model;

    public Controller(Model model) {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public void onNewsSelect() {
        model.selectNews();
    }

    public void onGetNewsById(Long id) {
        model.getNewsById(id);
    }

    public void onGetNewsByCategory(String category) {
        model.getNewsByCategory(category);
    }

    public void onGetNewsByDate(Long date) {
        model.getNewsByDate(date);
    }

    public void onGetNewsBetweenDate(Long from, Long to) {
        model.getNewsBetweenDate(from, to);
    }

    public void onSearchFullText(String searchString) {
        model.searchFullText(searchString);
    }
}
