package AccountantSourceCode.PaymentRelatedClasses;
import EmployeeSourceCode.*;

import java.sql.Timestamp;
public class PayCheque
{
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    private double amount;
    private String employeeID;
    private Timestamp issueDate;

    public PayCheque(double amount, String employeeID,  Timestamp issueDate) {
        this.amount = amount;
        this.employeeID = employeeID;
        this.issueDate = issueDate;
    }

}