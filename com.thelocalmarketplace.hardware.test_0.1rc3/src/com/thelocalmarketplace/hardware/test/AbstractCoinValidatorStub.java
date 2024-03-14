//Abdelrahman Abbas (UCID: 30110374)

package com.thelocalmarketplace.hardware.test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;

import com.tdc.Sink;
import com.tdc.coin.AbstractCoinValidator;
import com.tdc.coin.Coin;

public class AbstractCoinValidatorStub extends AbstractCoinValidator {

	protected AbstractCoinValidatorStub(Currency currency, Map<BigDecimal, Sink<Coin>> standardSinks) {
		super(currency, standardSinks);
		// TODO Auto-generated constructor stub
	}
}
