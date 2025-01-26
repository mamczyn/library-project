package pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl;

import java.util.function.Consumer;

import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.DataInput;

public class TextInput extends DataInput<String> {

	public TextInput(String placeholder, Consumer<String> checker) {
		super(DataInput.Type.TEXT, placeholder, checker);
	}
	
	public TextInput(String placeholder) {
		super(DataInput.Type.TEXT, placeholder);
	}

}
