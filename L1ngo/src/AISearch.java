import java.sql.*;

public class AISearch implements Search {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Apple?user=root&password=123456";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static String content;
    private static String language;
    private static int columnCount;
    private static StringBuilder sb=new StringBuilder();

    @Override
    public void getSearchContent(String searchContent) {
        this.content = searchContent;
    }

    @Override
    public void getLanguage(String language) {
        this.language = language;
    }

    @Override
    public String concreteSearch() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        columnCount = 4;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql_search="";
            switch (language){
                case "zh":
                    sql_search = "SELECT ChineseName,commodityLocation,commodityPrice,commodityImage FROM TB WHERE AIGenerated1 LIKE ? || WHERE AIGenerated2 LIKE ? || WHERE AIGenerated3 LIKE ? || WHERE AIGenerated4 LIKE ?|| WHERE AIGenerated5 LIKE ?";
                    break;
                case "fr":
                    sql_search = "SELECT FrenceName,commodityLocation,commodityPrice,commodityImage FROM TB WHERE AIGenerated1 LIKE ? || WHERE AIGenerated2 LIKE ? || WHERE AIGenerated3 LIKE ? || WHERE AIGenerated4 LIKE ?|| WHERE AIGenerated5 LIKE ?";
                    break;
                case "ru":
                    sql_search = "SELECT RussianName,commodityLocation,commodityPrice,commodityImage FROM TB WHERE AIGenerated1 LIKE ? || WHERE AIGenerated2 LIKE ? || WHERE AIGenerated3 LIKE ? || WHERE AIGenerated4 LIKE ?|| WHERE AIGenerated5 LIKE ?";
                    break;
                case "en":
                    sql_search = "SELECT EnglishName,commodityLocation,commodityPrice,commodityImage FROM TB WHERE AIGenerated1 LIKE ? || WHERE AIGenerated2 LIKE ? || WHERE AIGenerated3 LIKE ? || WHERE AIGenerated4 LIKE ?|| WHERE AIGenerated5 LIKE ?";
                    break;
                case "sp":
                    sql_search = "SELECT SpanishName,commodityLocation,commodityPrice,commodityImage FROM TB WHERE AIGenerated1 LIKE ? || WHERE AIGenerated2 LIKE ? || WHERE AIGenerated3 LIKE ? || WHERE AIGenerated4 LIKE ?|| WHERE AIGenerated5 LIKE ?";
                    break;
                case "ar":
                    sql_search = "SELECT ArabicName,commodityLocation,commodityPrice,commodityImage FROM TB WHERE AIGenerated1 LIKE ? || WHERE AIGenerated2 LIKE ? || WHERE AIGenerated3 LIKE ? || WHERE AIGenerated4 LIKE ?|| WHERE AIGenerated5 LIKE ?";
                    break;
            }
            pstmt = conn.prepareStatement(sql_search);
            pstmt.setString(1, "%"+content+"%");

            rs = pstmt.executeQuery();
            while (rs.next()) {
                StringBuilder sb2 = new StringBuilder();
                for (int i = 0; i < columnCount; i++) {
                    Object entity = rs.getObject(i + 1);
                    String target;
                    if(entity!=null){
                        target=entity.toString();
                    }else{
                        target=null;
                    }
                    if(i!=columnCount-1){
                        target=target+",";
                    }
                    else{
                        target=target+";";
                    }
                    sb2.append(target);
                    if(i==columnCount-1){
                        sb.append(sb2);
                    }
                }
                sb.append(sb2.toString());
                System.out.println(sb2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        if(sb.length()<1){
            String s=null;
            return s;
        }
        else{
            return sb.toString();
        }
    }
}

