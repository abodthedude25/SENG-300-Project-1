package com.thelocalmarketplace.software;
public class PayWithBanknote extends AbstractPay implements BanknoteValidatorObserver, BanknoteStorageUnitObserver, BanknoteInsertionSlotObserver {

	public PayWithBanknote(CustomerStationControl customerStationControl) {
		super(customerStationControl);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * STarts the payment process asking customer to insert cash and then updating the remaining balance required 
	 * Calls update remaining balance to get notify the customer to pay the remaining amount. 
	 */
	public BigDecimal pay(Order order) {
     BigDecimal totalUnpaid = order.getTotalUnpaid();
	    
	    if (totalUnpaid.compareTo(BigDecimal.ZERO) > 0 && !customerStationControl.isBlocked()) {
	        updateRemainingBalance(order);
	        customerStationControl.notifyCustomer("Please insert cash", customerStationControl.notifyInsertPaymentCode);
	    } 
	    return totalUnpaid;
	}
	
	/**
	 * notifies the customer to pay the remaining amount due
	 * @param order is the order the customer is currently still paying for
	 */
	private void updateRemainingBalance(Order order) {
		BigDecimal totalUnpaid = order.getTotalUnpaid();
		customerStationControl = order.getCustomerStationControl();
		customerStationControl.notifyCustomer(String.format("Reamining amount due: %.2f", totalUnpaid), customerStationControl.notifyOtherCode);
	
		
	}


	public void processBankNotePayment(BanknoteValidator validator, BigDecimal num) {
		amountDue = num;
		
	}

	@Override
	public void fullPaid() {
		amountDue = BigDecimal.ZERO;
		
	}

	@Override
	public void enabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOn(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void turnedOff(IComponent<? extends IComponentObserver> component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknoteInserted(BanknoteInsertionSlot slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknoteEjected(BanknoteInsertionSlot slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknoteRemoved(BanknoteInsertionSlot slot) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * this method is called when the storage is full, the attendant gets notified
	 */
	@Override
	public void banknotesFull(BanknoteStorageUnit unit) {
		banknoteAdded(unit);
		customerStationControl.notifyAttendant("Banknote storage is full", customerStationControl.notifyOtherCode);
	
		
	}
	
	
    /**
     * updates system when banknote is inserted
     */
	@Override
	public void banknoteAdded(BanknoteStorageUnit unit) {
		Order order = customerStationControl.getOrder();
		updatePayment(order);
	
		
	}

	
	/**
	 * calculates and notifies customer of the payment amount left due
	 * @param order is the order for which the customer is currently paying
	 */
	private void updatePayment(Order order) {
		amountDue = BigDecimal.ZERO;
		order.addBanknotesPaid(amountDue);
		BigDecimal totalUnpaid = order.getTotalUnpaid();
		
		if (totalUnpaid.compareTo(BigDecimal.ZERO) > 0) {
	        updateRemainingBalance(order);
	        customerStationControl.notifyCustomer("Please insert your payment", customerStationControl.notifyInsertPaymentCode);
	    }
		
		if (totalUnpaid.compareTo(BigDecimal.ZERO) == 0) {
			fullPaid();
		} 
		
		
			else if (totalUnpaid.compareTo(BigDecimal.ZERO) < 0) {
			    BigDecimal change = totalUnpaid.abs(); // Calculate the change amount

			    // Convert BigDecimal change to dollars
			    int changeInDollars = change.intValue();

			    // Define denominations in dollars: 100 dollars, 50 dollars, 20 dollars, 10 dollars, 5 dollars, 2 dollars, 1 dollar
			    int[] denominations = { 20, 10, 5, 2, 1};

			    int[] changeDenominations = new int[5]; // Array to store count of each denomination

			    // Calculate denominations for the change
			    for (int i = 0; i < denominations.length; i++) {
			        if (changeInDollars >= denominations[i]) {
			            changeDenominations[i] = changeInDollars / denominations[i];
			            changeInDollars %= denominations[i];
			        }
			    }
			    
			    // Now changeDenominations array will contain the count of each banknote in the change.
			 
			    
			    
			    customerStationControl.notifyCustomer(
			            String.format("Change returned: %d 20-dollar, %d 10-dollar, %d 5-dollar, %d 2-dollar, %d 1-dollar",
			                changeDenominations[0], changeDenominations[1], changeDenominations[2],
			                changeDenominations[3], changeDenominations[4]), customerStationControl.notifyOtherCode);
			
			        fullPaid(); // Mark the order as fully paid after returning change
			}
			}
		
		
		
	

	
	
	@Override
	public void banknotesLoaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesUnloaded(BanknoteStorageUnit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goodBanknote(BanknoteValidator validator, Currency currency, BigDecimal denomination) {
		processBankNotePayment(validator, denomination);
		
	}

	@Override
	public void badBanknote(BanknoteValidator validator) {
		customerStationControl.notifyCustomer("Invalid note, please insert a valid bank note", customerStationControl.notifyInvalidBankNote);
		
	}

	@Override
	public void processPayment(CoinValidator validator, BigDecimal num) {
		// TODO Auto-generated method stub
		
	}
}