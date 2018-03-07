package com.dhims.androidutils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility functions for handling input & output.
 *
 * @author Dhimant Desai (dhimant1990@gmail.com)
 */

public class IoUtils {

    private static final int BUFFER_SIZE = 1024 * 8;

    private IoUtils() {
        // Utility class.
    }

    public static int copy(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                LogUtils.e(e.getMessage(), e);
            }
            try {
                in.close();
            } catch (IOException e) {
                LogUtils.e(e.getMessage(), e);
            }
        }
        return count;
    }

}