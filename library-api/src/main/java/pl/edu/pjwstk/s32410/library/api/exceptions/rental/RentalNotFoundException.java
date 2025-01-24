package pl.edu.pjwstk.s32410.library.api.exceptions.rental;

public class RentalNotFoundException extends RuntimeException {
	
	public RentalNotFoundException() {
		super("The given rental was not found!");
	}
}
