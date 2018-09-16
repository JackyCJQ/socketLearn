package com.jacky.netty.Time;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TiemUnit {
    private final long value;

    public TiemUnit() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public TiemUnit(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000).toString();
    }
}
