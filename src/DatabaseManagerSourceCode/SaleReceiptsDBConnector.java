package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.SalesReceipt;
import EmployeeSourceCode.Employee;

import java.util.ArrayList;

public interface SaleReceiptsDBConnector
{
    public void insertSalesReceipt(SalesReceipt salesReceipt, String employeeID) throws Exception;
    public ArrayList<SalesReceipt> getEmployeeSalesReceipt(Employee employee) throws Exception;
}
