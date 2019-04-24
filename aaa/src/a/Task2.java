package a;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import util.JdbcUtils_C3P0;
import util.Log;

@ConfigurationProperties(prefix = "")
@Component
@RestController
public class Task2 {
	
//		�? �?段时�? 执行�?次，，ms为单�?
//		@Scheduled(fixedRate=1000)
	
//		固定时间 执行
		@Value("${delTime}")
		private  String delTime;
		
		@RequestMapping("/getTest")
		public String getTest() {
			System.out.println("当前的时间是"+delTime);
			return "getTest";
		}
//		@Scheduled(cron=delTime)
//		@Scheduled(cron="${delTime}")
		public void test1() throws SQLException {
			
			long startTime=System.currentTimeMillis();
		   
			Date date = new Date();
//			调用函数，得到一个月前的时间
			Date dateMonthAgo = getOnePreMonTime(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Log.LogWrite("刷新时间�?"+sdf.format(date)+"正在保留�?近一个月数据...");
//			调用保留�?个月数据的函数，并传递一个月前的时间，dateMonthAgo
			RetenDBOneMonth(sdf.format(dateMonthAgo));
			
			long endTime=System.currentTimeMillis();
			System.out.println("程序运行时间�? "+(endTime - startTime)+"ms");
		}
		
		@Value("${table}")
		private String table;
//		删除�?个月前的数据
		public void RetenDBOneMonth(String dateMothAgo) throws SQLException {
			
//			String sql = "delete from " + table + " where entrytime < to_date('" +dateMothAgo+ "','yyyy-MM-dd HH24:mi:ss')";
			String sql = "delete from " + table + " where enterdate < to_date('" +dateMothAgo+ "','yyyy-MM-dd HH24:mi:ss')";
			System.out.println("sql语句�?"+sql);
			Log.LogWrite("�?始删除一个月前的数据"+sql);
			Connection connection = JdbcUtils_C3P0.getConnection();
			try {
				Statement stat = connection.createStatement();
				int a = stat.executeUpdate(sql);
				Log.LogWrite("已经删除�?"+a+"条数�?");
				Log.LogWrite("�?个月前的数据已经删除完毕，已经保留最近一个月的数据（30天）");
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Log.LogWrite("数据删除失败");
				e.printStackTrace();
				connection.close();
			}
			
		}
		
//		获取�?个月前的时间
		public Date getOnePreMonTime(Date da) {
			Calendar calendar = Calendar.getInstance(); //得到日历
			calendar.setTime(da);//把当前时间赋给日�?
			calendar.add(calendar.MONTH, -1);//设置为前2�?
			da = calendar.getTime();//获取2个月前的时间
			return da;
		}
		

}	
