
/**
 * Main
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.impl.JDBCASCIITableAware;
import com.bethecoder.ascii_table.spec.IASCIITableAware;

public class SqlPlus {
	private static Connection conn = null;

	public static void getConnection(String driver, String url, String user, String pass) {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void executeSQL(String sql) {
		try {
			if (!sql.isEmpty()) {
				try {
					IASCIITableAware asciiTableAware = new JDBCASCIITableAware(conn, sql);
					if( asciiTableAware.getData()!= null && !asciiTableAware.getData().isEmpty() ) {
						ASCIITable.getInstance().printTable(asciiTableAware);
					}else if( asciiTableAware.getHeaders() !=null && !asciiTableAware.getHeaders().isEmpty()){
						System.out.println("["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"] "+" No ResultSet was Selected ");
					}
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			System.out.print(conn.getMetaData().getDatabaseProductName() + "> ");
			
					
			/*InputStreamReader rd = new InputStreamReader(System.in);
			String str = null ;
			int ch = 0;
			StringBuffer temp = new StringBuffer();
			while((ch = rd.read())!= -1){
				System.out.println("ch="+ch);
				 
			       temp.append((char)ch);
			       if(ch ==59 ) {
					   break;
				   }
			}
			str = temp.toString();*/
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String str = "" ;
			String line = "" ;
			while( (line = br.readLine()) !=null ) {
				if( "".equals(line) ){
					break;
				}
				str += line;
			}
			//System.out.println("str="+str);
			
			
			//最原始 String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
			if (!"exit".equalsIgnoreCase(str)) {
				executeSQL(str);
			} else {
				return;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		try {
			if (args.length == 5) {
				getConnection(args[0], args[1], args[2], args[3]);
				IASCIITableAware asciiTableAware = new JDBCASCIITableAware(conn, args[4]);
				ASCIITable.getInstance().printTable(asciiTableAware);
			} else {
				System.out.print("Driver:");
				String driver = new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.print("URL:");
				String url = new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.print("user:");
				String user = new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.print("password:");
				String passwd = new BufferedReader(new InputStreamReader(System.in)).readLine();
				getConnection(driver, url, user, passwd);
				System.out.print("\r\n" + conn.getMetaData().getDatabaseProductName() + "> ");
				String sql = new BufferedReader(new InputStreamReader(System.in)).readLine();
				if (conn != null) {
					executeSQL(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}