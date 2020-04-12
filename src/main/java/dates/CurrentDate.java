package dates;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CurrentDate {

    // Try and always specify the time zone you're working with
    private final static ZoneId TZ = ZoneId.of("Africa/Nairobi");

    private final Locale locale;
    private final DayOfWeek firstDayOfWeek;
    private final DayOfWeek lastDayOfWeek;

    public CurrentDate(final Locale locale) {
        this.locale = locale;
        this.firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
        this.lastDayOfWeek = DayOfWeek.of(((this.firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);
    }

    public LocalDate getFirstDay() {
        return LocalDate.now(TZ).with(TemporalAdjusters.previousOrSame(this.firstDayOfWeek));
    }

    public LocalDate getLastDay() {
        return LocalDate.now(TZ).with(TemporalAdjusters.nextOrSame(this.lastDayOfWeek));
    }

    public LocalDate getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }

    public String toString() {
        return String.format(   "The %s week starts on %s and ends on %s",
                this.locale.getDisplayName(),
                this.firstDayOfWeek,
                this.lastDayOfWeek);
    }
}