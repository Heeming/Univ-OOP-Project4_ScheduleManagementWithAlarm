package DBMS;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;

public class MonthCalendarPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime selected;
	
	private JPanel weekLabel = new JPanel();
	private JPanel calendar = new JPanel();
	private JButton[] calendarButtons = new JButton[31];
	private EachMonthListener listener = new EachMonthListener(); 
	
	private DayFrame daySchedules;
	private ScheduleList schedulesList;
	
	Font font1 = new Font("SansSerif", Font.BOLD, 13);
	private Color lightBlue = new Color (240,248,255);
	private Color darkBlue = new Color(153, 194, 255);
	private Color lightPink = new Color(255, 210, 210);
	private EtchedBorder eborder = new EtchedBorder(EtchedBorder.LOWERED);
	private LineBorder lborder = new LineBorder(darkBlue, 1);
	
	MonthCalendarPanel() {
		
	}
	
	MonthCalendarPanel(ScheduleList schedulesList) { 
		this(LocalDateTime.now(), schedulesList);
	}
	
	MonthCalendarPanel(LocalDateTime selected, ScheduleList schedulesList) {
		int i;
		this.selected = selected;
		
		weekLabel.setLayout(new GridLayout(1, 7));
		weekLabel.add(new JLabel("SUN", JLabel.CENTER)); // 요일 레이블 만들기
		weekLabel.add(new JLabel("MON", JLabel.CENTER));
		weekLabel.add(new JLabel("TUE", JLabel.CENTER));
		weekLabel.add(new JLabel("WED", JLabel.CENTER));
		weekLabel.add(new JLabel("THR", JLabel.CENTER));
		weekLabel.add(new JLabel("FRI", JLabel.CENTER));
		weekLabel.add(new JLabel("SAT", JLabel.CENTER));
		weekLabel.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		weekLabel.setBackground(lightBlue);
		weekLabel.setOpaque(true);
		
		calendar.setBackground(darkBlue);
		calendar.setLayout(new GridLayout(0, 7)); // 42칸
		for (i = 0; i < selected.minusDays(selected.getDayOfMonth() - 1).getDayOfWeek().getValue() % 7; i++) { 
			calendar.add(new JLabel(""));
		}		
		for (i = 0; i < selected.toLocalDate().lengthOfMonth(); i++) { // 실제 달력 버튼 생성
			calendarButtons[i] = new JButton(i + 1 + "");
			calendarButtons[i].setFont(font1);
			calendarButtons[i].setBackground(Color.white);
			calendarButtons[i].setBorder(lborder);
			//calendarButtons[i].setVerticalAlignment(SwingConstants.TOP);
			calendar.add(calendarButtons[i]);
			calendarButtons[i].addActionListener(listener);
			LocalDateTime temp = selected.minusDays(selected.getDayOfMonth() - 1).plusDays(i);
			if(schedulesList.getTodayList(temp.toLocalDate()).size()!=0) {
				calendarButtons[i].setBackground(lightPink);
				calendarButtons[i].setBorder(eborder);
			}
		}
		
		setLayout(new BorderLayout());
		add(weekLabel, BorderLayout.NORTH);
		add(calendar, BorderLayout.CENTER);
		
		daySchedules = new DayFrame(selected, schedulesList);
		this.schedulesList = schedulesList;
	}
	
	void closeDayFrame() {
		daySchedules.setVisible(false);
	}
	
	class EachMonthListener implements ActionListener { // 각 버튼에 대한 
		public void actionPerformed(ActionEvent e) {
			int i;
			for (i = 0; i < selected.toLocalDate().lengthOfMonth(); i++) {
				if (e.getSource() == calendarButtons[i]) {
					closeDayFrame();
					LocalDateTime tempLocalDate = selected.minusDays(selected.getDayOfMonth() - 1).plusDays(Integer.valueOf(e.getActionCommand()) - 1);
					selected = tempLocalDate;
					daySchedules = new DayFrame(selected, schedulesList);
				}
			}
		}
	}
}