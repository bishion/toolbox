package cn.bishion.timer.util;

import org.quartz.CronExpression;

import java.text.ParseException;

public class CronUtil {
    public static final void valid(String cron){
        try {
            new CronExpression(cron);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
