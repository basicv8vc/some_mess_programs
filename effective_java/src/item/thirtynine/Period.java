package item.thirtynine;

import java.util.Date;

public final class Period {
	private final Date start;
	private final Date end;
	
	/**
	 * 
	 * @param start the beginning of the period
	 * @param end the end of the period; must not precede start
	 * @throws IllegalArgumentException if start is after end
	 * @throws NullPointerException if start or end is null 
	 */
	public Period(Date start, Date end) {
		this.start = new Date(start.getTime()); //推荐使用构造器而不是clone方法
		this.end = new Date(end.getTime()); 
		//保护性拷贝在检查参数有效性之前进行
		if(this.start.compareTo(this.end) > 0)
			throw new IllegalArgumentException(start + " after " + end);
	}
//	public Period(Date start, Date end) {
//		if (start.compareTo(end) > 0)
//			throw new IllegalArgumentException(
//					start + " after " + end);
//		this.start = start;
//		this.end = end;
//	}
	
	public Date start() {
		return new Date(start.getTime());
	}
//	public Date start() {
//		return start;
//	}
	
	public Date end() {
		return new Date(end.getTime());
	}
//	public Date end() {
//		return end;
//	}
	

}
