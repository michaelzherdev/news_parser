package com.mzherdev.parser.model;

import com.mzherdev.parser.vo.News;

import java.util.List;

/**
 * Created by mzherdev on 27.09.16.
 */
public interface Strategy {

    List<News> getNews();
}
