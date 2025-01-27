package pl.edu.pjwstk.s32410.projects.library.app.controller.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Getter;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.exception.DataInputException;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.DataInput;
import pl.edu.pjwstk.s32410.projects.library.app.controller.data.input.impl.NumberInput;

@Getter
public abstract class DataInputContainer {
	private HashMap<String, DataInput<?>> inputs = new HashMap<>();
	private String title;
	
	public DataInputContainer(String title) {
		this.title = title;
	}
	
	public abstract String OnDataCorrectSubmission(Map<String, String> data);
	
	public void add(String name, DataInput<?> input) {
		inputs.put(name, input);
	}
	
	public DataInput<?> get(String name) {
		return inputs.get(name);
	}
	
	public String checkAndSend(Map<String, String> data) {
		String message = "";
		
		for(Entry<String, DataInput<?>> item : inputs.entrySet()) {
			try {
				String id = item.getKey();
				DataInput<?> input = item.getValue();
				
				String value = data.get(id);
				
				if(value == null) {
					input.check(null);
				}
				else if(input instanceof NumberInput) {
					try {
						int intVal = Integer.valueOf(value);
						((NumberInput) input).check(intVal);
					} catch(Exception e) {
						((NumberInput) input).check(0);
					}
				}
				else {
					((DataInput<String>) input).check(value);
				}
			} 
			catch(DataInputException ex) {
				if(!message.equals("")) message += ", ";
				message += ex.getMessage();
			}
			catch(Exception ex) {
				if(!message.equals("")) message += ", ";
				message += ex.getMessage();
			}
		}
		
		if(!message.equals("")) throw new DataInputException(message);
		
		return OnDataCorrectSubmission(data);
	}
}
