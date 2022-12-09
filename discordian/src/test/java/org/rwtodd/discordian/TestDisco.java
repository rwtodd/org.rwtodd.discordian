package org.rwtodd.discordian;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TestDisco {
    @ParameterizedTest
    @CsvSource(textBlock = """
        5, 0
        4, 1
        3, 2
        2, 3
        1, 4
        6, -1
        """)
    void testDaysToXDay(int julyDay, int daysTil) {
        var dtx = new DiscordianDate(8661,7,julyDay).getDaysTilXDay();
        assertEquals(daysTil, dtx);
    }

    @Test
    void testAnOldKnownChaoflux() {
        var d = new DiscordianDate(1956,2,19);
        assertTrue(d.isHolyDay());
        assertFalse(d.isTibs());
        assertEquals("Chaoflux", d.getHolyDayName());
        assertEquals(2449088, d.getDaysTilXDay());
        assertEquals("Setting Orange", d.getWeekday());
        assertEquals("SO", d.getShortWeekday());
        assertEquals("Chaos", d.getSeason());
        assertEquals("Chs", d.getShortSeason());
        assertEquals(50, d.getDayOfSeason());
    }

    @Test
    void testAnOldKnownNonHoliday() {
        var d = new DiscordianDate(1977,6,1);
        assertFalse(d.isHolyDay());
        assertFalse(d.isTibs());
        assertEquals("", d.getHolyDayName());
        assertEquals(2441315, d.getDaysTilXDay());
        assertEquals("Boomtime", d.getWeekday());
        assertEquals("BT", d.getShortWeekday());
        assertEquals("Confusion", d.getSeason());
        assertEquals("Cfn", d.getShortSeason());
        assertEquals(6, d.getDayOfSeason());
    }

}
