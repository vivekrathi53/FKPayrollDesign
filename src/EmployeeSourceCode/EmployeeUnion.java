package EmployeeSourceCode;

import AccountantSourceCode.ServiceFeeClasses.ServiceFee;

import java.sql.Timestamp;

public class EmployeeUnion
{

    public void deductServiceCharge(Employee employee, ServiceFee serviceFee, Timestamp startDate,Timestamp endDate) throws Exception {
        double amount = serviceFee.generateFee(startDate,endDate,employee);
        employee.deductFees(amount);
    }
}
