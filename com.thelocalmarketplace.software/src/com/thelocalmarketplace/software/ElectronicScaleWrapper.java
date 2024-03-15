/**
 * Yotam Rojnov (UCID: 30173949)
 * Duncan McKay (UCID: 30177857)
 * Mahfuz Alam (UCID:30142265)
 * Luis Trigueros Granillo (UCID: 30167989)
 * Lilia Skumatova (UCID: 30187339)
 * Abdelrahman Abbas (UCID: 30110374)
 * Talaal Irtija (UCID: 30169780)
 * Alejandro Cardona (UCID: 30178941)
 * Alexandre Duteau (UCID: 30192082)
 * Grace Johnson (UCID: 30149693)
 * Abil Momin (UCID: 30154771)
 * Tara Ghasemi M. Rad (UCID: 30171212)
 * Izabella Mawani (UCID: 30179738)
 * Binish Khalid (UCID: 30061367)
 * Fatima Khalid (UCID: 30140757)
 * Lucas Kasdorf (UCID: 30173922)
 * Emily Garcia-Volk (UCID: 30140791)
 * Yuinikoru Futamata (UCID: 30173228)
 * Joseph Tandyo (UCID: 30182561)
 * Syed Haider (UCID: 30143096)
 * Nami Marwah (UCID: 30178528)
 */

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
