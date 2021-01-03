package DBMS;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
	// 데이터
	private long id;
	
	private String name = null;
	private LocalDateTime start = null;
	private LocalDateTime end = null;
	private String memo = null;
	
	private boolean isError = false;
	
	// 생성자
	Schedule() { // 디폴트 생성자
		isError = true; 
	}
	
	Schedule(String name, LocalDateTime start, LocalDateTime end) {
		setID();
		setName(name);
		setStart(start);
		setEnd(end);
	}
	
	Schedule(String name, LocalDateTime start, LocalDateTime end, String memo) {
		setID();
		setName(name);
		setStart(start);
		setEnd(end);
		setMemo(memo);
	}
	
	Schedule(String name, String start, String end, String memo) {
		setID();
		setName(name);
		setStart(start);
		setEnd(end);
		setMemo(memo);
	}
	
	Schedule(long id, String name, LocalDateTime start, LocalDateTime end) {
		setID(id);
		setName(name);
		setStart(start);
		setEnd(end);
	}
	
	Schedule(long id, String name, LocalDateTime start, LocalDateTime end, String memo) {
		setID(id);
		setName(name);
		setStart(start);
		setEnd(end);
		setMemo(memo);
	}
	
	Schedule(long id, String name, String start, String end, String memo) {
		setID(id);
		setName(name);
		setStart(start);
		setEnd(end);
		setMemo(memo);
	}
	
	// 메소드	
	public static long mkID() {
		// System.out.println(System.currentTimeMillis());
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}

	public String getLine() {
		String result = null;
		
		if (!isError) {
			result = id + "//" + name + "//" + getStartString() + "//" + getEndString() + "//" + getMemo();
		}

		return result;
	} 
	
	public long getID() {
		return id;
	}
	
	public String getName() {
		String result = new String(name);
		return result;
	}

	public LocalDateTime getStart() {
		LocalDateTime result = LocalDateTime.of(start.toLocalDate(), start.toLocalTime());
		return result;
	}
	
	public LocalDateTime getEnd() {
		LocalDateTime result = LocalDateTime.of(end.toLocalDate(), end.toLocalTime());
		return result;
	}
	
	public String getStartString() {
		String result = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm"));
		return result;
	}
	
	public String getEndString() {
		String result = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm"));
		return result;
	}
	
	public String getMemo() {
		String result;
		
		if (memo == null) result = new String("null");
		else result = new String(memo);
		
		return result;
	}
	
	public boolean isMemoNull() {
		if (memo == null) return true;
		return false;
	}
	
	private boolean setID() {
		id = mkID();
		return true;
	}
	
	private boolean setID(long input) {
		id = input;
		return true;
	}
	
	private boolean setName(String input) {
		if (isNoTitle(input)) return false; 
		
		name = input;
		return true;
	}
	
	private boolean setStart(String input) {
		return setStart(mkParse(input));
	}
	
	public boolean setStart(LocalDateTime input) {
		LocalDateTime origin = start;
		start = input;
		
		if (start != null) {
			if (end == null) return true;
			else if (!isWrongInterval()) return true;
		}
		
		start = origin;
		return false;
	}
	
	private boolean setEnd(String input) {
		return setEnd(mkParse(input));
	}
	
	public boolean setEnd(LocalDateTime input) {
		LocalDateTime origin = end;
		end = input;
		
		if (end != null) {
			if (start == null) return true;
			else if (!isWrongInterval()) return true;
		}
		
		end = origin;
		return false;
	}
	
	private boolean setMemo(String input) {
		if (input == null || input.trim().equals("") || input.equals("null")) memo = null;
		else memo = input;
				
		return true;
	}
	
	private LocalDateTime mkParse(String input) {
		LocalDateTime result = null;
		if (isWrongFormat(input)) return result;
		
		result = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm")); 
		return result;
	}
	
	public boolean isToday(LocalDate today) {
		if (start.compareTo(LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 23, 59)) > 0 
			|| end.compareTo(LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), 0, 0)) < 0) return false;
		
		return true;
	}
	
	public boolean isNow(LocalDateTime now) {
		if (start.compareTo(now) > 0 || end.compareTo(now) < 0) return false;
			
		return true;
	}
	
	public boolean isAfter(LocalDateTime today) {
		if (end.compareTo(today) < 0) return false;
		
		return true;
	}
	
	public boolean checkIsError() { 
		return isError;  
	}
	
	private boolean isWrongFormat(String time) {
		if (time.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")) {
			int year, month, date, hour, min;
			
			String[] tokens = time.split("[- :]");
			
			year = Integer.parseInt(tokens[0]);
			month = Integer.parseInt(tokens[1]);
			date = Integer.parseInt(tokens[2]);
			hour = Integer.parseInt(tokens[3]);
			min = Integer.parseInt(tokens[4]);
			
			if (year >= 0 && month > 0 && month <= 12 
				&& date > 0 && (date <= 28 // 28보다 작거나 같으면 무조건 만족 
				|| (date <= 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12))
				|| (date <= 30 && (month == 4 || month == 6 || month == 9 || month == 11))
				|| (date <= 29 && month == 2 && (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)))) // 윤년일 때
				&& hour >= 0 && hour <= 24
				&& min >= 0 && min < 60) return false;
		}
			
		isError = true;
		return true;
	} 
	
	private boolean isNoTitle(String name) { // 타이틀 없음
		if (name != null && !name.equals("")) return false;
			
		isError = true;
		return true;
	}
	
	private boolean isWrongInterval() { // 시간 간격 
		if (start.compareTo(end) <= 0) return false;
		
		System.out.print("Start is after end. > ");
		isError = true;
		return true;
	}
}
