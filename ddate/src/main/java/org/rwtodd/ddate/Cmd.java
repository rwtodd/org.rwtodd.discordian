package org.rwtodd.ddate;

import org.rwtodd.discordian.DiscordianDate;

/**
 * The CLI runner for the ddate utility
 *
 * @author rwtodd
 */
public class Cmd {

    private static String removeInitialPlus(String s) {
        if (s.isEmpty() || (s.charAt(0) != '+')) {
            throw new IllegalArgumentException("Format string must start with a +!");
        }
        return s.substring(1);
    }

    public static void main(String[] args) {
        final var todayFmt = "Today is %{%A, the %e day of %B%} in the YOLD %Y%N%nCelebrate %H";
        final var otherFmt = "%{%A, %B %d%}, %Y YOLD";
        try {
            System.out.println(switch (args.length) {
                case 0 -> new DiscordianDate().format(todayFmt);
                case 1 -> new DiscordianDate().format(removeInitialPlus(args[0]));
                case 3 -> new DiscordianDate(Integer.parseInt(args[0]),
                        Integer.parseInt(args[1]),
                        Integer.parseInt(args[2])).format(otherFmt);
                case 4 -> new DiscordianDate(Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3])).format(removeInitialPlus(args[0]));
                default -> throw new Exception("Wrong number of arguments!");
            });
        } catch (Throwable e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Usage: ddate [+fmt] [year month day]");
            System.exit(1);
        }
    }
}
