package AccountantSourceCode.ServiceFeeClasses;

import java.util.Date;

public class FestivalFee implements ServiceFee
{

    @Override
    public double generateFee(Date startDate, Date endDate) {
        return 0;
    }
}