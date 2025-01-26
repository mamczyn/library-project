package pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl;

import java.util.function.Consumer;

import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.DataInput;

public class NumberInput extends DataInput<Integer> {

	public NumberInput(String title, Consumer<Integer> checker) {
		super(DataInput.Type.NUMBER, title, checker);
	}

}
