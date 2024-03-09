/**
 * Duncan McKay (UCID: 30177857)
 */

package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Formatter.BigDecimalLayoutForm;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.ComponentFailure;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.PLUCodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.SelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;

/**
 * Manages the payment process with coins for a self-checkout system.
 * Handles coin insertion, validation, and change dispensing.
 */
public class PaymentHandler extends SelfCheckoutStation {

	public BigDecimal amountSpent;
	private BigDecimal changeRemaining = BigDecimal.ZERO;
	private BigDecimal totalCost = BigDecimal.ZERO;
	private SelfCheckoutStation checkoutSystem = null;
	private ArrayList<Product> allProducts;

	public PaymentHandler(SelfCheckoutStation station, ArrayList<Product> allProducts) {
		if (station == null)
			throw new NullPointerException("No argument may be null.");
		this.checkoutSystem = station;
		this.allProducts = allProducts;
	}

	/**
	 * will be used to help with Signaling to the Customer the updated amount
	 * due after the insertion of each coin.
	 * 
	 * @return money left to pay
	 */
	public BigDecimal getChangeRemaining() {
		return this.changeRemaining;
	}

	/**
	 * Processes payment using coins inserted by the customer.
	 * 
	 * @param coinsList List of coins inserted by the customer.
	 * @return true if payment is successful, false otherwise.
	 * @throws DisabledException        If the coin slot is disabled.
	 * @throws CashOverloadException    If the cash storage is overloaded.
	 * @throws NoCashAvailableException If no cash is available for dispensing
	 *                                  change.
	 */
	public boolean processPaymentWithCoins(ArrayList<Coin> coinsList)
			throws DisabledException, CashOverloadException, NoCashAvailableException {
		if (coinsList == null)
			throw new NullPointerException("coinsList cannot be null."); // Check for null parameters.
		BigDecimal value = new BigDecimal("0");
		for (Coin coin : coinsList) { // Calculate the total value of coins inserted.
			value = value.add(coin.getValue());
		}

		this.amountSpent = value;
		this.changeRemaining = value.subtract(this.totalCost);

		for (Coin coin : coinsList) { // Accept each coin inserted by the customer.
			// Assume coins have already been checked before adding to coin list, done in CoinAdder insertCoin method
			value = value.subtract(coin.getValue());
		}

		if (value.compareTo(this.totalCost) < 0)
			return false; // Return false if the total value of valid coins is less than the total cost.

		this.amountSpent = this.totalCost;

		// Return true if accurate change is dispensed.
		if (value.compareTo(this.totalCost) > 0) {
			BigDecimal changeValue = value.subtract(this.totalCost);
			return dispenseAccurateChange(changeValue);
		}
		return true;
	}



