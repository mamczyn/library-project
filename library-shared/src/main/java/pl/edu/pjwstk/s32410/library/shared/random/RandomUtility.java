package pl.edu.pjwstk.s32410.library.shared.random;

import java.util.Random;

public class RandomUtility {
	private static Random random = new Random();
	
	public static int number(int min, int max) {
		return random.nextInt(min, max);
	}
}
