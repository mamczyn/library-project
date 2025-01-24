package pl.edu.pjwstk.s32410.library.api.exceptions.author;

public class AuthorNotFoundException extends RuntimeException {
	
	public AuthorNotFoundException() {
		super("The given author was not found!");
	}
}
