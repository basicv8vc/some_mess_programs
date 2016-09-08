package item.five;

import java.util.*;

// BAD
//public class Person {
//
//	private Date birthDate;
//
//	//DON'T DO THIS!
//	public boolean isBabyBoomer() {
//		//Unnecessary allocation if expensive object
//		Calendar gmtCal =
//				Calendar.getInstance(TimeZone.getTimeZone("GMT"));
//		gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
//		Date boomStart = gmtCal.getTime();
//		gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
//		Date boomEnd = gmtCal.getTime();
//		return birthDate.compareTo(boomStart) >=0 &&
//				birthDate.compareTo(boomEnd) < 0;
//	}
//
//}

//Better
public class Person {
	private Date birthDate;
	private static Date BOOM_START;
	private static Date BOOM_END;
	
	static {
		Calendar gmtCal =
				Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmtCal.set(1946, Calendar.JANUARY, 1, 0, 0, 0);
		BOOM_START = gmtCal.getTime();
		gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
		BOOM_END = gmtCal.getTime();
	}
	
	public boolean isBabyBoomer() {
		return birthDate.compareTo(BOOM_START) >=0 &&
				birthDate.compareTo(BOOM_END) < 0;
	}
}