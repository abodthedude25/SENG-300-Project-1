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

import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.software.PaymentHandler;

import java.util.ArrayList;

public class PaymentHandlerDemo {
    public static void main(String[] args) {
        // Create a list of products for demonstration
        ArrayList<Product> allProducts = new ArrayList<>();
        // Add some products (BarcodedProduct or PLUCodedProduct) to the list

        // Create a mock SelfCheckoutStation
        SelfCheckoutStation selfCheckoutStation = new SelfCheckoutStation();

        // Create a payment handler
        PaymentHandler paymentHandler = new PaymentHandler(selfCheckoutStation, allProducts);

        // Simulate coin insertion by the customer
        ArrayList<Coin> coinsList = new ArrayList<>();
        // Add some coins to the list

        try {
            // Process payment with the inserted coins
            boolean paymentSuccessful = paymentHandler.processPaymentWithCoins(coinsList);
            if (paymentSuccessful) {
                System.out.println("Payment successful!");
            } else {
                System.out.println("Payment failed!");
            }
        } catch (Exception e) {
            System.out.println("Payment failed: " + e.getMessage());
        }

        }
  }

