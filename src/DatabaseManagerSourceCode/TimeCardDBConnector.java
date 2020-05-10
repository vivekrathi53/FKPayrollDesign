package DatabaseManagerSourceCode;

import AccountantSourceCode.Miscellaneous.TimeCard;
import EmployeeSourceCode.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TimeCardDBConnector
{
    public void insertTimeCard(TimeCard timeCard, Employee employee) throws Exception;
    public ArrayList<TimeCard> getEmployeeTimeCard(Employee employee) throws Exception;
}
