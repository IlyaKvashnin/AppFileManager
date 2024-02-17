package com.sample.servlet.infrastructure.use_cases;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetCurrentTime {
    public String get(String datePattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
        LocalDateTime currentTime = LocalDateTime.now();

        return dtf.format(currentTime);
    }
}
