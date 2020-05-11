package AccountantSourceCode.PaymentRelatedClasses;

public class PaymentByMail implements PaymentMode
{
    public void pay(PayCheque payCheque)
    {
        System.out.println("Employee: "+payCheque.getEmployeeID()+" paid "+payCheque.getAmount()+" on "+payCheque.getIssueDate()+" via Payment By Mail");
    }
}