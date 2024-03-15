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
package com.thelocalmarketplace.software.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.scale.ElectronicScale;
import com.thelocalmarketplace.software.Order;
import com.thelocalmarketplace.software.SelfCheckoutStationSoftware;
import com.thelocalmarketplace.software.WeightDiscrepancy;

import powerutility.NoPowerException;
import powerutility.PowerGrid;


public class WeightDiscrepancyTest {         
	
	private WeightDiscrepancy weightDiscrepancy;
	private Order order;
	private ElectronicScale scale;
	private WeightDiscrepancy weightDiscrepancyla;
	private Order order2;
	private ElectronicScale scale2;
	private WeightDiscrepancy weightDiscrepancy33;
	private Order order3;
	private ElectronicScale scale3;
	private Order order4;
	private ElectronicScale scale4;
	private Order order5;
	private mockScale scale5;
	private WeightDiscrepancy weightDiscrepancy4;
     
     
    
	@Before

	public void setUp() throws OverloadedDevice {   
		 	
			
	        scale = new ElectronicScale();
	        PowerGrid grid = PowerGrid.instance();
	        scale.plugIn(grid);
	        scale.turnOn();
	        scale.enable();
	        order = new Order(scale);
	        weightDiscrepancy = new WeightDiscrepancy(order, scale);     
	          

    }
	

    
	@After
    public void tearDown() {
        
        // Unblock the system
        SelfCheckoutStationSoftware.setStationBlock(false);
    }
	
	  
	
	// created mockitem class that extends Item class
    class MockItem extends Item {
        public MockItem(Mass mass) {
            super(mass);
        }

    }   
    
    

    
    @Test (expected = RuntimeException.class )
    public void testconstructorexception() throws Exception {
    	weightDiscrepancy100.(order,scale);
    	
    }
    
    
    
    // create update mass test 
    @Test
    public void testUpdateMass_AddItemToOrder() throws OverloadedDevice {
         
    	 MockItem item1 = new MockItem(new Mass(100));
    	 MockItem item2 = new MockItem(new Mass(100));   
    	    
        
        order.addItemToOrder(item1);
        order.addItemToOrder(item2);
        
        
 
        WeightDiscrepancy weightDiscrepancy2 = new WeightDiscrepancy(order,scale);
        
        weightDiscrepancy2.updateMass();

        Mass expectedMass = new Mass(200);
 
       
        assertEquals(expectedMass, scale.getCurrentMassOnTheScale()); 
        
        
    } 
    

    @Test
    public void testcheckDiscrepancy_diff() throws OverloadedDevice {
    	
    	scale3 = new ElectronicScale();
        PowerGrid grid = PowerGrid.instance();
        scale3.plugIn(grid);
        scale3.turnOn();
        scale3.enable();
        order3 = new Order(scale3);
        weightDiscrepancy33 = new WeightDiscrepancy(order3, scale3);   
    	
    	
    	 MockItem item1 = new MockItem(new Mass(100));
         MockItem item2 = new MockItem(new Mass(150));
    	
         order3.addItemToOrder(item1);
         order3.addItemToOrder(item2);
         scale3.addAnItem(item1);
         
        
        weightDiscrepancy33.checkDiscrepancy(); 
        
        assertTrue(SelfCheckoutStationSoftware.getStationBlock());     
        
            }
    
    
    @Test
    public void testcheckDiscrepancy_same() throws OverloadedDevice {
    
    	 	scale2 = new ElectronicScale();
	        PowerGrid grid = PowerGrid.instance();
	        scale2.plugIn(grid);
	        scale2.turnOn();
	        scale2.enable();
	        order2 = new Order(scale2);
	        weightDiscrepancyla = new WeightDiscrepancy(order2, scale2);         
    	
    	 MockItem item1 = new MockItem(new Mass(100));
         
    	
         order2.addItemToOrder(item1);
        
         scale2.addAnItem(item1);      
       
         
        WeightDiscrepancy weightDiscrepancy3 = new WeightDiscrepancy(order2,scale2);
         
        weightDiscrepancy3.checkDiscrepancy();
        
        assertEquals(new Mass(100),scale2.getCurrentMassOnTheScale());
    
        
        //assertFalse(SelfCheckoutStationSoftware.getStationBlock());     
        
    }                
    
    
    @Test
	public void checkDescripExceptionTest() throws OverloadedDevice{
		
		scale4 = new ElectronicScale();
        PowerGrid grid = PowerGrid.instance();
        scale4.plugIn(grid);
        scale4.turnOn();
        scale4.enable();
        order4 = new Order(scale4);
        weightDiscrepancy4 = new WeightDiscrepancy(order4, scale4);   
    	
    
         MockItem item2 = new MockItem(new Mass(200000000000000000L));
   
         scale4.addAnItem(item2);
          
         weightDiscrepancy4.checkDiscrepancy();; 
         assertTrue(SelfCheckoutStationSoftware.getStationBlock());
         
}
    
    
    @Test
    public void testCheckRemoval_greaterthan() throws OverloadedDevice {
    	
            
        MockItem item1 = new MockItem(new Mass(100));
        MockItem item2 = new MockItem(new Mass(1000));
        MockItem item3 = new MockItem(new Mass(6000));
        
        order.addItemToOrder(item1); 
        order.addItemToOrder(item2);
        order.addItemToOrder(item3);
        
        scale.addAnItem(item1);
            
        
        weightDiscrepancy.checkDiscrepancy(); 
        
        assertFalse(weightDiscrepancy.checkRemoval());    
    }  

