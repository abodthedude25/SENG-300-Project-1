package com.thelocalmarketplace.software;

public class SelfCheckoutStationSoftware {
	private boolean blocked = false;
	
	public void setStationBlock(boolean value) {
		this.blocked = value;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
}
