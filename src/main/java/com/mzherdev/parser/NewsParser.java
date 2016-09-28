package com.mzherdev.parser;

import com.mzherdev.parser.model.*;
import com.mzherdev.parser.model.strategy.CnnRssStrategy;
import com.mzherdev.parser.model.strategy.RiaNewsType;
import com.mzherdev.parser.model.strategy.RiaStrategy;
import com.mzherdev.parser.view.View;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mzherdev on 27.09.16.
 */
public class NewsParser {

    public static void main(String[] args) {
        // change news site here with choosing a strategy
        Provider provider = new Provider(new RiaStrategy());
        Provider provider2 = new Provider(new CnnRssStrategy());

        View view = new View();
        Model model = new Model(view, new Provider[]{provider, provider2});
        Controller controller = new Controller(model);
        view.setController(controller);
        view.newsCategorySelectEmulationMethod();

        System.out.println("\ngetById");
        view.getNewsByIdEmulationMethod(10L);

        System.out.println("\ngetByCategory");
        view.getNewsByCategoryEmulationMethod(RiaNewsType.sport.toString().toLowerCase());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date from = calendar.getTime();

        System.out.println("\ngetByDate");
        view.getNewsByDateEmulationMethod(from.getTime());

        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date to = calendar.getTime();

        System.out.println("\ngetBetweenDate");
        view.getNewsBetweenDatesEmulationMethod(from.getTime(), to.getTime());

        System.out.println("\nsearchFullText");
        view.searchFullTextEmulationMethod("сентября");

        System.out.println("\nsearchFullText");
        view.searchFullTextEmulationMethod("soccer");
    }
}
