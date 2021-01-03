package DBMS;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Zoom extends Lecture {
   private String zoomId = null;
   private String zoomPw = null;
      
   Zoom(DayOfWeek dayOfWeek, String subjectCode, long scheduleId, int week, int serial_num, String url, boolean isDone, // Lecture
         String zoomID, String zoomPW,  // Zoom
         String name, String start, String end, String memo) { // Schedule 
      super(dayOfWeek, subjectCode, scheduleId, week, serial_num, url, isDone,
         name, start, end, memo);
      
      setZoomID(zoomID);
      setZoomPW(zoomPW);
      isNoInfo();
   }
   
   Zoom(DayOfWeek dayOfWeek, String subjectCode, long scheduleId, int week, int serial_num, String url, boolean isDone, // Lecture
         String zoomID, String zoomPW, // Zoom
         String name, LocalDateTime start, LocalDateTime end, String memo) { // Schedule
      super(dayOfWeek, subjectCode, scheduleId, week, serial_num, url, isDone,
         name, start, end, memo);
      
      setZoomID(zoomID);
      setZoomPW(zoomPW);
      isNoInfo();
   }
   
   // ¸Þ¼Òµå   
   public String getLine() {
      // System.out.println("zoom");
      
      String result = getZoomID() + "//" + getZoomPW();      
      return result + "//" + super.getLine();
   }

   public String getZoomID() {
      if (zoomId == null || zoomId.equals("") || zoomId.equals("null")) return "null";
            
      String temp = new String(zoomId);
      return temp;
   }
   
   public String getZoomPW() {
      if (zoomPw == null || zoomPw.equals("") || zoomPw.equals("null")) return "null";
      
      String temp = new String(zoomPw);
      return temp;
   }
   
   public boolean isZoomIdNull() {
      if (zoomId == null || zoomId.equals("") || zoomId.equals("null")) return true;
      return false;
   }
   
   public boolean isZoomPwNull() {
      if (zoomPw == null || zoomPw.equals("") || zoomPw.equals("null")) return true;
      return false;
   }
   
   private boolean setZoomID(String input) {
      if (input == null) return false;
      
      zoomId = input;
      return true;
   }
   
   private boolean setZoomPW(String input) {
      if (input == null) return false;
      
      zoomPw = input;
      return true;
   }
   
   private boolean isNoInfo() {
      if (zoomId != null || zoomPw != null || url == null) return false;
      
      isError = true;
      return true;
   }
}