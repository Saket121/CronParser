package com.cronparser;

import java.util.List;

public class CronExpression {
    private final List<Integer> minutes;
    private final List<Integer> hours;
    private final List<Integer> daysOfMonth;
    private final List<Integer> months;
    private final List<Integer> daysOfWeek;
    private final String command;

    public CronExpression(String cronString) {
        String[] parts = cronString.split("\\s+", 6);
        if (parts.length < 6) {
            throw new IllegalArgumentException("Cron string must have at least 6 fields");
        }

        this.minutes = FieldParser.parseField(parts[0], CronField.MINUTE);
        this.hours = FieldParser.parseField(parts[1], CronField.HOUR);
        this.daysOfMonth = FieldParser.parseField(parts[2], CronField.DAY_OF_MONTH);
        this.months = FieldParser.parseField(parts[3], CronField.MONTH);
        this.daysOfWeek = FieldParser.parseField(parts[4], CronField.DAY_OF_WEEK);
        this.command = parts[5];
    }

    public List<Integer> getMinutes() {
        return minutes;
    }

    public List<Integer> getHours() {
        return hours;
    }

    public List<Integer> getDaysOfMonth() {
        return daysOfMonth;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getCommand() {
        return command;
    }

    public String format() {
        return String.format("%-14s%s\n", "minute", listToString(minutes)) +
                String.format("%-14s%s\n", "hour", listToString(hours)) +
                String.format("%-14s%s\n", "day of month", listToString(daysOfMonth)) +
                String.format("%-14s%s\n", "month", listToString(months)) +
                String.format("%-14s%s\n", "day of week", listToString(daysOfWeek)) +
                String.format("%-14s%s", "command", command);
    }

    private String listToString(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) sb.append(" ");
            sb.append(list.get(i));
        }
        return sb.toString();
    }
}