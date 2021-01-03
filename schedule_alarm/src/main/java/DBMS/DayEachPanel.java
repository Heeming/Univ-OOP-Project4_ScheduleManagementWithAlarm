package DBMS;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class DayEachPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField title;
	private JTextField start;
	private JTextField end;
	private JTextField memo;
	
	private Font font1 = new Font("SansSerif", Font.BOLD, 13);
	private Color lightBlue = new Color (240,248,255);
	private Color darkPurple = new Color(147,112,219);
	private Color lavendar = new Color(230,230,250);
	
	DayEachPanel() {
		this(LocalDateTime.now());
	}
	
	DayEachPanel(LocalDateTime init) { 
		this("", init.format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm")), init.format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm")), "");
	}
	
	DayEachPanel(Schedule schedule) { 
		this(schedule.getName(), schedule.getStartString(), schedule.getEndString(), schedule.getMemo());
	}
	
	DayEachPanel(String title, String start, String end, String memo) { // add
		setLayout(new GridLayout(1, 4));
		setBackground(lightBlue);
		
		add(this.title = new JTextField(title));
		add(this.start = new JTextField(start));
		add(this.end = new JTextField(end));
		add(this.memo = new JTextField(memo));
		this.title.setFont(font1);
		this.start.setFont(font1);
		this.end.setFont(font1);
		this.memo.setFont(font1);
		
		setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
	}
	
	public Schedule getSchedule() {
		Schedule result = new Schedule(title.getText(), start.getText(), end.getText(), memo.getText());
		return result;
	}
}
