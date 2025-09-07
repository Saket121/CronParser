package com.cronparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FieldParser {

    public static List<Integer> parseField(String field, CronField cronField) {
        Set<Integer> values = new TreeSet<>();
        String[] parts = field.split(",");

        for (String part : parts) {
            if (part.contains("/")) {
                String[] stepParts = part.split("/");
                if (stepParts.length != 2) {
                    throw new IllegalArgumentException("Invalid step expression: " + part);
                }
                List<Integer> baseValues = parseBase(stepParts[0], cronField);
                int step = Integer.parseInt(stepParts[1]);
                if (step <= 0) {
                    throw new IllegalArgumentException("Step must be positive: " + step);
                }
                for (int i = 0; i < baseValues.size(); i += step) {
                    values.add(baseValues.get(i));
                }
            } else {
                values.addAll(parseBase(part, cronField));
            }
        }

        return new ArrayList<>(values);
    }

    private static List<Integer> parseBase(String base, CronField cronField) {
        List<Integer> values = new ArrayList<>();

        if (base.equals("*")) {
            for (int i = cronField.getMin(); i <= cronField.getMax(); i++) {
                values.add(i);
            }
        } else if (base.contains("-")) {
            String[] rangeParts = base.split("-");
            if (rangeParts.length != 2) {
                throw new IllegalArgumentException("Invalid range expression: " + base);
            }
            int start = Integer.parseInt(rangeParts[0]);
            int end = Integer.parseInt(rangeParts[1]);
            validateRange(start, end, cronField);
            for (int i = start; i <= end; i++) {
                values.add(i);
            }
        } else {
            int value = Integer.parseInt(base);
            validateValue(value, cronField);
            values.add(value);
        }

        return values;
    }

    private static void validateValue(int value, CronField cronField) {
        if (value < cronField.getMin() || value > cronField.getMax()) {
            throw new IllegalArgumentException(
                    String.format("Value %d out of range for %s [%d, %d]",
                            value, cronField.getName(), cronField.getMin(), cronField.getMax()));
        }
    }

    private static void validateRange(int start, int end, CronField cronField) {
        validateValue(start, cronField);
        validateValue(end, cronField);
        if (start > end) {
            throw new IllegalArgumentException(
                    String.format("Start (%d) cannot be greater than end (%d) for %s",
                            start, end, cronField.getName()));
        }
    }
}