package main;
import java.sql.*;

public class User {
    
    int time;
    String nickname;

    boolean istimeset = false;

    public User(String nickname){

        this.nickname = nickname;
    }
    public User(String nickname, int time){

        this.nickname = nickname;
        this.time = time;
        istimeset = true;
    }

    public String getNickname() {
        return nickname;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {

        if(!istimeset){
            this.time = time;
            istimeset = true;
        }
        else
            System.out.println("Time already set");
        
    }

    public static void insertIntoDatabase(User user){
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
            ResultSet rs = st.executeQuery("INSERT INTO leaderboard (nickname, time) \n VALUES ('" +user.getNickname()+ "'," +user.getTime()+ ");");
            
            rs.close();
            st.close();
            }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printLeaderboard(){
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
            ResultSet rs = st.executeQuery("SELECT * FROM leaderboard ORDER BY time asc");
            System.out.println("Users in ascending order: ");
            while (rs.next()) {
                
                System.out.println("User ID: " +rs.getString(1)+ " \nUser Nickname: " +rs.getString(2)+ " \nTime (in seconds): "+rs.getString(3));
                System.out.println();
                
            }
            rs.close();
            st.close();
            }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {

        User x = new User("Java", 13231);
        insertIntoDatabase(x);
        printLeaderboard();
    }

}
