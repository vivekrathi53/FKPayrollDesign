package AccountantSourceCode.Miscellaneous;


import java.sql.Timestamp;

public class SalesReceipt
{
    private Timestamp saleDate;
    private double amountOfSale;

    public SalesReceipt(Timestamp saleDate, double amountOfSale) {
        this.saleDate = saleDate;
        this.amountOfSale = amountOfSale;
    }

    public Timestamp getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Timestamp saleDate) {
        this.saleDate = saleDate;
    }

    public double getAmountOfSale() {
        return amountOfSale;
    }

    public void setAmountOfSale(double amountOfSale) {
        this.amountOfSale = amountOfSale;
    }
}
