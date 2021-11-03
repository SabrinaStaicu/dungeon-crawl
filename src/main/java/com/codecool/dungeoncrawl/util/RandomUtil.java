package com.codecool.dungeoncrawl.util;

import java.util.Random;

public class RandomUtil {
    private static final Random RANDOM = new Random();

    public static int nextInt(int upper) {
        return RANDOM.nextInt(upper);
    }

    public static int nextInt(int lower, int upper) {
        return lower + nextInt(upper - lower);
    }
}
