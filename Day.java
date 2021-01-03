public class Day implements Cloneable {

	private int year;
	private int month;
	private int day;
	private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

	public Day(int y, int m, int d) {
		this.year = y;
		this.month = m;
		this.day = d;
	}

	// Checks for correct date
	public static boolean correctFormat(String s) {
		String[] format = s.split("-");
		if (format.length != 3)
			return false;
		else if (!MonthNames.contains(format[1]))
			return false;
		else {
			Day tempDay = new Day(s);
			return valid(tempDay.year, tempDay.month, tempDay.day);
		}
	}

	public void set(String sDay) {
		String[] sDayParts = sDay.split("-");
		this.day = Integer.parseInt(sDayParts[0]);
		this.year = Integer.parseInt(sDayParts[2]);
		this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
	}

	public Day(String sDay) {
		set(sDay);
	}
	
	@Override
	public Day clone() {
		Day copy = null;
		try {
			copy = (Day) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return copy;
	}
	
	@Override
	public String toString() {
		return day + "-" + MonthNames.substring((month - 1) * 3, month * 3) + "-" + year;
	}
	
	// returns a yyyymmdd format to compare days
	public int format() { return (year*10000 + month*100 + day); }

	static public boolean isLeapYear(int y) {
		if (y % 400 == 0)
			return true;
		else if (y % 100 == 0)
			return false;
		else return y % 4 == 0;
	}

	static public boolean valid(int y, int m, int d) {
		if (m < 1 || m > 12 || d < 1)
			return false;
		switch (m) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return d <= 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return d <= 30;
			case 2:
				if (isLeapYear(y))
					return d <= 29;
				else
					return d <= 28;
		}
		return false;
	}
	
	private boolean isEndOfAMonth() {
		return !valid(year, month, day + 1);
	}
	
	private boolean isStartOfMonth() {
		return !valid(year, month, day - 1);
	}
	
	public Day next() {
		if (isEndOfAMonth())
			if (month == 12)
				return new Day(year + 1, 1, 1);
			else
				return new Day(year, month + 1, 1);
			else
			return new Day(year, month, day + 1);
	}
	
	public Day prev() {
		if (isStartOfMonth()) {
			if (month == 1)
				return new Day(year - 1, 12, 31);
			else {
				Day prevDay = new Day(year, month - 1, 31);
				switch (month) {
					case 5:
					case 7:
					case 10:
					case 12:
						prevDay.day = 30;
						break;
					case 3:
						if (isLeapYear(year))
							prevDay.day = 29;
						else
							prevDay.day = 28;
				}
				return prevDay;
			}
		} else {
			return new Day(year, month, day - 1);
		}
	}
	
	public Day forwardDays(int numDays) {
		if (valid(year, month, day + numDays))
			return new Day(year, month, day + numDays);
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
				return new Day(year, month + 1, (day + numDays) % 31);
			case 4:
			case 6:
			case 9:
			case 11:
				return new Day(year, month + 1, (day + numDays) % 30);
			case 2:
				if (isLeapYear(year))
					return new Day(year, month + 1, (day + numDays) % 29);
				else
					return new Day(year, month + 1, (day + numDays) % 28);
			default: // month = 12
				return new Day(year + 1, 1, (day + numDays) % 31);
		}
	}
}
