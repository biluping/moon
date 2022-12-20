import org.junit.jupiter.api.Test;

import java.sql.*;

public class TableInfo {

    @Test
    public void test() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://www.rabbit-cloud.top:5432/moon?currentSchema=information_schema", "postgres", "luping.bi");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                "SELECT " +
                        "b.COLUMN_NAME," +
                        "b.data_type," +
                        "col_description ( C.attrelid, C.attnum ) " +
                    "FROM " +
                        "pg_class A LEFT JOIN COLUMNS b ON A.relname = b.TABLE_NAME " +
                        "LEFT JOIN pg_attribute C ON b.COLUMN_NAME = C.attname AND C.attrelid = A.OID " +
                    "WHERE " +
                        "A.relname = 'moon_name_space'");
            while (resultSet.next()) {
                String column_name = resultSet.getString("column_name");
                String data_type = resultSet.getString("data_type");
                System.out.println(column_name + "   " + data_type);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        }

    }
}
