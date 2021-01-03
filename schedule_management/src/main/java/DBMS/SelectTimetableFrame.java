package DBMS;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SelectTimetableFrame {
	private JFrame selectFrame = new JFrame("Select Timetable");
	private JPanel selectPanel = new JPanel();
	private Font font1 = new Font("Arial", Font.BOLD, 14);
	private Color lightBlue = new Color(153, 204, 255);
	private Color darkBlue = new Color(133, 184, 255);
	private Color lightPink = new Color(255, 210, 210);
	private Color darkPurple = new Color(173,132,219);
	private Color lavendar = new Color(230,230,250);
	
	SelectTimetableFrame(DBManager DB) {
		// TODO Auto-generated method stub
	
		ScheduleList schedules = new ScheduleList(DB);
		TimetableList timetables = new TimetableList(DB, schedules);
		
	    selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.Y_AXIS));
	    
		JPanel t = new JPanel();
		t.setLayout(new GridLayout(1, 4));
		//t.setPreferredSize(new Dimension(500, 60));
	    
		JPanel s = new JPanel();
		s.setLayout(new GridLayout(1, 4));
		//s.setPreferredSize(new Dimension(500, 60));
		
		JLabel year = new JLabel("Year",JLabel.CENTER);
		JLabel semester = new JLabel("Semester",JLabel.CENTER);
		JLabel week = new JLabel("Week",JLabel.CENTER);
		JLabel start_time = new JLabel("Start time",JLabel.CENTER);
		year.setFont(font1);
		semester.setFont(font1);
		week.setFont(font1);
		start_time.setFont(font1);
		t.add(year);
		t.add(semester);
		t.add(week);
		t.add(start_time);
		t.setBackground(lavendar);
		
		JTextField iYear = new JTextField("2020");
		JTextField iSemester = new JTextField("2");
		JTextField iWeek = new JTextField("15");
		JTextField iStart_time = new JTextField("8");
		iYear.setFont(font1);
		iSemester.setFont(font1);
		iWeek.setFont(font1);
		iStart_time.setFont(font1);
		s.add(iYear);
		s.add(iSemester);
		s.add(iWeek);
		s.add(iStart_time);
		selectPanel.add(t);
		selectPanel.add(s);
		
		JPanel a = new JPanel();
		JButton add = new JButton("make timetable");
		a.setPreferredSize(new Dimension(400,10));
		add.setBackground(darkPurple);
		a.add(add);
		selectPanel.add(a);
		selectFrame.add(selectPanel);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(iYear.getText().equals("") || iSemester.getText().equals("")) {
					int n = timetables.find(LocalDate.now());
					TimetableFrame tf = new TimetableFrame(schedules,timetables.getTimetable(n));
					tf.makeTimetableFrame();									
				}
				else {
					int yr = Integer.parseInt(iYear.getText());
					int se = Integer.parseInt(iSemester.getText());
					int wk = Integer.parseInt(iWeek.getText());
					LocalDate ls = LocalDate.of(yr, 03, 15);
					if(se == 2) ls = LocalDate.of(yr, 9, 15);
					int st = Integer.parseInt(iStart_time.getText());
					int n = timetables.find(yr, se);
					System.out.println("n:"+ n);
					if(n == -1) {
						timetables.addTimetable(new Timetable(DB, yr, se, null, ls , 16));
						ScheduleList schedules = new ScheduleList(DB);
						TimetableList timetables = new TimetableList(DB, schedules);
						int k = timetables.find(yr, se);
						TimetableFrame tf = new TimetableFrame(schedules,timetables.getTimetable(k),wk,st);
						tf.makeTimetableFrame();
					}
					else {
						TimetableFrame tf = new TimetableFrame(schedules,timetables.getTimetable(n),wk,st);
						tf.makeTimetableFrame();					
					}					
				}
			}
		});

		selectFrame.setSize(400,180);
		selectFrame.setLocation(800, 500);
		selectFrame.setVisible(true);
		selectFrame.setFocusable(true);
	}
}
