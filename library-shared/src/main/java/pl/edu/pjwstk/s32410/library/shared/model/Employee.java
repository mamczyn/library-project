package pl.edu.pjwstk.s32410.library.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Employee {
	private Site site;
	private String name;
	private String surname;
	private String token;
}
