package DBMS;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Lecture extends Schedule { 
	protected String subjectCode;
	protected DayOfWeek dayOfWeek;
	
	protected int week;
	protected int serialNum;
	protected String url = null;
	
	protected boolean isDone = false;
	protected boolean isError = false;
	
	Lecture(DayOfWeek dayOfWeek, String subjectCode, long scheduleID, int week, int serialNum, String url, boolean isDone, // Lecture 생성자에 쓰임
			String name, String start, String end, String memo) { // Schedule 생성자에 쓰임
		super(scheduleID, name, start, end, memo);
		
		setSubjectCode(subjectCode);
		setDayOfWeek(dayOfWeek);
		setWeek(week);
		setSerialNum(serialNum);
		setUrl(url);
		setIsDone(isDone);
	}
	
	Lecture(DayOfWeek dayOfWeek, String subjectID, long scheduleID, int week, int serialNum, String url, boolean isDone,
			String name, LocalDateTime start, LocalDateTime end, String memo) {
		super(scheduleID, name, start, end, memo);
		
		setSubjectCode(subjectID);
		setDayOfWeek(dayOfWeek);
		setWeek(week);
		setSerialNum(serialNum);
		setUrl(url);
		setIsDone(isDone);
	}
	
	// 메소드	
	public String getLine() {
		// System.out.println("lecture");
		
		String result = subjectCode + "//" + dayOfWeek.toString() + "//" + week + "//" + serialNum + "//" + getUrl() + "//" + isDone;		
		return result + "//" + super.getLine();
	}
	
	public String getSubjectCode() {
		String temp = new String(subjectCode);
		return temp;
	}
	
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	
	public int getWeek() {
		return week;
	}
	
	public int getSerialNum() {
		return serialNum;
	}
	
	public String getUrl() {
		if (url == null || url.equals("") || url.equals("null")) return "null";
		
		String temp = new String(url);
		return temp;
	}
	
	public boolean isUrlNull() {
		if (url == null || url.equals("") || url.equals("null")) return true;
		return false;
	}
	
	public String getDayOfWeekString() {
		return dayOfWeek.name();
	}
	
	public boolean initSubjectCode(String input) {
		if (subjectCode == null) setSubjectCode(input);
		return true;
	}

	private boolean setSubjectCode(String input) {
		subjectCode = input; 
		return true;
	}
	
	private boolean setDayOfWeek(DayOfWeek input) {
		dayOfWeek = input; 
		return true;
	}
	
	private boolean setWeek(int input) {
		if (input <= 0) return false;
		
		week = input;
		return true;
	}
	
	private boolean setSerialNum(int input) {
		if (input <= 0) return false;
			
		serialNum = input;
		return true;
	}
	
	public boolean setIsDone(boolean set) {
		isDone = set;
		return true;
	}
	
	protected boolean setUrl(String input) {
		if (input == null )
		//|| !input.matches("^(http[s]?\\:\\/\\/)?((\\w+)[.])+(asia|biz|cc|cn|com|de|eu|in|info|jobs|jp|kr|mobi|mx|name|net|nz|org|travel|tv|tw|uk|us)(\\/(\\w*))*$")) 
			return false;
			
		url = input;
		return true;
	}
	
	public boolean checkIsDone() {
		return isDone;
	}

	public boolean checkIsError() {
		return isError;
	}
}