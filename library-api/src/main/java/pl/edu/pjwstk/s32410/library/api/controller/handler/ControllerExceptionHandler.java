package pl.edu.pjwstk.s32410.library.api.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pl.edu.pjwstk.s32410.library.api.exceptions.author.AuthorNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.book.BookNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.category.CategoryNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.customer.CustomerNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.employee.EmployeeNotFoundException;
import pl.edu.pjwstk.s32410.library.api.exceptions.rental.RentalConflictingDatesException;
import pl.edu.pjwstk.s32410.library.api.exceptions.rental.RentalIncorrectDatesException;
import pl.edu.pjwstk.s32410.library.api.exceptions.rental.RentalNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
    	AuthorNotFoundException.class, BookNotFoundException.class, 
    	CategoryNotFoundException.class, RentalNotFoundException.class, 
    	EmployeeNotFoundException.class, RentalNotFoundException.class,
    	CustomerNotFoundException.class
    })
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                			 .body(ex.getMessage());
    }

    @ExceptionHandler(RentalConflictingDatesException.class)
    public ResponseEntity<String> handleRentalConflictException(RentalConflictingDatesException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                			 .body(ex.getMessage());
    }
    
    @ExceptionHandler(RentalIncorrectDatesException.class)
    public ResponseEntity<String> handleRentalWrongDatesException(RentalIncorrectDatesException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                			 .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                			 .body("An unexpected error occurred: " + ex.getMessage());
    }
}
