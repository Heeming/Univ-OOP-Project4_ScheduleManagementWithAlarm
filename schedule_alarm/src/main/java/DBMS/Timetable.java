package DBMS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Timetable {
	private int year;
	private int semester;

	private String name = null; 
	private LocalDate startDate = null; // 학기 시작 날짜
	private int totalWeek = 16; // 총 주차 
	
	private boolean isError = false;
	
	private ArrayList<Subject> subjectList = null;
	
	private DBManager DB = null;
	private ScheduleList scheduleList = null;
	
	Timetable(DBManager DB, ScheduleList scheduleList, int year, int semester, String name, LocalDate startDate, int week) { 
		// DB에서 생성
		int i;
		
		this.DB = DB;
		this.scheduleList = scheduleList;
		
		setYear(year);
		setSemester(semester);
		setName(name);
		setStartDate(startDate);
		setTotalWeek(week);
		
		ArrayList<Object[]> result = DB.getSubjectList(year, semester);
		
		subjectList = new ArrayList<Subject>();
		Subject temp;
		for (i = 0; i < result.size(); i++) {
			temp = new Subject(DB, scheduleList, (String)result.get(i)[0], (String)result.get(i)[1], (String)result.get(i)[2], (String)result.get(i)[3]);
			if (!temp.checkIsError()) subjectList.add(temp);
		}
	}
	
	Timetable(DBManager DB, int year, int semester, String name, LocalDate startDate, int week) { 
		// 새로 생성
		this.DB = DB;
		
		setYear(year);
		setSemester(semester);
		setName(name);
		setStartDate(startDate);
		setTotalWeek(week);
	}
	
	public void updateSubject(Subject oldSubject, ArrayList<SubjectDayTime> dayTimeList, String subName, String url, String profName, String memo) { 
		if (subjectList != null && subjectList.indexOf(oldSubject) != -1) {
			DB.delSubject(oldSubject, year, semester);
			subjectList.remove(oldSubject);
		}
		
		Subject temp = new Subject(DB, scheduleList, dayTimeList, startDate, totalWeek, url, subName, profName, memo);
		if (temp.checkIsError()) return;
		
		subjectList.add(temp);
		DB.addSubject(temp, year, semester);
	}
	
	public Subject getSubject(int i) {
		return subjectList.get(i);
	}

	public int numOfSubject() {
		return subjectList.size();
	}
	
	public ArrayList<Subject> getSubjectList(){
	      return subjectList;
	}
	
	public String getLine() {
		int i;
		// System.out.println("timetable");
		String result = year + "//" + semester + "//" + name + "//" + getStartDateString() + "//" + totalWeek + "\n\n";
		
		System.out.println("subjectList size: "+subjectList.size());
		for (i = 0; i < subjectList.size(); i++) {
			result += subjectList.get(i).getLine() + "\n";
		}
		return result;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getSemester() {
		return semester;
	}
	
	public String getName() {
		String temp = new String(name); 
		return temp;
	}
	
	public LocalDate getStartDate() {
		LocalDate temp = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
		return temp;
	}
	
	public int getTotalWeek() {
		return totalWeek;
	}
	
	public String getStartDateString() {
		String result = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return result;
	}
	
	private boolean setYear(int input) {
		if (isWrongYear(input)) return false;
		
		year = input;
		return true;
	}
	
	private boolean setSemester(int input) {
		if (isWrongSemester(input)) return false;
		
		semester = input;
		return true;
	}
	
	private boolean setName(String input) {
		if (input == null || input.equals("")) name = getYear() + " - semester " + getSemester();	
		else name = input;
		
		return true;
	}
	
	private boolean setStartDate(String input) {
		return setStartDate(mkParse(input));
	}
	
	private boolean setStartDate(LocalDate input) {
		if (isWrongStartDate(input)) return false;
		
		startDate = input;
		return true;
	}
	
	private boolean setTotalWeek(int input) {
		if (input <= 0) return false;
		
		totalWeek = input;
		return true;
	}
	
	private LocalDate mkParse(String input) {
		LocalDate result = null;
		if (isWrongFormat(input)) return result;
		
		result = LocalDate.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd")); 
		return result;
	}

	public boolean checkIsError() {
		return isError;
	}	
	
	private boolean isWrongYear(int year) {
		if (year > 0) return false;

		isError = true;
		return true;
	}
	
	private boolean isWrongSemester(int semester) {
		if (semester > 0) return false;
		
		isError = true;
		return true;
	}
	
	private boolean isWrongStartDate(LocalDate startDate) {
		if (startDate != null && startDate.getYear() == year) return false;
		
		isError = true;
		return true;
	}
	
	private boolean isWrongFormat(String startDate) {
		if (startDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			int year, month, date;
			
			String[] tokens = startDate.split("[- :]");
			
			year = Integer.parseInt(tokens[0]);
			month = Integer.parseInt(tokens[1]);
			date = Integer.parseInt(tokens[2]);
			
			if (year >= 0 && month > 0 && month <= 12 
				&& date > 0 && (date <= 28 // 28보다 작거나 같으면 무조건 만족 
				|| (date <= 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12))
				|| (date <= 30 && (month == 4 || month == 6 || month == 9 || month == 11))
				|| (date <= 29 && month == 2 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)))) // 윤년일 때
				) return false;
		}
			
		isError = true;
		return true;
	} 
}