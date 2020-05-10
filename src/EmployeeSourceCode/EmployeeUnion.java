package EmployeeSourceCode;

import AccountantSourceCode.ServiceFeeClasses.ServiceFee;

import java.sql.Timestamp;
import java.util.ArrayList;

public class EmployeeUnion
{
    public ArrayList<Employee> employees;
    public void deductServiceCharge(Employee employee, ServiceFee serviceFee, Timestamp startDate,Timestamp endDate) throws Exception {
        double amount = serviceFee.generateFee(startDate,endDate,employee);
        employee.deductFees(amount);
    }
}
