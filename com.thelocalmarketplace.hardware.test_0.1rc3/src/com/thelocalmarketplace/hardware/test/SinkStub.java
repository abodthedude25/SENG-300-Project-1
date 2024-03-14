package com.thelocalmarketplace.hardware.test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.Sink;
import com.tdc.coin.Coin;

public class SinkStub implements Sink<Coin>{

	@Override
	public boolean hasSpace() {
		return true;
	}

	@Override
	public void receive(Coin cash) throws CashOverloadException, DisabledException {
		return;
	}

}
