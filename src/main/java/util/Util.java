package util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by root on 1/1/17.
 */
public class Util {

    public static long randLongBetween(long start, long end) {
        return ThreadLocalRandom.current().nextLong(start, end);
    }

    public static int randIntBetween(int start, int end) {
        return ThreadLocalRandom.current().nextInt(start, end);
    }

    public static String randomString(int len) {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(ThreadLocalRandom.current().nextInt(AB.length())));
        return sb.toString();
    }
}
