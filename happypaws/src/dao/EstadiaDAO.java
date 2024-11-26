package dao;

import model.Estadia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadiaDAO {

	private Connection connection;
	
    public EstadiaDAO(Connection connection) {
        this.connection = connection;
    }

    
    public void create(Estadia estadia, int idAnimal) throws SQLException {
        String sql = "INSERT INTO Estadia (idAnimal, dataEntrada, dataSaida, observacoes) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            stmt.setDate(2, estadia.getDataEntrada());
            stmt.setDate(3, estadia.getDataSaida());
            stmt.setString(4, estadia.getObservacoes());
            stmt.executeUpdate();
        }
    }

    
    public List<Estadia> read() throws SQLException {
        List<Estadia> estadias = new ArrayList<>();
        String sql = "SELECT * FROM Estadia";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                estadias.add(mapRow(rs));
            }
        }
        return estadias;
    }

    
    public void update(Estadia estadia, int idEstadia) throws SQLException {
        String sql = "UPDATE Estadia SET dataEntrada = ?, dataSaida = ?, observacoes = ? WHERE idEstadia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, estadia.getDataEntrada());
            stmt.setDate(2, estadia.getDataSaida());
            stmt.setString(3, estadia.getObservacoes());
            stmt.setInt(4, idEstadia);
            stmt.executeUpdate();
        }
    }

   
    public void delete(int idEstadia) throws SQLException {
        String sql = "DELETE FROM Estadia WHERE idEstadia = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEstadia);
            stmt.executeUpdate();
        }
    }

    
    protected Estadia mapRow(ResultSet rs) throws SQLException {
        Estadia estadia = new Estadia();
        estadia.setIdEstadia(rs.getInt("idEstadia"));
        estadia.setIdAnimal(rs.getInt("idAnimal"));
        estadia.setDataEntrada(rs.getDate("dataEntrada"));
        estadia.setDataSaida(rs.getDate("dataSaida"));
        estadia.setObservacoes(rs.getString("observacoes"));
        return estadia;
    }
    
    public List<Estadia> getEstadiasPorDono(int idDono) throws SQLException {
        List<Estadia> estadias = new ArrayList<>();
        String sql = "SELECT e.* FROM Estadia e INNER JOIN Animal a ON e.idAnimal = a.idAnimal WHERE a.idDono = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDono);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    estadias.add(mapRow(rs));
                }
            }
        }
        return estadias;
    }
}
