package item.nine;

public final class PhoneNumber {
	private short areaCode;
	private short prefix;
	private short lineNumber;
	
	public PhoneNumber(int areaCode, int prefix,
					   int lineNumber) {
		rangeCheck(areaCode, 999, "area code");
		rangeCheck(prefix, 999, "prefix");
		rangeCheck(lineNumber, 9999, "line number");
		this.areaCode = (short) areaCode;
		this.prefix = (short) prefix;
		this.lineNumber = (short) lineNumber;
		
	}
	
	private static void rangeCheck(int arg, int max, String name) {
		if (arg < 0 || arg > max)
			throw new IllegalArgumentException(name + ": " + arg);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if(!(o instanceof PhoneNumber))
			return false;
		PhoneNumber pn = (PhoneNumber) o;
		return pn.lineNumber == lineNumber
				&& pn.prefix == prefix
				&& pn.areaCode == areaCode;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + areaCode;
		result = 31 * result + prefix;
		result = 31 * result + lineNumber;
		return result;
	}
	
	/**
	 * Returns the stirng representation of this phone number.
	 * The string consists of fourteen characters whose format
	 * is "(XXX) YYY-ZZZZ", where XXX is the area code, YYY is
	 * the prefix, and ZZZZ is the line number.
	 */
	@Override
	public String toString() {
		return String.format("(%03d) %03d-%04d", areaCode, prefix, lineNumber);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
