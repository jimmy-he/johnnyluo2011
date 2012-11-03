// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 2006-10-3 12:44:20
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DateTime.java

package com.crm.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTime extends Date {

	public DateTime() {
		this((Date) null);
	}

	public DateTime(Date date) {
		this(date, 13);
	}

	public DateTime(String dateString) {
		this(dateString, 13);
	}

	public DateTime(String dateString, int type) {
		this.type = 0;
		empty = true;
		setTime(dateString, type);
	}

	private void setTime(String dateTimeString, int type) {
		if (dateTimeString == null || dateTimeString.trim().length() == 0) {
			empty = true;
			return;
		}
		try {
			dateTimeString = correct(dateTimeString);
			SimpleDateFormat dateFormat = getDateFormat(type);
			Date date = dateFormat.parse(dateTimeString);
			setTime(date.getTime());
			empty = false;
		} catch (ParseException e) {
			empty = true;
			throw new IllegalArgumentException("unable to parse " + dateTimeString);
		}
		this.type = type;
		check(this, type);
	}

	private void setTime(Date date, int type) {
		if (date == null) {
			empty = true;
			return;
		} else {
			setTime(getDateFormat(type).format(date), type);
			return;
		}
	}

	public DateTime(Date date, int type) {
		this.type = 0;
		empty = true;
		setTime(date, type);
	}

	public DateTime(DateTime dateTime, int type) {
		this(((Date) (dateTime)), type);
	}

	public int getYear() {
		check(this, 11);
		return Integer.parseInt(getDateFormat(11).format(this));
	}

	public int getMonth() {
		check(this, 22);
		return Integer.parseInt(getDateFormat(22).format(this));
	}

	public int getDay() {
		check(this, 33);
		return Integer.parseInt(getDateFormat(33).format(this));
	}

	public int getHour() {
		check(this, 44);
		return Integer.parseInt(getDateFormat(44).format(this));
	}

	public int getMinute() {
		check(this, 55);
		return Integer.parseInt(getDateFormat(55).format(this));
	}

	public int getSecond() {
		check(this, 66);
		return Integer.parseInt(getDateFormat(66).format(this));
	}

	public static void setDateDelimiter(String delimiter) {
		DateTime.delimiter = delimiter;
	}

	public static String getDateDelimiter() {
		return delimiter;
	}

	private static SimpleDateFormat getDateFormat(int type) {
		String pattern = "";
		switch (type) {
		case 11: // '\013'
			pattern = "yyyy";
			break;

		case 12: // '\f'
			pattern = "yyyy" + delimiter + "MM";
			break;

		case 13: // '\r'
			pattern = "yyyy" + delimiter + "MM" + delimiter + "dd";
			break;

		case 14: // '\016'
			pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH";
			break;

		case 15: // '\017'
			pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm";
			break;

		case 16: // '\020'
			pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss";
			break;

		case 17: // '\021'
			pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss.SSS";
			break;

		case 22: // '\026'
			pattern = "MM";
			break;

		case 23: // '\027'
			pattern = "MM" + delimiter + "dd";
			break;

		case 24: // '\030'
			pattern = "MM" + delimiter + "dd HH";
			break;

		case 25: // '\031'
			pattern = "MM" + delimiter + "dd HH:mm";
			break;

		case 26: // '\032'
			pattern = "MM" + delimiter + "dd HH:mm:ss";
			break;

		case 27: // '\033'
			pattern = "MM" + delimiter + "dd HH:mm:ss.SSS";
			break;

		case 33: // '!'
			pattern = "dd";
			break;

		case 34: // '"'
			pattern = "dd HH";
			break;

		case 35: // '#'
			pattern = "dd HH:mm";
			break;

		case 36: // '$'
			pattern = "dd HH:mm:ss";
			break;

		case 37: // '%'
			pattern = "dd HH:mm:ss.SSS";
			break;

		case 44: // ','
			pattern = "HH";
			break;

		case 45: // '-'
			pattern = "HH:mm";
			break;

		case 46: // '.'
			pattern = "HH:mm:ss";
			break;

		case 47: // '/'
			pattern = "HH:mm:ss.SSS";
			break;

		case 55: // '7'
			pattern = "mm";
			break;

		case 56: // '8'
			pattern = "mm:ss";
			break;

		case 57: // '9'
			pattern = "mm:ss.SSS";
			break;

		case 66: // 'B'
			pattern = "ss";
			break;

		case 67: // 'C'
			pattern = "ss.SSS";
			break;

		case 77: // 'M'
			pattern = "SSS";
			break;

		case 18: // '\022'
		case 19: // '\023'
		case 20: // '\024'
		case 21: // '\025'
		case 28: // '\034'
		case 29: // '\035'
		case 30: // '\036'
		case 31: // '\037'
		case 32: // ' '
		case 38: // '&'
		case 39: // '\''
		case 40: // '('
		case 41: // ')'
		case 42: // '*'
		case 43: // '+'
		case 48: // '0'
		case 49: // '1'
		case 50: // '2'
		case 51: // '3'
		case 52: // '4'
		case 53: // '5'
		case 54: // '6'
		case 58: // ':'
		case 59: // ';'
		case 60: // '<'
		case 61: // '='
		case 62: // '>'
		case 63: // '?'
		case 64: // '@'
		case 65: // 'A'
		case 68: // 'D'
		case 69: // 'E'
		case 70: // 'F'
		case 71: // 'G'
		case 72: // 'H'
		case 73: // 'I'
		case 74: // 'J'
		case 75: // 'K'
		case 76: // 'L'
		default:
			throw new IllegalArgumentException(type + " is not support");
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter;
	}

	public String toString() {
		if (empty) {
			return "";
		} else {
			SimpleDateFormat dateFormat = getDateFormat(type);
			return dateFormat.format(this);
		}
	}

	public String toString(int type) {
		if (empty) {
			return "";
		} else {
			check(this, type);
			SimpleDateFormat dateFormat = getDateFormat(type);
			return dateFormat.format(this);
		}
	}

	public static DateTime current() {
		SimpleDateFormat dateFormat = getDateFormat(17);
		return new DateTime(dateFormat.format(new Date()), 17);
	}

	public static int intervalYear(DateTime startDate, int startHour, DateTime endDate, int endHour) {
		if (startDate.getType() != 13)
			throw new IllegalArgumentException("startDate is not a validate DateTime which type is YEAR_TO_DAY");
		if (endDate.getType() != 13)
			throw new IllegalArgumentException("endDate is not a validate DateTime which type is YEAR_TO_DAY");
		startDate = new DateTime(startDate + " " + startHour, 14);
		endDate = new DateTime(endDate + " " + endHour, 14);
		int yearDiff = endDate.getYear() - startDate.getYear();
		if (endDate.getMonth() > startDate.getMonth())
			yearDiff++;
		else if (endDate.getMonth() < startDate.getMonth())
			yearDiff--;
		else if (endDate.getDay() > startDate.getDay())
			yearDiff++;
		else if (endDate.getDay() < startDate.getDay())
			yearDiff--;
		else if (endHour > startHour)
			yearDiff++;
		else if (endHour < startHour)
			yearDiff--;
		return yearDiff;
	}

	public static int intervalMonth(DateTime startDate, int startHour, DateTime endDate, int endHour) {
		if (startDate.getType() != 13)
			throw new IllegalArgumentException("startDate is not a validate DateTime which type is YEAR_TO_DAY");
		if (endDate.getType() != 13)
			throw new IllegalArgumentException("endDate is not a validate DateTime which type is YEAR_TO_DAY");
		startDate = new DateTime(startDate + " " + startHour, 14);
		endDate = new DateTime(endDate + " " + endHour, 14);
		int monthDiff = (endDate.getYear() - startDate.getYear()) * 12;
		if (endDate.getMonth() > startDate.getMonth()) {
			monthDiff += endDate.getMonth() - startDate.getMonth();
			if (endDate.getDay() > startDate.getDay())
				monthDiff++;
			else if (endDate.getDay() >= startDate.getDay() && endDate.getHour() > startDate.getHour())
				monthDiff++;
		} else if (endDate.getMonth() < startDate.getMonth()) {
			monthDiff += endDate.getMonth() - startDate.getMonth();
			if (endDate.getDay() <= startDate.getDay())
				if (endDate.getDay() < startDate.getDay())
					monthDiff--;
				else if (endDate.getHour() > startDate.getHour())
					monthDiff++;
				else if (endDate.getHour() < startDate.getHour())
					monthDiff--;
		} else if (endDate.getDay() > startDate.getDay())
			monthDiff++;
		else if (endDate.getDay() < startDate.getDay())
			monthDiff--;
		else if (endDate.getHour() <= startDate.getHour() && endDate.getHour() < startDate.getHour())
			monthDiff--;
		return monthDiff;
	}

	public static int intervalDay(DateTime startDate, int startHour, DateTime endDate, int endHour) {
		if (startDate.getType() != 13)
			throw new IllegalArgumentException("startDate is not a validate DateTime which type is YEAR_TO_DAY");
		if (endDate.getType() != 13)
			throw new IllegalArgumentException("endDate is not a validate DateTime which type is YEAR_TO_DAY");
		long diffTime = (endDate.getTime() + (long) endHour * 0x36ee80L)
				- (startDate.getTime() + (long) startHour * 0x36ee80L);
		int diffDay = (int) (diffTime / 0x5265c00L);
		long diffT = diffTime - (long) diffDay * 0x5265c00L;
		if (diffT > 0L)
			diffDay++;
		else if (diffT < 0L)
			diffDay--;
		return diffDay;
	}

	public DateTime addDay(int day) {
		DateTime dt = new DateTime(toString());
		dt.setTime(getTime() + (long) day * 0x5265c00L);
		return dt;
	}

	public DateTime addMonth(int iMonth) {
		DateTime dt = (DateTime) clone();
		GregorianCalendar gval = new GregorianCalendar();
		gval.setTime(dt);
		gval.add(2, iMonth);
		dt.setTime(gval.getTime().getTime());
		return dt;
	}

	public DateTime addYear(int iYear) {
		DateTime dt = (DateTime) clone();
		GregorianCalendar gval = new GregorianCalendar();
		gval.setTime(dt);
		gval.add(1, iYear);
		dt.setTime(gval.getTime().getTime());
		return dt;
	}

	public DateTime addHour(int hour) {
		DateTime dt = (DateTime) clone();
		dt.setTime(getTime() + (long) hour * 0x36ee80L);
		return dt;
	}

	public DateTime addMinute(int minute) {
		DateTime dt = (DateTime) clone();
		dt.setTime(getTime() + (long) minute * 60000L);
		return dt;
	}

	public int getType() {
		return type;
	}

	private static String correct(String dateString) {
		String resultString = dateString;
		if (dateString.indexOf("/") > -1)
			resultString = dateString.replace("/", delimiter);
		if (dateString.indexOf("-") > -1)
			resultString = dateString.replace("/", delimiter);
		return resultString;
	}

	public boolean isEmpty() {
		return empty;
	}

	private void check(DateTime dateTime, int type) {
		if (dateTime.isEmpty())
			throw new IllegalStateException("DateTime is empty.");
		int types[] = { 11, 12, 13, 14, 15, 16, 17, 22, 23, 24, 25, 26, 27, 33, 34, 35, 36, 37, 44, 45, 46, 47, 55, 56,
				57, 66, 67, 77 };
		boolean isValidType = false;
		for (int i = 0; i < types.length; i++)
			if (types[i] == type)
				isValidType = true;

		if (!isValidType)
			throw new IllegalStateException("this type is not support.");
		if (dateTime.getType() != type) {
			if (dateTime.getType() / 10 > type / 10)
				throw new IllegalStateException("this type is out of range of this datetime instance.");
			if (dateTime.getType() % 10 < type % 10)
				throw new IllegalStateException("this type is out of range of this datetime instance.");
		}
	}

	public static final int YEAR_TO_YEAR = 11;
	public static final int YEAR_TO_MONTH = 12;
	public static final int YEAR_TO_DAY = 13;
	public static final int YEAR_TO_HOUR = 14;
	public static final int YEAR_TO_MINUTE = 15;
	public static final int YEAR_TO_SECOND = 16;
	public static final int YEAR_TO_MILLISECOND = 17;
	public static final int MONTH_TO_MONTH = 22;
	public static final int MONTH_TO_DAY = 23;
	public static final int MONTH_TO_HOUR = 24;
	public static final int MONTH_TO_MINUTE = 25;
	public static final int MONTH_TO_SECOND = 26;
	public static final int MONTH_TO_MILLISECOND = 27;
	public static final int DAY_TO_DAY = 33;
	public static final int DAY_TO_HOUR = 34;
	public static final int DAY_TO_MINUTE = 35;
	public static final int DAY_TO_SECOND = 36;
	public static final int DAY_TO_MILLISECOND = 37;
	public static final int HOUR_TO_HOUR = 44;
	public static final int HOUR_TO_MINUTE = 45;
	public static final int HOUR_TO_SECOND = 46;
	public static final int HOUR_TO_MILLISECOND = 47;
	public static final int MINUTE_TO_MINUTE = 55;
	public static final int MINUTE_TO_SECOND = 56;
	public static final int MINUTE_TO_MILLISECOND = 57;
	public static final int SECOND_TO_SECOND = 66;
	public static final int SECOND_TO_MILLISECOND = 67;
	public static final int MILLISECOND_TO_MILLISECOND = 77;
	private static String delimiter = "-";
	private int type;
	private boolean empty;

}