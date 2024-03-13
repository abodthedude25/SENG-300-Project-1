/*
 * Start Session Demo
 * 
 * import lol
 */


package com.thelocalmarketplace.software;

public class SelfCheckoutStationSoftware {
	private boolean blocked = false;
	
	public void setStationBlock(boolean value) {
		this.blocked = value;
	}
	
	public boolean getStationBlock() {
		return this.blocked;
	}
}
