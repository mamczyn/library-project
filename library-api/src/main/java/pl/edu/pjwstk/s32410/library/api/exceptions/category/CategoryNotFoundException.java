package pl.edu.pjwstk.s32410.library.api.exceptions.category;

public class CategoryNotFoundException extends RuntimeException {
	
	public CategoryNotFoundException() {
		super("The given category was not found!");
	}
}
