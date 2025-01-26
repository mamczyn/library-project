package pl.edu.pjwstk.s32410.projects.library.app.controller.data.input;

import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class DataInput<T> {
	private Consumer<T> checker = (v) -> {};
	private String placeholder;
	private T value;
	private Type type;
	
	public DataInput(Type type, String placeholder, Consumer<T> checker) {
		this.type = type;
		this.placeholder = placeholder;
		this.checker = checker;
	}
	
	public DataInput(Type type, String placeholder) {
		this(type, placeholder, (v) -> {});
	}

	public void check() {
		checker.accept(value);
	}
	
	public static enum Type {
		NUMBER,
		TEXT,
		SELECT
	}
}
