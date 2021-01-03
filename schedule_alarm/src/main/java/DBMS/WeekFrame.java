package DBMS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class WeekFrame {
	WeekFrame timetable = this;
	int startTime = 7;
	private AddSubjectFrame openAddPanel;
	private boolean newFlag = false;
	
	private JFrame tableFrame = new JFrame("Time Table");
	private GridBagLayout gridBag = new GridBagLayout();
	private JPanel timetablePanel = new JPanel();
	
	private Font font1 = new Font("SansSerif", Font.BOLD, 13);
	private Color lightBlue = new Color (240,248,255);
	private Color darkBlue = new Color(153, 194, 255);
	private Color darkPurple = new Color(173,132,219);
	private Color lavendar = new Color(230,230,250);
	Color currentColor = Color.orange;

	private EtchedBorder eborder = new EtchedBorder(EtchedBorder.RAISED);
	private LineBorder lborder = new LineBorder(Color.LIGHT_GRAY, 1);
	
	GridBagConstraints getConstraints(int y, int x, int h, int w) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.weightx = 1;
		gbc.weighty = 1;
		return gbc;
	}

	private boolean checkSubjectTime(int startTime, int y, int x) {// wed, 8~10 / fri, 13~15
		boolean r = true;

		LocalDateTime subStart = LocalDateTime.of(2020, 12, 03, 8, 00);
		LocalDateTime subEnd = LocalDateTime.of(2020, 12, 03, 10, 00);

		int day = subStart.getDayOfWeek().getValue();
		LocalTime start = LocalTime.of(subStart.getHour(), subStart.getMinute());
		LocalTime end = LocalTime.of(subEnd.getHour(), subEnd.getMinute());

		int h = startTime + y / 2;
		if (h >= 24)
			h -= 24;
		int m = (y % 2) * 30;
		LocalTime t = LocalTime.of(h, m);

		// System.out.println(t+" "+start+" "+end);
		if (x == day - 2) {
			if (t.equals(end))
				r = false;
			if (t.isAfter(start) && t.isBefore(end))
				r = false;
		}

		LocalDateTime subStart2 = LocalDateTime.of(2020, 12, 06, 13, 00);
		LocalDateTime subEnd2 = LocalDateTime.of(2020, 12, 06, 15, 00);

		int day2 = subStart2.getDayOfWeek().getValue();
		LocalTime start2 = LocalTime.of(subStart2.getHour(), subStart2.getMinute());
		LocalTime end2 = LocalTime.of(subEnd2.getHour(), subEnd2.getMinute());

		int h2 = startTime + y / 2;
		if (h2 >= 24)
			h2 -= 24;
		int m2 = (y % 2) * 30;
		LocalTime t2 = LocalTime.of(h2, m2);

		if (x == day2 - 2) {
			if (t2.equals(end2))
				r = false;
			if (t2.isAfter(start2) && t2.isBefore(end2))
				r = false;
		}
		return r;
	}
	// Subject의 함수

	void addSubjectPanel(String name, int y, int x, int h, int w) {
		// String name 대신 Subject를 받아와야 함
		JLabel subjectPanel = new JLabel(name);
		subjectPanel.setFont(font1);
		subjectPanel.setBorder(eborder);
		subjectPanel.setBackground(darkBlue);
		subjectPanel.setOpaque(true);

		/*
		 * Subject -> LocalDateTime start int day = start.getDayOfWeek().getValue(); int
		 * h = start.getHour(); int m = start.getMinuite(); int y = (h - startTime)*2 +
		 * 1; if(m>=30) y++; // 7시 -> 1 // 13시 -> (13-7)*2 + 1 = 6*2+1 = 13 // 13시 30분
		 * -> 14 int x = day - 2;
		 */

		GridBagConstraints gbc = getConstraints(y, x, h, w);
		gridBag.setConstraints(subjectPanel, gbc);
		timetablePanel.add(subjectPanel);

	}

	/*
	 * startTime = 7 -> y = 1 y = 1 3 5 7 -> y/2 + 7 = h y = 2 4 6 8 -> y/2 + 6 = h,
	 * m = 30 if(y%2 == 1){ start = Integer.toString(y/2 + 7)+":00"; end =
	 * Integer.toString(y/2 + 7)+":30"; } else{ start = Integer.toString(y/2 +
	 * 7)+":00"; end = Integer.toString(y/2 + 7)+":30"; }
	 */

	WeekFrame() {
		timetablePanel.setLayout(gridBag);
		timetablePanel.setBackground(Color.white);
		String[] weekDays = {"","Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
		
		for(int i=0;i<8;i++) {	// 요일 레이블
			JLabel weekDay = new JLabel(weekDays[i], JLabel.CENTER);
			weekDay.setPreferredSize(new Dimension(40, 40));
			if(i!=0) {
				weekDay.setBackground(darkPurple);
				weekDay.setOpaque(true);
				weekDay.setFont(font1);
			}
			weekDay.setBorder(eborder);
			GridBagConstraints gbc = getConstraints(0,i,1,1);
			gridBag.setConstraints(weekDay,gbc);
	        timetablePanel.add(weekDay);
		}

		for(int i=8;i<48*8;i++) { 	// 시간표 레이블
			JLabel time = new JLabel(" ", JLabel.CENTER);
			time.setOpaque(true);
			time.setBackground(Color.white);
			int y = i / 8;
			int x = i % 8;
			String start, end;
			if(y%2 == 1){
				start = Integer.toString(y/2 + 7)+":00"; 
				end = Integer.toString(y/2 + 7)+":30";
			}
			else{
				start = Integer.toString(y/2 + 6)+":30"; 
				end = Integer.toString(y/2 + 7)+":00";	 
			}
			
			/*time.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if(time.getBackground() == Color.white){
						time.setBackground(currentColor);
						if(openAddPanel == null) {
							openAddPanel = new AddSubject(timetable);
							openAddPanel.makeAddSubject(weekDays[x],start,end);
						}
						else openAddPanel.setAddSubjectTime(weekDays[x],start,end);						
					}
					else{ 
						time.setBackground(Color.white);
						if(openAddPanel != null) {
							boolean f = openAddPanel.setAddSubjectTime(weekDays[x],start,end);						
							if(!f) time.setBackground(currentColor);
						}
					}
				}
				@Override
				public void mouseEntered(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
				@Override
				public void mouseReleased(MouseEvent arg0) {}
				
			});*/
			
			time.setFont(font1);
			time.setBorder(eborder);
			if(i%8 == 0) {	// 시간 레이블
				time.setBorder(eborder);
				time.setText(start+" ~ "+end);
				time.setOpaque(true);
				time.setBorder(eborder);
				time.setBackground(lavendar);		
			}
			time.setPreferredSize(new Dimension(40, 40));
			GridBagConstraints gbc = getConstraints(y,x,1,1);
			gridBag.setConstraints(time,gbc);
			//System.out.println(Integer.toString(y)+", "+Integer.toString(x)+"->"+checkSubjectTime(y,x));
	        if(checkSubjectTime(startTime, y,x)) timetablePanel.add(time);
		}
	}
	
	void makeTable(){
		
		addSubjectPanel("OOP",3,2,4,1);
		addSubjectPanel("OOP",13,5,4,1);
		
		JScrollPane scrollPane = new JScrollPane(timetablePanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		tableFrame.add(scrollPane);
		tableFrame.setSize(1200,800);
		tableFrame.setLocation(100, 100);
		tableFrame.setVisible(true);
		tableFrame.setFocusable(true);
	}
}
