package vn.edu.ifi.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtilities {

	public DateUtilities() {

	}

	public static Date getCurrent() {
		return Date.from(java.time.ZonedDateTime.now().toInstant());
	}

	public static String getFormattedDate(Date date) {
		return new SimpleDateFormat("EEEEEEEE dd MMMMMMMM yyyy", Locale.FRENCH).format(date);
	}
}
