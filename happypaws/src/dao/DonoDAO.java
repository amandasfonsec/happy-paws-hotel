
package dao;

import model.Dono;
import util.SessaoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class DonoDAO  {
	
	private Connection connection;
	
    public DonoDAO(Connection connection) {
		this.connection = connection;
	}

	
    public void create(Dono dono) throws SQLException {
        String sql = "INSERT INTO Dono (nomeDono, email, cpf, senha, telefone, contatoEmergencia) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dono.getNomeDono());
            stmt.setString(2, dono.getEmail());
            stmt.setString(3, dono.getCpf());
            stmt.setString(4, dono.getSenha());
            stmt.setString(5, dono.getTelefone());
            stmt.setString(6, dono.getContatoEmergencia());
            stmt.executeUpdate();
        }
    }

   
    public List<Dono> read() throws SQLException {
        List<Dono> donos = new ArrayList<>();
        String sql = "SELECT * FROM Dono";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                donos.add(mapRow(rs));
            }
        }
        return donos;
    }

    
    public void update(Dono dono) throws SQLException {
        String sql = "UPDATE Dono SET nomeDono = ?, email = ?, cpf = ?, senha = ?, telefone = ?, contatoEmergencia = ? WHERE idDono = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dono.getNomeDono());
            stmt.setString(2, dono.getEmail());
            stmt.setString(3, dono.getCpf());
            stmt.setString(4, dono.getSenha());
            stmt.setString(5, dono.getTelefone());
            stmt.setString(6, dono.getContatoEmergencia());
            stmt.setInt(7, SessaoUsuario.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    
    public void delete(int idDono) throws SQLException {
        String sql = "DELETE FROM Dono WHERE idDono = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDono);
            stmt.executeUpdate();
        }
    }

   
    protected Dono mapRow(ResultSet rs) throws SQLException {
        Dono dono = new Dono();
        dono.setIdDono(rs.getInt("idDono"));
        dono.setNomeDono(rs.getString("nomeDono"));
        dono.setEmail(rs.getString("email"));
        dono.setCpf(rs.getString("cpf"));
        dono.setSenha(rs.getString("senha"));
        dono.setTelefone(rs.getString("telefone"));
        dono.setContatoEmergencia(rs.getString("contatoEmergencia"));
        return dono;
    }
    
    
    public Integer validarLogin(Dono dono) {
        try {
            String sql = "select idDono from dono where email = ? and senha = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, dono.getEmail());
            pstm.setString(2, dono.getSenha());
            
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("idDono");
            } else {
                return null;
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "DonoDAO: " + erro);
            return null;
        }
    }
    
    public boolean verificarCpfExistente(String cpf) {
        String sql = "SELECT COUNT(*) FROM dono WHERE cpf = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean verificarEmail(String email) {
        String query = "SELECT COUNT(*) FROM dono WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
            	int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  
    }
}
