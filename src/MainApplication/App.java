package MainApplication;

import AccountantSourceCode.CommissionAccountant;
import AccountantSourceCode.HourlyPayAccountant;
import AccountantSourceCode.PaymentRelatedClasses.BankTransfer;
import AccountantSourceCode.SalaryAccountant;
import AccountantSourceCode.TimeCardAccountant;
import DatabaseManagerSourceCode.HourlyEmpSqlConnector;
import DatabaseManagerSourceCode.SalariedEmpSqlConnector;
import EmployeeSourceCode.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Scanner;

public class App  {
    public static void main(String[] args)
    {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection=null;
        String url = "jdbc:mysql://127.0.0.1:3306/FKPayrollDesign";
        try {
            connection = DriverManager.getConnection(url,"vivek","password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SalariedEmpSqlConnector salariedEmpSqlConnector = new SalariedEmpSqlConnector(connection);
        HourlyEmpSqlConnector hourlyEmpSqlConnector = new HourlyEmpSqlConnector(connection);
        CommissionAccountant commissionAccountant = new CommissionAccountant(new SalariedEmpSqlConnector(connection));
        SalaryAccountant salaryAccountant = new SalaryAccountant(salariedEmpSqlConnector);
        HourlyPayAccountant hourlyPayAccountant = new HourlyPayAccountant(hourlyEmpSqlConnector,8,1.5);
        TimeCardAccountant timeCardAccountant = new TimeCardAccountant(hourlyEmpSqlConnector);
        HourlyEmployee.setTimeCardAccountant(timeCardAccountant);
        HourlyEmployee.setCommissionAccountant(commissionAccountant);
        HourlyEmployee.sethourlyPayAccountant(hourlyPayAccountant);
        SalariedEmployee.setCommissionAccountant(commissionAccountant);
        SalariedEmployee.setSalaryAccountant(salaryAccountant);

        try {
            salaryAccountant.doDailyWork();
            hourlyPayAccountant.doDailyWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Select one of Below Choice : ");
            System.out.println("1. Insert New Hourly Employee");
            System.out.println("2. Insert New Salary Employee");
            System.out.println("3. Insert a new TimeCard");
            System.out.println("4. Insert a new salesReceipt");
            //System.out.println("5. Insert a new time card");
            System.out.println("6. Insert a service charge on employee");
            System.out.println("7. Run Payroll For Today");
            int ty=sc.nextInt();
            if(ty==1)
            {
                insertNewHourlyEmployee(sc,hourlyEmpSqlConnector);
            }
            else if(ty==2)
            {
                insertNewSalaryEmployee(sc,salariedEmpSqlConnector);
            }
            else if(ty==3)
            {
                //insertNewTimeCard(sc,hourlyEmpSqlConnector);
            }
            else if(ty==4)
            {

            }
            else if(ty==5)
            {

            }
            else if(ty==6)
            {

            }
            else if(ty==7)
            {

            }
        }
    }

    private static void insertNewTimeCard(Scanner sc, HourlyEmpSqlConnector hourlyEmpSqlConnector)
    {
        System.out.println("Enter ");
    }

    private static void insertNewSalaryEmployee(Scanner sc, SalariedEmpSqlConnector salariedEmpSqlConnector)
    {
        //public SalariedEmployee(String name, String employeeId, Timestamp joiningDate, double monthlySalary, PaymentMode paymentMode)
        System.out.println("Enter name of employee");
        String empName = sc.next();
        System.out.println("Enter ID of employee");
        String empID = sc.next();
        System.out.println("Enter Joining Date TimeStamp Format");
        Timestamp joiningDate = inputTimestamp(sc);
        System.out.println("Enter Monthly Salary");
        double monthlyRate = sc.nextDouble();
        try {
            salariedEmpSqlConnector.insertEmployee(new SalariedEmployee(empName,empID,joiningDate,monthlyRate,new BankTransfer()));
            // currently by default payment mode is BankTransfer
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    private static Timestamp inputTimestamp(Scanner sc)
    {
        System.out.println("Enter Day DD" );
        String day = sc.next();
        System.out.println("Enter Month MM");
        String month = sc.next();
        System.out.println("Enter Year YYYY");
        String year = sc.next();
        System.out.println("Enter Hours HH");
        String hours = sc.next();
        System.out.println("Enter Minutes MM");
        String minutes = sc.next();
        System.out.println("Enter seconds SS");
        String seconds = sc.next();
        return Timestamp.valueOf(year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds);
    }
    private static void insertNewHourlyEmployee(Scanner sc, HourlyEmpSqlConnector hourlyEmpSqlConnector)
    {
        //public HourlyEmployee(String name, String employeeId, Timestamp joiningDate, double hourlyRate)
        System.out.println("Enter name of employee");
        String empName = sc.next();
        System.out.println("Enter ID of employee");
        String empID = sc.next();
        System.out.println("Enter Joining Date TimeStamp Format");
        Timestamp joiningDate = inputTimestamp(sc);
        System.out.println("Enter Hourly Rate");
        double hourlyRate = sc.nextDouble();
        try {
            hourlyEmpSqlConnector.insertEmployee(new HourlyEmployee(empName,empID,joiningDate,hourlyRate, new BankTransfer()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
