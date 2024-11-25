import java.sql.SQLException;
import java.util.Scanner;

public class ManagerInterface {
	
	static ManagerLog log = ManagerLog.getLog();
	
//	public static ArrayList<String> synList = new ArrayList<String>();
	
	static ManagerDatabase md = new ManagerDatabase();
	
	static boolean debug = true;
	
	public static void IMMI(String cabinetID, String layerID, String commodityPrice, String chineseName, String[] trans, String[] syns) throws SQLException {
		enterItem(new String[] {cabinetID,layerID,commodityPrice,chineseName}, trans, syns);
	}

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		if(debug) {
			Scanner sc = new Scanner(System.in);
			System.out.println("cabinetID, layerID, commodityPrice, chineseName");
			String[] kasumi = sc.nextLine().split(",");
			System.out.println("trans");
			String[] toyama = sc.nextLine().split(",");
			System.out.println("syns");
			String[] toyamakasumi = sc.nextLine().split(",");
			
			IMMI(kasumi[0],kasumi[1],kasumi[2],kasumi[3], toyama, toyamakasumi);
//			getOverview();
		}else {
			
			ManagerList.addManager("Toyama Kasumi", "krkrdkdk");
			ManagerList.addManager("Overwatch", "huxinhang");
			ManagerList.addManager("Benghuai", "Xingqiongtiedao");
			boolean logIn = true;
			Scanner sc = new Scanner(System.in);
			
			
			// Log in function
			while(!logIn) {
				System.out.printf("[Log In]\nPlease enter your user name:\n");
				
				String name = sc.nextLine();
				System.out.print("Please enter your password:\n");
				String password = sc.nextLine();
				if(logIn(name, password)) {
					log.recordLog("[Manager Interface] The manager ["+name+"] logged in. (" +System.currentTimeMillis()/1000 +")");
					logIn = true;
				}
			}
			
			
			
			String str = "";
//			Window a = new Window();
			while(!str.toLowerCase().equals("quit")) {
				System.out.println("Waiting for input...");
				str = sc.nextLine();
				if(str.equals("Enter")) {
					System.out.println("[Database]Please enter the information of the new item:\nNB. cabinetID, layerID, imageName, price, name and num");
					String temp = sc.nextLine();
					String[] temp2 = temp.split(",");
					if(temp2.length != 6) {
						System.err.println("Your input does not match the rule, please check again!");
					}else {
//						enterItem(temp2);
					}
				}else if(str.contains("RemoveAll")){
					if(str.length() <= 9) {
						System.err.println("Your input does not match the rule, please check again!");
					}else {
						removeAll(str.substring(10));
					}
				}else if(str.contains("Remove")){
//					try {
						String[] arr = str.split(" ");
						updateItem(arr[1],Integer.parseInt(arr[2]));
//					}catch(Exception e) {
//						System.err.printf("Your input does not match the rule, please check again!\nArr[0]:%s, Arr[1]:%s, Arr[2]:%s\n");
//					}
				}else {
					switch(str) {
						case "GetLog": {getLog();}break;
						case "Help": {help();}break;
						case "Overview": {getOverview();}break;
					}
				}
			}
		}

		
//		sc.close();
		
	}
	
	private static void help() {
		System.out.println("Get Log: GetLog");
	}
	
	public static void getLog() {
		log.readLog();
	}
	
	private static void getOverview() throws SQLException {
//		log.readLog("./tempDatabase.txt");
		ManagerDatabase.displayItem("TB");
	}
	
	private static void enterItem(String[] rowInfo, String[] trans, String[] syns) throws SQLException {
		ManagerDatabase.insertItem("TB", rowInfo, trans, syns);
	}
	
	private static void removeAll(String itemName) throws SQLException {
		ManagerDatabase.removeItem("TB", itemName);
	}
	
	private static void updateItem(String itemName, int num) throws SQLException {
		ManagerDatabase.updateItem("TB", itemName, num);
	}
	
	public static boolean logIn(String name, String password) {
		if(ManagerList.logIn(name, password)) {
			System.out.printf("***%s, WELCOME!***\n", name);
			return true;
		}else {
			System.err.printf("Manager %s is not found or you had inputed a wrong password. Please check again.\n", name);
			return false;
		}
	}

}
