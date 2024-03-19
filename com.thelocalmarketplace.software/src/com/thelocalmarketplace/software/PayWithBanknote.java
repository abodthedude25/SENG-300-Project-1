package com.thelocalmarketplace.software;

import java.math.BigDecimal;

import com.thelocalmarketplace.hardware.SelfCheckoutStation;

public class PayWithBanknote  {

	
private SelfCheckoutStation checkoutSystem = null;


public PayWithBanknote(SelfCheckoutStation station){

    
    if (station == null)
			throw new NullPointerException("No argument may be null.");
	this.checkoutSystem = station;

}


// 1. Customer: Inserts a banknote in the System.
// 2. System: Reduces the remaining amount due by the value of the inserted banknote.
// 3. System: Signals to the Customer the updated amount due after the insertion of the banknote.
// 4. System: If the remaining amount due is greater than 0, go to 1.
// 5. System: If the remaining amount due is less than 0, dispense the amount of change due.
}