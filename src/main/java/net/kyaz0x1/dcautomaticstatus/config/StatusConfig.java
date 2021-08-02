package net.kyaz0x1.dcautomaticstatus.config;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class StatusConfig {

    public static int UPDATE_TIME = 15;
    public static TimeUnit TIME_TYPE = TimeUnit.SECONDS;

    public static int UPDATE_TIME_BEFORE = 15;
    public static TimeUnit TIME_TYPE_BEFORE = TimeUnit.SECONDS;

    public static final AtomicInteger TOTAL_UPDATES = new AtomicInteger();


}