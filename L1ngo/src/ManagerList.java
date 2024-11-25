import java.util.LinkedList;

final class ManagerList {
	// This linked list is storing all the information of normal manager
	private static LinkedList<String> managerList = new LinkedList<String>();
	private static LinkedList<String> managerPassword = new LinkedList<String>();
	
	// This linked list is storing all the information of super manager
	private static LinkedList<String[]> superList = new LinkedList<String[]>();
	
	public static void addManager(String name, String password) {
		if(managerList.contains(name)) {
			System.err.printf("User %s is already registered! Please check your input again!\n", name);
		}else {
			managerList.add(name);
			managerPassword.add(password);
			ManagerLog log = ManagerLog.getLog();
			log.recordLog("[AddManager] Add the manager ["+name+"], with the password ["+password+"](" +System.currentTimeMillis()/1000 +")");
		}
	}
	
	public static void removeManager(String name, String password) {
		if(logIn(name,password)) {
			managerList.remove(name);
			managerPassword.remove(password);
			System.out.printf("Manager %s is now removed!\n", name);
		}else {
			System.err.printf("Manager %s is not found or you had inputed a wrong password. Please check again.\n", name);
		}
	}
	
	public static boolean logIn(String name, String password) {
		if(!managerList.contains(name)) {
			return false;
		}
		return password.equals(managerPassword.get(managerList.indexOf(name)));
	}
}
