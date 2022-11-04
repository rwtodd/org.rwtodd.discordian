package org.rwtodd.discordian;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * Represents a formattable discordian date.
 *
 * @author rwtodd
 */
public class DiscordianDate {

    /* some static constants... */
    private static final String TIBS = "St. Tib's Day";
    private static final String[] SEASONS
            = {"Chaos", "Chs", "Discord", "Dsc", "Confusion", "Cfn",
            "Bureaucracy", "Bcy", "The Aftermath", "Afm"};
    private static final String[] DAYS
            = {"Sweetmorn", "SM", "Boomtime", "BT",
            "Pungenday", "PD", "Prickle-Prickle", "PP",
            "Setting Orange", "SO"};
    private static final String[] HOLYDAY_5
            = {"Mungday", "Mojoday", "Syaday", "Zaraday", "Maladay"};
    private static final String[] HOLYDAY_50
            = {"Chaoflux", "Discoflux", "Confuflux", "Bureflux", "Afflux"};
    private static final String[] EXCLAIM
            = {"Hail Eris!", "All Hail Discordia!", "Kallisti!", "Fnord.", "Or not.",
            "Wibble.", "Pzat!", "P'tang!", "Frink!", "Slack!", "Praise \"Bob\"!", "Or kill me.",
            // randomness, from the Net and other places. Feel free to add (after
            // checking with the relevant authorities, of course).
            "Grudnuk demand sustenance!", "Keep the Lasagna flying!",
            "You are what you see.", "Or is it?", "This statement is false.",
            "Lies and slander, sire!", "Hee hee hee!", "Hail Eris, Hack Java!"};

    private static String ordinalSuffix(int number) {
        if (number / 10 == 1) {
            return "th";
        }
        return switch (number % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    /* non-statics below here */
    private final LocalDate gdate;
    private final int adjusted_yday;
    private final int season_day;
    private final boolean tibs_p;

    /**
     * Initialize a DiscordianDate with a gregorian LocalDate.
     *
     * @param ld the local date
     */
    public DiscordianDate(final LocalDate ld) {
        gdate = ld;
        adjusted_yday
                = ld.getDayOfYear()
                - ((ld.isLeapYear() && (ld.getMonthValue() > 2)) ? 2 : 1);
        season_day = (adjusted_yday % 73) + 1;
        tibs_p = (ld.getMonthValue() == 2) && (ld.getDayOfMonth() == 29);
    }

    /**
     * Initialize a DiscordianDate with the year/month/day.
     *
     * @param y the year (YYYY)
     * @param m the month (MM)
     * @param d the day (DD))
     */
    public DiscordianDate(int y, int m, int d) {
        this(LocalDate.of(y, m, d));
    }

    /**
     * Initialize a DiscordianDate with the current date.
     */
    public DiscordianDate() {
        this(LocalDate.now());
    }

    public int getYear() {
        return gdate.getYear() + 1166;
    }

    public int getDayOfSeason() {
        return season_day;
    }

    public boolean isTibs() {
        return tibs_p;
    }

    public String getSeason() {
        return (tibs_p) ? TIBS : SEASONS[2 * (adjusted_yday / 73)];
    }

    public String getShortSeason() {
        return (tibs_p) ? TIBS : SEASONS[2 * (adjusted_yday / 73) + 1];
    }

    public String getWeekday() {
        return (tibs_p) ? TIBS : DAYS[2 * (adjusted_yday % 5)];
    }

    public String getShortWeekday() {
        return (tibs_p) ? TIBS : DAYS[2 * (adjusted_yday % 5) + 1];
    }

    public boolean isHolyDay() {
        return (season_day == 5) || (season_day == 50);
    }

    public String getHolyDay() {
        return switch (season_day) {
            case 5 -> HOLYDAY_5[adjusted_yday / 73];
            case 50 -> HOLYDAY_50[adjusted_yday / 73];
            default -> "";
        };
    }

    public long daysTilXDay() {
        return gdate.until(LocalDate.of(8661, 7, 5), ChronoUnit.DAYS);
    }

    public String format(String fstr) {
        final var last = fstr.length();
        final var buff = new StringBuilder(last * 2);
        var idx = 0;
        while (idx < last) {
            if (fstr.charAt(idx) == '%') {
                ++idx;
                switch (fstr.charAt(idx)) {
                    case '%' -> buff.append('%');
                    case 'A' -> buff.append(getWeekday());
                    case 'a' -> buff.append(getShortWeekday());
                    case 'B' -> buff.append(getSeason());
                    case 'b' -> buff.append(getShortSeason());
                    case 'd' -> buff.append(getDayOfSeason());
                    case 'e' -> buff.append(season_day)
                            .append(ordinalSuffix(season_day));
                    case 'H' -> buff.append(getHolyDay());
                    case 'n' -> buff.append('\n');
                    case 't' -> buff.append('\t');
                    case 'X' -> buff.append(daysTilXDay());
                    case 'Y' -> buff.append(getYear());
                    case '.' -> buff.append(EXCLAIM[(int) (Math.random() * EXCLAIM.length)]);
                    case '}' -> {/* nothing */
                    }
                    case 'N' -> {
                        if (!isHolyDay()) {
                            idx = last;
                        }
                    }
                    case '{' -> {
                        if (tibs_p) {
                            buff.append(TIBS);
                            idx = fstr.indexOf("%}", idx) + 1;
                            if (idx == 0) {
                                idx = last;
                            }
                        }
                    }
                    default -> buff.append(fstr.charAt(idx));
                }
            } else {
                buff.append(fstr.charAt(idx));
            }
            ++idx;
        }
        return buff.toString();
    }
}
