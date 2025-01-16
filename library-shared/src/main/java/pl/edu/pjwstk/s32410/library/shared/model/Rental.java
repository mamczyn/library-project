package pl.edu.pjwstk.s32410.library.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pjwstk.s32410.library.shared.model.book.StorageBook;

@AllArgsConstructor
@Getter
public class Rental {
	private Site site;
	private StorageBook book;
	private	Customer customer;
	private Employee employee;
}
