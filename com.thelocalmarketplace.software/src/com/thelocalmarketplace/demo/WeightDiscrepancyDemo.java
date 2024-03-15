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

package com.thelocalmarketplace.demo;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.WeightDiscrepancy;
import com.jjjwelectronics.OverloadedDevice;

import java.util.ArrayList;

public class WeightDiscrepancyDemo {
    public static void main(String[] args) {
        // Create a list of items for demonstration
        ArrayList<Item> items = new ArrayList<>();
        // Add some items to the list

        // Create a mock ElectronicScale
        ElectronicScale scale = new ElectronicScale();

        // Create a mock Order
        Order order = new Order(items);

        try {
            // Create a WeightDiscrepancy instance
            WeightDiscrepancy weightDiscrepancy = new WeightDiscrepancy(order, scale);

            // Update the mass on the scale
            weightDiscrepancy.updateMass();

            // Check for discrepancies
            weightDiscrepancy.checkDiscrepancy();

            // Check if an item has been removed
            boolean itemRemoved = weightDiscrepancy.checkRemoval();
            System.out.println("Item removed: " + itemRemoved);

            // Check if an item has been added to the bagging area
            boolean itemAddedToBaggingArea = weightDiscrepancy.checkBaggageAddition();
            System.out.println("Item added to bagging area: " + itemAddedToBaggingArea);

            // Check if there's a weight change
            boolean weightChanged = weightDiscrepancy.checkWeightChange();
            System.out.println("Weight changed: " + weightChanged);

            // Simulate unblocking
            WeightDiscrepancy.unBlock();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