	@Test
	public void unBlockTest() throws Exception {
		
		scale4 = new ElectronicScale();
        PowerGrid grid = PowerGrid.instance();
        scale4.plugIn(grid);
        scale4.turnOn();
        scale4.enable();
        order4 = new Order(scale4);
        weightDiscrepancy4 = new WeightDiscrepancy(order4, scale4);   
    	
    	
    	 MockItem item1 = new MockItem(new Mass(100));
         MockItem item2 = new MockItem(new Mass(150));
    	
         order4.addItemToOrder(item1);
         order4.addItemToOrder(item2);
         scale4.addAnItem(item1);
          
        weightDiscrepancy4.unBlock(); 
        
        assertFalse(SelfCheckoutStationSoftware.getStationBlock());     
        
            }
		
	
	
	@Test
	public void unBlockCatchExceptionTest() throws OverloadedDevice{
		
		scale4 = new ElectronicScale();
        PowerGrid grid = PowerGrid.instance();
        scale4.plugIn(grid);
        scale4.turnOn();
        scale4.enable();
        order4 = new Order(scale4);
        weightDiscrepancy4 = new WeightDiscrepancy(order4, scale4);   
    	
    
         MockItem item2 = new MockItem(new Mass(200000000000000000L));
   
         scale4.addAnItem(item2);
          
         weightDiscrepancy4.unBlock(); 
         assertTrue(SelfCheckoutStationSoftware.getStationBlock());    
 
            }
		
 
	
    @Test
    public void testCheckRemoval_lessthan() throws OverloadedDevice {
            
        scale5 = new mockScale();
        PowerGrid grid = PowerGrid.instance();
        scale5.plugIn(grid);
        scale5.turnOn();
        scale5.enable();
        
        Mass mass1 = new Mass(5000000);
        MockItem item1 = new MockItem(mass1); 
        scale5.addAnItem(item1); 
        order5 = new Order(scale5);
       
        
        order5.addTotalWeightInGrams(3);  
      
        weightDiscrepancy4 = new WeightDiscrepancy(order5, scale5);  
     
        assertTrue(weightDiscrepancy.checkRemoval());
        
    	}
    @Test
    public void testCheckbaggage_greaterthan() throws Exception {
        
  	   scale5 = new mockScale();
         PowerGrid grid = PowerGrid.instance();
         scale5.plugIn(grid);
         scale5.turnOn();
         scale5.enable();
         
         Mass mass1 = new Mass(3000000);
         MockItem item1 = new MockItem(mass1); 
         scale5.addAnItem(item1); 
         order5 = new Order(scale5);
        
         
         order5.addTotalWeightInGrams(5);  
       
         weightDiscrepancy4 = new WeightDiscrepancy(order5, scale5);  
         
        assertTrue(weightDiscrepancy.checkBaggageAddition()); 
    }  
    
    
    @Test
    public void testCheckbaggage_lessthan() {
   
        MockItem item1 = new MockItem(new Mass(1000));
        MockItem item2 = new MockItem(new Mass(10));
        MockItem item3 = new MockItem(new Mass(60));
        
        order.addItemToOrder(item1); 
        order.addItemToOrder(item2);
        order.addItemToOrder(item3);
        
        scale.addAnItem(item1);
    
        weightDiscrepancy.checkBaggageAddition();
        assertFalse(weightDiscrepancy.checkBaggageAddition());       
        
    }  
    

    
    @Test
    public void checkWeightChangeTestTrue() throws Exception{
  	   scale5 = new mockScale();
         PowerGrid grid = PowerGrid.instance();
         scale5.plugIn(grid);
         scale5.turnOn();
         scale5.enable();
         
         Mass mass1 = new Mass(5000000);
         MockItem item1 = new MockItem(mass1); 
         scale5.addAnItem(item1); 
         order5 = new Order(scale5);
        
         
         order5.addTotalWeightInGrams(3);  
       
         weightDiscrepancy4 = new WeightDiscrepancy(order5, scale5);  
        assertTrue(weightDiscrepancy.checkWeightChange());    
    }
    
  	@Test
    public void checkWeightChangeTestFalse() throws Exception{
        
        scale5 = new mockScale();
        PowerGrid grid = PowerGrid.instance();
        scale5.plugIn(grid);
        scale5.turnOn();
        scale5.enable();
        
        Mass mass1 = new Mass(5000000);
        MockItem item1 = new MockItem(mass1); 
        scale5.addAnItem(item1); 
        order5 = new Order(scale5);
       
        
        order5.addTotalWeightInGrams(5);  
      
        weightDiscrepancy4 = new WeightDiscrepancy(order5, scale5);  
        
        
        assertFalse(weightDiscrepancy.checkWeightChange());    
        
    } 

  	


  	// mass changes and session is not blocked
  	@Test
  	public void notifymasschange_blocked() {
  		MockItem item1 = new MockItem(new Mass(1000));
  		order.addItemToOrder(item1); 
        scale.addAnItem(item1);
        weightDiscrepancy.notifyMassChanged();
        
  	} 
    
  	

    
 
}