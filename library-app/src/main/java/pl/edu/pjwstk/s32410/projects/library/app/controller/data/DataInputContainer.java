package pl.edu.pjwstk.s32410.projects.library.app.controller.data;

import java.util.HashMap;

import lombok.Getter;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.exception.DataInputException;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.DataInput;

@Getter
public class DataInputContainer {
	private HashMap<String, DataInput<?>> inputs = new HashMap<>();
	private String title;
	
	public DataInputContainer(String title) {
		this.title = title;
	}
	
	public void add(String name, DataInput<?> input) {
		inputs.put(name, input);
	}
	
	public DataInput<?> get(String name) {
		return inputs.get(name);
	}
	
	public void check() {
		String message = "";
		
		for(DataInput<?> input : inputs.values()) {
			try {
				input.check();
			} 
			catch(DataInputException ex) {
				if(!message.equals("")) message += ", ";
				message += ex.getMessage();
			}
		}
		
		if(!message.equals("")) throw new DataInputException(message);
	}
}
