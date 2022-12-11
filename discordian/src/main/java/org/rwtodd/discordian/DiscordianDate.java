package org.rwtodd.discordian;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Represents a discordian date.  It can be queried for info or formatted into
 * a string.
 *
 * @author Richard Todd
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
     * Initialize a {@code DiscordianDate} with a gregorian {@link LocalDate}.
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
     * Initialize a {@code DiscordianDate} with the year/month/day.
     *
     * @param y the year (YYYY)
     * @param m the month (MM)
     * @param d the day (DD))
     */
    public DiscordianDate(int y, int m, int d) {
        this(LocalDate.of(y, m, d));
    }

    /**
     * Initialize a {@code DiscordianDate} with the current date.
     */
    public DiscordianDate() {
        this(LocalDate.now());
    }

    /**
     * Get the Discordian year.
     * @return the year
     */
    public int getYear() {
        return gdate.getYear() + 1166;
    }

    /**
     * Get the day of the discordian season. There are 73 days per season.
     * @return the day in the season (1 - 73)
     */
    public int getDayOfSeason() {
        return season_day;
    }

    /**
     * Find ouf if it happens to be <i>St. Tib's Day</i>.
     * @return well is it??
     */
    public boolean isTibs() {
        return tibs_p;
    }

    /**
     * Get the name of the current season.  The discordian calendqr has five of them.
     *
     * @return the current season.
     * @see #getShortSeason()
     */
    public String getSeason() {
        return (tibs_p) ? TIBS : SEASONS[2 * (adjusted_yday / 73)];
    }

    /**
     * Get an abbreviated name for the current season.
     *
     * @return the abbreviated name
     * @see #getSeason()
     */
    public String getShortSeason() {
        return (tibs_p) ? TIBS : SEASONS[2 * (adjusted_yday / 73) + 1];
    }

    /**
     * Get the name of the current weekday. There are 5 days in the discordian week.
     * If it happens to be St. Tib's Day, the return string will say so, since it isn't
     * actually a weekday.
     *
     * @return the weekday
     * @see #getShortWeekday()
     */
    public String getWeekday() {
        return (tibs_p) ? TIBS : DAYS[2 * (adjusted_yday % 5)];
    }

    /**
     * Get the abbreviated name of the current weekday. There are 5 days in the discordian week.
     * If it happens to be St. Tib's Day, the return string will say so, since it isn't
     * actually a weekday.
     *
     * @return the weekday
     * @see #getWeekday()
     */
    public String getShortWeekday() {
        return (tibs_p) ? TIBS : DAYS[2 * (adjusted_yday % 5) + 1];
    }

    /**
     * Find out if today is a holy day.  There are two per season.
     * @return well is it???
     */
    public boolean isHolyDay() {
        return (season_day == 5) || (season_day == 50);
    }

    /**
     * Get the name of the current holy day, or the empty string if it isn't one.
     * @return the name.
     */
    public String getHolyDayName() {
        return switch (season_day) {
            case 5 -> HOLYDAY_5[adjusted_yday / 73];
            case 50 -> HOLYDAY_50[adjusted_yday / 73];
            default -> "";
        };
    }

    /**
     * Find out how many days there are until <i>X-Day</i> (which, in the Gregorian
     * Calendar, is July 5, 8661).
     *
     * @return how many days until X-Day
     */
    public long getDaysTilXDay() {
        return gdate.until(LocalDate.of(8661, 7, 5), ChronoUnit.DAYS);
    }

    /**
     * <p>Generate a string for the date according to the given format string.  The accepted format
     * strings correspond very closely with those accepted by the Linux {@code ddate} command.</p>
     *
     * <ul>
     *     <li>{@code %A/%a} = the weekday/the abbreviated weekday</li>
     *     <li>{@code %B/%b} = the season/the abbreviated season</li>
     *     <li>{@code %d/%e} = the day of season/the ordinal day of season</li>
     *     <li>{@code %H} = the name of the Holy Day</li>
     *     <li>{@code %X} = days until X-Day</li>
     *     <li>{@code %Y} = the year</li>
     *     <li>{@code %N} = skip the rest unless it's a holy day</li>
     *     <li><code>%{ ... %}</code> = if it's Tibs, announce that and skip the "...".
     *     Otherwise, format whatever is in the "..." part.</li>
     *     <li>{@code %%, %n, %t} = a literal '%', newline, or tab</li>
     *     <li>{@code %.} = give a random exclamation</li>
     * </ul>
     * @param fstr the format string
     * @return the generated string
     */
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
                    case 'e' -> buff.append(getDayOfSeason())
                            .append(ordinalSuffix(getDayOfSeason()));
                    case 'H' -> buff.append(getHolyDayName());
                    case 'n' -> buff.append('\n');
                    case 't' -> buff.append('\t');
                    case 'X' -> {
                        final var nf = NumberFormat.getIntegerInstance();
                        nf.setGroupingUsed(true);
                        buff.append(nf.format(getDaysTilXDay()));
                    }
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
