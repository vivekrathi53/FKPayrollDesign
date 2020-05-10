package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.TimeCard;
import EmployeeSourceCode.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeCardDBConnector
{
    public Employee getEmployee(String employeeId) throws Exception;
    public void insertTimeCard(TimeCard timeCard, String employeeID) throws Exception;
    public ArrayList<TimeCard> getEmployeeTimeCard(String employeeID) throws Exception;
}
