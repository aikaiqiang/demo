
package kaywall.top.example.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class H2DatabaseExample {

    private static final Logger LOGGER = Logger.getLogger(H2DatabaseExample.class.getName());

    // JDBC URL for the in-memory H2 database
    private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";  // 默认用户名
    private static final String PASSWORD = ""; // 默认密码为空

    public static void main(String[] args) {
        try (Connection connection = connect()) {
            createTable(connection);
            insertData(connection, "Alice", 30);
            insertData(connection, "Bob", 25);
            System.out.println("所有用户：");
            selectAllData(connection);

            System.out.println("\n更新 Alice 的年龄为 31：");
            updateData(connection, "Alice", 31);
            selectAllData(connection);

            System.out.println("\n删除 Bob：");
            deleteData(connection, "Bob");
            selectAllData(connection);

        } catch (SQLException e) {
            LOGGER.severe("数据库操作发生异常: " + e.getMessage());  // 使用日志记录异常
        }
    }

    // 连接到 H2 数据库
    private static Connection connect() throws SQLException {
//        try {
//            Class.forName("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // 创建表
    private static void createTable(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(255), "
                + "age INT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    // 插入数据
    private static void insertData(Connection conn, String name, int age) throws SQLException {
        String sql = "INSERT INTO users(name, age) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
        }
    }

    // 查询所有数据
    private static void selectAllData(Connection conn) throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Age: " + rs.getInt("age"));
            }
        }
    }

    // 更新数据
    private static void updateData(Connection conn, String name, int age) throws SQLException {
        String sql = "UPDATE users SET age = ? WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, age);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        }
    }

    // 删除数据
    private static void deleteData(Connection conn, String name) throws SQLException {
        String sql = "DELETE FROM users WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }
}