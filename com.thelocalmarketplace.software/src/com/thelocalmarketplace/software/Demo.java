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
import com.tdc.coin.Coin;
import com.jjjwelectronics.*;
import com.jjjwelectronics.scanner.*;
import com.jjjwelectronics.scale.*;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.Scanner;

/**
 * This class is a Demo of the functions created in software.
 * Is extra, not required for the assignment, but the professor recommended it.
 */
public class Demo {

    /**
     * Main function for the demo.
     */
    public static void main(String[] args) {

        // Initialize the station and scale
        SelfCheckoutStation station = new SelfCheckoutStation();
        SelfCheckoutStationSoftware software = new SelfCheckoutStationSoftware();
        ElectronicScale scale = new ElectronicScale();

        // Try catch block for any errors found.
        try {
            // Represents user input
            Scanner input = new Scanner(System.in);

            // Test startSession function
            software.startSession(input);

            // Create order and addItemViaBarcode scan objects
            Order order = new Order(scale);

            // Create barcodes for two items, an apple and banana
            Numeral[] list = {Numeral.valueOf((byte)5), Numeral.valueOf((byte)5)};
            Numeral[] list1 = {Numeral.valueOf((byte)7), Numeral.valueOf((byte)4)};
            Barcode barcodeOfApple = new Barcode(list);
            Barcode barcodeOfBanana = new Barcode(list1);

            // Create a string to hold user input.
            String itemInput;

            // Makes barcoded product for apple and banana
            BarcodedProduct apple = new BarcodedProduct(barcodeOfApple, "An apple", 5, 1.00 );
            BarcodedProduct banana = new BarcodedProduct(barcodeOfApple, "A banana", 3, 2.00 );

            ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcodeOfApple, apple);
            ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcodeOfBanana, banana);

            // Make an arraylist for the coins added
            ArrayList<Coin> coinsList = new ArrayList<>();

            // User interaction
            System.out.println("Enter '1' for Apple.");
            System.out.println("Enter '2' for Banana.");
            itemInput = input.nextLine();

            // If the user wants to add an Apple, this occurs
            if (itemInput.equals("1")) {
                // Tests addItemViaBarcodeScan function
                order.addItemViaBarcodeScan(barcodeOfApple);
                PaymentHandler paymentHandler = new PaymentHandler(station, order);

                System.out.println("About to print order.");
                System.out.println("order length is: " + order.getOrder().size());
                for(Item item: order.getOrder()){
                    System.out.println(item.toString());
                }


                System.out.println("The price of an apple is $5. You insert 5 $1 bills.");

                // Add 5 1 dollar coins to the coinsList
                for (int i = 0; i < 5; i++) {
                    Coin coin = new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(1));
                    coinsList.add(coin);
                }


                // Test processPaymentWithCoins function, if successful print out a receipt.
                if (paymentHandler.processPaymentWithCoins(coinsList)) {
                    System.out.println("Payment Successful!");
                    paymentHandler.receiptPrinter(order);
                } else
                    System.out.println("Unsuccessful Payment!");

            // If the user wants to add a Banana, this occurs
            } else if (itemInput.equals("2")) {
                // Tests addItemViaBarcodeScan function
                order.addItemViaBarcodeScan(barcodeOfBanana);
                PaymentHandler paymentHandler = new PaymentHandler(station, order);

                System.out.println("The price of a banana is $3. You insert 3 $1 bills.");

                // Add 5 1 dollar coins to the coinsList
                for (int i = 0; i < 3; i++) {
                    Coin coin = new Coin(Currency.getInstance(Locale.CANADA), BigDecimal.valueOf(1));
                    coinsList.add(coin);
                }

                // Test processPaymentWithCoins function, if successful print out a receipt.
                if (paymentHandler.processPaymentWithCoins(coinsList)) {
                    System.out.println("Payment Successful!");
                    paymentHandler.receiptPrinter(order);
                } else
                    System.out.println("Unsuccessful Payment!");
            }
            else {
                System.out.println("Unable to process input.");
            }
        } catch (Exception e) {
            System.out.println("Failed to initialize order: " + e.getMessage());
        }
    }
}
