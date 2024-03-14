package com.thelocalmarketplace.hardware.test;

import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.Sink;
import com.tdc.coin.Coin;

class StandardSinkStub implements Sink<Coin> {
	   private boolean hasSpaceVar = false;
	   
	   public StandardSinkStub() {
		   return;
	   }
		@Override
		public void receive(Coin cash) throws CashOverloadException, DisabledException {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean hasSpace() {
			// TODO Auto-generated method stub
			return hasSpaceVar;
		}
		
		public void setHasSpace(boolean val) {
			hasSpaceVar = val;
		}
		
	}