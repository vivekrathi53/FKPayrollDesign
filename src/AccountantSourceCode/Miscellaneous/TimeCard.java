package AccountantSourceCode.Miscellaneous;


import java.sql.Timestamp;

public class TimeCard
{
    private Timestamp  submitDate;
    private Timestamp  startTimestamp  ;
    private Timestamp  endTimestamp  ;

    public TimeCard(Timestamp  submitDate, Timestamp   startTimestamp  , Timestamp   endTimestamp  ) {
        this.submitDate  = submitDate;
        this.startTimestamp   = startTimestamp  ;
        this.endTimestamp   = endTimestamp  ;
    }

    public Timestamp getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Timestamp  submitDate) {
        this.submitDate = submitDate;
    }

    public Timestamp   getStartTimestamp  () {
        return startTimestamp  ;
    }

    public void setStartTimestamp  (Timestamp   startTimestamp  ) {
        this.startTimestamp   = startTimestamp  ;
    }

    public Timestamp   getEndTimestamp  () {
        return endTimestamp  ;
    }

    public void setEndTimestamp  (Timestamp   endTimestamp  ) {
        this.endTimestamp   = endTimestamp  ;
    }
}
