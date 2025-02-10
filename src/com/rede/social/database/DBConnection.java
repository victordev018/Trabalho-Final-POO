package com.rede.social.database;

import com.rede.social.exception.database.DBException;

import java.sql.*;

public class DBConnection {

    private Connection conn = null;

    public Connection getConnection() throws DBException {
        String localURL = "jdbc:postgresql://localhost/social_media_app?user=postgres&password=jotave9474&ssl=false";
        String remoteURL = "jdbc:postgresql://dpg-cue10qdsvqrc73d2lvvg-a.oregon-postgres.render.com/social_media?user=jotasammy&password=4CbOriMJLLpsYUreWDAqK1v5UDqZ7Fvx&ssl=false";
        try {
            conn = DriverManager.getConnection(localURL);
            return conn;
        } catch (SQLException e) {
            throw new DBException("Falha enquanto tenta se conectar com o banco de dados" + e.getMessage());
        }
    }

    public void closeConnection() throws DBException{
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DBException("Falha ao tentar fechar conexçao com o banco de dados");
            }
        }
    }

    public static void closeStatement(Statement statement) throws DBException {

        if (statement != null){
            try {
                statement.close();
            }
            catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

    // close ResultSet
    public static void closeResultSet(ResultSet rs) throws DBException {

        if (rs != null){
            try {
                rs.close();
            }
            catch (SQLException e) {
                throw new DBException(e.getMessage());
            }
        }
    }

}
