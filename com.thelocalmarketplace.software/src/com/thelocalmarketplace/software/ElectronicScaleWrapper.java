package com.thelocalmarketplace.software;

import java.util.List;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.scale.ElectronicScaleListener;
import com.jjjwelectronics.scale.IElectronicScale;

import powerutility.PowerGrid;

public class ElectronicScaleWrapper implements IElectronicScale {
	    private BaggingAreaListener baggingAreaListener;

	    // Method to add a listener
	    public void addElectronicScaleListener1(BaggingAreaListener listener) {
	        this.baggingAreaListener = listener;
	    }

	    // Method to remove a listener
	    public void removeElectronicScaleListener() {
	        this.baggingAreaListener = null;
	    }

	    // Method to simulate mass change and notify the listener
	    public void theMassOnTheScaleHasChanged(Mass mass) {
	        if (baggingAreaListener != null) {
	            baggingAreaListener.theMassOnTheScaleHasChanged(this, mass);
	        }
	    }
	    
	    public void addElectronicScaleListener(BaggingAreaListener baggingAreaListener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isPluggedIn() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isPoweredUp() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void plugIn(PowerGrid grid) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unplug() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void turnOn() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void turnOff() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean deregister(ElectronicScaleListener listener) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void deregisterAll() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void register(ElectronicScaleListener listener) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void disable() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void enable() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isDisabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<ElectronicScaleListener> listeners() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Mass getMassLimit() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Mass getSensitivityLimit() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addAnItem(Item item) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeAnItem(Item item) {
			// TODO Auto-generated method stub
			
		}
}
