package EmployeeSourceCode;

import java.sql.Timestamp  ;
import AccountantSourceCode.PaymentRelatedClasses.*;
public interface Employee
{
    public void setName(String Name);
    public String getName();
    public String getEmployeeId();
    public void setEmployeeId(String employeeId);
    public Timestamp  getJoiningDate();
    public void setJoiningDate(Timestamp   joiningDate);
    public PaymentMode getPaymentMode() ;
    public void setPaymentMode(PaymentMode paymentMode);
}