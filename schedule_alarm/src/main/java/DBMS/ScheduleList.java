package DBMS;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleList {
	// 데이터
	ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();
	private DBManager DB = null;
	
	// 생성자
	ScheduleList(DBManager DB) {
		int i;
		Schedule temp;
		
		this.DB = DB;
		if (DB == null) System.out.println("Fail to connect DB. ");
		
		ArrayList<Object[]> result = DB.getNormalScheduleList();
		for (i = 0; i < result.size(); i++) {
			temp = new Schedule((long)result.get(i)[0], (String)result.get(i)[1], (LocalDateTime)result.get(i)[2],
					(LocalDateTime)result.get(i)[3], (String)result.get(i)[4]);
			if (!temp.checkIsError()) scheduleList.add(temp);
		}
		
		result.clear();
		result = DB.getZoom();
		for (i = 0; i < result.size(); i++) {
			temp = new Zoom((DayOfWeek)result.get(i)[0], (String)result.get(i)[1], (long)result.get(i)[2],
					(int)result.get(i)[3], (int)result.get(i)[4], (String)result.get(i)[5], (boolean)result.get(i)[6], (String)result.get(i)[7], 
					(String)result.get(i)[8], (String)result.get(i)[9],(LocalDateTime)result.get(i)[10],
					(LocalDateTime)result.get(i)[11], (String)result.get(i)[12]);
			if (!temp.checkIsError()) scheduleList.add(temp);
		}
		
		result.clear();
		result = DB.getOnlineLec();
		for (i = 0; i < result.size(); i++) {
			temp = new OnlineLecture((DayOfWeek)result.get(i)[0], (String)result.get(i)[1], (long)result.get(i)[2],
					(int)result.get(i)[3], (int)result.get(i)[4], (String)result.get(i)[5], (boolean)result.get(i)[6], 
					(String)result.get(i)[7], (LocalDateTime)result.get(i)[8],
					(LocalDateTime)result.get(i)[9], (String)result.get(i)[10]);
			if (!temp.checkIsError()) scheduleList.add(temp);
		}
	}
	
	// 메소드
	public int numOfSchedules() {
		return scheduleList.size();
	}
	
	public int numOfAfterLecture(LocalDateTime today) {
		int i, count = 0;
		
		for (i = 0; i < scheduleList.size(); i++) {
			if (scheduleList.get(i).isAfter(today) && (scheduleList.get(i) instanceof Lecture) 
				&& ((Lecture)scheduleList.get(i)).checkIsDone()) count++;
		}
		
		return count;
	} 
	
	public int numOfAfterZoom(LocalDateTime today) {
		int i, count = 0;
		
		for (i = 0; i < scheduleList.size(); i++) {
			if (scheduleList.get(i).isAfter(today) && (scheduleList.get(i) instanceof Zoom) 
				&& ((Lecture)scheduleList.get(i)).checkIsDone()) count++;
		}
		
		return count;
	} 
	
	public int numOfAfterSchedules(LocalDateTime today) {
		int i, count = 0;
		
		for (i = 0; i < scheduleList.size(); i++) {
			if (scheduleList.get(i).isAfter(today)) count++;
		}
		
		return count;
	}
	
	public Schedule getSchedule(int i) { 
		if (i >= 0 && i < scheduleList.size()) return scheduleList.get(i);
		return null;
	}
	
	public ArrayList<Lecture> getLectures(String code, DayOfWeek dayOfWeek) {
		int i;
		ArrayList<Lecture> result = new ArrayList<Lecture>();
			
		for (i = 0; i < scheduleList.size(); i++) {
			if ((scheduleList.get(i) instanceof Lecture) && ((Lecture)scheduleList.get(i)).getSubjectCode().equals(code)
				&& ((Lecture)scheduleList.get(i)).getDayOfWeek().equals(dayOfWeek)) {
				result.add((Lecture)scheduleList.get(i));
			}
		}
			
		return result;
	}
	
	
	
	// edit
	public ArrayList<Schedule> getTodayList(LocalDate selected) { // selected에 해당하는 스케쥴
		int i;
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		for (i = 0; i < scheduleList.size(); i++) {
			if (scheduleList.get(i).isToday(selected)) result.add(scheduleList.get(i));
		}
		
		return result;
	}
	
	public ArrayList<Schedule> getTodayNormalList(LocalDate selected) { // selected에 해당하는 일반 스케쥴
		int i;
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		for (i = 0; i < scheduleList.size(); i++) {
			if (!(scheduleList.get(i) instanceof Lecture) && scheduleList.get(i).isToday(selected)) result.add(scheduleList.get(i));
		}
		
		return result;
	}
	
	public ArrayList<Schedule> getTodayZoomList(LocalDate selected) { // selected에 해당하는 Zoom 스케쥴
		int i;
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		for (i = 0; i < scheduleList.size(); i++) {
			if ((scheduleList.get(i) instanceof Zoom) && scheduleList.get(i).isToday(selected)&&scheduleList.get(i).isAfter(LocalDateTime.now()));
				result.add(scheduleList.get(i));
		}
		
		return result;
	}
	
	public ArrayList<Schedule> getTodayLecList(LocalDate selected) { // selected에 해당하는 OnlineLecture 스케쥴
		int i;
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		
		for (i = 0; i < scheduleList.size(); i++) {
			if ((scheduleList.get(i) instanceof OnlineLecture) && scheduleList.get(i).isToday(selected)) result.add(scheduleList.get(i));
		}
	
		return result;
	}
	
	
	public ArrayList<Lecture> getNowZoom(LocalDateTime now, int minuteTerm) {
		int i;
		ArrayList<Lecture> result = new ArrayList<Lecture>();
		
		for (i = 0; i < scheduleList.size(); i++) {
			if ((scheduleList.get(i) instanceof Lecture) && scheduleList.get(i).isNow(now )
				&& ((Lecture)scheduleList.get(i)).checkIsDone()) result.add((Lecture) scheduleList.get(i));
		}
		
		return result;
	}
	
	public void updateList(ArrayList<Schedule> oldSchedules, ArrayList<Schedule> newSchedules) {
		DB.delSchedule(oldSchedules);
		
		int i;
		for (i = 0; i < oldSchedules.size(); i++) {
			if (scheduleList.remove(oldSchedules.get(i)));
		}
		
		for (i = 0; i < newSchedules.size(); i++) {
			if (!newSchedules.get(i).checkIsError()) scheduleList.add(newSchedules.get(i)); 
		}
		
		DB.addSchedule(newSchedules);
	}
}