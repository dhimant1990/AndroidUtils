package com.dhims.androidutils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creates unique ids to identify the notifications.
 *
 * @author Dhimant Desai (dhimant1990@gmail.com)
 */

public final class NotificationIdFactory {
    private static final AtomicInteger mNextId = new AtomicInteger(0);

    public static int create() {
        return mNextId.incrementAndGet();
    }

    private NotificationIdFactory() {
    }
}