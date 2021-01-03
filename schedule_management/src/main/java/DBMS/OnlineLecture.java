package DBMS;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OnlineLecture extends Lecture {
	OnlineLecture(DayOfWeek dayOfWeek, String subjectId, long scheduleId, int week, int serial_num, String url, boolean isDone, // Lecture
			String name, String start, String end, String memo) { 
		
		super(dayOfWeek, subjectId, scheduleId, week, serial_num, url, isDone,
			name, start, end, memo);
	}
	
	OnlineLecture(DayOfWeek dayOfWeek, String subjectId, long scheduleId, int week, int serial_num, String url, boolean isDone, // Lecture
			String name, LocalDateTime start, LocalDateTime end, String memo) { // Schedule 
		
		super(dayOfWeek, subjectId, scheduleId, week, serial_num, url, isDone,
			name, start, end, memo);
	}

	public String getLine() {
		// System.out.println("online lecture");
		
		return super.getLine();
	}
	
	public boolean initOnlineLecUrl(String url) {
		// System.out.println(url);
		if (this.url == null) {
			setUrl(url);
			return true;
		}
		return false;
	}

	private LocalTime mkParse(String input) {
		LocalTime result = null;
		if (isWrongFormat(input)) return result;
		
		result = LocalTime.parse(input, DateTimeFormatter.ofPattern("hh:mm:ss")); 
		return result;
	}
	
	private boolean isWrongFormat(String time) {
		if (time.matches("^\\d{2}:\\d{2}[:\\d{2}]?$")) {
			int hour = 0, min, sec;
			
			String[] tokens = time.split("[- :]");
			
			if (tokens.length == 3) {
				hour = Integer.parseInt(tokens[0]);
				min = Integer.parseInt(tokens[1]);
				sec = Integer.parseInt(tokens[2]);
			} else {
				min = Integer.parseInt(tokens[0]);
				sec = Integer.parseInt(tokens[1]);
			}
				
			if (hour >= 0 && hour <= 12 && min >= 0 && min < 60 && sec >= 0 && sec < 60) return false;
		}
			
		return true;
	} 
}