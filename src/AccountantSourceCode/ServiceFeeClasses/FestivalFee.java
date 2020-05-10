package AccountantSourceCode.ServiceFeeClasses;
import EmployeeSourceCode.Employee;

import java.sql.Timestamp;

public class FestivalFee implements ServiceFee
{
    private double feeRate;// per day

    public FestivalFee(double feeRate) {
        this.feeRate = feeRate;
    }

    @Override
    public double generateFee(Timestamp startDate, Timestamp endDate, Employee employee) {
        return ServiceFee.getDaysDifference(startDate,endDate)*feeRate;
    }
}