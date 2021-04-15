package repository;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final int DEFAULT_LIST_SIZE = 10;
    public static final int NUMBER_OF_RECORDS_ON_PAGE = 3;
}
