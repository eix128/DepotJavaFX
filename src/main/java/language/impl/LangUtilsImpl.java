package language.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import language.LangUtils;

import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by kadir.basol on 7.4.2016.
 */
@Singleton
public class LangUtilsImpl implements LangUtils {
    private ArrayList<String> months;
    private ArrayList<String> days;


    @Inject
    private ResourceBundle resourceBundle;


    @Inject
    public void init() {
        months = new ArrayList<String>(12);
        months.add(resourceBundle.getString("months.jan"));
        months.add(resourceBundle.getString("months.feb"));
        months.add(resourceBundle.getString("months.mar"));
        months.add(resourceBundle.getString("months.apr"));
        months.add(resourceBundle.getString("months.may"));
        months.add(resourceBundle.getString("months.jun"));
        months.add(resourceBundle.getString("months.jul"));
        months.add(resourceBundle.getString("months.aug"));
        months.add(resourceBundle.getString("months.sep"));
        months.add(resourceBundle.getString("months.oct"));
        months.add(resourceBundle.getString("months.nov"));
        months.add(resourceBundle.getString("months.dec"));

        days = new ArrayList<>( 7 );
        days.add(resourceBundle.getString("days.sun"));
        days.add(resourceBundle.getString("days.mon"));
        days.add(resourceBundle.getString("days.tue"));
        days.add(resourceBundle.getString("days.wed"));
        days.add(resourceBundle.getString("days.thu"));
        days.add(resourceBundle.getString("days.fri"));
        days.add(resourceBundle.getString("days.sat"));
    }


    public final String getMonthByIndex(int monthIndex) {
        return months.get(monthIndex);
    }

    public final String getDaysByIndex(int dayIndex) {
        return days.get(dayIndex);
    }
}
