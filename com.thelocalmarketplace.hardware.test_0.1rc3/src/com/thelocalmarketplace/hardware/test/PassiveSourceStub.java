package com.thelocalmarketplace.hardware.test;

import com.tdc.CashOverloadException;
import com.tdc.ComponentFailure;
import com.tdc.DisabledException;
import com.tdc.PassiveSource;
import com.tdc.coin.Coin;

public class PassiveSourceStub implements PassiveSource<Coin> {

	@Override
	public void reject(Coin cash) throws CashOverloadException, DisabledException, ComponentFailure {
		//NOTE: we returned void in the reject function of our PassiveSourceStub instead of throwing a 
		// ComponentFailure exception, as expected from the reject function in the javadocs, due to 
		//gaining a higher coverage in the CoinDispenser Class.
//		throw new ComponentFailure();
		return;
	}

}
