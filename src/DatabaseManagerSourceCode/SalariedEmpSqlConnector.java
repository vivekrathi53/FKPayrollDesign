package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.Miscellaneous.TimeCard;
import AccountantSourceCode.PaymentRelatedClasses.*;
import EmployeeSourceCode.*;


import java.sql.*;
import java.util.ArrayList;

public class SalariedEmpSqlConnector  implements SaleReceiptsDBConnector, DBConnector
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
        String query = "INSERT INTO EmployeeTable  VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1, employee.getName());
        preStat.setString(2, employee.getEmployeeId());
        preStat.setTimestamp(3,employee.getJoiningDate());
        preStat.setInt(4,convertPaymentMode(employee.getPaymentMode()));
        preStat.setDouble(5,0);
        preStat.executeUpdate();
        insertSalariedEmployee((SalariedEmployee) employee);

    }

    @Override
    public Employee getEmployee(String employeeId) throws Exception {
        String query = "SELECT * FROM EmployeeTable WHERE EmployeeID='"+employeeId+"'";
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        query = "SELECT * FROM EmployeeMonthlySalaryTable WHERE EmployeeId='"+employeeId+"'";
        preStat = connection.prepareStatement(query);
        ResultSet resultSet2 = preStat.executeQuery();
        if(resultSet.next()&&resultSet2.next())
        {
            return new SalariedEmployee(resultSet.getString("Name"),resultSet.getString("EmployeeID"),
                    resultSet.getTimestamp("JoiningDate"),resultSet2.getDouble("MonthlySalary"),getPaymentMode(resultSet.getInt("PaymentMode")));
        }
        else
            throw new Exception("NO SUCH EMPLOYEE");
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
        String query = "SELECT * FROM EmployeeMonthlySalaryTable WHERE EmployeeID = '"+employeeID+"'";
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        resultSet.next();
        return resultSet.getDouble("MonthlySalary");
    }
    public static int convertPaymentMode(PaymentMode paymentMode)
    {
        if(paymentMode instanceof BankTransfer)
            return 1;
        else if(paymentMode instanceof PaymentByMail)
            return 2;
        else if(paymentMode instanceof PaymentByPickup)
            return 3;
        return 0;// illegal paymentMode
    }
    public static PaymentMode getPaymentMode(int id)
    {
        if(id==1)
            return new BankTransfer();
        else if(id==2)
            return new PaymentByMail();
        else if(id==3)
            return new PaymentByMail();
        return null;// illegal id
    }
    /*@Override
    public void deleteEmployee(Employee e) throws SQLException {

    }*/

    @Override
    public double getEmployeeDues(Employee employee) throws Exception {
        String query = "SELECT * FROM EmployeeTable WHERE EmployeeID = "+employee.getEmployeeId();
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        resultSet.next();
        return resultSet.getDouble("DueCharges");
    }

    @Override
    public void setEmployeeDues(Employee employee,double dueAmount) throws Exception {
        String query = "UPDATE EmployeeTable SET DueCharges = "+dueAmount+" WHERE EmployeeID = '"+employee.getEmployeeId()+"'";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.executeUpdate();
        return;
    }

    @Override
    public void insertSalesReceipt(SalesReceipt salesReceipt, String employeeId) throws SQLException
    {
        String query = "INSERT INTO SalesReceiptTable VALUES (?, ?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1, employeeId);
        preStat.setTimestamp(2, salesReceipt.getSaleDate());
        preStat.setDouble(3, salesReceipt.getAmountOfSale());
        preStat.executeUpdate();
    }

    @Override
    public ArrayList<SalesReceipt> getEmployeeSalesReceipt(Employee employee) throws Exception {
        String query = "SELECT * FROM SalesReceiptTable WHERE EmployeeID = '"+employee.getEmployeeId()+"'";
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        ArrayList<SalesReceipt> saleReceiptList = new ArrayList<>();
        while(resultSet.next())
        {
            saleReceiptList.add(new SalesReceipt(resultSet.getTimestamp("saleDate"),resultSet.getDouble("amountOfSale")));
        }
        return saleReceiptList;
    }

    @Override
    public Timestamp getEmployeeJoiningDate(Employee employee) throws SQLException
    {
        String query = "SELECT * FROM EmployeeTable WHERE EmployeeID = "+employee.getEmployeeId();
        PreparedStatement preStat = connection.prepareStatement(query);
        ResultSet resultSet = preStat.executeQuery();
        resultSet.next();
        return resultSet.getTimestamp("JoiningDate");
    }
}