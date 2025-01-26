package pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.DataInput;

@Getter
@Setter
public class SelectInput extends DataInput<String> {
	private Map<String, String> options;
	
	public SelectInput(String placeholder, Consumer<String> checker) {
		this(placeholder, checker, new HashMap<>());
	}
	
	public SelectInput(String placeholder, Consumer<String> checker, Map<String, String> options) {
		super(DataInput.Type.SELECT, placeholder, checker);
		
		this.options = options;
		
		this.options.put("default", placeholder);
	}

}
