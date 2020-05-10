package AccountantSourceCode.ServiceFeeClasses;
import EmployeeSourceCode.Employee;

import java.sql.Timestamp;

public interface ServiceFee
{
    double generateFee(Timestamp startDate, Timestamp endDate, Employee employee);
    public static long getHoursDifference(Timestamp startTime,Timestamp endTime)
    {
        long startSeconds = startTime.getTime()/1000;
        long endSeconds = endTime.getTime()/1000;
        return (endSeconds-startSeconds)/3600;
    }

    public static  long getDaysDifference(Timestamp joiningTime,Timestamp currentTime)
    {
        long totalHours = getHoursDifference(joiningTime,currentTime);
        return totalHours/24;
    }
}