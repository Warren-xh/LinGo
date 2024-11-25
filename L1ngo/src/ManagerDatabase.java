import com.mysql.cj.jdbc.MysqlDataSource;
 
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
 
public class ManagerDatabase {
	
	private static DataSource dataSource;
	
    public static void insertItem(String tableName, String[] str, String[] trans, String[] syns) throws SQLException {
        // 创建数据源
    	if(dataSource == null) {
    		dataSource = new MysqlDataSource();
    	}
    	
    	((MysqlDataSource)dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/Apple?characterEncoding=utf8&useSSL=false");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("123456");
 
        // 与数据库建立联系
        Connection connection = dataSource.getConnection();
 
        // 创建SQL语句
//        String loc = str[0]+"-"+str[1];
//        String a = "'"+str[0]+"'";
//        int b = Integer.parseInt(str[1]);
//        float d = Float.parseFloat(str[2]);
//        String e = str[3];
        String cID = "'"+str[0]+"'";
        int lID = -1;
        String loc = "";
        float price = 0;
        String name = "'"+str[3]+"'";
        try {
        	lID = Integer.parseInt(str[1]);
        	price = Float.parseFloat(str[2]);
        	loc = "'" + str[0] + "-" + lID + "'";
        }catch(Exception e) {
        	System.err.printf("Your layerID %s or price %s cannot be transformed into Integer!",str[1],str[2]);
        }
        
        for(int i=0; i<trans.length; i++) {
        	trans[i] = "'"+trans[i]+"'";
        }
        
        for(int i=0; i<syns.length; i++) {
        	syns[i] = "'"+syns[i]+"'";
        }

//        String sql = "INSERT INTO "+tableName+"(cabinetID, layerID, commodityLocation, commodityImage, commodityPrice, ChineseName, FrenchName, RussianName, EnglishName, SpanishName, ArabicName, AIGenerated1, AIGenerated2, AIGenerated3, AIGenerated4, AIGenerated5) VALUES(" + a + "," + b + ",'" + loc + "',null,'" +d+",'"+e+"','"+trans[0]+"','"+trans[1]+"','"+trans[2]+"','"+trans[3]+"','"+trans[4]+"','"+syns[0]+"','"+syns[1]+"','"+syns[2]+"','"+syns[3]+"','"+syns[4]+"');";
        String sql = "INSERT INTO " 
        			+ tableName
        			+ "(cabinetID, layerID, commodityLocation, commodityImage, commodityPrice, ChineseName, FrenchName, RussianName, EnglishName, SpanishName, ArabicName, AIGenerated1, AIGenerated2, AIGenerated3, AIGenerated4, AIGenerated5)"
        			+ " VALUES("
        			+ cID + ","
        			+ lID + ","
        			+ loc + ","
        			+ "null,"
        			+ price + ","
        			+ name + ","
        			+ trans[0] + ","
					+ trans[1] + ","
					+ trans[2] + ","
					+ trans[3] + ","
					+ trans[4] + ","
					+ syns[0] + ","
					+ syns[1] + ","
					+ syns[2] + ","
					+ syns[3] + ","
					+ syns[4]
        			+ ");";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
 
        // 发送已经解析好的SQL语句到数据库服务器中
        preparedStatement.executeUpdate();
        
        System.out.printf("Adding row [%s, %s, %s] successfully!\n",str[0],str[1],str[2],str[3]);
 
        // 释放资源
        preparedStatement.close();
        connection.close();
 
    }
    
    public static void displayItem(String tableName) throws SQLException {
        // 创建数据源
    	if(dataSource == null) {
    		dataSource = new MysqlDataSource();
    	}
    	
    	((MysqlDataSource)dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/Apple?characterEncoding=utf8&useSSL=false");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("123456");
 
        // 与数据库建立联系
        Connection connection = dataSource.getConnection();
 
        String sql1 = "SELECT * FROM "+tableName;
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
 
        // 得到结果集
        ResultSet resultSet = preparedStatement1.executeQuery();
        System.out.printf("\n///[Table %s]///\n", tableName);
        while(resultSet.next()){
            int id = resultSet.getInt("ID");
            String name = resultSet.getString("commodityName");
            String price = resultSet.getFloat("commodityPrice")+"";
            String loc = resultSet.getString("commodityNumber");
            int num = resultSet.getInt("remainNum");
            System.out.printf("%s(%s): %s LOC:%s [%s]\n",name,id,price,loc+"-"+id,num);
        }
        System.out.println("/////////\n");
 
        // 释放资源
        preparedStatement1.close();
        connection.close();
 
    }
    
    public static void removeItem(String tableName, String itemName) throws SQLException {
        // 创建数据源
    	if(dataSource == null) {
    		dataSource = new MysqlDataSource();
    	}
    	
    	((MysqlDataSource)dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/Apple?characterEncoding=utf8&useSSL=false");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("123456");
 
        // 与数据库建立联系
        Connection conn = dataSource.getConnection();
        
        PreparedStatement searchStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM TB WHERE ChineseName = ?";
        searchStmt = conn.prepareStatement(searchQuery);
        searchStmt.setString(1, itemName);
        rs = searchStmt.executeQuery();
 
        String deleteQuery = "DELETE FROM TB WHERE ChineseName = ?";
        deleteStmt = conn.prepareStatement(deleteQuery);
        deleteStmt.setString(1, itemName);
        int rowsDeleted = deleteStmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("No item deleted.");
        }
        
        if (rs != null) rs.close();
        if (searchStmt != null) searchStmt.close();
        if (deleteStmt != null) deleteStmt.close();
        if (conn != null) conn.close();
    }
    
    public static void updateItem(String tableName, String itemName, int updateNum) throws SQLException {
        // 创建数据源
    	if(dataSource == null) {
    		dataSource = new MysqlDataSource();
    	}
    	
    	((MysqlDataSource)dataSource).setUrl("jdbc:mysql://127.0.0.1:3306/Apple?characterEncoding=utf8&useSSL=false");
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("123456");
 
        // 与数据库建立联系
        Connection conn = dataSource.getConnection();
        
        PreparedStatement searchStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;
        
        String searchQuery = "SELECT * FROM TB WHERE commodityName = ?";
        searchStmt = conn.prepareStatement(searchQuery);
        searchStmt.setString(1, itemName);
        rs = searchStmt.executeQuery();
        
        String updateQuery = null;
        
        rs.next();
 
        int nowNum = rs.getInt("remainNum");
        if(nowNum <= updateNum) {
        	updateQuery = "DELETE FROM TB WHERE commodityName = ?";
        }else {
        	updateQuery = "UPDATE TB SET remainNum=? WHERE commodityName = ?";
        }
        
        deleteStmt = conn.prepareStatement(updateQuery);
        deleteStmt.setInt(1, nowNum-updateNum);
        deleteStmt.setString(1, itemName);
        int rowsDeleted = deleteStmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("No item deleted.");
        }
        
        if (rs != null) rs.close();
        if (searchStmt != null) searchStmt.close();
        if (deleteStmt != null) deleteStmt.close();
        if (conn != null) conn.close();
    }
}