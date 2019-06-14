package cn.crabime.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
	public static String getCurrentTime(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date);
	}
}
