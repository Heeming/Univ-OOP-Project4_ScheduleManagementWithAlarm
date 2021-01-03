package DBMS;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class DaySchedulePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate selected;
	
	private JPanel label = new JPanel();
	private JPanel schedulePanel = new JPanel();
	MatchPanelToIndex eachSchedules; // 정보의 연결다리 역할
	
	private Font font1 = new Font("SansSerif", Font.BOLD, 13);
	private Color lightBlue = new Color (240,248,255);
	private Color darkBlue = new Color(153, 194, 255);
	private Color darkPurple = new Color(173,132,219);
	private Color lavendar = new Color(230,230,250);
	
	DaySchedulePanel() {
		
	}
	
	DaySchedulePanel(LocalDateTime selected, ScheduleList scheduleList) {
		int i;
		
		this.selected = LocalDate.of(selected.getYear(), selected.getMonth(), selected.getDayOfMonth());
		
		label.setLayout(new GridLayout(1, 4));
		JLabel title = new JLabel("Title",JLabel.CENTER);
		JLabel start = new JLabel("Start Time",JLabel.CENTER);
		JLabel end = new JLabel("End Time",JLabel.CENTER);
		JLabel memo = new JLabel("Memo",JLabel.CENTER);
		title.setFont(font1);
		start.setFont(font1);
		end.setFont(font1);
		memo.setFont(font1);
		label.add(title);
		label.add(start);
		label.add(end);
		label.add(memo);
		label.setBackground(darkBlue);
		label.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		
		schedulePanel.setLayout(new GridLayout(0, 1));
		schedulePanel.setBackground(lightBlue);
		schedulePanel.setSize(0, 500);
		eachSchedules = new MatchPanelToIndex(scheduleList); 
		for (i = 0; i < eachSchedules.getSize(); i++) {
			schedulePanel.add(eachSchedules.panel.get(i));
		}
		
		setLayout(new BorderLayout());
		add(label, BorderLayout.NORTH);
		add(schedulePanel, BorderLayout.CENTER);
		
		setSize();
	}
	

	public void addBlank() {
		eachSchedules.mkNewEachPanel();
		schedulePanel.add(eachSchedules.panel.get(eachSchedules.getSize() - 1));
	}
	
	void setSize() {
		if (eachSchedules.getSize() == 0) schedulePanel.setPreferredSize(new Dimension(500, 70));
		else schedulePanel.setPreferredSize(new Dimension(500, 70 * eachSchedules.getSize()));
	}
	
	class MatchPanelToIndex { // edit
		private ArrayList<Schedule> oldSchedules = null;
		private ArrayList<Schedule> newSchedules = null;
		private ArrayList<DayEachPanel> panel = new ArrayList<DayEachPanel>();
		
		MatchPanelToIndex (ScheduleList scheduleList) {
			int i;
			
			oldSchedules = scheduleList.getTodayList(selected); 
			for (i = 0; i < oldSchedules.size(); i++) {
				panel.add(new DayEachPanel(oldSchedules.get(i)));
			}
		}
		
		public ArrayList<Schedule> getOldSchedules() {
			return oldSchedules;
		}
		
		public ArrayList<Schedule> getNewSchedules() {
			return newSchedules;
		}
		
		public void mkNewEachPanel() {
			panel.add(new DayEachPanel(LocalDateTime.of(selected, LocalTime.now())));
			
			setSize();
		}
		
		public int getSize() {
			return panel.size();
		}
		
		public void updatePanelToSchedule() {
			int i;
			ArrayList<Schedule> result = new ArrayList<Schedule>();
			Schedule temp;
			
			for (i = 0; i < panel.size(); i++) {
				temp = panel.get(i).getSchedule();
				if(!temp.checkIsError()) result.add(temp);
			}
			
			newSchedules = result;
		}
	}
}