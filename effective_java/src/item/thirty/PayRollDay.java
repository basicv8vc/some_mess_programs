package item.thirty;
/**
 * 如果多个枚举常量同时共享共同的行为，则考虑策略枚举
 * @author basicv8vc
 *
 */
public enum PayRollDay {
	MONDAY(PayType.WEEKDAY), TUESDAY(PayType.WEEKDAY),
	WEDNESDAY(PayType.WEEKDAY), THURSDAY(PayType.WEEKDAY),
	FRIDAY(PayType.WEEKDAY),
	SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);
	
	private final PayType payType;
	PayRollDay(PayType payType) { this.payType = payType; }
	
	double pay(double hoursWorked, double payRate) {
		return payType.pay(hoursWorked, payRate);
	}
	
	//The Strategy enum type
	private enum PayType {
		//特定于常量的方法实现(constant-specific method implementation)
		WEEKDAY {
			double overtimePay(double hours, double payRate) {
				return hours <= HOURS_PER_SHIFT ? 0:
					(hours - HOURS_PER_SHIFT) * payRate / 2;
			}
			
			
		},
		WEEKEND {
			double overtimePay(double hours, double payRate) {
				return hours * payRate / 2;
			}
			
		};
		private static final int HOURS_PER_SHIFT = 8;
		
		abstract double overtimePay(double hrs, double payRate);
		
		double pay(double hoursWorked, double payRate) {
			double basePay = hoursWorked * payRate;
			return basePay + overtimePay(hoursWorked, payRate);
		}
	}

}
