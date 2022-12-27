package ru.salon.util;

import java.util.HashMap;
import java.util.Map;

public class Time {
    public static Map<String, String> time;
    static {
        time = new HashMap<>();
        time.put("08-00", Status.Free.name());
        time.put("09-00", Status.Free.name());
        time.put("10-00", Status.Free.name());
        time.put("11-00", Status.Free.name());
        time.put("12-00", Status.Free.name());
        time.put("13-00", Status.Free.name());
        time.put("14-00", Status.Free.name());
        time.put("15-00", Status.Free.name());
        time.put("16-00", Status.Free.name());
        time.put("17-00", Status.Free.name());
        time.put("18-00", Status.Free.name());
    }

}
