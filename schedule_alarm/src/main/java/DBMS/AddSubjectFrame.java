package DBMS;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AddSubjectFrame {
	TimetableFrame timetableFrame;
	Color currentColor;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
	
	//ArrayList<SubjectDayTime> dayTimes = new ArrayList<SubjectDayTime>();
	
	JFrame jFrame = new JFrame("Add subject");
	JPanel contentPanel = new JPanel();
	JPanel dayTimesPanel = new JPanel();
	JPanel subjectPanel = new JPanel();
	JPanel titlePanel = new JPanel();
	JLabel weekDay = new JLabel("WeekDay",JLabel.CENTER);
	JLabel start = new JLabel("Start Time",JLabel.CENTER);
	JLabel end = new JLabel("End Time",JLabel.CENTER);
	JLabel location = new JLabel("Location",JLabel.CENTER);
	
    JLabel name = new JLabel("Subject Name",JLabel.CENTER);
	JTextField insertName = new JTextField();
    JLabel profName = new JLabel("Professor Name",JLabel.CENTER);
	JTextField insertProfName = new JTextField();
    JLabel url = new JLabel("Eclass Url",JLabel.CENTER);
	JTextField insertUrl = new JTextField();
	JPanel subject = new JPanel();
	JPanel buttonPanel = new JPanel();
    JButton addButton = new JButton("ADD");
    JButton confirmButton = new JButton("CONFIRM");
    Font font1 = new Font("Arial", Font.BOLD, 14);
	    
	Color lightBlue = new Color(153,204,255);
	Color darkBlue = new Color(133,184,255);
	Color lightPink = new Color(255,210,210);
    
	GridBagLayout gridBag;
	GridBagConstraints gbc;
	

	
	AddSubjectFrame(TimetableFrame t){
		this.timetableFrame = t;
		currentColor = t.currentColor;
		gridBag = new GridBagLayout();
		subjectPanel.setLayout(gridBag);
		
		gbc = getConstraints(0,0,1,1,0.2f,1.0f);
		gridBag.setConstraints(name,gbc);
        subjectPanel.add(name);
        
		gbc = getConstraints(0,1,1,1,0.8f,1.0f);
		gbc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(insertName,gbc);
        subjectPanel.add(insertName);

		gbc = getConstraints(1,0,1,1,0.2f,1.0f);
		gridBag.setConstraints(profName,gbc);
        subjectPanel.add(profName);
        
		gbc = getConstraints(1,1,1,1,0.8f,1.0f);
		gbc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(insertProfName,gbc);
        subjectPanel.add(insertProfName);
        
		gbc = getConstraints(2,0,1,1,0.2f,1.0f);
		gridBag.setConstraints(url,gbc);
        subjectPanel.add(url);
        
		gbc = getConstraints(2,1,1,1,0.8f,1.0f);
		gbc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(insertUrl,gbc);
        subjectPanel.add(insertUrl);
        
        name.setFont(font1);
        profName.setFont(font1);
        url.setFont(font1);
        insertName.setFont(font1);
        insertProfName.setFont(font1);
        insertUrl.setFont(font1);
        
        subjectPanel.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		subjectPanel.setPreferredSize(new Dimension(500,100));
		subjectPanel.setOpaque(true);
		subjectPanel.setBackground(currentColor);
		
		titlePanel.setLayout(new GridLayout(1, 4));
		weekDay.setFont(font1);
		start.setFont(font1);
		end.setFont(font1);
		location.setFont(font1);
		titlePanel.add(weekDay);
		titlePanel.add(start);
		titlePanel.add(end);
		titlePanel.add(location);
		//titlePanel.setPreferredSize(new Dimension(500,50));
		titlePanel.setOpaque(true);
		titlePanel.setBackground(Color.white);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		
		subject = addDayTime();
		
		dayTimesPanel.setLayout(new BoxLayout(dayTimesPanel, BoxLayout.Y_AXIS));
		dayTimesPanel.add(subject);
		
		addButton.setPreferredSize(new Dimension(220, 30));
		confirmButton.setPreferredSize(new Dimension(220, 30));
		JLabel space = new JLabel("   ");
		buttonPanel.add(addButton);
		buttonPanel.add(space);
		buttonPanel.add(confirmButton);
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Add clicked");
				JPanel newSubject = addDayTime();
				dayTimesPanel.add(newSubject);
				makeAddSubjectFrame();
			}
		});
		
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Confirm clicked");
				int i = dayTimesPanel.getComponentCount();
				System.out.println("dayTimesPanel :"+i);				
				ArrayList<SubjectDayTime> daytimes = new ArrayList<SubjectDayTime>();
				
				String name = insertName.getText();
				String profName = insertProfName.getText();
				String url = insertUrl.getText();
				System.out.println(name+" / "+profName+" / "+url);
				
				for(int j=0; j<i; j++) {
					JPanel subject_p = (JPanel) dayTimesPanel.getComponent(j);
					JPanel sub = (JPanel) subject_p.getComponent(0);
					
					System.out.println("j: "+j);
					JTextField weekday = (JTextField) sub.getComponent(0);
					System.out.println(weekday.getText());
					JTextField start = (JTextField) sub.getComponent(1);
					System.out.println(start.getText());
					JTextField end = (JTextField) sub.getComponent(2);
					System.out.println(end.getText());
					JTextField location = (JTextField) sub.getComponent(3);
					System.out.println(location.getText());
					
					//JPanel zl_p = (JPanel) subject_p.getComponent(1);
					JPanel zl = (JPanel) subject_p.getComponent(1);					

					JTextField zoomUrl = (JTextField) zl.getComponent(1);
					System.out.println(zoomUrl.getText());
					JTextField code = (JTextField) zl.getComponent(3);
					System.out.println(code.getText());
					JTextField password = (JTextField) zl.getComponent(5);
					System.out.println(password.getText());
					JTextField dayleft = (JTextField) zl.getComponent(7);
					System.out.println(dayleft.getText());
					JTextField lecnum = (JTextField) zl.getComponent(9);
					System.out.println(lecnum.getText());

					// subjectdaytime 생성
					if(!weekday.getText().equals("") && !start.getText().equals("") && !end.getText().equals("")) {
						int d = 0;
						String[] weekDays = {"","Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
						for(int k=1;k<8;k++) {
							if(weekDays[k].equals(weekday.getText())) d = k;
						}
						if(d == 0) System.out.println("day of week error "+d);
						
						LocalTime ls = LocalTime.parse(start.getText(), formatter);
						LocalTime le = LocalTime.parse(end.getText(), formatter);
						
						boolean isOffline = true;
						if(!zoomUrl.getText().equals("") || !code.getText().equals("") || !password.getText().equals("")) {
							System.out.println("zoom");
							SubjectDayTime temp = new SubjectDayTime(timetableFrame.schedules, DayOfWeek.of(d), ls,
									le, location.getText(), zoomUrl.getText(), code.getText(), password.getText(),
									null, 1, timetableFrame.timetable.getStartDate(), timetableFrame.timetable.getTotalWeek());			
							daytimes.add(temp);
							isOffline = false;
						}
						if(!dayleft.getText().equals("") || !lecnum.getText().equals("")) {
							System.out.println("online lec");
							int dl = 7, num = 1;
							if(!dayleft.getText().equals("")) dl = Integer.parseInt(dayleft.getText());
							if(!lecnum.getText().equals("")) num = Integer.parseInt(lecnum.getText());
							SubjectDayTime temp = new SubjectDayTime(timetableFrame.schedules, DayOfWeek.of(d), ls,
									le, location.getText(), dl , null , num, timetableFrame.timetable.getStartDate(), timetableFrame.timetable.getTotalWeek());	
							daytimes.add(temp);
							isOffline = false;
						}
						if(isOffline) {
							SubjectDayTime temp = new SubjectDayTime(timetableFrame.schedules, null, DayOfWeek.of(d), ls,
									le, location.getText());
							daytimes.add(temp);
							System.out.println("temp done");
						}
					}

				}

				// 새 과목 생성, add
				if(url.equals("")) {
					System.out.println("url을 입력하세요");
				}
				for(int k=0; k<daytimes.size(); k++) {
					SubjectDayTime d = daytimes.get(k);
					if(d.checkIsError())daytimes.remove(d);
				}
				timetableFrame.timetable.updateSubject(null, daytimes, name, url, profName, null);
				
				System.out.println("AddSubject");
				// 출력
				System.out.println(timetableFrame.timetable.getLine());
				
				TimetableFrame tf = new TimetableFrame(timetableFrame.schedules, timetableFrame.timetable, timetableFrame.week+1, timetableFrame.startTime);
				tf.makeTimetableFrame();
				timetableFrame.tableFrame.setVisible(false);
				timetableFrame.tableFrame.dispose();
				jFrame.setVisible(false);
				jFrame.dispose();
				//timetableFrame.makeTimetableFrame();
			}
		});
	}
	
	JPanel addDayTime() {
		//SubjectDayTime sdt = new SubjectDayTime();
		JPanel daytime = new JPanel();
		JPanel s = new JPanel();
		JPanel zoomLec = addZoomLecture();
		
		s.setLayout(new GridLayout(1, 4));
		s.setPreferredSize(new Dimension(500, 60));
		daytime.setLayout(new BoxLayout(daytime, BoxLayout.Y_AXIS));
		
		JTextField weekDay = new JTextField();
		JTextField start = new JTextField();
		JTextField end = new JTextField();
		JTextField location = new JTextField();
		JPanel p = new JPanel();
		JButton plus = new JButton("insert zoom / lec info");
		JButton delete = new JButton("delete");
		plus.setBackground(currentColor);
		plus.setPreferredSize(new Dimension(500,20));
		
		weekDay.setFont(font1);
		start.setFont(font1);
		end.setFont(font1);
		location.setFont(font1);
		
		s.add(weekDay);
		s.add(start);
		s.add(end);
		s.add(location);
		s.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 5 , 10));
		p.add(plus);
		p.add(delete);
		daytime.add(s);
		daytime.add(zoomLec);
		daytime.add(p);
		daytime.setOpaque(true);
		daytime.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 5 , 0));
		zoomLec.setVisible(false);

		plus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!zoomLec.isVisible()) {
					zoomLec.setVisible(true);
					delete.setVisible(false);
					plus.setText("close");
				}
				else {
					zoomLec.setVisible(false);
					delete.setVisible(true);
					plus.setText("open");
				}
				makeAddSubjectFrame();
			}
		});
		
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!start.getText().equals("") && !end.getText().equals("") && !weekDay.getText().equals("")) {
					LocalTime ls = LocalTime.parse(start.getText(), formatter);
					LocalTime le = LocalTime.parse(end.getText(), formatter);
					String day = weekDay.getText();
					System.out.println(ls+" "+le+" "+day);
					
					int d = 0;
					String[] weekDays = {"","Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
					for(int i=1;i<8;i++) {
						if(weekDays[i].equals(day)) d = i;
					}
					timetableFrame.deleteDayTime(ls, le, d);	
				}
				
				dayTimesPanel.remove(daytime);
				//dayTimes.remove(sdt);
				makeAddSubjectFrame();
			}
		});
		//dayTimes.add(sdt);        
		return daytime;
	}
	
		
	public void makeAddSubjectFrame(){
		contentPanel.removeAll();
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setPreferredSize(new Dimension(600, 50));
		
		//JScrollPane scrollPane = new JScrollPane(dayTimesPanel);
		//scrollPane.setSize(new Dimension(500, 300));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		contentPanel.add(subjectPanel);
		if(dayTimesPanel.getComponentCount()!=0) contentPanel.add(titlePanel);
		contentPanel.add(dayTimesPanel);
		contentPanel.add(buttonPanel);
		
		//setSize();
		jFrame.add(contentPanel);
		jFrame.pack();
		jFrame.setLocation(1300, 100);
		jFrame.setVisible(true);
		jFrame.setFocusable(true);
	}
	
	public void makeAddSubject(String weekday, String start, String end){
		contentPanel.removeAll();
		buttonPanel.setBackground(Color.WHITE);
		int i = dayTimesPanel.getComponentCount();
		System.out.println("dayTimesPanel수: "+i);
		if(i==0) {
			JPanel newSubject = addDayTime();
			dayTimesPanel.add(newSubject);
		}
		JPanel subject_p = (JPanel) dayTimesPanel.getComponent(i-1);
		JPanel subject = (JPanel) subject_p.getComponent(0);
		
		Component insertWeekDay = subject.getComponent(0);
		if(insertWeekDay instanceof JTextField) {
			JTextField w = (JTextField)insertWeekDay;
			w.setText(weekday);
		}
		Component insertStart = subject.getComponent(1);
		if(insertStart instanceof JTextField) {
			JTextField s = (JTextField)insertStart;
			s.setText(start);
		}
		Component insertEnd = subject.getComponent(2);
		if(insertEnd instanceof JTextField) {
			JTextField e = (JTextField)insertEnd;
			e.setText(end);
		}
		
		makeAddSubjectFrame();
		return;
	}
	
	public boolean setAddSubjectTime(String weekday, String start, String end){
		int i = dayTimesPanel.getComponentCount();
		boolean flag = false;
		
		for(int j=0; j<i; j++) {
			JPanel subject_p = (JPanel) dayTimesPanel.getComponent(j);
			JPanel subject = (JPanel) subject_p.getComponent(0);
			JTextField iDay = (JTextField)subject.getComponent(0);	
			if(iDay.getText().equals(weekday)) {
				JTextField iStart = (JTextField)subject.getComponent(1);
				JTextField iEnd = (JTextField)subject.getComponent(2);
				LocalTime ls = LocalTime.parse(iStart.getText(), formatter);
				LocalTime le = LocalTime.parse(iEnd.getText(), formatter);
				LocalTime ts = LocalTime.parse(start, formatter);
				LocalTime te = LocalTime.parse(end, formatter);
				if(ts.isAfter(ls) && te.isBefore(le)) {
					return false;
				}
				if(iEnd.getText().equals(start)) {
					iEnd.setText(end);
					flag = true;
				}
				else if(iEnd.getText().equals(end)) {
					iEnd.setText(start);
					flag = true;
				}
				else if(iStart.getText().equals(start)) {
					iStart.setText(end);
					flag = true;
				}
				else if(iStart.getText().equals(end)) {
					iStart.setText(start);
					flag = true;
				}
				if(flag) {
					if(iStart.getText().equals(iEnd.getText())) {
						//dayTimes.remove(j);
						dayTimesPanel.remove(subject_p);
						makeAddSubjectFrame();
					}
					return true;
				}
			}
		}
		
		JPanel newSubject = addDayTime();
		dayTimesPanel.add(newSubject);
		makeAddSubject(weekday, start, end);		
		return true;
	}
	
	GridBagConstraints getConstraints(int y, int x, int h, int w, float wx, float wy){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill= GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.weightx = wx;
        gbc.weighty = wy;
        return gbc;
    }	
	
	JPanel addZoomLecture() {
		gridBag = new GridBagLayout();
		JPanel zl = new JPanel();
		
		JLabel zoomUrl = new JLabel("Zoom URL",JLabel.CENTER);
		JTextField insertUrl = new JTextField();
	    JLabel zoomCode = new JLabel("Zoom Code",JLabel.CENTER);
		JTextField insertCode = new JTextField();
	    JLabel zoomPassword = new JLabel("Zoom Password",JLabel.CENTER);
		JTextField insertPw = new JTextField();
	    JLabel dueDay = new JLabel("Days until deadline",JLabel.CENTER);
		JTextField insertDueDay = new JTextField();
	    JLabel lecNum = new JLabel("Number of lectures",JLabel.CENTER);
		JTextField insertLecNum = new JTextField();
		
		JButton delete = new JButton("delete");
		
		zl.setLayout(gridBag);
		
		gbc = getConstraints(0,0,1,1,0.2f,1.0f);
		gridBag.setConstraints(zoomUrl,gbc);
        zl.add(zoomUrl);
        
		gbc = getConstraints(0,1,1,3,0.7f,1.0f);
		gridBag.setConstraints(insertUrl,gbc);
        zl.add(insertUrl);
        
        gbc = getConstraints(1,0,1,1,0.2f,1.0f);
		gridBag.setConstraints(zoomCode,gbc);
        zl.add(zoomCode);
        
		gbc = getConstraints(1,1,1,1,0.7f,1.0f);
		gridBag.setConstraints(insertCode,gbc);
        zl.add(insertCode);
        
        gbc = getConstraints(1,2,1,1,0.2f,1.0f);
		gridBag.setConstraints(zoomPassword,gbc);
        zl.add(zoomPassword);
        
		gbc = getConstraints(1,3,1,1,0.7f,1.0f);
		gridBag.setConstraints(insertPw,gbc);
        zl.add(insertPw);
        
        gbc = getConstraints(2,0,1,1,0.2f,1.0f);
		gridBag.setConstraints(dueDay,gbc);
        zl.add(dueDay);
        
		gbc = getConstraints(2,1,1,1,0.7f,1.0f);
		gridBag.setConstraints(insertDueDay,gbc);
        zl.add(insertDueDay);
        
        gbc = getConstraints(2,2,1,1,0.2f,1.0f);
		gridBag.setConstraints(lecNum,gbc);
        zl.add(lecNum);
        
		gbc = getConstraints(2,3,1,1,0.7f,1.0f);
		gridBag.setConstraints(insertLecNum,gbc);
        zl.add(insertLecNum);
        
        gbc = getConstraints(0,4,3,1,0.1f,1.0f);
        gbc.fill = GridBagConstraints.NONE;
        gridBag.setConstraints(delete,gbc);
        zl.add(delete);
        
        delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JPanel par = (JPanel) zl.getParent();
				JPanel sub = (JPanel) par.getComponent(0);
				JTextField iDay = (JTextField)sub.getComponent(0);	
				JTextField iStart = (JTextField)sub.getComponent(1);
				JTextField iEnd = (JTextField)sub.getComponent(2);
				
				if(!iStart.getText().equals("") && !iEnd.getText().equals("") && !iDay.getText().equals("")) {
					LocalTime ls = LocalTime.parse(iStart.getText(), formatter);
					LocalTime le = LocalTime.parse(iEnd.getText(), formatter);
					String day = iDay.getText();
					System.out.println(ls+" "+le+" "+day);
					
					int d = 0;
					String[] weekDays = {"","Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
					for(int i=1;i<8;i++) {
						if(weekDays[i].equals(day)) d = i;
					}
					timetableFrame.deleteDayTime(ls, le, d);	
				}
				int i = dayTimesPanel.getComponentCount();

				for(int j=0; j<i; j++) {
					JPanel subject_p = (JPanel) dayTimesPanel.getComponent(j);
					if(subject_p == par) {
						dayTimesPanel.remove(subject_p);
						//dayTimes.remove(j);
						makeAddSubjectFrame();
					}
				}
			}	
        });
        
		zl.setPreferredSize(new Dimension(500,80));
		zl.setOpaque(true);
        return zl;
	}

	
	/*void setSize(){
		System.out.println("size: "+dayTimes.size());
		if (dayTimes.size() == 0) dayTimesPanel.setPreferredSize(new Dimension(500, 160 * dayTimes.size()));
		else dayTimesPanel.setPreferredSize(new Dimension(500, 160 * dayTimes.size()));
	}*/
}


/*
 */