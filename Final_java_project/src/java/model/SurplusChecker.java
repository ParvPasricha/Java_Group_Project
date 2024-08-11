package model;

import java.sql.Date;
import java.time.LocalDate;

public class SurplusChecker {

    public static boolean isSurplus(Date expirationDate) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        LocalDate expiration = expirationDate.toLocalDate();
        return !expiration.isAfter(nextWeek); // Returns true if the date is within the next week
    }
}
