package net.tkdid1000.armiworld.utils;

import java.util.Random;

public class NumberUtil {

	public static int getRandomNumberRange(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}
}