package AccountantSourceCode.ServiceFeeClasses;

import EmployeeSourceCode.Employee;

import java.sql.Timestamp;

public class UnionFee implements ServiceFee{
    public double feeRate;// perday

    public UnionFee(double feeRate) {
        this.feeRate = feeRate;
    }

    @Override
    public double generateFee(Timestamp startDate, Timestamp endDate, Employee employee) {
        return ServiceFee.getDaysDifference(startDate,endDate)*feeRate;
    }
}
