package chasingclub.server.core.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AutoRestart {
    public static List<String> restartTimes = new ArrayList<>();
    public static String GetTime(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Bangkok"));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(cal.getTime());
    }

    public static void CheckTime(){
        String time = GetTime();

        System.out.println(time);
        if(time.equals("06:30")){
            System.out.println("Restarting");
        }

    }
}
