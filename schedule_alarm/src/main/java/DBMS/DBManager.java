package DBMS;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class DBManager {
   private DatabaseAuthInformation dbInfo;
    private String dbUrl;
   
    DBManager () {
        dbInfo = new DatabaseAuthInformation();
        dbInfo.parse_auth_info("auth/mysql.auth");

        /* Prepare the URL for DB connection */
        dbUrl = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Seoul&useSSL=false",
                dbInfo.getHost(), dbInfo.getPort(), dbInfo.getDatabase_name());

        // create & initialize table       
       createTimeTable();
        createSubject();
        createSubjectDaytime();
        createSchedule();
        createZoom();
        createOnlineLecture();
    }
    
    private boolean checkTableIsIn(String tableName) {
       // check table
       String stringCheckTable = "show tables like ?";
       
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
                PreparedStatement statCheckTable = db_conn.prepareStatement(stringCheckTable)) {
           statCheckTable.setString(1, tableName);
           ResultSet result = statCheckTable.executeQuery();
              
           if (result.next() == false) return false;
           else return true;
      } catch (SQLException e) {
          e.printStackTrace();
          
          return false;
      }
    }
    
    // init, 테이블 만들고 초기화
    private void createTimeTable() {
       if (checkTableIsIn("timetable")) return;
 
       int i;
        String StringCreateTable = "create table timetable (year int(5), semester int(1), name varchar(30), start_day DATE, total_week int(2), " +
                "primary key (year, semester))";
        String stringInsert = "insert into timetable (year, semester, name, start_day, total_week) values (?, ?, ?, ?, ?)";

        // Assume that the file is parsed (String[] of float[] form)
        int[] inputYears = { 2020 };
        int[] inputSemesters = { 2 };
        String[] inputNames = { "2020 semester 2" };
        LocalDate[] inputStartDays = { LocalDate.of(2020, 9, 1) };
        int[] inputTotalWeeks = { 16 };
        
        // create table
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statCreateTable = db_conn.createStatement()) {
            statCreateTable.executeUpdate(StringCreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // init tuples
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < inputYears.length; i++) {
                statInsert.setInt(1, inputYears[i]);
                statInsert.setInt(2, inputSemesters[i]);
                statInsert.setString(3, inputNames[i]);
                statInsert.setDate(4, Date.valueOf(inputStartDays[i]));
                statInsert.setInt(5, inputTotalWeeks[i]);
                
                statInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createSubject() {
       if (checkTableIsIn("subject")) return;
       
       int i;
        String StringCreateTable = "create table subject (code char(5), year int(4), semester int(1), name varchar(30), professor varchar(15), memo varchar(30), " +
                "primary key (code, year, semester), foreign key (year, semester) references timetable(year, semester))";
        String stringInsert = "insert into subject (code, year, semester, name, professor, memo) values (?, ?, ?, ?, ?, ?)";

        // Assume that the file is parsed (String[] of float[] form)
        String[] inputCodes = { "41087", "39853" };
        int[] inputYears = { 2020, 2020 };
        int[] inputSemesters = { 2, 2 };
        String[] inputNames = { "OOP", "automata" };
        String[] inputProfessors = { "Son Bong Soo", "Kim Myung Ho" };
        String[] inputMemos = { "online lecture", "zoom" };
        
         // create table
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statCreateTable = db_conn.createStatement()) {
            statCreateTable.executeUpdate(StringCreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // init tuples
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < inputCodes.length; i++) {
                statInsert.setString(1, inputCodes[i]);
                statInsert.setInt(2, inputYears[i]);
                statInsert.setInt(3, inputSemesters[i]);
                statInsert.setString(4, inputNames[i]);
                statInsert.setString(5, inputProfessors[i]);
                statInsert.setString(6, inputMemos[i]);

                statInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSubjectDaytime() {
       if (checkTableIsIn("subject_daytime")) return;
       
       int i;
        String StringCreateTable = "create table subject_daytime (subject_code char(5), year int(4), semester int(1), day_of_week int(1), "
              + "start TIME, end TIME, location varchar(15), "
                + "primary key (subject_code, year, semester, day_of_week), foreign key (subject_code, year, semester) references subject(code, year, semester))";
        String stringInsert = "insert into subject_daytime (subject_code, year, semester, day_of_week, start, end, location) values (?, ?, ?, ?, ?, ?, ?)";

        // Assume that the file is parsed (String[] of float[] form)
        String[] inputCodes = { "41087", "41087", "39853", "39853" };
        int[] inputYears = { 2020, 2020, 2020, 2020 };
        int[] inputSemesters = { 2, 2, 2, 2 };
        int[] inputDayOfWeeks = { DayOfWeek.MONDAY.getValue(), DayOfWeek.WEDNESDAY.getValue(), DayOfWeek.TUESDAY.getValue(), DayOfWeek.THURSDAY.getValue() };
        LocalTime[] inputStarts = { LocalTime.of(11, 0), LocalTime.of(11, 0), LocalTime.of(13, 0), LocalTime.of(13, 0) };
        LocalTime[] inputEnds = { LocalTime.of(13, 0), LocalTime.of(12, 0), LocalTime.of(15, 0), LocalTime.of(14, 0) };
        String[] inputLocations = { "310-728", "310-728", "310-726", "310-727" };
        
        // create table
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statCreateTable = db_conn.createStatement()) {
            statCreateTable.executeUpdate(StringCreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // init tuples
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < inputCodes.length; i++) {
                statInsert.setString(1, inputCodes[i]);
                statInsert.setInt(2, inputYears[i]);
                statInsert.setInt(3, inputSemesters[i]);
                statInsert.setInt(4, inputDayOfWeeks[i]);
                statInsert.setTime(5, Time.valueOf(inputStarts[i]));
                statInsert.setTime(6, Time.valueOf(inputEnds[i]));
                statInsert.setString(7, inputLocations[i]);
                
                statInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createSchedule() {
       if (checkTableIsIn("schedule")) return;
       
       int i;
        String StringCreateTable = "create table schedule (id BIGINT, name varchar(20), start TIMESTAMP, end TIMESTAMP, memo varchar(30), " +
                "primary key (id))";
        String stringInsert = "insert into schedule (id, name, start, end, memo) values (?, ?, ?, ?, ?)";

        // Assume that the file is parsed (String[] of float[] form)
        long[] inputIDs = { 2020120315, 2020120316, 2020120314, 2020120317, 2020120318 };
        String[] inputNames = { "automata-14-THU", "automata-15-TUE", "automata-15-THU", "OOP-15-1", "OOP-15-2" };
        LocalDateTime[] inputStarts = { LocalDateTime.of(2020, 12, 3, 13, 0), LocalDateTime.of(2020, 12, 8, 13, 0), LocalDateTime.of(2020, 12, 10, 13, 0), 
              LocalDateTime.of(2020, 12, 13, 23, 59), LocalDateTime.of(2020, 12, 13, 23, 59) };
        LocalDateTime[] inputEnds = { LocalDateTime.of(2020, 12, 3, 14, 0), LocalDateTime.of(2020, 12, 8, 15, 0), LocalDateTime.of(2020, 12, 10, 15, 0), 
              LocalDateTime.of(2020, 12, 13, 23, 59), LocalDateTime.of(2020, 12, 13, 23, 59) };
        String[] inputMemos = { "zoom", "zoom", "zoom", "online_lectue", "online_lectue" };

        // create table
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statCreateTable = db_conn.createStatement()) {
            statCreateTable.executeUpdate(StringCreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // init tuples
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < inputIDs.length; i++) {
                statInsert.setLong(1, inputIDs[i]);
                statInsert.setString(2, inputNames[i]);
                statInsert.setTimestamp(3, Timestamp.valueOf(inputStarts[i]));
                statInsert.setTimestamp(4, Timestamp.valueOf(inputEnds[i]));
                statInsert.setString(5, inputMemos[i]);

                statInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createZoom() {
       if (checkTableIsIn("zoom")) return;
       
       int i;
        String StringCreateTable = "create table zoom (schedule_id BIGINT, subject_code char(5), year int(4), semester int(1), day_of_week int(1), "
              + "week int(2), serial_num int(1), url varchar(100), is_done boolean, zoom_id char(13), zoom_pw char(6), " 
                + "primary key (schedule_id), foreign key (schedule_id) references schedule(id), "
                + "foreign key (subject_code, year, semester, day_of_week) references subject_daytime(subject_code, year, semester, day_of_week))";
        String stringInsert = "insert into zoom (schedule_id, subject_code, year, semester, day_of_week, week, serial_num, "
              + "url, is_done, zoom_id, zoom_pw) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Assume that the file is parsed (String[] of float[] form)
        long[] inputScheduleIDs = { 2020120315, 2020120316, 2020120317 };
        String[] inputSubjectIDs = { "39853", "39853", "39853" };
        int[] inputYears = { 2020, 2020, 2020 };
        int[] inputSemesters = { 2, 2, 2 };
        int[] inputDayOfWeeks = { DayOfWeek.THURSDAY.getValue(), DayOfWeek.TUESDAY.getValue(), DayOfWeek.THURSDAY.getValue() };
        int[] inputWeeks = { 14, 15, 15 };
        int[] inputSerialNums = { 1, 1, 1 };
        String[] inputUrls = { "https://eclass3.cau.ac.kr/courses/39853", "https://eclass3.cau.ac.kr/courses/39853", "https://eclass3.cau.ac.kr/courses/39853" };
        boolean[] inputIsDones = { true, false, false };
        String[] inputZoomIDs = { "968 3318 7434", "910 9524 2658", "968 3318 7434" };
        String[] inputZoomPWs = { "123456", "567890", "123456" };
        
        // create table
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statCreateTable = db_conn.createStatement()) {
            statCreateTable.executeUpdate(StringCreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // init tuples
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < inputScheduleIDs.length; i++) {
                statInsert.setLong(1, inputScheduleIDs[i]);
                statInsert.setString(2, inputSubjectIDs[i]);
                statInsert.setInt(3, inputYears[i]);
                statInsert.setInt(4, inputSemesters[i]);
                statInsert.setInt(5, inputDayOfWeeks[i]);
                statInsert.setInt(6, inputWeeks[i]);
                statInsert.setInt(7, inputSerialNums[i]);
                statInsert.setString(8, inputUrls[i]);
                statInsert.setBoolean(9, inputIsDones[i]);
                statInsert.setString(10, inputZoomIDs[i]);
                statInsert.setString(11, inputZoomPWs[i]);
                
                statInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createOnlineLecture() {
       if (checkTableIsIn("online_lecture")) return;
       
       int i;
        String StringCreateTable = "create table online_lecture (schedule_id BIGINT, subject_code char(5), year int(4), semester int(1), day_of_week int(1), "
              + "week int(2), serial_num int(1), url varchar(100), is_done boolean, " 
                + "primary key (schedule_id), foreign key (schedule_id) references schedule(id), "
                + "foreign key (subject_code, year, semester, day_of_week) references subject_daytime(subject_code, year, semester, day_of_week))";
        String stringInsert = "insert into online_lecture (schedule_id, subject_code, year, semester, day_of_week, week, serial_num, url, "
              + "is_done) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Assume that the file is parsed (String[] of float[] form)
        long[] inputScheduleIDs = { 2020120317, 2020120318 };
        String[] inputSubjectIDs = { "41087", "41087" };
        int[] inputYears = { 2020, 2020 };
        int[] inputSemesters = { 2, 2 };
        int[] inputDayOfWeeks = { DayOfWeek.MONDAY.getValue(), DayOfWeek.MONDAY.getValue() };
        int[] inputWeeks = { 15, 15 };
        int[] inputSerialNums = { 1, 2 };
        String[] inputUrls = { "https://eclass3.cau.ac.kr/courses/41087", "https://eclass3.cau.ac.kr/courses/41087"};
    
        boolean[] inputIsDones = { true, false };
        
        // create table
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statCreateTable = db_conn.createStatement()) {
            statCreateTable.executeUpdate(StringCreateTable);

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // init tuples
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < inputScheduleIDs.length; i++) {
                statInsert.setLong(1, inputScheduleIDs[i]);
                statInsert.setString(2, inputSubjectIDs[i]);
                statInsert.setInt(3, inputYears[i]);
                statInsert.setInt(4, inputSemesters[i]);
                statInsert.setInt(5, inputDayOfWeeks[i]);
                statInsert.setInt(6, inputWeeks[i]);
                statInsert.setInt(7, inputSerialNums[i]);
                statInsert.setString(8,  inputUrls[i]);
                statInsert.setBoolean(9, inputIsDones[i]);
                
                statInsert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    // get
    public ArrayList<Object[]> getTimetableList() { // 전체 timetable list
       ArrayList<Object[]> resultObjectsList = new ArrayList<Object[]>();
       ResultSet result;

        String stringQuery = "select * "
                       + "from timetable "
                       + "order by year, semester ";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statQuery = db_conn.createStatement()) {

            result = statQuery.executeQuery(stringQuery);
            while(result.next()) {
            resultObjectsList.add(new Object[] { result.getInt("year"), result.getInt("semester"), result.getString("name"), 
                   result.getDate("start_day").toLocalDate(), result.getInt("total_week") });
         }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultObjectsList;
    }
    
    public Object[] getTimetable(int year, int semester) { // 선택일에 맞는 timetable
       Object[] resultObjects = new Object[] { };
       ResultSet result;

        String stringQuery = "select * "
                       + "from timetable "
                       + "where year = ? and semester = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statQuery = db_conn.prepareStatement(stringQuery)) {
           statQuery.setInt(1, year);
           statQuery.setInt(2,  semester);
           
            result = statQuery.executeQuery();
            while(result.next()) {
            resultObjects = new Object[] { result.getInt("year"), result.getInt("semester"), result.getString("name"), 
                   result.getDate("start").toLocalDate(), result.getInt("totalWeek") };
         }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultObjects;
    }

    public ArrayList<Object[]> getSubjectList(int year, int semester) { // timetable에 해당하는 subject들
       ArrayList<Object[]> resultObjectsList = new ArrayList<Object[]>();
       ResultSet result;

        String stringQuery = "select * "
                       + "from subject "
                       + "where year = ? and semester = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statQuery = db_conn.prepareStatement(stringQuery)) {
           statQuery.setInt(1, year);
           statQuery.setInt(2, semester);
           
            result = statQuery.executeQuery();
            while(result.next()) {
            resultObjectsList.add(new Object[] { result.getString("code"), result.getString("name"), 
                  result.getString("professor"), result.getString("memo") });
         }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultObjectsList;
    }
    
    public ArrayList<Object[]> getSubjectDaytime(String subject_code) { // subject에 해당하는 daytime
      ArrayList<Object[]> resultObjectsList = new ArrayList<Object[]>();
      ResultSet result;

        String stringQuery = "select * "
                       + "from subject_daytime "
                       + "where subject_code = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statQuery = db_conn.prepareStatement(stringQuery)) {
           statQuery.setString(1, subject_code);
           
            result = statQuery.executeQuery();
            while(result.next()) {
            resultObjectsList.add(new Object[] { DayOfWeek.of(result.getInt("day_of_week")), result.getTime("start").toLocalTime(),
                  result.getTime("end").toLocalTime(), result.getString("location") });
         }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultObjectsList;
   }
   
    
   public ArrayList<Object[]> getNormalScheduleList() { // 전체 schedule 중 Lecture가 아닌 것
      ArrayList<Object[]> resultObjectsList = new ArrayList<Object[]>();
      ResultSet result;

        String stringQuery = "select * "
                       + "from schedule "
                       + "where id not in ((select schedule_id "
                                   + "from zoom)"
                                   + "union "
                                   + "(select schedule_id "
                                   + "from online_lecture))";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             Statement statQuery = db_conn.createStatement()) {

            result = statQuery.executeQuery(stringQuery);
            while(result.next()) {
               resultObjectsList.add(new Object[] { result.getLong("id"), result.getString("name"),
                     result.getTimestamp("start").toLocalDateTime(), result.getTimestamp("end").toLocalDateTime(),
                     result.getString("memo") });
         }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultObjectsList;
   }
   
   public ArrayList<Object[]> getZoom() { // 전체 schedule
      ArrayList<Object[]> resultObjectsList = new ArrayList<Object[]>();
      ResultSet result;

        String stringQuery = "select * "
                       + "from zoom, schedule "
                       + "where zoom.schedule_id = schedule.id "
                       + "order by week, day_of_week, serial_num";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
                Statement statQuery = db_conn.createStatement()) {

               result = statQuery.executeQuery(stringQuery);
               while(result.next()) {
                  resultObjectsList.add(new Object[] { DayOfWeek.of(result.getInt("day_of_week")), result.getString("subject_code"), 
                     result.getLong("schedule_id"), result.getInt("week"), result.getInt("serial_num"), result.getString("url"),  result.getBoolean("is_done"), 
                     result.getString("zoom_id"), result.getString("zoom_pw"), 
                     result.getString("name"), result.getTimestamp("start").toLocalDateTime(), result.getTimestamp("end").toLocalDateTime(),
                     result.getString("memo") });
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }

        return resultObjectsList;
   }
   
   public ArrayList<Object[]> getOnlineLec() { // 전체 schedule
      ArrayList<Object[]> resultObjectsList = new ArrayList<Object[]>();
      ResultSet result;

        String stringQuery = "select * "
                       + "from online_lecture, schedule "
                       + "where online_lecture.schedule_id = schedule.id "
                       + "order by week, day_of_week, serial_num";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
                Statement statQuery = db_conn.createStatement()) {

               result = statQuery.executeQuery(stringQuery);
               while(result.next()) {
                  resultObjectsList.add(new Object[] { DayOfWeek.of(result.getInt("day_of_week")), result.getString("subject_code"), 
                     result.getLong("schedule_id"), result.getInt("week"), result.getInt("serial_num"), result.getString("url"), result.getBoolean("is_done"), 
                     result.getString("name"), result.getTimestamp("start").toLocalDateTime(), result.getTimestamp("end").toLocalDateTime(),
                     result.getString("memo") });
            }

           } catch (SQLException e) {
               e.printStackTrace();
           }
        
        return resultObjectsList;
   }
   
   // add
   public boolean addTimetable(Timetable input) {
      String stringInsert = "insert into timetable (year, semester, name, start_day, total_week) values (?, ?, ?, ?, ?)";
        
        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) { 
            statInsert.setInt(1, input.getYear());
            statInsert.setInt(2, input.getSemester());
            statInsert.setString(3, input.getName());
            statInsert.setDate(4, Date.valueOf(input.getStartDate()));
            statInsert.setInt(5, input.getTotalWeek());
            
            statInsert.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
   
   public boolean addSubject(Subject input, int year, int semester) {
      String stringInsert = "insert into subject (code, year, semester, name, professor, memo) values (?, ?, ?, ?, ?, ?)";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            statInsert.setString(1, input.getCode());
            statInsert.setInt(2, year);
            statInsert.setInt(3, semester);
            statInsert.setString(4, input.getName());
            if (input.isProfNameNull()) statInsert.setNull(5, Types.VARCHAR);
            else statInsert.setString(5, input.getProfName());
            if (input.isMemoNull()) statInsert.setNull(6, Types.VARCHAR);
            else statInsert.setString(6, input.getMemo());

            statInsert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        if(!addSubjectDayTime(input.getDayTimeList(), input.getCode(), year, semester)) return false;
        return true;
   }
   
   public boolean addSubjectDayTime(ArrayList<SubjectDayTime> input, String subjectCode, int year, int semester) {
      int i;
        String stringInsert = "insert into subject_daytime (subject_code, year, semester, day_of_week, start, end, location) values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < input.size(); i++) {
                statInsert.setString(1, subjectCode);
                statInsert.setInt(2, year);
                statInsert.setInt(3, semester);
                statInsert.setInt(4, input.get(i).getDayOfWeek().getValue());
                statInsert.setTime(5, Time.valueOf(input.get(i).getStart()));
                statInsert.setTime(6, Time.valueOf(input.get(i).getEnd()));
                if (input.get(i).isLocationNull()) statInsert.setNull(7, Types.VARCHAR);
                else statInsert.setString(7, input.get(i).getLocation());
                
                statInsert.executeUpdate();
                
                if(!addZoom(input.get(i).getZoomList(), subjectCode, year, semester, input.get(i).getDayOfWeek())) return false;
                if(!addOnlineLec(input.get(i).getOnlineLecList(), subjectCode, year, semester, input.get(i).getDayOfWeek())) return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
   }

   public boolean addSchedule(ArrayList<Schedule> input) {
      int i; 
      String stringInsert = "insert into schedule (id, name, start, end, memo) values (?, ?, ?, ?, ?)";
 
      try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < input.size(); i++) {
                statInsert.setLong(1, input.get(i).getID());
                statInsert.setString(2, input.get(i).getName());
                statInsert.setTimestamp(3, Timestamp.valueOf(input.get(i).getStart()));
                statInsert.setTimestamp(4, Timestamp.valueOf(input.get(i).getEnd()));
                if (input.get(i).isMemoNull()) statInsert.setNull(5, Types.VARCHAR);
                else statInsert.setString(5, input.get(i).getMemo());

                statInsert.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
   
   public boolean addZoomSchedule(ArrayList<Zoom> input) {
      int i;
      ArrayList<Schedule> temp = new ArrayList<Schedule>();
      for (i = 0; i < input.size(); i++) {
         temp.add((Schedule)input.get(i));
      }
         
      return addSchedule(temp);
   }
   
   public boolean addOnlineLecSchedule(ArrayList<OnlineLecture> input) {
      int i;
      ArrayList<Schedule> temp = new ArrayList<Schedule>();
      for (i = 0; i < input.size(); i++) {
         temp.add((Schedule)input.get(i));
      }
         
      return addSchedule(temp);
   }
   
   public boolean addZoom(ArrayList<Zoom> input, String subjectCode, int year, int semester, DayOfWeek dayOfWeek) {
      int i;
      if (!addZoomSchedule(input)) return false;
      
      String stringInsert = "insert into zoom (schedule_id, subject_code, year, semester, day_of_week, week, serial_num,"
              + "url, is_done, zoom_id, zoom_pw) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < input.size(); i++) {
                statInsert.setLong(1, input.get(i).getID());
                statInsert.setString(2, subjectCode);
                statInsert.setInt(3, year);
                statInsert.setInt(4, semester);
                statInsert.setInt(5, dayOfWeek.getValue());
                statInsert.setInt(6, input.get(i).getWeek());
                statInsert.setInt(7, input.get(i).getSerialNum());
                if (input.get(i).isUrlNull()) statInsert.setNull(8, Types.VARCHAR);
                else statInsert.setString(8, input.get(i).getUrl());
                statInsert.setBoolean(9, input.get(i).checkIsDone());
                if (input.get(i).isZoomIdNull()) statInsert.setNull(10, Types.VARCHAR);
                else statInsert.setString(10, input.get(i).getZoomID());
                if (input.get(i).isZoomPwNull()) statInsert.setNull(11, Types.VARCHAR);
                else statInsert.setString(11, input.get(i).getZoomPW());
                
                statInsert.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
   
   public boolean addOnlineLec(ArrayList<OnlineLecture> input, String subjectCode, int year, int semester, DayOfWeek dayOfWeek) {
      int i;
      if (!addOnlineLecSchedule(input)) return false;
      
        String stringInsert = "insert into online_lecture (schedule_id, subject_code, year, semester, day_of_week, week, serial_num, "
              + "url, is_done) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < input.size(); i++) {
               statInsert.setLong(1, input.get(i).getID());
                statInsert.setString(2, subjectCode);
                statInsert.setInt(3, year);
                statInsert.setInt(4, semester);
                statInsert.setInt(5, dayOfWeek.getValue());
                statInsert.setInt(6, input.get(i).getWeek());
                statInsert.setInt(7, input.get(i).getSerialNum());
                if (input.get(i).isUrlNull()) statInsert.setNull(8, Types.VARCHAR);
                else statInsert.setString(8, input.get(i).getUrl());
                statInsert.setBoolean(9, input.get(i).checkIsDone());
                
                statInsert.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
   
   // delete
   public boolean delTimetable(Timetable input) {
       int i;
       for (i = 0; i < input.numOfSubject(); i++) { // 외래키 제약, subject부터 제거
          if (!delSubject(input.getSubject(i), input.getYear(), input.getSemester())) return false;
       }
      
       String stringInsert = "delete from timetable where year = ? and semester = ?";
       try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
                PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
               statInsert.setInt(1, input.getYear());
               statInsert.setInt(2, input.getSemester());
               
               statInsert.executeUpdate();
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
   }
   
   public boolean delSubject(Subject input, int year, int semester) {
      int i;
      for (i = 0; i < input.numOfSubject(); i++) { // 외래키 제약, subjectDayTime부터 제거
          if (!delSubjectDayTime(input.getCode(), year, semester)) return false;
       }
      
      String stringInsert = "delete from subject where code = ? and year = ? and semester = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
            PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            statInsert.setString(1, input.getCode());
            statInsert.setInt(2, year);
            statInsert.setInt(3, semester);

            statInsert.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
   
   public boolean delSubjectDayTime(String subjectCode, int year, int semester) {
      if (!delZoom(subjectCode, year, semester)) return false; // 외래키 제약
      if (!delOnlineLec(subjectCode, year, semester)) return false;
        
      String stringInsert = "delete from subject_daytime where subject_code = ? and year = ? and semester = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            
            statInsert.setString(1, subjectCode);
            statInsert.setInt(2, year);
            statInsert.setInt(3, semester);

            statInsert.executeUpdate();
            
            return true;
       } catch (SQLException e) {
            e.printStackTrace();
            return false;
       }
   }
   
   public boolean delSchedule(ArrayList<Schedule> input) {
      int i; 
      String stringInsert = "delete from schedule where id = ?";

      try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
                PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
               for (i = 0; i < input.size(); i++) {
                   statInsert.setLong(1, input.get(i).getID());
                   
                   statInsert.executeUpdate();
               }
               return true;
           } catch (SQLException e) {
               e.printStackTrace();
               return false;
           }
   }
   
   public boolean delZoom(ArrayList<Zoom> input) {
      // DB와 zoom 일치시키기
      int i;
      String stringDelSchedule = "delete from schedule where schedule_id = ?";
        String stringDelZoom = "delete from zoom where schedule_id = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
              PreparedStatement statDel1 = db_conn.prepareStatement(stringDelSchedule);
               PreparedStatement statDel2 = db_conn.prepareStatement(stringDelZoom)) {
            for (i = 0; i < input.size(); i++) {
                statDel1.setLong(1, input.get(i).getID());
                statDel1.executeUpdate();
                
                statDel2.setLong(1, input.get(i).getID());
                statDel2.executeUpdate();
           }
            return true;
       } catch (SQLException e) {
            e.printStackTrace();
            return false;
       }
   }
   
   public boolean delOnlineLec(ArrayList<OnlineLecture> input) {
      int i;
      String stringDelSchedule = "delete from schedule where schedule_id = ?";
        String stringDelZoom = "delete from online_lecture where schedule_id = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
              PreparedStatement statDel1 = db_conn.prepareStatement(stringDelSchedule);
               PreparedStatement statDel2 = db_conn.prepareStatement(stringDelZoom)) {
            for (i = 0; i < input.size(); i++) {
                statDel1.setLong(1, input.get(i).getID());
                statDel1.executeUpdate();
                
                statDel2.setLong(1, input.get(i).getID());
                statDel2.executeUpdate();
           }
            return true;
       } catch (SQLException e) {
            e.printStackTrace();
            return false;
       }
   }
   
   public boolean delZoom(String subjectCode, int year, int semester) {
      // DB와 zoom 일치시키기
      String stringDelSchedule = "delete from schedule "
                         + "where id in (select schedule_id "
                                    + "from Zoom "
                                    + "where subject_code = ? and year = ? and semester = ?)";
      
      String stringDelZoom = "delete from zoom where subject_code = ? and year = ? and semester = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
            PreparedStatement statDel1 = db_conn.prepareStatement(stringDelSchedule);
           PreparedStatement statDel2 = db_conn.prepareStatement(stringDelZoom)) {     
            statDel1.setString(1, subjectCode);
            statDel1.setInt(2, year);
            statDel1.setInt(3, semester);
            statDel1.executeUpdate();
            
            statDel2.setString(1, subjectCode);
            statDel2.setInt(2, year);
            statDel2.setInt(3, semester);
            statDel2.executeUpdate();
            return true;
       } catch (SQLException e) {
            e.printStackTrace();
            return false;
       }
   }
   
   public boolean delOnlineLec(String subjectCode, int year, int semester) {
      String stringDelSchedule = "delete from schedule "
             + "where id in (select schedule_id "
                        + "from Zoom "
                        + "where subject_code = ? and year = ? and semester = ?)";

      String stringDelZoom = "delete from online_lecture where subject_code = ? and year = ? and semester = ?";
      
      try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
         PreparedStatement statDel1 = db_conn.prepareStatement(stringDelSchedule);
         PreparedStatement statDel2 = db_conn.prepareStatement(stringDelZoom);) {     
         statDel1.setString(1, subjectCode);
         statDel1.setInt(2, year);
         statDel1.setInt(3, semester);
         statDel1.executeUpdate();
         
         statDel2.setString(1, subjectCode);
         statDel2.setInt(2, year);
         statDel2.setInt(3, semester);
         statDel2.executeUpdate();
         return true;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      }
   }
   
   public boolean updateZoomIsDone(ArrayList<Zoom> input, boolean isDone) {
      int i;
        String stringInsert = "update zoom set is_done = ? where schedule_id = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
            PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < input.size(); i++) {
                statInsert.setLong(1, input.get(i).getID());
                statInsert.setBoolean(2, isDone);
               
                statInsert.executeUpdate();
           }
            return true;
       } catch (SQLException e) {
            e.printStackTrace();
            return false;
       }
   }
   
   public boolean updateOnlineLecIsDone(ArrayList<OnlineLecture> input, boolean isDone) {
      int i;
        String stringInsert = "update zoom set is_done = ? where schedule_id = ?";

        try (Connection db_conn = DriverManager.getConnection(dbUrl, dbInfo.getUsername(), dbInfo.getPassword());
             PreparedStatement statInsert = db_conn.prepareStatement(stringInsert)) {
            for (i = 0; i < input.size(); i++) {
               statInsert.setLong(1, input.get(i).getID());
                statInsert.setBoolean(2, isDone);
                
                statInsert.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
}