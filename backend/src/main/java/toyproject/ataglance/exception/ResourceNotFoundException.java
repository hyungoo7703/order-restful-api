package toyproject.ataglance.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {} 

	public ResourceNotFoundException(String message) {
	 super(message); 
	}
}
