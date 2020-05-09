package AccountantSourceCode;

import EmployeeSourceCode.*;
import DatabaseManagerSourceCode.*;
import AccountantSourceCode.PaymentRelatedClasses.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import static java.lang.Math.min;

public class SalaryAccountant implements Accountant
{
    private SalariedEmpSqlConnector dbconnector;
    private int dailyWorkhours;
    private double overtimeMultiple;
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


    private double estimatePay(SalariedEmployee employee) throws Exception {
        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        double monthlySalary = dbconnector.getEmployeeRate(employee);
        long totalDays = min(getDaysDifference(dbconnector.getEmployeeJoiningDate(employee),new Timestamp(System.currentTimeMillis())),monthMaxDays);
        return (totalDays*monthlySalary)/monthMaxDays;
    }
    public void payEmployee(Employee employee) throws Exception {
        // make employee payment
        double amount = estimatePay((SalariedEmployee)employee);
        if(amount!=0)
        {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            employee.getPaymentMode().pay(generatePayCheque(employee,amount,timestamp));
        }
    }
    public void doDailyWork() throws Exception {
        // check which employee needed to we paid today and pay them
        ArrayList<Employee> arrayList = dbconnector.getAllEmployees();
        Calendar calendar = Calendar.getInstance();
        int monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date date = calendar.getTime();
        if(date.getDay()==monthMaxDays)
        {
            for(Employee e: arrayList)
                payEmployee(e);
        }
        else
            System.out.println("Today Is Not Last Day Of Month :P");
    }
}