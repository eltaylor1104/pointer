package org.Pointer.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);
    private Properties props;
    private Connection conn;

    private String username;
    private String password;
    private String url;


    private Properties populateProperties(){
        if (props == null){
            props = new Properties();

            try (FileReader in = new FileReader("Properties/db.propConfs")) {
                props.load(in);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            username = props.getProperty("username");
            password = props.getProperty("password");
            url = props.getProperty("url");
        }

        return props;
    }

    public Connection connect() {
        logger.info("Connecting to DB");
        populateProperties();
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
        return conn;
    }

    public void disconnect() {
        logger.info("Disconnecting from DB");
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }
    }
}
