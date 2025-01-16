package pl.edu.pjwstk.s32410.library.shared.model.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pjwstk.s32410.library.shared.model.Site;

@AllArgsConstructor
@Getter
public class StorageBook {
	private Site site;
	private Book reference;
}
