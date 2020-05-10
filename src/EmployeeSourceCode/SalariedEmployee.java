package EmployeeSourceCode;

import java.sql.Timestamp;
import AccountantSourceCode.*;
import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.PaymentRelatedClasses.*;
import EmployeeSourceCode.Employee;

public class SalariedEmployee implements Employee
{
    private String name;
    private String employeeId;
    private Timestamp joiningDate;
    private double monthlySalary;
    private static CommissionAccountant commissionAccountant;
    private static SalaryAccountant salaryAccountant;
    private PaymentMode paymentMode;

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public void deductFees(double amount) throws Exception {
        salaryAccountant.deduceFeeCharges(this, amount);
    }


    public SalariedEmployee(String name, String employeeId, Timestamp joiningDate, double monthlySalary, PaymentMode paymentMode) {
        this.name = name;
        this.employeeId = employeeId;
        this.joiningDate = joiningDate;
        this.monthlySalary = monthlySalary;
        this.paymentMode = paymentMode;
    }

    public double getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(double monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Timestamp getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Timestamp joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }



    public static CommissionAccountant getCommissionAccountant() {
        return commissionAccountant;
    }

    public static void setCommissionAccountant(CommissionAccountant commissionAccountant) {
        SalariedEmployee.commissionAccountant = commissionAccountant;
    }

    public static SalaryAccountant getSalaryAccountant() {
        return salaryAccountant;
    }

    public static void setSalaryAccountant(SalaryAccountant salaryAccountant) {
        SalariedEmployee.salaryAccountant = salaryAccountant;
    }

    public int submitToCommissionAccountant(SalesReceipt salesReceipt) throws Exception {
        if(commissionAccountant.checkSalesReceipt(this,salesReceipt))
        {
            // accepted;
            commissionAccountant.submitSalesReceipt(this,salesReceipt);
            return 1;
        }
        else
        {
            // rejected
            return 0;
        }
    }
}