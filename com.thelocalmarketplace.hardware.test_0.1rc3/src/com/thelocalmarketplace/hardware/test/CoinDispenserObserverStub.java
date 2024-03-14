package com.thelocalmarketplace.hardware.test;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserObserver;
import com.tdc.coin.ICoinDispenser;

public class CoinDispenserObserverStub implements CoinDispenserObserver {

	boolean isCoinsEmpty = false;

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		return;
		
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		return;
		
	}

	@Override
	public void coinsFull(ICoinDispenser dispenser) {
		return;
		
	}

	@Override
	public void coinsEmpty(ICoinDispenser dispenser) {
		isCoinsEmpty = true;
		return;
		
	}

	@Override
	public void coinAdded(ICoinDispenser dispenser, Coin coin) {
		return;
		
	}

	@Override
	public void coinRemoved(ICoinDispenser dispenser, Coin coin) {
		return;
		
	}

	@Override
	public void coinsLoaded(ICoinDispenser dispenser, Coin... coins) {
		return;
		
	}

	@Override
	public void coinsUnloaded(ICoinDispenser dispenser, Coin... coins) {
		return;
		
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		return;
		
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		return;
		
	}

}
