package AccountantSourceCode.PaymentRelatedClasses;
import EmployeeSourceCode.*;

import java.sql.Timestamp;
public class PayCheque
{
    private double amount;
    private String employeeID;
    private Timestamp issueDate;

    public PayCheque(double amount, String employeeID,  Timestamp issueDate) {
        this.amount = amount;
        this.employeeID = employeeID;
        this.issueDate = issueDate;
    }

}