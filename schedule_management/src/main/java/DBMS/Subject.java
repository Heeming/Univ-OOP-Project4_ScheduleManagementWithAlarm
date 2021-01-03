package DBMS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Subject {
	public final static String CAU_URL = "https://eclass3.cau.ac.kr/courses";
	
	private String code = null;
	private String name = null;
	private String profName = null;
	private String memo = null;
	
	private DBManager DB = null;
	private ScheduleList scheduleList = null;
	
	private ArrayList<SubjectDayTime> dayTimeList = new ArrayList<SubjectDayTime>();
	private boolean isError = false;
	
	Subject(DBManager DB, ScheduleList scheduleList, ArrayList<SubjectDayTime> dayTimeList, LocalDate startDate, int totalWeek,
			String codeOrUrl, String name, String profName, String memo) {
		// 새로 추가
		this.DB = DB;
		this.scheduleList = scheduleList;
		this.dayTimeList = dayTimeList;
		
		setCode(codeOrUrl);
		setName(name);
		setProfName(profName);
		setMemo(memo);
		
		// dayTime의 lecture code, url 바꾸기
		int i;
		
		for (i = 0; i < dayTimeList.size(); i++) {
			dayTimeList.get(i).initCode(code);
			dayTimeList.get(i).initOnlineLecUrl(getUrl());
		}
	}

	Subject(DBManager DB, ScheduleList scheduleList, String codeOrUrl, String name, String profName, String memo) {
		int i;
		
		this.DB = DB;
		this.scheduleList = scheduleList;
		
		setCode(codeOrUrl);
		setName(name);
		setProfName(profName);
		setMemo(memo);
		
		// set daytimeList
		ArrayList<Object[]> result = DB.getSubjectDaytime(code);
		
		SubjectDayTime temp;
		for (i = 0; i < result.size(); i++) {
			temp = new SubjectDayTime(scheduleList, code, (DayOfWeek)result.get(i)[0], (LocalTime)result.get(i)[1], 
					(LocalTime)result.get(i)[2], (String)result.get(i)[3]);
			if (!temp.checkIsError()) dayTimeList.add(temp);
		}	
	}
	
	public static String getUrlFromCode(String code) {
		return "https://eclass3.cau.ac.kr/courses/" + code;
	}
	
	public int numOfSubject() {
		return dayTimeList.size();
	}

	public String getLine() {
		// System.out.println("subject");
		
		int i;
		String result = code + "//" + name + "//" + getProfName() + "//" + getMemo() + "\n\n";
		
		for (i = 0; i < dayTimeList.size(); i++) {
			result += dayTimeList.get(i).getLine() + "\n";
		}
		return result;
	}
	
	public ArrayList<SubjectDayTime> getDayTimeList() {
		return dayTimeList;
	}
	
	public SubjectDayTime getDayTime(int index) {
		if (index <= 0 || index >= dayTimeList.size()) return null;
		return dayTimeList.get(index);
	}
	
	public SubjectDayTime getDayTime(DayOfWeek dayOfWeek) { // 요일 정보를 통해 해당 SubjectDayTime 객체 찾음
		int i;
		
		for (i = 0; i < dayTimeList.size(); i++) {
			if (dayTimeList.get(i).getDayOfWeek() == dayOfWeek) 
				return dayTimeList.get(i);
		}
		
		return null;
	}
	
	public String getCode() {
		return new String(code);
	}
	
	public String getName() {
		return new String(name);
	}
	
	public boolean isProfNameNull() {
		if(profName == null) return true;
		return false;
	}
	
	public boolean isMemoNull() {
		if(memo == null) return true;
		return false;
	}
	
	public String getProfName() {
		String result;
		
		if (profName == null || profName.equals("")) result = new String("null");
		else result = new String(profName);
		
		return result;
	}
	
	public String getMemo() {
		String result; 
		
		if (memo == null || memo.equals("")) result = new String("null");
		else result = new String(memo);
		
		return result;
	}
	
	public String getUrl() {
		return CAU_URL + "/" + code;
	}
	
	public int numOfDaytime() {
		return dayTimeList.size();
	}
	
	private boolean setCode(String input) {
		int i;
		
		if (isNoCode(input)) {
			if (isNoUrl(input)) {
				isError = true;
				return false;
			}
			String[] tokens = input.split("/");
			
			for (i = 0; i < tokens.length; i++) {
				// System.out.println(tokens[i]);
				
				if (tokens[i].equals("courses") && i + 1 < tokens.length) { 
					if (!isNoCode(tokens[i + 1])) {
						code = tokens[i + 1];
						return true;
					}
				}
			}
			
			isError = true;
			return false;
		}
		code = input;
		
		return true;
	}
	
	private boolean setName(String input) {
		if (isNoTitle(input)) return false; 
		
		name = input;
		return true;
	}
	
	private boolean setProfName(String input) {
		if (input == null || input.trim().equals("") || input.equals("null")) profName = null;
		else profName = input;
		
		return true;
	}
	
	private boolean setMemo(String input) {
		if (input == null || input.trim().equals("") || input.equals("null")) memo = null;
		else memo = input;
				
		return true;
	}

	public boolean checkIsError() {
		return isError;
	}
	
	private boolean isNoUrl(String input) {
		if (input != null && input.matches("^" + CAU_URL + "(\\/(\\w*))*$")) return false;
		
		return true;
	}
	
	private boolean isNoCode(String input) {
		if (input != null && input.matches("^\\d{5}$")) return false;

		return true;
	}
	
	private boolean isNoTitle(String name) { // 타이틀 없음
		if (name != null && !name.equals("")) return false;
			
		isError = true;
		return true;
	}
}