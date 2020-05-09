package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.Miscellaneous.TimeCard;
import AccountantSourceCode.PaymentRelatedClasses.*;
import EmployeeSourceCode.*;


import java.sql.*;
import java.util.ArrayList;

abstract public class MySqlConnector implements DBConnector, SaleReceiptsDBConnector
{
    String url;
    Connection connection;
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
    public void insertSalesReceipt(SalesReceipt salesReceipt, Employee employee) throws SQLException
    {
        String query = "INSERT INTO SalesReceiptTable VALUES (?, ?, ?)";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setString(1, employee.getEmployeeId());
        preStat.setTimestamp(2, salesReceipt.getSaleDate());
        preStat.setDouble(3, salesReceipt.getAmountOfSale());
    }

    @Override
    public ArrayList<SalesReceipt> getEmployeeSalesReceipt(Employee employee) throws Exception {
        String query = "SELECT * FROM SalesReceiptTable WHERE EmployeeID = "+employee.getEmployeeId();
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