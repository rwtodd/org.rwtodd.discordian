package ddate

import groovy.transform.CompileStatic as CS
import org.rwtodd.discordian.DiscordianDate as DD
import java.time.LocalDate
import org.rwtodd.args.*

/**
 * The CLI runner for the ddate utility
 *
 * @author rwtodd
 */

@CS class Cmd {

    public static void main(String[] args) {
        final var todayFmt = "Today is %{%A, the %e day of %B%} in the YOLD %Y%N%nCelebrate %H"
        final var otherFmt = "%{%A, %B %d%}, %Y YOLD"
        final var today = LocalDate.now()
        final var date = new DateParam([ 'date', 'd'], today, "the date to display (default: today)")
        final var fmt = new StringParam(['foramt','f'], null, 'the format of the output')
        final var hlp = new FlagParam(['help'], 'print this help text')
        final Parser parser = [date,fmt,hlp]
        try {
            final var extras = parser.parse(args)
            if(hlp.value) { throw new Exception("help text requested") }
            if(date.value === today && extras.size() == 1) {
                date.process('date', extras[0])
            }
            if(fmt.value === null) {
                fmt.process("format",  (date.value == today) ? todayFmt : otherFmt)
            }
            System.out.println(new DD(date.value).format(fmt.value))
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.err.println('\nUsage: ddate [options] [date]')
            parser.printHelpText(System.err)
            System.err.println("""
Format Strings: (e.g.,  "Today is %{%A, the %E of %B%}!")
  %A  weekday        /  %a  weekday (short version)
  %B  season         /  %b  season (short version)
  %d  day of season  /  %e  ordinal day of season
  %Y  the Year of Our Lady of Discord
  %X  the number of days left until X-Day
 
  %H  name of the holy day, if it is one
  %N  directive to skip the rest of the format
      if today is not a holy day
 
  %{ ... %}  either announce Tibs Day, or format the
             interior string if it is not Tibs Day
          
  %n  newline        /  %t  tab\n\n
""")
        }
    }
}
