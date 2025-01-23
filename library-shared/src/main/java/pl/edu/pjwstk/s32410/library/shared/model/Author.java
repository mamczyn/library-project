package pl.edu.pjwstk.s32410.library.shared.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;
    
    private String surname = "";
    private String biography = "Unknown";
    private String nationality = "Unknown";
    
    public boolean containsInName(String query) {
    	if(name == null || query == null) return false;
    	
    	return name.toLowerCase().contains(query.toLowerCase());
    }
    
    public boolean containsInSurname(String query) {
    	if(surname == null || query == null) return false;
    	
    	return surname.toLowerCase().contains(query.toLowerCase());
    }
    
    public boolean containsInNameAndSurname(String query) {
    	if(name == null || surname == null || query == null) return false;
    	
    	return (name.toLowerCase() + " " + surname.toLowerCase()).contains(query.toLowerCase());
    }
}
