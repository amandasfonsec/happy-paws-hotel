package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaMySQL {


    public static Connection conectaBD() {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/PetHotel?user=root&password=";
            conn = DriverManager.getConnection(url);
            //JOptionPane.showMessageDialog(null, "Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Erro de Conexão: " + e.getMessage());
        }
        return conn;
    }


    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }


	public static Connection getConnection() {

		return null;
	}
}
