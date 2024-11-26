package util;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        Connection conn = null;

        try {

            conn = ConectaMySQL.conectaBD();

            if (conn != null) {
                System.out.println("Conexão realizada com sucesso!");
            } else {
                System.out.println("Falha na conexão.");
            }
        } finally {

            ConectaMySQL.closeConnection(conn);
        }
    }
}
