package DBMS;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class MonthFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime selected;
	private ScheduleList schedulesList;
	
	private MonthCalendarPanel calendarPanel;
	
	private JPanel shiftPanel = new JPanel();
	private JButton left = new JButton("<");
	private JButton right = new JButton(">");
	private JPanel monthInfoPanel = new JPanel();
	private JLabel year;
	private JLabel month;
	
	private Color lightBlue = new Color (240,248,255);
	private Color darkBlue = new Color(153, 194, 255);
	
	Font font1 = new Font("SansSerif", Font.BOLD, 13);
	
	ShiftMonthListener shiftListener = new ShiftMonthListener();
	
	MonthFrame(ScheduleList schedulesList) {
		// 디폴트 생성자
		this(LocalDateTime.now(), schedulesList);
	}
	
	MonthFrame(LocalDateTime selected, ScheduleList schedulesList) {
		super("Schedule Planner");
		this.selected = selected;
		
		shiftPanel.setLayout(new BoxLayout(shiftPanel, BoxLayout.X_AXIS));
		shiftPanel.add(left);

		year = new JLabel(selected.getYear() + "", JLabel.CENTER);
		month = new JLabel(selected.getMonthValue() + "", JLabel.CENTER);
		year.setFont(font1);
		month.setFont(font1);
		monthInfoPanel.setLayout(new GridLayout(2, 1));
		monthInfoPanel.add(year);
		monthInfoPanel.add(month);
		monthInfoPanel.setBackground(darkBlue);
		
		shiftPanel.add(monthInfoPanel);
		shiftPanel.add(right);
		shiftPanel.setBackground(darkBlue);
		shiftPanel.setBorder(BorderFactory.createEmptyBorder(5 , 10 , 5 , 10));

		left.addActionListener(shiftListener);
		right.addActionListener(shiftListener);
		
		setLayout(new BorderLayout());
		
		add(shiftPanel, BorderLayout.NORTH);
		this.schedulesList = schedulesList;
		
		calendarPanel = new MonthCalendarPanel(selected, schedulesList);
		add(calendarPanel, BorderLayout.CENTER);
		
		pack();
		//setLocation(400, 250);
		//setVisible(true);
		setSize(600,600);
		setLocation(100, 100);
		setVisible(true);
		setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public MonthFrame() {
		// TODO Auto-generated constructor stub
	}

	void editMonthFrame(LocalDateTime selected) {
		this.selected = selected;
		
		year.setText(selected.getYear() + "");
		month.setText(selected.getMonthValue() + "");
		
		remove(calendarPanel);
		
		calendarPanel = new MonthCalendarPanel(selected, schedulesList);
		add(calendarPanel, BorderLayout.CENTER);
		
		//pack();
		repaint();
	}
	
	class ShiftMonthListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LocalDateTime tempDate;
			if (e.getSource() == left) {
				tempDate = selected.minusMonths(1).minusDays(selected.getDayOfMonth() - 1);
			} else { // right인 경우
				tempDate = selected.plusMonths(1).minusDays(selected.getDayOfMonth() - 1);				
			}
			
			calendarPanel.closeDayFrame();
			editMonthFrame(tempDate);
		}
	}
}