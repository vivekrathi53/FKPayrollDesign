package AccountantSourceCode;
import AccountantSourceCode.Miscellaneous.SalesReceipt;
import EmployeeSourceCode.*;
import DatabaseManagerSourceCode.*;
import AccountantSourceCode.PaymentRelatedClasses.*;
public class CommissionAccountant implements Accountant
{
    private SaleReceiptsDBConnector dbconnector;
    public CommissionAccountant(SaleReceiptsDBConnector dbconnector)
    {
        this.dbconnector=dbconnector;
    }
    public boolean checkSalesReceipt(Employee employee, SalesReceipt salesReceipt)
    {
        return true; // if valid and accepted timeCard;
        //return false;// if invalid or rejected timeCard;
    }
    public void submitSalesReceipt(Employee employee,SalesReceipt salesReceipt) throws Exception {

        // call DBconnector
        dbconnector.insertSalesReceipt(salesReceipt,employee);
    }

}