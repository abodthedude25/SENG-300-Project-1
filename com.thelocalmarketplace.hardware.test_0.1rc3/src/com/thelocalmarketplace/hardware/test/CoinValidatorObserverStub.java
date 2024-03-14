package com.thelocalmarketplace.hardware.test;
import java.math.BigDecimal;
import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.AbstractCoinValidator;
import com.tdc.coin.CoinValidatorObserver;
public class CoinValidatorObserverStub implements CoinValidatorObserver{
	
	boolean isEnabled = false;
	boolean isDisabled = false;
	boolean isTurnedOn = false;
	boolean isTurnedOff = false;
	boolean isValidCoinDetected = false;
	boolean isInvalidCoinDetected = false;
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		isEnabled = true;
	}
	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		isDisabled = true;
	}
	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		isTurnedOn = true;
	}
	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		isTurnedOff = true;
	}
	@Override
	public void validCoinDetected(AbstractCoinValidator validator, BigDecimal value) {
		isValidCoinDetected = true;
	}
	@Override
	public void invalidCoinDetected(AbstractCoinValidator validator) {
		isInvalidCoinDetected = true;
	}
}
