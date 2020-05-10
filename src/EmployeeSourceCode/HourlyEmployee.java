package EmployeeSourceCode;
import java.sql.Timestamp;
import AccountantSourceCode.*;
import AccountantSourceCode.Miscellaneous.*;
import AccountantSourceCode.PaymentRelatedClasses.*;
public class HourlyEmployee implements Employee
{
    private String name;
    private String employeeId;
    private Timestamp joiningDate;
    private double hourlyRate;
    private static TimeCardAccountant timeCardAccountant;
    private static CommissionAccountant commissionAccountant;
    private static HourlyPayAccountant hourlyPayAccountant;
    private PaymentMode paymentMode;

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public void deductFees(double amount) throws Exception {
        hourlyPayAccountant.deduceFeeCharges(this,amount);
    }


    public HourlyEmployee(String name, String employeeId, Timestamp joiningDate, double hourlyRate,PaymentMode paymentMode) {
        this.name = name;
        this.employeeId = employeeId;
        this.joiningDate = joiningDate;
        this.hourlyRate = hourlyRate;
        this.paymentMode = paymentMode;
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

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public static TimeCardAccountant getTimeCardAccountant() {
        return timeCardAccountant;
    }

    public static void setTimeCardAccountant(TimeCardAccountant timeCardAccountant) {
        HourlyEmployee.timeCardAccountant = timeCardAccountant;
    }

    public static CommissionAccountant getCommissionAccountant() {
        return commissionAccountant;
    }

    public static void setCommissionAccountant(CommissionAccountant commissionAccountant) {
        HourlyEmployee.commissionAccountant = commissionAccountant;
    }

    public static HourlyPayAccountant gethourlyPayAccountant() {
        return hourlyPayAccountant;
    }

    public static void sethourlyPayAccountant(HourlyPayAccountant hourlyPayAccountant) {
        HourlyEmployee.hourlyPayAccountant = hourlyPayAccountant;
    }

    public int submitTimeCardToAccountant(TimeCard timeCard) throws Exception {
        if(timeCardAccountant.checkTimeCard(this,timeCard)) {
            timeCardAccountant.submitTimeCard(this,timeCard);
            // accepted
            return 1;
        }
        else
        {
            // rejected
            return 0;
        }
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
            return 0;
            // rejected
        }
    }


}