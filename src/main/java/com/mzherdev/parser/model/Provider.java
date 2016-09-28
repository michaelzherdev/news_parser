package com.mzherdev.parser.model;

import com.mzherdev.parser.vo.News;

import java.util.List;

/**
 * Created by mzherdev on 27.09.16.
 */
public class Provider {

    private Strategy strategy;

    public Provider(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<News> getNews(){
        return strategy.getNews();
    }
}
