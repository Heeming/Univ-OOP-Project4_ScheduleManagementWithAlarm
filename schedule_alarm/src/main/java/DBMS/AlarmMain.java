package DBMS;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class AlarmMain {
    public static void main(String[] args) {
		    	
    	boolean MailFlag=true;
    	
        while (true) {
        	DBManager DM = new DBManager();
        	ScheduleList List = new ScheduleList(DM);
        	List.scheduleList=List.getTodayList(LocalDate.now());
        	LocalDateTime mailTime=LocalDateTime.of(LocalDate.now(),LocalTime.of(2, 8)); //input mail time
        	Date today = new Date();
        	System.out.println("start");
        	System.out.println(List.numOfSchedules());
        	System.out.println(List.getSchedule(0).getName());
        	if(LocalDateTime.now().isBefore(mailTime) && MailFlag) {
        		Alarm MailAlarm = new Alarm(List);
            	Timer t = new Timer();
            	Date d = new Date(today.getYear(), today.getMonth(), today.getDate(), 2, 8, 0); //input mail time
            	t.schedule(MailAlarm, d);
            	MailFlag=false;
        	}
        	int zoomN=List.numOfAfterZoom(LocalDateTime.now());
        	Alarm[] thread = new Alarm[zoomN];
        	Timer[] threadTimer = new Timer[zoomN];
        	Date [] threadTime = new Date[zoomN];
        	ArrayList<Schedule> zoomList=List.getTodayZoomList(LocalDate.now());
        	System.out.println(zoomN);
        	for(int i=0;i<zoomN;i++) {
        		thread[i]=new Alarm(zoomList.get(i));
        		System.out.println(zoomList.get(i).getName());
        		threadTimer[i]=new Timer();
        		LocalDateTime T=zoomList.get(i).getStart().minusMinutes(5);
        		threadTime[i]=new Date(today.getYear(), today.getMonth(), today.getDate(), T.getHour(),T.getMinute(), 0);
        		threadTimer[i]=new Timer();
        		threadTimer[i].schedule(thread[i], threadTime[i]);
        	}
        	
        	// while을 일정한 시간만큼 돌려야 함으로
            try {
            	LocalTime nowTime=LocalTime.now();
            	int TimeSec=nowTime.getHour()*3600+nowTime.getMinute()*60+nowTime.getSecond();
                Thread.sleep(1000 * (86400-TimeSec));
                MailFlag=true;
            } catch (Exception e) {
            	
            }
        }
    }
}