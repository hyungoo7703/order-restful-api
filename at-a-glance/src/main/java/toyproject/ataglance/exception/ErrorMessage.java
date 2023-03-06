package toyproject.ataglance.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {

	private int statusCode;
	private LocalDateTime dateErrorOccur;
	private String message;
	private String description;

}
