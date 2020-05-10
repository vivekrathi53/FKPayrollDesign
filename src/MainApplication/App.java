package MainApplication;

import AccountantSourceCode.CommissionAccountant;
import AccountantSourceCode.HourlyPayAccountant;
import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.Miscellaneous.TimeCard;
import AccountantSourceCode.PaymentRelatedClasses.BankTransfer;
import AccountantSourceCode.SalaryAccountant;
import AccountantSourceCode.ServiceFeeClasses.FestivalFee;
import AccountantSourceCode.ServiceFeeClasses.MembershipFee;
import AccountantSourceCode.ServiceFeeClasses.UnionFee;
import AccountantSourceCode.TimeCardAccountant;
import DatabaseManagerSourceCode.*;
import EmployeeSourceCode.*;

import java.sql.*;
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
        EmployeeUnion employeeUnion = new EmployeeUnion();

        Scanner sc = new Scanner(System.in);
        while(true)
        {
            System.out.println("Select one of Below Choice : ");
            System.out.println("1. Insert New Hourly Employee");
            System.out.println("2. Insert New Salary Employee");
            System.out.println("3. Insert a new TimeCard");
            System.out.println("4. Insert a new salesReceipt");
            System.out.println("5. Change employee details ");
            System.out.println("6. Insert a service charge on employee");
            System.out.println("7. Run Payroll For Today");
            int ty=sc.nextInt();
            try {
                    if (ty == 1) {
                        insertNewHourlyEmployee(sc, hourlyEmpSqlConnector);
                    } else if (ty == 2) {
                        insertNewSalaryEmployee(sc, salariedEmpSqlConnector);
                    } else if (ty == 3) {
                        insertNewTimeCard(sc, hourlyEmpSqlConnector);
                    } else if (ty == 4) {
                        insertNewSalesReceipt(sc, hourlyEmpSqlConnector, commissionAccountant);
                    } else if (ty == 6) {
                        System.out.println("Enter EmployeeID");
                        String empId = sc.next();
                        if (empId.charAt(0) == 'H') {
                            HourlyEmployee hourlyEmployee = (HourlyEmployee) hourlyEmpSqlConnector.getEmployee(empId);
                            deductServiceCharge(sc, hourlyEmployee, employeeUnion);
                        } else if (empId.charAt(0) == 'S') {
                            SalariedEmployee salariedEmployee = (SalariedEmployee) salariedEmpSqlConnector.getEmployee(empId);
                            deductServiceCharge(sc, salariedEmployee, employeeUnion);
                        }
                    }
                    else if(ty==7)
                    {
                        salaryAccountant.doDailyWork();
                        hourlyPayAccountant.doDailyWork();
                    }
                }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }


    private static void deductServiceCharge(Scanner sc, Employee employee, EmployeeUnion employeeUnion) throws Exception {
        System.out.println("Select Service Charge Type");
        System.out.println("1. FestivalFee");
        System.out.println("2. MembershipFee");
        System.out.println("3. UnionRoutineFee");
        int type=sc.nextInt();
        System.out.println("Enter StartDateOfCharges");
        Timestamp startDate = inputTimestamp(sc);
        System.out.println("Enter EndDateOfCharges");
        Timestamp endDate = inputTimestamp(sc);
        System.out.println("Enter Rate of Charges");
        double rate = sc.nextDouble();
        if(type==1)
        {
            employeeUnion.deductServiceCharge(employee,new FestivalFee(rate),startDate,endDate);
        }
        else if(type==2)
        {
            employeeUnion.deductServiceCharge(employee,new MembershipFee(rate),startDate,endDate);
        }
        else if(type==3)
        {
            employeeUnion.deductServiceCharge(employee,new UnionFee(rate),startDate,endDate);
        }
    }

    private static void insertNewSalesReceipt(Scanner sc, SaleReceiptsDBConnector dbConnector, CommissionAccountant commissionAccountant) throws Exception {
        System.out.println("Enter EmployeeID");
        String empId = sc.next();
        System.out.println("Enter Sale Date");
        Timestamp saleDate = inputTimestamp(sc);
        System.out.println("Enter sale amount");
        double amount = sc.nextDouble();
        SalesReceipt salesReceipt = new SalesReceipt(saleDate,amount);
        if(commissionAccountant.checkSalesReceipt(empId,salesReceipt))
            commissionAccountant.submitSalesReceipt(empId,salesReceipt);
        else
            throw new Exception("Invalid Sales Receipt");
    }

    private static void insertNewTimeCard(Scanner sc, TimeCardDBConnector timeCardDBConnector) throws Exception {
        System.out.println("Enter Submission Date");
        Timestamp submitDate = inputTimestamp(sc);
        System.out.println("Enter Start Date");
        Timestamp startDate = inputTimestamp(sc);
        System.out.println("Enter End Date");
        Timestamp endDate = inputTimestamp(sc);
        System.out.println("Enter EmployeeID");
        String empId = sc.next();
        HourlyEmployee employee = (HourlyEmployee) timeCardDBConnector.getEmployee(empId);
        employee.submitTimeCardToAccountant(new TimeCard(submitDate,startDate,endDate));
    }

    private static void insertNewSalaryEmployee(Scanner sc, SalariedEmpSqlConnector salariedEmpSqlConnector) throws SQLException {
        //public SalariedEmployee(String name, String employeeId, Timestamp joiningDate, double monthlySalary, PaymentMode paymentMode)
        System.out.println("Enter name of employee");
        String empName = sc.next();
        System.out.println("Enter ID of employee");
        String empID = sc.next();
        System.out.println("Enter Joining Date TimeStamp Format");
        Timestamp joiningDate = inputTimestamp(sc);
        System.out.println("Enter Monthly Salary");
        double monthlyRate = sc.nextDouble();
        salariedEmpSqlConnector.insertEmployee(new SalariedEmployee(empName,empID,joiningDate,monthlyRate,new BankTransfer()));
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
    private static void insertNewHourlyEmployee(Scanner sc, HourlyEmpSqlConnector hourlyEmpSqlConnector) throws SQLException {
        //public HourlyEmployee(String name, String employeeId, Timestamp joiningDate, double hourlyRate)
        System.out.println("Enter name of employee");
        String empName = sc.next();
        System.out.println("Enter ID of employee");
        String empID = sc.next();
        System.out.println("Enter Joining Date TimeStamp Format");
        Timestamp joiningDate = inputTimestamp(sc);
        System.out.println("Enter Hourly Rate");
        double hourlyRate = sc.nextDouble();
        hourlyEmpSqlConnector.insertEmployee(new HourlyEmployee(empName,empID,joiningDate,hourlyRate, new BankTransfer()));
    }
}
