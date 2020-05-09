package MainApplication;

import AccountantSourceCode.CommissionAccountant;
import AccountantSourceCode.HourlyPayAccountant;
import AccountantSourceCode.SalaryAccountant;
import AccountantSourceCode.TimeCardAccountant;
import DatabaseManagerSourceCode.HourlyEmpSqlConnector;
import DatabaseManagerSourceCode.SalariedEmpSqlConnector;
import EmployeeSourceCode.HourlyEmployee;
import EmployeeSourceCode.SalariedEmployee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application
{
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
    }
}
