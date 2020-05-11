package EmployeeSourceCode;

import java.sql.Timestamp  ;

import AccountantSourceCode.CommissionAccountant;
import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.PaymentRelatedClasses.*;
public interface Employee
{
    public void setName(String Name);
    public String getName();
    public double getCommissionRate();
    public void setCommissionRate(double commissionRate);
    public String getEmployeeId();
    public void setEmployeeId(String employeeId);
    public Timestamp  getJoiningDate();
    public void setJoiningDate(Timestamp   joiningDate);
    public PaymentMode getPaymentMode() ;
    public void setPaymentMode(PaymentMode paymentMode);
    public void deductFees(double amount) throws Exception;

}