package pl.edu.pjwstk.s32410.library.api.exceptions.employee;

public class EmployeeNotFoundException extends RuntimeException {
	
	public EmployeeNotFoundException() {
		super("The given employee was not found!");
	}

}
