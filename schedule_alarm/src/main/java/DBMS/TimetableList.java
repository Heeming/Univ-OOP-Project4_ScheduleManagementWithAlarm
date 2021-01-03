package DBMS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimetableList {
	private ArrayList<Timetable> timetableList = new ArrayList<Timetable>();
	
	private DBManager DB = null;
	private ScheduleList scheduleList = null;
	
	TimetableList(DBManager DB, ScheduleList scheduleList) {
		int i;
		
		this.DB = DB;
		this.scheduleList = scheduleList;
		
		ArrayList<Object[]> result = DB.getTimetableList();
		Timetable temp;
		for (i = 0; i < result.size(); i++) {
		    temp = new Timetable(DB, scheduleList, (int)result.get(i)[0], (int)result.get(i)[1], (String)result.get(i)[2], 
		    		(LocalDate)result.get(i)[3], (int)result.get(i)[4]);
		    if (!temp.checkIsError()) timetableList.add(temp);
		}
	}
	
	public boolean addTimetable(Timetable input) {
		if (input.checkIsError()) return false;
	
		DB.addTimetable(input);
		timetableList.add(input);
		return true;
	}
	
	public int numOfTimetable() {
		return timetableList.size();
	}
	
	public Timetable getTimetable(int year, int semester) {
		return getTimetable(find(year, semester));
	}
	
	public Timetable getTimetable(int i) {
		Timetable result = null;
		
		if (i >= 0 && i < timetableList.size()) result = timetableList.get(i); 
		return result;
	}
	
	public int find(int year, int semester) {
		int index;
		for (index = 0; index < timetableList.size(); index++) {
			if (timetableList.get(index).getYear() == year && timetableList.get(index).getSemester() == semester)
				break;
		}
		System.out.println("index: "+index);
		
		if (index < 0 || index >= timetableList.size()) index = -1;
		return index;
	}
	
	public int find(LocalDate selected) {
		int index;
		
		for (index = 0; index < timetableList.size(); index++) {
			if (timetableList.get(index).getStartDate().compareTo(selected.minusDays(timetableList.get(index).getStartDate().getDayOfWeek().getValue() % 7)) >= 0
				&& timetableList.get(index).getStartDate().plusDays(timetableList.get(index).getTotalWeek() * 7)
				.compareTo(selected.plusDays(6 - timetableList.get(index).getStartDate().getDayOfWeek().getValue() % 7)) <= 0)
				break;
		}
		
		if (index < 0 || index >= timetableList.size()) index = -1;
		return index;
	}
}
