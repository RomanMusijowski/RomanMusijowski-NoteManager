package com.example.developerapi.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
