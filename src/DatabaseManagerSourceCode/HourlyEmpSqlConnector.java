package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.Miscellaneous.TimeCard;
import AccountantSourceCode.PaymentRelatedClasses.*;
import EmployeeSourceCode.*;


import java.sql.*;
import java.util.ArrayList;

public class HourlyEmpSqlConnector extends MySqlConnector implements TimeCardDBConnector
{
    String url;
    Connection connection;
    public HourlyEmpSqlConnector(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void insertEmployee(Employee employee) throws SQLException
    {
        String query = "INSERT INTO EmployeeTable  VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1, employee.getName());
        preStat.setString(2, employee.getEmployeeId());
        preStat.setTimestamp(3,employee.getJoiningDate());
        preStat.setInt(4,convertPaymentMode(employee.getPaymentMode()));
        preStat.setDouble(5,0);
        preStat.executeUpdate();
        insertHourlyEmployee((HourlyEmployee)employee);
    }

    /*@Override
    public void deleteEmployee(Employee e) throws SQLException {

    }*/


    private void insertHourlyEmployee(HourlyEmployee hourlyEmployee) throws SQLException
    {
        String query = "INSERT INTO EmployeeHourlySalaryTable  VALUES (?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1,hourlyEmployee.getEmployeeId());
        preStat.setDouble(2,hourlyEmployee.getHourlyRate());
        preStat.executeUpdate();
    }



    public void insertTimeCard(TimeCard timeCard,Employee employee) throws SQLException {
        String query = "INSERT INTO TimeCardTable VALUES (?, ?, ?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1, employee.getEmployeeId());
        preStat.setTimestamp(2, timeCard.getSubmitDate());
        preStat.setTimestamp(3, timeCard.getStartTimestamp());
        preStat.setTimestamp(4,timeCard.getEndTimestamp());

    }






    public ArrayList<TimeCard> getEmployeeTimeCard(Employee employee) throws Exception {
        String query = "SELECT * FROM TimeCardTable WHERE EmployeeID = "+employee.getEmployeeId();
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        ArrayList<TimeCard > timeCardsList = new ArrayList<>();
        while(resultSet.next())
        {
            timeCardsList.add(new TimeCard(resultSet.getTimestamp("submitDate"),resultSet.getTimestamp("startTimeStmap"),resultSet.getTimestamp("endTimeStamp")));
        }
        return timeCardsList;
    }


    @Override
    public double getEmployeeRate(Employee employee) throws SQLException {
        return getEmployeeHourlyRate(employee.getEmployeeId());
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
            if(employeeID.charAt(1)=='H')
                employeeList.add(new SalariedEmployee(resultSet.getString("Name"),resultSet.getString("EmployeeID"),resultSet.getTimestamp("JoiningDate"),getEmployeeHourlyRate(employeeID),getPaymentMode(resultSet.getInt("PaymentMode"))));
        }
        return employeeList;
    }


    private double getEmployeeHourlyRate(String employeeID) throws SQLException {
        String query = "SELECT * FROM EmployeeHourlySalaryTable WHERE EmployeeID = "+employeeID;
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        resultSet.next();
        return resultSet.getDouble("HourlyRate");
    }


}