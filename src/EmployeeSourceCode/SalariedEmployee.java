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
    private double commissionRate=2.0;

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

    @Override
    public double getCommissionRate() {
        return commissionRate;
    }

    @Override
    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
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


}