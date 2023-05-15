package com.example.hanghae_market.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ChatTime {
    public static String chatTime(LocalDateTime time) {
        String timeToString = "";
        LocalDate day = time.toLocalDate();
        if (day.compareTo(LocalDate.now()) == 0) {
            timeToString = time.format(DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko")));
        } else if (day.equals(LocalDate.now().minusDays(1))) {
            timeToString = "어제";
        } else {
            timeToString = time.format(DateTimeFormatter.ofPattern("M월 d일"));
        }
        return timeToString;
    }
}
