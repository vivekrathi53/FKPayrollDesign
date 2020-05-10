package AccountantSourceCode;

import AccountantSourceCode.Miscellaneous.TimeCard;
import AccountantSourceCode.PaymentRelatedClasses.PayCheque;
import DatabaseManagerSourceCode.HourlyEmpSqlConnector;
import EmployeeSourceCode.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class HourlyPayAccountant implements Accountant
{
    private HourlyEmpSqlConnector dbconnector;
    private int dailyWorkhours;
    private double overtimeMultiple;
    public HourlyPayAccountant(HourlyEmpSqlConnector dbconnector,int dailyWorkhours,double overtimeMultiple)
    {
        this.dbconnector=dbconnector;
        this.dailyWorkhours = dailyWorkhours;
        this.overtimeMultiple = overtimeMultiple;
    }
    /*public void deductCharges(Employee employee, Double amount)
    {

    }*/
    public void deduceFeeCharges(Employee employee,double amount) throws Exception {
        double dues = dbconnector.getEmployeeDues(employee);
        dues+=amount;
        dbconnector.setEmployeeDues(employee,dues);
    }
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
    private double estimatePay(HourlyEmployee employee) throws Exception {
        ArrayList<TimeCard> timeCardList = dbconnector.getEmployeeTimeCard(employee.getEmployeeId());
        double hourlyrate = dbconnector.getEmployeeRate(employee);
        double totalAmount=0;
        for (TimeCard timeCard: timeCardList)
        {
            long hoursWorked = getHoursDifference(timeCard.getStartTimestamp(),timeCard.getEndTimestamp());
            totalAmount += min(hoursWorked,dailyWorkhours)*hourlyrate;
            totalAmount += max(hoursWorked-dailyWorkhours,0)*hourlyrate*overtimeMultiple;
        }
        double dueCharges = dbconnector.getEmployeeDues(employee);
        return totalAmount - dueCharges;
    }
    public void payEmployee(Employee employee) throws Exception {
        // make employee payment
        double amount = estimatePay((HourlyEmployee) employee);
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
        Date date = calendar.getTime();
        String todayDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        if(todayDay=="Friday")
        {
            for(Employee e : arrayList)
                payEmployee(e);
        }
        else
            System.out.println("Today is not Friday :P");
    }
}
