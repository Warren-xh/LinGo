import java.io.*;

final class ManagerLog {
	private static ManagerLog log;
	
//	private static boolean createdLog;
	
	private ManagerLog() {
		
	}
	
	public void recordLog(String str) {
		try {
			File checkFile = new File("./log.txt");
			FileWriter writer = null;
			if(!checkFile.exists()) {
				checkFile.createNewFile();
			}
			writer = new FileWriter(checkFile, true);
			writer.append(str+"\n");
			writer.flush();
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readLog() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./log.txt"));
			String line = null;
			System.out.println("****Log****");
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized ManagerLog getLog() {
		if(log == null) {
			log = new ManagerLog();
		}
		return log;
	}
}