	/**
	 * Dispenses the correct amount of change to the customer and gives them the
	 * choice to print a receipt.
	 * 
	 * Implements change dispensing logic using available coin denominations.
	 * 
	 * @param changeValue The amount of change to be dispensed.
	 * @return true if correct change is dispensed, false otherwise.
	 * @throws DisabledException        If the coin slot is disabled.
	 * @throws CashOverloadException    If the cash storage is overloaded.
	 * @throws NoCashAvailableException If no cash is available for dispensing
	 *                                  change.
	 */
	public boolean dispenseAccurateChange(BigDecimal changeValue)
			throws DisabledException, CashOverloadException, NoCashAvailableException {
		BigDecimal amountDispensed = new BigDecimal("0.0");
		BigDecimal remainingAmount = changeValue;
		List<BigDecimal> coinDenominations = this.checkoutSystem.coinDenominations;
		Collections.sort(coinDenominations);
		Collections.reverse(coinDenominations);

		if (remainingAmount.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal lowestCoin = coinDenominations.get(coinDenominations.size() - 1);
			if (remainingAmount.compareTo(lowestCoin) < 0) {
				this.checkoutSystem.coinDispensers.get(lowestCoin).emit();
				amountDispensed = changeValue;
				remainingAmount = BigDecimal.ZERO;
			}
			for (int i = 0; i < coinDenominations.size(); i++) {
				BigDecimal val = coinDenominations.get(i);
				while (remainingAmount.compareTo(val) >= 0 && this.checkoutSystem.coinDispensers.get(val).size() > 0) {
					this.checkoutSystem.coinDispensers.get(val).emit();
					amountDispensed = amountDispensed.add(val);
					remainingAmount = remainingAmount.subtract(val);
					i = coinDenominations.size();
				}
			}
		}

		if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
			try (Scanner receiptRequest = new Scanner(System.in)) {
				System.out.println("Would you like a receipt?");

				String receiptAnswer = receiptRequest.nextLine();

				while (receiptAnswer.compareToIgnoreCase("yes") != 0 || receiptAnswer.compareToIgnoreCase("no") != 0) {
					System.out.println("Sorry, that input is not acceptable. Try again.");
					System.out.println("Would you like a receipt?");
					receiptAnswer = receiptRequest.nextLine();
				}
				if (receiptAnswer.compareToIgnoreCase("yes") == 0) {
					receiptPrinter();
					System.out.println("Thank you for your time. We hope to see you again!");
					return true;

				}

				if (receiptAnswer.compareToIgnoreCase("no") == 0) {
					System.out.println("No worries. Thank you for your time. We hope to see you again!");
					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Prints a receipt for the customer, with all the products' info, price, the
	 * total cost, total amount paid, and change due.
	 */

	private void receiptPrinter() throws outOfPaperException, outOfInkException {

		ArrayList<String> receiptItems = new ArrayList<String>();
		int paperSpaceCounter = 100;
		int inkCounter = 100;

		for (int i = 0; i < allProducts.size(); i++) {
			String productDescription;
			Product p = allProducts.get(i);

			if (p instanceof BarcodedProduct) {
				productDescription = ((BarcodedProduct) p).getDescription();
				long price = (allProducts.get(i).getPrice());
				receiptItems.add(productDescription + "$" + String.format("%.2f", price));
			}

			if (p instanceof PLUCodedProduct) {
				productDescription = ((PLUCodedProduct) p).getDescription();
				long price = (allProducts.get(i).getPrice());
				receiptItems.add(productDescription + "$" + String.format("%.2f", price));
			}

		}

		BigDecimal purchaseValue = totalCost;
		BigDecimal amountPaid = amountSpent;
		BigDecimal changeDue = changeRemaining;

		receiptItems.add("Total: $" + String.format("%.2f", purchaseValue));
		receiptItems.add("Paid: $" + String.format("%.2f", amountPaid));
		receiptItems.add("Change: $" + String.format("%.2f", changeDue));

		for (int i = 0; i < receiptItems.size(); i++) {
			System.out.println("\n");
			paperSpaceCounter -= 5;

			if (paperSpaceCounter <= 0) {
				checkoutSystem = null;
				throw new outOfPaperException("Duplicate receipt must be printed. This station needs maintenance.");
				return;
			}

			System.out.println(receiptItems.get(i));
			inkCounter -= 5;
			paperSpaceCounter -= 5;

			if (inkCounter <= 0) {
				checkoutSystem = null;
				throw new outOfInkException("Duplicate receipt must be printed. This station needs maintenance.");
				return;
			}

			if (paperSpaceCounter <= 0) {
				checkoutSystem = null;
				throw new outOfPaperException("Duplicate receipt must be printed. This station needs maintenance.");
				return;
			}

			System.out.println("\n");
			paperSpaceCounter -= 5;

			if (paperSpaceCounter <= 0) {
				checkoutSystem = null;
				throw new outOfPaperException("Duplicate receipt must be printed. This station needs maintenance.");
				return;
			}
		}
	}

	/**
	 * Loads coins into the coin dispensers for change.
	 * 
	 * @param coins Coins to be loaded into the dispensers.
	 * @throws CashOverloadException If the coin dispensers are overloaded with
	 *                               coins.
	 */
	public void loadCoinDispenser(Coin... coins) throws CashOverloadException {
		if (coins == null) {
			throw new NullPointerSimulationException("coins instance cannot be null.");
		}
		for (Coin c : coins) {
			if (c == null) {
				throw new NullPointerSimulationException("coin instance cannot be null.");
			}
			BigDecimal v = c.getValue();
			try {
				this.checkoutSystem.coinDispensers.get(v).load(c);
			} catch (CashOverloadException e) {
				throw new CashOverloadException("Coin Dispenser for coins of value " + v.doubleValue() + " is full.");
			} catch (SimulationException e) {
				throw new ComponentFailure("This coin type does not exist.");
			}
		}
	}

	/**
	 * Empties the coin storage unit.
	 */
	public void emptyCoinStorage() {
		this.checkoutSystem.coinStorage.unload();

	}

}
