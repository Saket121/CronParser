package com.cronparser;

public class CronParser {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java com.cronparser.CronParser \"<cron_string>\"");
            System.exit(1);
        }

        try {
            CronExpression cron = new CronExpression(args[0]);
            System.out.println(cron.format());
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing cron string: " + e.getMessage());
            System.exit(1);
        }
    }
}