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
import com.thelocalmarketplace.software.*;
import com.jjjwelectronics.*;
import com.jjjwelectronics.scanner.*;
import com.jjjwelectronics.scale.*;
import com.thelocalmarketplace.hardware.*;

public class Demo {

    public static void main(String[] args) {
    
        SelfCheckoutStation station = new SelfCheckoutStation();
        SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware();
        ElectronicScaleWrapper scale = new ElectronicScaleWrapper();
        
        try {
            software.startSession();

            Order order = new Order(scale); 
            AddItemViaBarcodeScan scannerListener = new AddItemViaBarcodeScan(order);

            Numeral[] list = {Numeral.valueOf((byte)5), Numeral.valueOf((byte)5)};
            Numeral[] list1 = {Numeral.valueOf((byte)7), Numeral.valueOf((byte)4)};
            Barcode barcodeOfApple = new Barcode(list);
            Barcode barcodeOfBanana = new Barcode(list1);

            System.out.println("Scanning items...");
            scannerListener.aBarcodeHasBeenScanned(null, barcodeOfApple);
            scannerListener.aBarcodeHasBeenScanned(null, barcodeOfBanana);

            BaggingAreaListener baggingAreaListener = new BaggingAreaListener(order);
            scale.addElectronicScaleListener1(baggingAreaListener); 

            System.out.println("Adding item to the scale...");
            scale.theMassOnTheScaleHasChanged(new Mass(100)); 
            scale.theMassOnTheScaleHasChanged(new Mass(200)); 

            // Complete the checkout process
            System.out.println("Finalizing order...");
            System.out.println("Total price: $" + order.getTotalPrice());
            System.out.println("Total weight: " + order.getTotalWeightInGrams() + " grams");

            System.out.println("Payment received. Thank you for shopping with us!");
        } catch (Exception e) {
            System.out.println("Failed to initialize order: " + e.getMessage());
        }
    }
}
