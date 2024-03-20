package com.thelocalmarketplace.software;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteInsertionSlot;
import com.tdc.banknote.BanknoteValidator;

import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;

//figure out how to use gold?

import com.jjjwelectronics.EmptyDevice;

import com.jjjwelectronics.OverloadedDevice;

import com.jjjwelectronics.printer.*;


public class PayWithBanknoteUpdated {

	private BanknoteInsertionSlot insertionSlot;
	private BanknoteValidator banknoteValidator;
	public Currency currency;
	//public BigDecimal amountInserted;
	public BigDecimal changeRemaining;
	// to get the total cost of all banknotes that are inserted
	private BigDecimal valueOfAllBanknotes = new BigDecimal("0");
	public BigDecimal totalCost;
	//not sure
	private SelfCheckoutStationBronze checkoutSystem = null;
	//the type of bills that are accepted
	private BigDecimal[] denominations = { new BigDecimal(500), new BigDecimal(200), new BigDecimal(100),
			new BigDecimal(50), new BigDecimal(20), new BigDecimal(10), new BigDecimal(5) };
	
	private ReceiptPrinterGold gold;

	//remove this once part of the paymentHandler class
	public PayWithBanknoteUpdated(BigDecimal totalAmount) {

		this.totalCost = totalAmount;
		// create a new instance of insertionSlot
		insertionSlot = new BanknoteInsertionSlot();
		currency = Currency.getInstance("CAD");
		// set up the validator so that it's cad and the bills are from 5 to 500
		// The issue is how do we check if it's valid!!
		banknoteValidator = new BanknoteValidator(currency, denominations);
		
	}
	/**
	 * Update the totalCost if not enough money is giving
	 * as in constructor we only create instance of PayWithBanknote once 
	 * If adding to paymentHandler can remove later
	 * @param totalAmount
	 */
	public void setTotalCost(BigDecimal totalAmount) {
		this.totalCost = totalAmount;
	}

	/**
	 * will be used to help with Signaling to the Customer the updated amount due
	 * after the insertion of each coin.
	 *
	 * @return money left to pay
	 */
	public BigDecimal getCostRemaining() {
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
	 * @throws OutOfInkException
	 * @throws OutOfPaperException
	 * @throws OverloadedDevice
	 * @throws EmptyDevice
	 */
	public boolean processPaymentWithBanknotes(ArrayList<Banknote> BanknotesList)
			throws DisabledException, CashOverloadException, NoCashAvailableException, EmptyDevice, OverloadedDevice {
		
		// first check if parameter is null or not
		if (BanknotesList == null) {
			throw new NullPointerException("Banknotes cannot be null.");
		}
		//moved instances of BanknoteValidator and BanknoteInsertion
		
		
		for (Banknote banknote : BanknotesList) { // Calculate the total value of coins inserted.
			/*
			 * banknote is passed to the sink and a "banknoteInserted" event is announced;
			 * otherwise, a "banknoteEjected" event is announced
			 */
				
			insertionSlot.receive(banknote);
			
			// have to check if banknote is valid now using Validator:
			banknoteValidator.receive(banknote);
			
			//After checking if valid have to get total cost
			//getDenomination() returns the value of the note
			valueOfAllBanknotes = valueOfAllBanknotes.add(banknote.getDenomination());
		}
		
		//checks if the amount inserted is less then cost
		if(valueOfAllBanknotes.compareTo(totalCost)< 0 ) {
			return false;
		}
		//if value is equal or greater then cost
		// have to calculate the change value
		this.changeRemaining = valueOfAllBanknotes.subtract(this.totalCost);
		if(changeRemaining.compareTo(new BigDecimal(0)) > 0) {
			return dispenseAccurateChange(changeRemaining);
		}
		
		return true;
	}

	//Are we using the old one for this?
	//do we need to change the old one slightly
	private boolean dispenseAccurateChange(BigDecimal changeRemaining) {
		
		return false;
	}

}

// 1. Customer: Inserts a banknote in the System.
// 2. System: Reduces the remaining amount due by the value of the inserted banknote.
// 3. System: Signals to the Customer the updated amount due after the insertion of the banknote.
// 4. System: If the remaining amount due is greater than 0, go to 1.
// 5. System: If the remaining amount due is less than 0, dispense the amount of change due.
