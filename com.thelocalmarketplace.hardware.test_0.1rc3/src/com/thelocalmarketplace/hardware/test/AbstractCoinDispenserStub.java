package com.thelocalmarketplace.hardware.test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.PassiveSource;
import com.tdc.coin.AbstractCoinDispenser;
import com.tdc.coin.Coin;

public class AbstractCoinDispenserStub extends AbstractCoinDispenser {
	public PassiveSource<Coin> source;

	public AbstractCoinDispenserStub(int capacity) {
		super(capacity);
		// TODO Auto-generated constructor stub
	}
}
