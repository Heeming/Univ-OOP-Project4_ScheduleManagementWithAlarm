package DBMS;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class TestMain {
    public static void main(String[] args) {
		DBManager DB = new DBManager();
		
		ScheduleList schedules = new ScheduleList(DB);
		
		TimetableList timetables = new TimetableList(DB, schedules);
		// DB�� ����� ���̺� �׽�Ʈ
		System.out.println(timetables.getTimetable(0).getLine());
		
		// �� �ð�ǥ ����
		//timetables.addTimetable(new Timetable(DB, 2020, 1, null, LocalDate.of(2020, 03, 15), 16));
		
		// dayTime arrayList
		ArrayList<SubjectDayTime> newDayTime = new ArrayList<SubjectDayTime>();
		
		// �� DayTime����
		//SubjectDayTime temp = new SubjectDayTime(schedules, DayOfWeek.FRIDAY, "11:00", "12:00", "310-727", 
		//		null, "929 1662 3828", "951867", "12345", 1, LocalDate.of(2020, 03, 15), 16); // null�� zoom url
		//newDayTime.add(temp);
		
		// �� DayTime����
		//temp = new SubjectDayTime(schedules, DayOfWeek.TUESDAY, "11:00", "13:00", "310-727", 
		//		10, "12345", 1, LocalDate.of(2020, 03, 15), 16); // null�� zoom url
		//newDayTime.add(temp);
		
		// �� ���� ����, add
		//timetables.getTimetable(0).updateSubject(null, newDayTime, "database", 
		//		"https://eclass3.cau.ac.kr/courses/12345", "Kang", null);
		
		System.out.println("Hello");
		
		MonthFrame calendar = new MonthFrame(schedules);
		
		SelectTimetableFrame stable = new SelectTimetableFrame(DB);
		
		//TimetableFrame tf = new TimetableFrame(schedules,timetables.getTimetable(0));
		//tf.makeTimetableFrame();
    	
    }
}