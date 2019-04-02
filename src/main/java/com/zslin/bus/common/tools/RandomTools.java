package com.zslin.bus.common.tools;

import java.util.Random;

/**
 * 随机工具类
 * @author 钟述林
 *
 */
public class RandomTools {

	private static Random randGen = null;
	private static char[] numbersAndLetters = null;
	
	/**
	 * 生成长度为length的随机字符串
	 * @param length 长度
	 * @return
	 */
	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char [] randBuffer = new char[length];
		for (int i=0; i<randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}
	
	public static String genTimeNo() {
		return genTimeNo(5, 5);
	}
	
	/** 生成随机6位数 **/
	public static String genCode() {
		Random r = new Random();
		int val = r.nextInt(999999);
		while(val<100000) {
			val = r.nextInt(999999);
		}
		return String.valueOf(val);
	}
	
	/** 生成随机4位数 */
	public static String genCode4() {
		Random r = new Random();
		int val = r.nextInt(9999);
		while(val<1000) {
			val = r.nextInt(9999);
		}
		return String.valueOf(val);
	}
	
	public static String genTimeNo(int strLen, int timeLen) {
		String timeStr = System.currentTimeMillis()+"";
		return randomString(strLen)+timeStr.substring(timeStr.length()-timeLen, timeStr.length());
	}
}
