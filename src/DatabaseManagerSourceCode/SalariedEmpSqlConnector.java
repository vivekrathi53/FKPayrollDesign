package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.Miscellaneous.TimeCard;
import AccountantSourceCode.PaymentRelatedClasses.*;
import EmployeeSourceCode.*;


import java.sql.*;
import java.util.ArrayList;

public class SalariedEmpSqlConnector extends MySqlConnector
{
    String url;
    Connection connection;
    public SalariedEmpSqlConnector(Connection connection)
    {
        this.connection=connection;
    }


    @Override
    public void insertEmployee(Employee employee) throws SQLException
    {
        String query = "INSERT INTO EmployeeTable  VALUES (?, ?, ?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1, employee.getName());
        preStat.setString(2, employee.getEmployeeId());
        preStat.setTimestamp(3,employee.getJoiningDate());
        preStat.setInt(4,convertPaymentMode(employee.getPaymentMode()));
        preStat.executeUpdate();
        insertSalariedEmployee((SalariedEmployee) employee);

    }

    /*@Override
    public void deleteEmployee(Employee e) throws SQLException {

    }*/

    private void insertSalariedEmployee(SalariedEmployee salariedEmployee) throws SQLException
    {
        String query = "INSERT INTO EmployeeMonthlySalaryTable Values (?,?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1,salariedEmployee.getEmployeeId());
        preStat.setDouble(2,salariedEmployee.getMonthlySalary());
        preStat.executeUpdate();
    }


    @Override
    public double getEmployeeRate(Employee employee) throws SQLException {
        return getEmployeeMonthlyRate(employee.getEmployeeId());
        //return 0;// non registered Employee
    }

    @Override
    public ArrayList<Employee> getAllEmployees() throws SQLException
    {
        String query = "SELECT * FROM EmployeeTable ";
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        ArrayList<Employee> employeeList = new ArrayList<>();
        while(resultSet.next())
        {
            String employeeID = resultSet.getString("EmployeeID");
            if(employeeID.charAt(0)=='S')
                employeeList.add(new SalariedEmployee(resultSet.getString("Name"),resultSet.getString("EmployeeID"),resultSet.getTimestamp("JoiningDate"),getEmployeeMonthlyRate(employeeID),getPaymentMode(resultSet.getInt("PaymentMode"))));
        }
        return employeeList;
    }

    private double getEmployeeMonthlyRate(String employeeID) throws SQLException {
        String query = "SELECT * FROM EmployeeMonthlySalaryTable WHERE EmployeeID = "+employeeID;
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        resultSet.next();
        return resultSet.getDouble("MonthlySalary");
    }

}