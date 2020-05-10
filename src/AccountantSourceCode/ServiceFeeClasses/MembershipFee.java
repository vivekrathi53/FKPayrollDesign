package AccountantSourceCode.ServiceFeeClasses;

import EmployeeSourceCode.Employee;

import java.sql.Timestamp;
import java.util.Date;

public class MembershipFee implements ServiceFee
{
    private double feeRate;// per day

    @Override
    public double generateFee(Timestamp startDate, Timestamp endDate, Employee employee) {
        return ServiceFee.getDaysDifference(startDate,endDate)*feeRate;
    }
}