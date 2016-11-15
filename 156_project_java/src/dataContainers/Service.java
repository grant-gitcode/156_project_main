package dataContainers;

import reports.Invoice;

/**
 * Service is the companion class to Ticket. Also like Ticket, Service is a subclass of Product.
 * Service sets a final TAX attribute and serves as a way to broadly distinguish between two
 * types of ways the theater makes money.
 * @author Grant
 *
 */
public abstract class Service extends Product {
	
	private final double TAX = 0.04;
	
	public Service() {
		
	}
	
	public Service(String productCode) {
		super(productCode);
	}
	
	public Service(Service toClone) {
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