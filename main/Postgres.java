package main;
import java.sql.*;

public class Postgres {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        String url = "jdbc:postgresql://hattie.db.elephantsql.com:5432/gqrbhzwp";
        String username = "gqrbhzwp";
        String password = "0ZaNcWqdxpFDgzlB7qvEuJM0ecXkepu4";

        try {
            Connection db = DriverManager.getConnection(url, username, password);
            Statement st = db.createStatement();
            //st.executeQuery ("create table elephant (id int, name varchar)");
            ResultSet rs = st.executeQuery("SELECT * FROM event_users");
            while (rs.next()) {
                
                System.out.print("Column 1 returned ");
                System.out.println(rs.getString(1));
                System.out.print("Column 2 returned ");
                System.out.println(rs.getString(2));
            }
            rs.close();
            st.close();
        }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}