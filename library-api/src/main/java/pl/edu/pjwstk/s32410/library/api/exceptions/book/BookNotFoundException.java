package pl.edu.pjwstk.s32410.library.api.exceptions.book;

public class BookNotFoundException extends RuntimeException {
	
	public BookNotFoundException() {
		super("The given book was not found!");
	}
}
