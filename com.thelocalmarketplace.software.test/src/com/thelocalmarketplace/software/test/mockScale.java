package com.thelocalmarketplace.software.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.Mass.MassDifference;
import com.jjjwelectronics.scale.ElectronicScale;
import com.jjjwelectronics.scale.ElectronicScaleListener;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import powerutility.NoPowerException;

public class mockScale extends ElectronicScale {
	private List<Item> items = new ArrayList<>();
    private Mass currentMass = Mass.ZERO;
    private Mass massLimit;
    private Mass sensitivityLimit;



    @Override
    public Mass getMassLimit() {
        return massLimit;
    }

    @Override
    public Mass getSensitivityLimit() {
        return sensitivityLimit;
    }

    @Override
    public synchronized void addAnItem(Item item) {
        currentMass = currentMass.sum(item.getMass());
        items.add(item);
        
    }

    @Override
    public synchronized void removeAnItem(Item item) {
        items.remove(item);
        currentMass = calculateCurrentMass();
    }

    private Mass calculateCurrentMass() {
        Mass newMass = Mass.ZERO;
        for (Item item : items) {
            newMass = newMass.sum(item.getMass());
        }
        return newMass;
    }
    @Override
    public Mass getCurrentMassOnTheScale() {
        return currentMass;
    }
}
