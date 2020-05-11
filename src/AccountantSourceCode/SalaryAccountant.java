package AccountantSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import EmployeeSourceCode.*;
import DatabaseManagerSourceCode.*;
import AccountantSourceCode.PaymentRelatedClasses.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Math.min;

public class SalaryAccountant implements Accountant
{
    private SalariedEmpSqlConnector dbconnector;

    public SalaryAccountant(SalariedEmpSqlConnector dbconnector)
    {
        this.dbconnector=dbconnector;
    }
    /*public void deductCharges(Employee employee, Double amount)
    {

    }*/
    public PayCheque generatePayCheque(Employee employee, double amount, Timestamp issueDate)
    {
        return new PayCheque(amount , employee.getEmployeeId(), issueDate);
    }

    public void deduceFeeCharges(Employee employee,double amount) throws Exception {
        double dues = dbconnector.getEmployeeDues(employee);
        dues+=amount;
        dbconnector.setEmployeeDues(employee,dues);
    }


    private long getHoursDifference(Timestamp startTime,Timestamp endTime)
    {
        long startSeconds = startTime.getTime()/1000;
        long endSeconds = endTime.getTime()/1000;
        return (endSeconds-startSeconds)/3600;
    }

    private long getDaysDifference(Timestamp joiningTime,Timestamp currentTime)
    {
        long totalHours = getHoursDifference(joiningTime,currentTime);
        return totalHours/24;
    }

    private double getCommissionAmount(Employee employee) throws Exception {
        ArrayList<SalesReceipt> salesReceiptList = dbconnector.getEmployeeSalesReceipt(employee);
        double commissionMoney = 0;
        for(SalesReceipt salesReceipt:salesReceiptList)
        {
            if(getDaysDifference(salesReceipt.getSaleDate(),new Timestamp(System.currentTimeMillis()))<=7)
            {
                commissionMoney+=(salesReceipt.getAmountOfSale()*employee.getCommissionRate())/100;
            }
        }
        return commissionMoney;
    }

    private double estimatePay(SalariedEmployee employee) throws Exception {
        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        double monthlySalary = dbconnector.getEmployeeRate(employee);
        double dueCharges =  dbconnector.getEmployeeDues(employee);
        long totalDays = min(getDaysDifference(dbconnector.getEmployeeJoiningDate(employee),new Timestamp(System.currentTimeMillis())),monthMaxDays);
        return (totalDays*monthlySalary)/monthMaxDays - dueCharges;
    }
    public void payEmployee(Employee employee) throws Exception {
        // make employee payment
        double amount = estimatePay((SalariedEmployee)employee);
        if(amount>0)
        {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            employee.getPaymentMode().pay(generatePayCheque(employee,amount,timestamp));
            dbconnector.setEmployeeDues(employee,0);
        }
        else if(amount<0)
        {
            throw new Exception("Negative pay!! Large Dues of employee");
        }
    }
    public void doDailyWork() throws Exception {
        // check which employee needed to we paid today and pay them
        ArrayList<Employee> arrayList = dbconnector.getAllEmployees();
        Calendar calendar = Calendar.getInstance();
        int monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date date = calendar.getTime();
        String todayDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        if((date.getDay()==5&&date.getDate()+2>=monthMaxDays)&&(date.getDate()==monthMaxDays))
        {
            for(Employee e: arrayList)
                payEmployee(e);
        }
        else if(todayDay.equals("Friday"))
        {
            for(Employee employee : arrayList)
            {
                double commissionPay = getCommissionAmount(employee);
                if(commissionPay>0)
                {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    employee.getPaymentMode().pay(generatePayCheque(employee,commissionPay,timestamp));
                }
            }
        }
        else
            System.out.println("Today Is Not Last Day Of Month :P and Also not friday");
    }
}