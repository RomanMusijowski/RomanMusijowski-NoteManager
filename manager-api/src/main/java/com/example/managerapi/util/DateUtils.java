package com.example.managerapi.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils {

    public static LocalDateTime getCurrentDate() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }
}
