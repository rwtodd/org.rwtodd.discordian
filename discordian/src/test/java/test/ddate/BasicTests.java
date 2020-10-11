package test.ddate;

import org.rwtodd.discordian.DiscordianDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tests of the org.rwtodd.discordian module.
 *
 * @author rwtodd
 */
public class BasicTests {

    @Test
    void testDatesCloseToXDay() {
        assertEquals(0, new DiscordianDate(8661, 7, 5).daysTilXDay());
        assertEquals(1, new DiscordianDate(8661, 7, 4).daysTilXDay());
        assertEquals(2, new DiscordianDate(8661, 7, 3).daysTilXDay());
        assertEquals(3, new DiscordianDate(8661, 7, 2).daysTilXDay());
        assertEquals(4, new DiscordianDate(8661, 7, 1).daysTilXDay());
    }

    @Test
    void testDayAfterXDay() {
        assertEquals(-1, new DiscordianDate(8661, 7, 6).daysTilXDay());
    }

    @Test
    void testAnOldChaoflux() {
        final var d = new DiscordianDate(1956, 2, 19);
        assertTrue(d.isHolyDay());
        assertFalse(d.isTibs());
        assertEquals("Chaoflux", d.getHolyDay());
        assertEquals(2449088, d.daysTilXDay());
        assertEquals("Setting Orange", d.getWeekday());
        assertEquals("SO", d.getShortWeekday());
        assertEquals("Chaos", d.getSeason());
        assertEquals("Chs", d.getShortSeason());
        assertEquals(50, d.getDayOfSeason());
    }
    
    @Test
    void testAnOldDate() {
        final var d = new DiscordianDate(1977, 6, 1);
        assertFalse(d.isHolyDay());
        assertFalse(d.isTibs());
        assertEquals("", d.getHolyDay());
        assertEquals(2441315, d.daysTilXDay());
        assertEquals("Boomtime", d.getWeekday());
        assertEquals("BT", d.getShortWeekday());
        assertEquals("Confusion", d.getSeason());
        assertEquals("Cfn", d.getShortSeason());
        assertEquals(6, d.getDayOfSeason());        
    }
}
