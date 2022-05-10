package main;
import java.sql.*;
import java.util.ArrayList;

public class User {
    static String url = "jdbc:postgresql://hattie.db.elephantsql.com:5432/gqrbhzwp";
    static String username = "gqrbhzwp";
    static String password = "0ZaNcWqdxpFDgzlB7qvEuJM0ecXkepu4";
    long time;
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
    public long getTime() {
        if(istimeset){
            return time;
        }
        return -1;
    }
    public void setTime(long time) {

        if(!istimeset){
            this.time = time;
            istimeset = true;
        }
        else
            System.out.println("Time already set");
        
    }

    public static void insertIntoDatabase(User user){
        if(user.getTime()==-1)
            return;
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


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
    @Override
    public String toString() {
        
        return "\nNickname: " + getNickname() + " " + " Time(seconds): " +getTime() ;
    }
    public static void printLeaderboard(){
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }



        try {
            Connection db = DriverManager.getConnection(url, username, password);
            Statement st = db.createStatement();
            //st.executeQuery ("create table elephant (id int, name varchar)");
            ResultSet rs = st.executeQuery("SELECT * FROM leaderboard ORDER BY time asc");
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
    public static ArrayList<User> LeaderboardToArray(){
        ArrayList<User> res = new ArrayList<User>();
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }


        try {
            Connection db = DriverManager.getConnection(url, username, password);
            Statement st = db.createStatement();
            //st.executeQuery ("create table elephant (id int, name varchar)");
            ResultSet rs = st.executeQuery("SELECT * FROM leaderboard ORDER BY time asc");
            System.out.println("Users in ascending order: ");
            while (rs.next()) {
                res.add(new User(rs.getString(2), Integer.parseInt(rs.getString(3)) ));
                //System.out.println("User ID: " +rs.getString(1)+ " \nUser Nickname: " +rs.getString(2)+ " \nTime (in seconds): "+rs.getString(3));
                //System.out.println();
                
            }
            rs.close();
            st.close();
            }
        catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
        
    }
 

}
