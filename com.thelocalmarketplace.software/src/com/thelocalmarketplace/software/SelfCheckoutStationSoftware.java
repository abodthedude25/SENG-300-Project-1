package com.thelocalmarketplace.software;

public class SelfCheckoutStationSoftware {
	private static boolean blocked = false;
	
	public static void setStationBlock(boolean value) {
		blocked = value;
	}
	
	public static boolean isBlocked() {
		return blocked;
	}
}
