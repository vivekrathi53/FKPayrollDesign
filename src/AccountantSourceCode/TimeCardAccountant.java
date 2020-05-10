package AccountantSourceCode;
import AccountantSourceCode.Miscellaneous.TimeCard;
import EmployeeSourceCode.*;
import DatabaseManagerSourceCode.*;
import AccountantSourceCode.PaymentRelatedClasses.*;
public class TimeCardAccountant implements Accountant
{
    private TimeCardDBConnector dbconnector;
    public boolean checkTimeCard(HourlyEmployee hourlyEmployee, TimeCard timeCard)
    {
        return true; // if valid and accepted timeCard;
        //return false;// if invalid or rejected timeCard;
    }
    public void submitTimeCard(HourlyEmployee hourlyEmployee,TimeCard timeCard) throws Exception {
        // call DBconnector
        dbconnector.insertTimeCard(timeCard,hourlyEmployee);
    }
    public TimeCardAccountant(TimeCardDBConnector dbconnector)
    {
        this.dbconnector=dbconnector;
    }
}