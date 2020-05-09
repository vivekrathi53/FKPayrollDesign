package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import AccountantSourceCode.Miscellaneous.TimeCard;
import EmployeeSourceCode.Employee;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface DBConnector
{
    public void insertEmployee(Employee e) throws Exception;
    //public void deleteEmployee(Employee e) throws Exception;
    public void insertSalesReceipt(SalesReceipt salesReceipt, Employee employee) throws Exception;
    //public void insertTimeCard(TimeCard timeCard, Employee employee) throws  Exception;
    //public ArrayList<TimeCard> getEmployeeTimeCard(Employee employee) throws Exception;
    public ArrayList<SalesReceipt> getEmployeeSalesReceipt(Employee employee) throws Exception;
    public double getEmployeeRate(Employee employee) throws Exception;
    public Timestamp getEmployeeJoiningDate(Employee employee) throws Exception;
    public ArrayList<Employee> getAllEmployees() throws Exception;
}