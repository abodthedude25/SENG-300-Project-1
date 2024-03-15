package com.thelocalmarketplace.demo;
import com.thelocalmarketplace.software.*;
import com.jjjwelectronics.*;
import com.jjjwelectronics.scanner.*;
import com.jjjwelectronics.scale.*;
import com.thelocalmarketplace.hardware.*;

public class Demo {

    public static void main(String[] args) {
    
        SelfCheckoutStation station = new SelfCheckoutStation();
        ElectronicScaleWrapper scale = new ElectronicScaleWrapper(); 
        try {
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
        } catch (OverloadedDevice e) {
            System.out.println("Failed to initialize order: " + e.getMessage());
        }
    }
}
