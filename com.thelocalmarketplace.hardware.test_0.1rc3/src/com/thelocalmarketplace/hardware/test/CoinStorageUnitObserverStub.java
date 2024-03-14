package com.thelocalmarketplace.hardware.test;

import com.tdc.IComponent;
import com.tdc.IComponentObserver;
import com.tdc.coin.AbstractCoinStorageUnit;
import com.tdc.coin.CoinStorageUnitObserver;

class CoinStorageUnitObserverStub implements CoinStorageUnitObserver {
	boolean isCoinsFull = false;
	boolean isCoinAdded = false;
	boolean isCoinsLoaded = false;
	boolean isCoinsUnloaded = false;
	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		return;
	}
	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		return;
	}
	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		return;
	}
	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		return;
	}
	@Override
	public void coinsFull(AbstractCoinStorageUnit unit) {
		isCoinsFull = true;
		return;
	}
	@Override
	public void coinAdded(AbstractCoinStorageUnit unit) {
		isCoinAdded = true;
		return;
	}
	@Override
	public void coinsLoaded(AbstractCoinStorageUnit unit) {
		isCoinsLoaded = true;
		return;
	}
	@Override
	public void coinsUnloaded(AbstractCoinStorageUnit unit) {
		isCoinsUnloaded = true;
		return;
	}
	
}