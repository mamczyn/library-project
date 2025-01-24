package pl.edu.pjwstk.s32410.library.api.exceptions.rental;

public class RentalIncorrectDatesException extends RuntimeException {
	
	public RentalIncorrectDatesException() {
		super("The rental's dates are incorrect!");
	}
}
