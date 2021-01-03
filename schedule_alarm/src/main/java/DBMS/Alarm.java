package DBMS;
import java.util.TimerTask;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Desktop;
import java.io.IOException;

public class Alarm extends TimerTask{
	String url;
	String curlstart="curl -d";
	String mail="\"{\\\"value1\\\":\\\"gurwns9325@naver.com\\\",";
	String content="programming";
	String value2="\\\"value2\\\":\\\""+ content +"\\\"}\"";
	String curlEn="-H \"Content-Type: application/json\" -X POST https://maker.ifttt.com/trigger/OOP/with/key/bLBPqJSN19mS-jNRNayYns"; 
	boolean mailFlag=false;
	boolean LinkFlag=false;
	
	@Override
	public void run() {
		try {
			if(mailFlag) {
				String command=curlstart+" "+mail+value2+" "+curlEn;
				System.out.println(command);
				String cmd[]= {"cmd.exe","/C", command};
				Runtime rT=Runtime.getRuntime();
				Process pC=rT.exec(cmd);
				System.out.println("mail º¸³¿");
			}
			if(LinkFlag) {
				Desktop.getDesktop().browse(new URI(url));
			}
		} catch (IOException e) { 
				e.printStackTrace();
		} catch (URISyntaxException e) {
				e.printStackTrace();
		}
	}	
	
	Alarm(ScheduleList List){
		content="Today Schedule Is ^<br^>";
		
		for(int i=0;i<List.numOfSchedules();i++) {
			String scName=List.getSchedule(i).getName();
			String scTime=List.getSchedule(i).getStart().toString();
			String CT=scName+"  "+scTime;
			content+=CT+"^<br^>";
		}
		value2="\\\"value2\\\":\\\""+ content +"\\\"}\"";
		mailFlag=true;
		System.out.println("mail º¸³¿ class");
	}
	
	Alarm(Schedule sc){
		if(sc instanceof Zoom) {
			Zoom Zsc=(Zoom)sc;
			url=Zsc.getUrl();
			LinkFlag=true;
		}
		if(sc instanceof OnlineLecture) {
			OnlineLecture Osc=(OnlineLecture)sc;
			url=Osc.getUrl();
			LinkFlag=true;
		}
	}
}