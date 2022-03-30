package toyproject.ataglance.exception;

@SuppressWarnings("serial")
public class NotANormalRequestException extends RuntimeException {

	public NotANormalRequestException() {}
	
	public NotANormalRequestException(String message) {
		 super(message); 
	}
}
