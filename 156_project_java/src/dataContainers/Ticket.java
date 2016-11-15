package dataContainers;

import reports.Invoice;

/**
 * This class serves as the superclass of MovieTicket and SeasonPass. It has one final field, 
 * a double TAX, and is mostly used as a way to categorize the distinction between Ticket and 
 * Service objects.
 * @author Grant
 *
 */
public abstract class Ticket extends Product {
	
	private final double TAX = 0.06;
	
	public Ticket() {
		
	}
	
	public Ticket(String productCode) {
		super(productCode);
	}
	
	public Ticket(Ticket toClone) {
		super(toClone);
	}

	@Override
	public double computeSubTotal(Invoice inv) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTax(Invoice inv) {
		return this.TAX;
	}

}