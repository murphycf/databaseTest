import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

public class AddressTest {
    private static Connection connection;

    @BeforeClass
    public static void openDBConnection() throws ClassNotFoundException, SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/test?useSSL=false";
        String username = "root";
        String password = "12345";

        connection = DriverManager.getConnection(dbUrl, username, password);
    }

    @AfterClass
    public static void closeDBConnection() throws SQLException {
//        PreparedStatement statement = connection.prepareStatement("delete from address where id > 2");
//        statement.executeUpdate();

        connection.close();
    }

    @Test
    public void shouldGetTwoFirstAddresses() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from address");

        resultSet.next();
        assertEquals(resultSet.getInt("id"), 1);
        assertEquals(resultSet.getString("address"), "Vancouver");
        assertEquals(resultSet.getInt("city_id"), 1);

        resultSet.next();
        assertEquals(resultSet.getInt("id"), 2);
        assertEquals(resultSet.getString("address"), "Calgary");
        assertEquals(resultSet.getInt("city_id"), 2);
//
//        resultSet.next();
//        assertEquals(resultSet.getInt("id"), 3);
//        assertEquals(resultSet.getString("address"), "Edmonton");
//        assertEquals(resultSet.getInt("city_id"), 1);

    }

    @Test
    public void shouldHaveRightTableColumns() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from information_schema.columns where table_name = 'address'");

        resultSet.next();
        assertEquals(resultSet.getString("column_name"), "id");
        assertEquals(resultSet.getString("data_type"), "int");
        assertEquals(resultSet.getString("column_key"), "PRI");
        assertEquals(resultSet.getString("extra"), "auto_increment");

        resultSet.next();
        assertEquals(resultSet.getString("column_name"), "address");
        assertEquals(resultSet.getString("data_type"), "varchar");
        assertEquals(resultSet.getString("character_maximum_length"), "255");
        assertEquals(resultSet.getString("column_key"), "");
        assertEquals(resultSet.getString("extra"), "");

        resultSet.next();
        assertEquals(resultSet.getString("column_name"), "city_id");
        assertEquals(resultSet.getString("data_type"), "int");
        assertEquals(resultSet.getString("column_key"), "");
        assertEquals(resultSet.getString("extra"), "");
    }

    public void shouldInsertNewAddress() throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("insert into address values (default, ?, ?)");

        preparedStatement.setString(1, "Edmonton");
        preparedStatement.setInt(2, 3);

        preparedStatement.executeUpdate();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from address");

        resultSet.next();
        resultSet.next();
        resultSet.next();
        assertEquals(resultSet.getString("address"), "Edmonton");
        assertEquals(resultSet.getInt("city_id"),3);
}

}