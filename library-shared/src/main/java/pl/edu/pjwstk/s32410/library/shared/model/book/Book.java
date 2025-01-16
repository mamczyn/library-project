package pl.edu.pjwstk.s32410.library.shared.model.book;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Book {
	private List<Category> categories = new ArrayList<>();
	private List<Author> authors = new ArrayList<>();

	private String title;
	private String description;
}
