package DBMS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SubjectDayTime {
   private DayOfWeek dayOfWeek;
   private LocalTime start;
   private LocalTime end;
   private String location;
   
   private ArrayList<Lecture> lectureList = null;
   private boolean isError = false;
   
   SubjectDayTime(ScheduleList scheduleList, String code, DayOfWeek dayOfWeek, String start, String end, String location) {
      // offline lecture
      setDayOfWeek(dayOfWeek);
      setStart(start);
      setEnd(end);
      setLocation(location);
      
      lectureList = scheduleList.getLectures(code, dayOfWeek);
   }
   
   SubjectDayTime(ScheduleList scheduleList, String code, DayOfWeek dayOfWeek, LocalTime start, LocalTime end, String location) {
      setDayOfWeek(dayOfWeek);
      setStart(start);
      setEnd(end);
      setLocation(location);
      
      lectureList = scheduleList.getLectures(code, dayOfWeek);
   }
   
   SubjectDayTime(ScheduleList scheduleList, DayOfWeek dayOfWeek, String start, String end, String location, 
         String url, String zoomID, String zoomPW, String subjectCode, int numOfLecs, LocalDate startDate, int totalWeek) {
      // zoom
      setDayOfWeek(dayOfWeek);
      setStart(start);
      setEnd(end);
      setLocation(location);
      
      mkZoomList(subjectCode, zoomID, zoomPW, url, numOfLecs, startDate, totalWeek);
   }
   
   SubjectDayTime(ScheduleList scheduleList, DayOfWeek dayOfWeek, LocalTime start, LocalTime end, String location, 
         String url, String zoomID, String zoomPW, String subjectCode, int numOfLecs, LocalDate startDate, int totalWeek) {
      // zoom
      setDayOfWeek(dayOfWeek);
      setStart(start);
      setEnd(end);
      setLocation(location);
      
      mkZoomList(subjectCode, zoomID, zoomPW, url, numOfLecs, startDate, totalWeek);
   }
   
   SubjectDayTime(ScheduleList scheduleList, DayOfWeek dayOfWeek, String start, String end, String location, 
         int daysToAdd, String subjectCode, int numOfLecs, LocalDate startDate, int totalWeek) {
      // online_lecture
      setDayOfWeek(dayOfWeek);
      setStart(start);
      setEnd(end);
      setLocation(location);
      
      mkOnlineLecList(subjectCode, daysToAdd, numOfLecs, startDate, totalWeek);
   }
   
   SubjectDayTime(ScheduleList scheduleList, DayOfWeek dayOfWeek, LocalTime start, LocalTime end, String location, 
         int daysToAdd, String subjectCode, int numOfLecs, LocalDate startDate, int totalWeek) {
      // online_lecture
      setDayOfWeek(dayOfWeek);
      setStart(start);
      setEnd(end);
      setLocation(location);
      
      mkOnlineLecList(subjectCode, daysToAdd, numOfLecs, startDate, totalWeek);
   }
   
   public int numOfLecture() {
      return lectureList.size();
   }
   
   public String getLine() {
      // System.out.println("daytime");
      
      int i;
      String result = "- " + dayOfWeek.toString() + "//" + getStartString() + "//" + getEndString() + "//" + getLocation() + "\n";
      
      ArrayList<Zoom> zoom = getZoomList();
      ArrayList<OnlineLecture> onlineLecture = getOnlineLecList();
      
      result += "zoom\n";
      for (i = 0; i < zoom.size(); i++) {
         result += zoom.get(i).getLine() + "\n";
      }
      
      result += "online lecture\n";
      for (i = 0; i < onlineLecture.size(); i++) {
         result += onlineLecture.get(i).getLine() + "\n";
      }
      return result + "\n";
   }
   
   public boolean isInterval(LocalTime time, DayOfWeek dayOfWeek) {
      if (this.dayOfWeek != dayOfWeek || start.compareTo(time) > 0 || end.compareTo(time) < 0) return false;
      
      return true;
   }

   public ArrayList<Zoom> getZoomList() {
      int i;
      ArrayList<Zoom> result = new ArrayList<Zoom>();
      
      for (i = 0; i < lectureList.size(); i++) {
         if (lectureList.get(i) instanceof Zoom) result.add((Zoom) lectureList.get(i));
      }
      return result;
   }
   
   public ArrayList<OnlineLecture> getOnlineLecList() {
      int i;
      ArrayList<OnlineLecture> result = new ArrayList<OnlineLecture>();
      
      for (i = 0; i < lectureList.size(); i++) {
         if (lectureList.get(i) instanceof OnlineLecture) result.add((OnlineLecture) lectureList.get(i));
      }
      return result;   
   }
   
   public ArrayList<Lecture> getWeekLecture(int week) {
      int i;
      ArrayList<Lecture> result = new ArrayList<Lecture>();
      
      for (i = 0; i < lectureList.size(); i++) {
         if (lectureList.get(i).getWeek() == week) result.add((OnlineLecture) lectureList.get(i));
      }
      return result;
   }
   
   public boolean initCode(String code) {
      int i;
      for (i = 0; i < lectureList.size(); i++) {
         lectureList.get(i).initSubjectCode(code);
      }
      
      return false;
   }
   
   public boolean initOnlineLecUrl(String url) {
      int i;
      String newUrl = url + "/external_tools/2";
      
      for (i = 0; i < getOnlineLecList().size(); i++) {
         getOnlineLecList().get(i).initOnlineLecUrl(newUrl);
      }
      
      return false;
   }
   
   public DayOfWeek getDayOfWeek() {
      return dayOfWeek; 
   }
   
   public LocalTime getStart() {
      LocalTime result = LocalTime.of(start.getHour(), start.getMinute());
      return result;
   }
   
   public LocalTime getEnd() {
      LocalTime result = LocalTime.of(end.getHour(), end.getMinute());
      return result;
   }
   
   public String getLocation() {
      if (location == null || location.equals("") || location.equals("null")) return "null";
      
      String result = new String(location);
      return result;
   }
   
   public String getDayOfWeekString() {
      return dayOfWeek.name();
   }
   
   public String getStartString() {
      String result = start.format(DateTimeFormatter.ofPattern("kk:mm"));
      return result;
   }
   
   public String getEndString() {
      String result = end.format(DateTimeFormatter.ofPattern("kk:mm"));
      return result;
   }
   
   public boolean isLocationNull() {
      if (location == null || location.equals("") || location.equals("null")) return true;
      return false;
   }
   
   public boolean checkIsError() {
      return isError;
   }
   
   public void mkZoomList(String subjectCode, String zoomID, String zoomPW, String url, int numOfLecs, 
         LocalDate startDate, int totalWeek) {
      int i, j;
      LocalDate tempDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
      
      lectureList = new ArrayList<Lecture>();
            
      for (i = 0; i < totalWeek; i++) {
         tempDate = tempDate.plusDays(7);
         
         for (j = 0; j < numOfLecs; j++) {
            lectureList.add(new Zoom(dayOfWeek, subjectCode, Schedule.mkID(), i + 1, 1, url, 
               (LocalDateTime.of(tempDate, end).compareTo(LocalDateTime.now()) <= 0), zoomID, zoomPW, 
               "zoom", LocalDateTime.of(tempDate, start), LocalDateTime.of(tempDate, end), 
               "week " + (i + 1)));
         }
      }
   }
   
   public void mkOnlineLecList(String subjectCode, int daysToAdd, int numOfLecs, 
         LocalDate startDate, int totalWeek) {
      int i, j;
      LocalDate tempDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
      
      lectureList = new ArrayList<Lecture>();
      
      tempDate = tempDate.plusDays(daysToAdd);
      for (i = 0; i < totalWeek; i++) {
         tempDate = tempDate.plusDays(7 + daysToAdd);
         
         for (j = 0; j < numOfLecs; j++) {
            lectureList.add(new OnlineLecture(dayOfWeek, subjectCode, Schedule.mkID(), i + 1, j + 1, null, 
               (LocalDateTime.of(tempDate, LocalTime.of(23, 59)).compareTo(LocalDateTime.now()) <= 0), 
               "online_lecture", LocalDateTime.of(tempDate, LocalTime.of(23, 59)), 
               LocalDateTime.of(tempDate, LocalTime.of(23, 59)), "week " + (i + 1) + " lecture " + (j + 1) ));   
         }
      }
   }

   private boolean setDayOfWeek(DayOfWeek input) {
      dayOfWeek = input; 
      return true;
   }
   
   private boolean setStart(int input) { // 시작 교시로 계산
      if (input <= 0) return false;
      
      this.start = LocalTime.of(input + 8, 0); // 1교시 = 9시
      return true;
   }
   
   private boolean setEnd(int input) {
      if (input <= 0) return false;
      this.end = LocalTime.of(input + 8, 0); // 1교시 = 9시   
      
      return true;
   }
   
   private boolean setStart(String input) {
      LocalTime origin = start;
      start = mkParse(input);
      
      if (start != null) {
         if (end == null) return true;
         else if (!isWrongInterval()) return true;
      }
      
      start = origin;
      return false;
   }
   
   private boolean setStart(LocalTime input) {
      LocalTime origin = start;
      start = input;
      
      if (start != null) {
         if (end == null) return true;
         else if (!isWrongInterval()) return true;
      }
      
      start = origin;
      return false;
   }
   
   private boolean setEnd(String input) {
      LocalTime origin = end;
      end = mkParse(input);
      
      if (end != null) {
         if (start == null) return true;
         else if (!isWrongInterval()) return true;
      }
      
      end = origin;
      return false;
   }
   
   private boolean setEnd(LocalTime input) {
      LocalTime origin = end;
      end = input;
      
      if (end != null) {
         if (start == null) return true;
         else if (!isWrongInterval()) return true;
      }
      
      end = origin;
      return false;
   }
   
   private boolean setLocation(String input) {
      if (input == null || input.equals("") || input.equals("null")) location = input;
      return true;
   }
   
   private LocalTime mkParse(String input) {
      LocalTime result = null;
      if (isWrongFormat(input)) return result;
      
      result = LocalTime.parse(input, DateTimeFormatter.ofPattern("kk:mm")); 
      return result;
   }
   
   private boolean isWrongFormat(String time) {
      if (time.matches("^\\d{2}:\\d{2}$")) {
         int hour, min;
         
         String[] tokens = time.split("[- :]");
         
         hour = Integer.parseInt(tokens[0]);
         min = Integer.parseInt(tokens[1]);
         
         if (hour >= 0 && hour <= 24 && min >= 0 && min < 60) return false;
      }
         
      isError = true;
      return true;
   } 
   
   private boolean isWrongInterval() { // 시간 간격 
      if (start.compareTo(end) <= 0) return false;
      
      isError = true;
      return true;
   }
}