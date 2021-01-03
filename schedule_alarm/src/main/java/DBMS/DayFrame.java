package DBMS;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DayFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime selected;
	private ScheduleList scheduleList;
	
	private DaySchedulePanel schedulePanel;
	
	private JPanel editPanel = new JPanel();
	private JButton cancel = new JButton("Cancel");
	private JButton add = new JButton("Add");
	private JButton save = new JButton("Save");
	private ButtonsListener listener = new ButtonsListener();
	
	private Font font1 = new Font("SansSerif", Font.BOLD, 13);
	private Color lightBlue = new Color (240,248,255);
	private Color darkBlue = new Color(153, 194, 255);
	private Color lavendar = new Color(230,230,250);
	
	DayFrame() {
		
	}
	
	DayFrame(ScheduleList scheduleList) {
		this(LocalDateTime.now(), scheduleList);
	}
	
	DayFrame(LocalDateTime selected, ScheduleList scheduleList) {
		super("Day Schedule - " + selected.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		this.selected = selected;
		this.scheduleList = scheduleList;
		
		setLayout(new BorderLayout());
		
		editPanel.setLayout(new GridLayout(1, 3));
		editPanel.add(cancel);
		editPanel.add(add);
		editPanel.add(save);
		editPanel.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
		cancel.addActionListener(listener);
		add.addActionListener(listener);
		save.addActionListener(listener);
		
		schedulePanel = new DaySchedulePanel(selected, scheduleList);
		
		add(schedulePanel, BorderLayout.CENTER);
		add(editPanel, BorderLayout.SOUTH);
		
		pack();
		setLocation(750, 250);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void closeAndAnotherOpen(LocalDateTime selected) { // 
		remove(schedulePanel);
		schedulePanel = new DaySchedulePanel(selected, scheduleList);
		add(schedulePanel, BorderLayout.CENTER);
		
		pack();
		repaint();
	}
	
	class ButtonsListener implements ActionListener { // edit
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancel) {
				setVisible(false);
			} else if (e.getSource() == add) {
				schedulePanel.addBlank();
				
				pack();
				repaint();
			} else if (e.getSource() == save) {
				schedulePanel.eachSchedules.updatePanelToSchedule();
				scheduleList.updateList(schedulePanel.eachSchedules.getOldSchedules(), schedulePanel.eachSchedules.getNewSchedules());
				
				closeAndAnotherOpen(selected);
				MonthFrame calendar = new MonthFrame(scheduleList);
			}
		}
	}	
}
