package utilitary;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataHelper {
	
	public static Date fechaActual()
	{   
		Date nuevaFecha = new Date();
		Calendar calendar = Calendar.getInstance();
		nuevaFecha = calendar.getTime();
		return(nuevaFecha);
	}
}
