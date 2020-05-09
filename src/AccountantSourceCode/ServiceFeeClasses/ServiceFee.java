package AccountantSourceCode.ServiceFeeClasses;

import java.util.Date;

public interface ServiceFee
{
    double generateFee(Date startDate, Date endDate);
}