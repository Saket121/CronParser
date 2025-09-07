package com.cronparser;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronParserTest {
    @Test
    public void testAsterisk() {
        CronExpression cron = new CronExpression("* * * * * /usr/bin/find");
        assertEquals(Arrays.asList(
                        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
                        15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
                        30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44,
                        45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59),
                cron.getMinutes());
    }

    @Test
    public void testStep() {
        CronExpression cron = new CronExpression("*/15 * * * * /usr/bin/find");
        assertEquals(Arrays.asList(0, 15, 30, 45), cron.getMinutes());
    }

    @Test
    public void testRange() {
        CronExpression cron = new CronExpression("0 1-5 * * * /usr/bin/find");
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), cron.getHours());
    }

    @Test
    public void testList() {
        CronExpression cron = new CronExpression("0 0 1,15 * * /usr/bin/find");
        assertEquals(Arrays.asList(1, 15), cron.getDaysOfMonth());
    }

    @Test
    public void testMixed() {
        CronExpression cron = new CronExpression("*/15 0 1,15 * 1-5 /usr/bin/find");
        assertEquals(Arrays.asList(0, 15, 30, 45), cron.getMinutes());
        assertEquals(Arrays.asList(0), cron.getHours());
        assertEquals(Arrays.asList(1, 15), cron.getDaysOfMonth());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), cron.getMonths());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), cron.getDaysOfWeek());
        assertEquals("/usr/bin/find", cron.getCommand());
    }

    @Test
    public void testInvalidCronString() {
        assertThrows(IllegalArgumentException.class, () -> {
            new CronExpression("*/15 0 1,15 * /usr/bin/find");
        });
    }
}
