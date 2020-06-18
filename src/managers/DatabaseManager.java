package managers;

import java.sql.*;
import java.util.ArrayList;


/**
 * Class to manage database operations.
 */
public class DatabaseManager {
	
	private DatabaseManager() {} // hide constructor
	
    private static Connection conn = null;
    private static String path = null;
    
    
    /**
     * 
     */
    public static void connect(String path) {
    	DatabaseManager.path = path;
    	connect();
    }
    /**
     * Connect to an sqlite database and create the recent files table if it doesn't exist.
     * 
     * @param path path to database
     */
    public static void connect() {
        try {
            // db parameters
            Class.forName("org.sqlite.JDBC");
            String url = String.format("jdbc:sqlite:%s", DatabaseManager.path);
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
        	e.printStackTrace();
        }
        
        // creating the table if its not already there
        String sql = "CREATE TABLE IF NOT EXISTS RECENT (\n" +
                "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    NAME TEXT NOT NULL,\n" +
                "    DATA BLOB NOT NULL,\n" +
                "    RECENTDATE TEXT DEFAULT (datetime('now', 'localtime'))" + // 0 for text, 1 for image
                ");";
        
        PreparedStatement pstmt = null;
        try {
        	pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	if(pstmt != null) {
        		try {
        			pstmt.close();
        		} catch (SQLException e){
        			// do nothing
        		}
        	}
        }
    }

    /**
     * disconnect from database
     */
    
    public static void disconnect() {
    	if(conn == null)
    		return;
    	
    	try {
    		conn.close();
    	} catch(SQLException e) {
    		
    	}
    }
    
    
    /**
     * Insert a data into the database with a certain name
     *
     * @param name              The name of the object, will be used to retrieve it later.
     * @param data   			The canvas data.
     */
    public static void insert(String name, byte[] data) {

        if(data == null)
            throw new IllegalArgumentException("data cannot be null");

        String sql = "INSERT INTO recent(NAME, DATA) VALUES(?,?)";
        PreparedStatement pstmt = null;
        try {
        	pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setBytes(2, data);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	if(pstmt != null) {
        		try {
        			pstmt.close();
        		} catch (SQLException e){
        			// do nothing
        		}
        	}
        }
    }

    /**
     * Get a data object from the database given a name.
     * @param name  The name used to store the data object.
     * @return      The data object with that name, if not found return null.
     */
    public static byte[] get(int id)
    {
        String sql = "SELECT DATA FROM recent WHERE ID= ?;";
        byte[] data = null;
        PreparedStatement pstmt = null;
        try {
        	pstmt  = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs  = pstmt.executeQuery();
            if (rs.isClosed())
                return null;


            data = rs.getBytes(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	if(pstmt != null) {
        		try {
        			pstmt.close();
        		} catch (SQLException e){
        			// do nothing
        		}
        	}
        }
        return data;
    }



    public static ArrayList<CanvasDatabaseObject> list()
    {
    	if(conn == null) return null;
    	
        String sql = "SELECT ID, NAME, RECENTDATE FROM RECENT ORDER BY datetime(RECENTDATE) DESC LIMIT 15;";
        
        ArrayList<CanvasDatabaseObject> list = new ArrayList<CanvasDatabaseObject>();
        
        PreparedStatement pstmt  = null;
        try {
        	pstmt  = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if (rs.isClosed())
                return null;

            while(rs.next()) {
            	int id = rs.getInt(1);
                String name = rs.getString(2);
                String date = rs.getString(3);
                list.add(
                        new CanvasDatabaseObject(id, name, date)
                );

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
        	if(pstmt != null) {
        		try {
        			pstmt.close();
        		} catch (SQLException e){
        			// do nothing
        		}
        	}
        }
        
        return list;

    }

    public static boolean isConnected()
    {
    	if (conn == null) return false;
    	
    	boolean isOpened = false;
    	try {
    		isOpened = conn.isClosed();
    	} catch (SQLException e) {
    		
    	}
    	return isOpened;
    }

}