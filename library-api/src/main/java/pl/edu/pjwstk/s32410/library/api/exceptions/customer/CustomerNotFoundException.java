package pl.edu.pjwstk.s32410.library.api.exceptions.customer;

public class CustomerNotFoundException extends RuntimeException {
	
	public CustomerNotFoundException() {
		super("The given customer was not found!");
	}
}
