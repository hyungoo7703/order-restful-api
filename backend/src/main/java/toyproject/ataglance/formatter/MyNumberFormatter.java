package toyproject.ataglance.formatter;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class MyNumberFormatter implements Formatter<Integer> {

	/* 
	 * String -> Integer 
	 * 오브젝트 타입을 단순히 casting을 (Integer)로 다운 cast로 진행하게 되면 오류가 발생하는 점 주의!
	 */
	@Override
	public Integer parse(String string, Locale locale) throws ParseException { 
		return Integer.parseInt(String.valueOf(NumberFormat.getInstance(locale).parse(string))); 
	}

	/* Integer -> String */
	@Override
	public String print(Integer integer, Locale locale) {
		return NumberFormat.getInstance(locale).format(integer); 
	}

}