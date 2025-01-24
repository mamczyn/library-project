package pl.edu.pjwstk.s32410.library.api.exceptions.rental;

public class RentalConflictingDatesException extends RuntimeException {
	
	public RentalConflictingDatesException() {
		super("The rental's dates are conflicting with other rentals!");
	}
}
