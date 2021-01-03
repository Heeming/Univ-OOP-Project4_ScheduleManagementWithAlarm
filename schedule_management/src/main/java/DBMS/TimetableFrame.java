package DBMS;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class TimetableFrame {
	TimetableFrame timetableFrame = this;
	Timetable timetable;
	ScheduleList schedules;
	ArrayList<Subject> subjects = new ArrayList<Subject>();
	int week = 3;
	int startTime = 8;
	private AddSubjectFrame openAddPanel;
	private boolean newFlag = false;
	
	JFrame infoFrame = new JFrame();
	JFrame tableFrame = new JFrame("Time Table");
	private GridBagLayout gridBag = new GridBagLayout();
	private JPanel timetablePanel = new JPanel();
	
	private Color lightBlue = new Color(153, 204, 255);
	private Color darkBlue = new Color(133, 184, 255);
	private Color lightPink = new Color(255, 210, 210);
	private Color darkPurple = new Color(173,132,219);
	private Color lavendar = new Color(230,230,250);
	private Color orange = new Color(255,180,51); 
	Color currentColor = orange;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
	private Font font1 = new Font("SansSerif", Font.BOLD, 13);
	private Font font2 = new Font("SansSerif", Font.BOLD, 14);

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

	TimetableFrame(ScheduleList s, Timetable t) {
		schedules = s;
		timetable = t;
		
		System.out.println("TimeTableFrame");
		
		System.out.println(t.getLine());
		
		ArrayList<Subject> subs = timetable.getSubjectList();
		System.out.println("sub size:"+subs.size());
		subjects.addAll(subs);
	}
	
	TimetableFrame(ScheduleList s, Timetable t, int week, int startTime) {
		schedules = s;
		timetable = t;
		this.week = week;
		this.startTime = startTime;
		
		System.out.println("TimeTableFrame");
		
		//System.out.println(t.getLine());
		
		ArrayList<Subject> subs = timetable.getSubjectList();
		
		System.out.println("sub size:"+subs.size());
		subjects.addAll(subs);
	}
	
	
	private boolean checkSubjectTime(Subject subject, int y, int x) {
		// wed, 8~10 / fri, 13~15
		ArrayList<SubjectDayTime> daytimes = subject.getDayTimeList();
		boolean r = true;
		
		for(int i=0; i<daytimes.size(); i++) {
			SubjectDayTime sdt = daytimes.get(i);
			LocalTime start = sdt.getStart();
			LocalTime end = sdt.getEnd();
			DayOfWeek day = sdt.getDayOfWeek();
			if(start.equals(end)) return r;
			
			int hour = timetableFrame.startTime + y / 2;
			if (hour >= 24)
				hour -= 24;
			int minuite = (y % 2) * 30;
			LocalTime checkTime = LocalTime.of(hour, minuite);

			int d = day.getValue();
			if (x == d) {
				if (checkTime.equals(end))
					r = false;
				if (checkTime.isAfter(start) && checkTime.isBefore(end))
					r = false;
			}
		}
		return r;
	}

	
	void addSubjectPanel(Subject subject) {
		// String name 대신 Subject를 받아와야 함
		Random rand = new Random();
		int k = rand.nextInt(225);
		int r = rand.nextInt(175)+80;
		int g = rand.nextInt(175)+80;
		int b = rand.nextInt(175)+80;
		Color c = new Color(k, r, g, b);

		ArrayList<SubjectDayTime> daytimes = subject.getDayTimeList();
		
		for(int i=0; i<daytimes.size(); i++) {
			JPanel subjectPanel = new JPanel();
			JLabel subjectName = new JLabel(subject.getName(), JLabel.CENTER);
			subjectName.setPreferredSize(new Dimension(40,40));
			subjectName.setBackground(darkBlue);
			subjectName.setOpaque(false);
			subjectPanel.add(subjectName);
			subjectPanel.setFont(font1);
			subjectPanel.setBorder(eborder);
			subjectPanel.setBackground(c);
			subjectPanel.setOpaque(true);	
			subjectPanel.setLayout(new GridLayout(2,1));
			
			SubjectDayTime daytime = daytimes.get(i);
			int day = daytime.getDayOfWeek().getValue();
			LocalTime start = daytime.getStart();
			LocalTime end = daytime.getEnd();
			if(start.equals(end))return;
			
			ArrayList<Zoom> zooms = daytime.getZoomList();
			ArrayList<OnlineLecture> lecs = daytime.getOnlineLecList();
			System.out.println("zoom size: "+zooms.size());
			System.out.println("lec size: "+lecs.size());
			int n = (zooms.size()+lecs.size())/16;
			
			/*JPanel checklist = new JPanel();
			checklist.setOpaque(false);
			for(int j=0; j<zooms.size()/16; j++) {
				Zoom z = zooms.get((j+1)*16-(16-week+1));
				Checkbox zoom = new Checkbox("zoom "+(j+1));
				if(z.isDone) {
					zoom.setState(true);
				}
				zoom.setFont(font1);
				zoom.setBackground(c);
				checklist.add(zoom);
				zoom.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getStateChange() == ItemEvent.SELECTED) {
							z.setIsDone(true);						
						}
						else {
							z.setIsDone(false);
						}
					}
				});
			}
			for(int j=0; j<lecs.size()/16; j++) {
				OnlineLecture l = lecs.get((j+1)*16-(16-week+1));
				Checkbox lec = new Checkbox("lec "+(j+1));
				if(l.isDone) {
					lec.setState(true);
				}
				lec.setFont(font1);
				lec.setBackground(c);
				checklist.add(lec);
				lec.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						// TODO Auto-generated method stub
						if(e.getStateChange() == ItemEvent.SELECTED) {
							l.setIsDone(true);						
						}
						else {
							l.setIsDone(false);
						}
					}
				});
			}
			
			subjectPanel.add(checklist);*/
			int h = start.getHour();
			int m = start.getMinute(); 
			int y = (h - startTime)*2 + 1; 
			if(m>=30) y++;
			int x = day;		 
			int time = (end.getHour() - start.getHour())*2;    //7시 30 ~ 9시
			if(end.getMinute() - start.getMinute() < 0) time = time-1;
			
			GridBagConstraints gbc = getConstraints(y, x, time, 1);
			subjectPanel.setPreferredSize(new Dimension(40,40*time));
			//subjectName.setBorder(BorderFactory.createEmptyBorder(5*time , 0 , 2*time , 0));
			gridBag.setConstraints(subjectPanel, gbc);
			timetablePanel.add(subjectPanel);
			
			final int yf = y, xf = x;
			subjectPanel.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					makeInfoFrame(subject, yf, xf);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent arg0) {}
			});
		}
		
	}

	void makeInfoFrame(Subject s, int y, int x) {
		infoFrame.setVisible(false);
		infoFrame.dispose();
		infoFrame = new JFrame(s.getName() +" week"+week);
		
		ArrayList<SubjectDayTime> daytimes = s.getDayTimeList();
		JLabel subjectInfo = new JLabel(s.getName()+" / "+s.getProfName()+" / "+s.getUrl());
		subjectInfo.setFont(font2);
		subjectInfo.setOpaque(true);
		subjectInfo.setBackground(lightBlue);
		subjectInfo.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		
		ArrayList<Zoom> zooms = new ArrayList<Zoom>();
		ArrayList<OnlineLecture> lecs = new ArrayList<OnlineLecture>();
		for(int i=0; i<daytimes.size(); i++) {
			SubjectDayTime d = daytimes.get(i);
			zooms.addAll(d.getZoomList());
			lecs.addAll(d.getOnlineLecList());
		}
		int n = 1;	
		infoFrame.add(subjectInfo);
		
		for(int i=0; i<zooms.size(); i++) {
			Zoom z = zooms.get(i);
			int weekNum = z.getWeek();
			if(weekNum == week) {
				n++;
				JPanel zoomInfo = new JPanel();
				zoomInfo.setLayout(new GridLayout(2,2));
				JTextField zu = new JTextField("Zoom URL :  "+z.getUrl());
				JTextField zc = new JTextField("Zoom Code :  "+z.getZoomID());
				JTextField zp = new JTextField("Zoom Password :  "+z.getZoomPW());
				JTextField zd = new JTextField("Done :  "+z.isDone);
				zu.setFont(font1);
				zc.setFont(font1);
				zp.setFont(font1);
				zd.setFont(font1);
				zu.setBorder(eborder);
				zc.setBorder(eborder);
				zp.setBorder(eborder);
				zd.setBorder(eborder);
				zoomInfo.add(zu);
				zoomInfo.add(zc);
				zoomInfo.add(zp);
				zoomInfo.add(zd);
				infoFrame.add(zoomInfo);			
			}
		}
		for(int i=0; i<lecs.size(); i++) {
			OnlineLecture l = lecs.get(i);
			int weekNum = l.getWeek();	
			if(weekNum == week) {
				n++;
				JPanel lecInfo = new JPanel();
				lecInfo.setLayout(new GridLayout(2,2));
				JTextField zu = new JTextField("Lec Day :  "+l.getDayOfWeekString());
				JTextField zc = new JTextField("Lec Start :  "+l.getStartString());
				JTextField zp = new JTextField("Lec End :  "+l.getEndString());
				JTextField zd = new JTextField("Done :  "+l.isDone);
				zu.setFont(font1);
				zc.setFont(font1);
				zp.setFont(font1);
				zd.setFont(font1);
				zu.setBorder(eborder);
				zc.setBorder(eborder);
				zp.setBorder(eborder);
				zd.setBorder(eborder);
				lecInfo.add(zu);
				lecInfo.add(zc);
				lecInfo.add(zp);
				lecInfo.add(zd);
				infoFrame.add(lecInfo);				
			}
		}
		infoFrame.setLayout(new GridLayout(n,1));
		infoFrame.pack();
		infoFrame.setLocation(x*40+40, y*40);
		infoFrame.setVisible(true);
		infoFrame.setFocusable(true);
	}
	
	void makeTimetableFrame(){
		
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
				int h = y/2 + startTime;
				if(h>=24) h-=24;
				start = Integer.toString(h)+":00"; 
				end = Integer.toString(h)+":30";
			}
			else{
				int h = y/2 + startTime -1;
				if(h>=24) h-=24;
				start = Integer.toString(h)+":30"; 
				h = h + 1;
				if(h>=24) h-=24;
				end = Integer.toString(h)+":00";	 
			}
			
			time.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
					if(time.getBackground() == Color.white){
						time.setBackground(currentColor);
						time.setForeground(currentColor);
						if(openAddPanel == null) {
							openAddPanel = new AddSubjectFrame(timetableFrame);
							openAddPanel.makeAddSubject(weekDays[x],start,end);
						}
						else openAddPanel.setAddSubjectTime(weekDays[x],start,end);						
					}
					else{ 
						time.setBackground(Color.white);
						time.setForeground(Color.white);
						if(openAddPanel != null) {
							boolean f = openAddPanel.setAddSubjectTime(weekDays[x],start,end);						
							if(!f) {
								time.setBackground(currentColor);
								time.setForeground(currentColor);
							}
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
				
			});
			
			time.setFont(font2);
			time.setBorder(eborder);
			if(i%8 == 0) {	// 시간 레이블
				time.setBorder(eborder);
				time.setText(start+" ~ "+end);
				time.setOpaque(true);
				time.setBorder(eborder);
				time.setBackground(lavendar);		
			}
			else {
				time.setText(start+"/"+end+"/"+x);
				time.setForeground(Color.white);
			}
			time.setPreferredSize(new Dimension(40, 40));
			GridBagConstraints gbc = getConstraints(y,x,1,1);
			gridBag.setConstraints(time,gbc);
			boolean flag = true;
			//if(!checkSubjectTime(startTime, y,x)) flag = false;
			for(int k=0; k<subjects.size(); k++) {
				if(!checkSubjectTime(subjects.get(k),y,x)) flag = false;
			}
			if(flag) timetablePanel.add(time);
		}
		
		for(int i=0; i<subjects.size(); i++) {
			System.out.println("i"+i+subjects.get(i).getName());
			addSubjectPanel(subjects.get(i));
		}
		
		JScrollPane scrollPane = new JScrollPane(timetablePanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		tableFrame.add(scrollPane);
		tableFrame.setSize(1200,800);
		tableFrame.setLocation(100, 100);
		tableFrame.setVisible(true);
		tableFrame.setFocusable(true);
	}

	public void deleteDayTime(LocalTime start, LocalTime end, int weekDay) {
		// TODO Auto-generated method stub
		for(int i=0;i<timetablePanel.getComponentCount();i++) {
			Component com = timetablePanel.getComponent(i);
			if(com instanceof JLabel) {
				JLabel time = (JLabel) com;
				String str = time.getText();
				String[] tokens = str.split("/");
				if(tokens.length == 1) continue;
				LocalTime ls = LocalTime.parse(tokens[0], formatter);
				LocalTime le = LocalTime.parse(tokens[1], formatter);			
				int d = Integer.parseInt(tokens[2]);
				
				if(weekDay == d) {
					if(ls.equals(start) || le.equals(end) || (ls.isAfter(start) && le.isBefore(end)) ) {
						time.setBackground(Color.white);
						time.setForeground(Color.white);
					}
				}
			}
		}
		timetablePanel.revalidate();
		timetablePanel.repaint();
	}
}
