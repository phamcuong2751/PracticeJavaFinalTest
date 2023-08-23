package org.example.sampleJdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/your_database_name";
        String username = "your_username";
        String password = "your_password";

        try {
            // Kết nối đến cơ sở dữ liệu MySQL
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Thêm dữ liệu vào bảng "students"
            addStudent(connection, "John Doe", 25);
            addStudent(connection, "Jane Smith", 22);

            // Truy vấn và hiển thị dữ liệu từ bảng "students"
            queryStudents(connection);

            // Đóng kết nối
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Connection connection, String name, int age) throws SQLException {
        String insertSql = "INSERT INTO students (name, age) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, age);
        preparedStatement.executeUpdate();
    }

    public static void queryStudents(Connection connection) throws SQLException {
        String querySql = "SELECT * FROM students";
        PreparedStatement preparedStatement = connection.prepareStatement(querySql);
        ResultSet resultSet = preparedStatement.executeQuery();

        System.out.println("Student List:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
        }
    }
}

