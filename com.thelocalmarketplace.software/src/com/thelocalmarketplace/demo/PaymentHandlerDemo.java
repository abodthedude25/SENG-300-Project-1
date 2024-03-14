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

