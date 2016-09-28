package com.mzherdev.parser.model;

import com.mzherdev.parser.jdbc.DBConnection;
import com.mzherdev.parser.view.View;
import com.mzherdev.parser.vo.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzherdev on 27.09.2016.
 */
public class Model
{
    private View view;
    private Provider[] providers;

    public Model(View view, Provider[] providers)
    {
        if(view == null || providers == null || providers.length == 0)
            throw new IllegalArgumentException();
        this.view = view;
        this.providers = providers;
    }

    public void selectNews() {
        List<News> newsList = new ArrayList();
        for (Provider provider : providers)
            newsList.addAll(provider.getNews());
        //         save to db here
        String sql = "INSERT INTO NEWS (TITLE, TEXT, CATEGORY, IMAGE, URL, DATE) VALUES (?,?,?,?,?,?)";
        Connection connection = DBConnection.getConnection();
        try {
            for (News news : newsList) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, news.getTitle());
                preparedStatement.setString(2, news.getText());
                preparedStatement.setString(3, news.getCategory());
                preparedStatement.setString(4, news.getImage());
                preparedStatement.setString(5, news.getUrl());
                preparedStatement.setTimestamp(6, new Timestamp(news.getDate().getTime()));
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.update(newsList);
    }

    public void getNewsById(Long id) {
        String sql = "SELECT * FROM NEWS n WHERE n.id = ?";
        Connection connection = DBConnection.getConnection();
        News news = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                news = new News();
                news.setId(resultSet.getLong("id"));
                news.setTitle(resultSet.getString("title"));
                news.setText(resultSet.getString("text"));
                news.setCategory(resultSet.getString("category"));
                news.setImage(resultSet.getString("image"));
                news.setUrl(resultSet.getString("url"));
                news.setDate(resultSet.getTimestamp("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.showNews(news);
    }

    public void getNewsByCategory(String category){
        String sql = "SELECT * FROM NEWS n WHERE n.category = ? ORDER BY n.id";
        Connection connection = DBConnection.getConnection();
        List<News> newsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getLong("id"));
                news.setTitle(resultSet.getString("title"));
                news.setText(resultSet.getString("text"));
                news.setCategory(resultSet.getString("category"));
                news.setImage(resultSet.getString("image"));
                news.setUrl(resultSet.getString("url"));
                news.setDate(resultSet.getTimestamp("date"));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.showNewsList(newsList);
    }

    public void getNewsByDate(Long date){
        String sql = "SELECT * FROM NEWS n WHERE n.date >= ? ORDER BY n.id";
        Connection connection = DBConnection.getConnection();
        List<News> newsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(date));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getLong("id"));
                news.setTitle(resultSet.getString("title"));
                news.setText(resultSet.getString("text"));
                news.setCategory(resultSet.getString("category"));
                news.setImage(resultSet.getString("image"));
                news.setUrl(resultSet.getString("url"));
                news.setDate(resultSet.getTimestamp("date"));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.showNewsList(newsList);
    }

    public void getNewsBetweenDate(Long from, Long to){
        String sql = "SELECT * FROM NEWS n WHERE n.date >= ? AND n.date < ? ORDER BY n.id";
        Connection connection = DBConnection.getConnection();
        List<News> newsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(from));
            preparedStatement.setTimestamp(2, new Timestamp(to));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getLong("id"));
                news.setTitle(resultSet.getString("title"));
                news.setText(resultSet.getString("text"));
                news.setCategory(resultSet.getString("category"));
                news.setImage(resultSet.getString("image"));
                news.setUrl(resultSet.getString("url"));
                news.setDate(resultSet.getTimestamp("date"));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.showNewsList(newsList);
    }

    public void searchFullText(String searchString) {
        String sql = "SELECT * FROM NEWS n WHERE n.title LIKE ? OR n.text LIKE ? ORDER BY n.id";
        Connection connection = DBConnection.getConnection();
        List<News> newsList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchString + "%");
            preparedStatement.setString(2, "%" + searchString + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                News news = new News();
                news.setId(resultSet.getLong("id"));
                news.setTitle(resultSet.getString("title"));
                news.setText(resultSet.getString("text"));
                news.setCategory(resultSet.getString("category"));
                news.setImage(resultSet.getString("image"));
                news.setUrl(resultSet.getString("url"));
                news.setDate(resultSet.getTimestamp("date"));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        view.showNewsList(newsList);
    }
}