package dao;

import model.Animal;
import util.SessaoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {
	
	private Connection connection;

    public AnimalDAO(Connection connection) {
    	this.connection = connection;
	}

	
    public void create(Animal animal) throws SQLException {
        String sql = "INSERT INTO Animal (nome, sexo, idade, especie, raca, foto, idDono) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, String.valueOf(animal.getSexo()));
            stmt.setInt(3, animal.getIdade());
            stmt.setString(4, animal.getEspecie());
            stmt.setString(5, animal.getRaca());
            stmt.setBytes(6, animal.getImgAnimal());
            stmt.setInt(7,SessaoUsuario.getIdUsuario());
            stmt.executeUpdate();
        }
    }

    
    public List<Animal> mostrarAnimais(int idDono) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM Animal WHERE idDono = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDono);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Animal animal = new Animal();
                    animal.setNome(rs.getString("nome"));
                    animal.setSexo(rs.getString("sexo").charAt(0));
                    animal.setIdade(rs.getInt("idade"));
                    animal.setEspecie(rs.getString("especie"));
                    animal.setRaca(rs.getString("raca"));
                    animal.setImgAnimal(rs.getBytes("foto")); 
                    animal.setIdAnimal(rs.getInt("idAnimal"));
                    animals.add(animal);
                }
            }
        }
        return animals;
    }

   
    public void update(Animal animal, int id) throws SQLException {
        String sql = "UPDATE Animal SET nome = ?, sexo = ?, idade = ?, especie = ?, raca = ?, idDono = ? WHERE idAnimal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, String.valueOf(animal.getSexo()));
            stmt.setInt(3, animal.getIdade());
            stmt.setString(4, animal.getEspecie());
            stmt.setString(5, animal.getRaca());
            //stmt.setBytes(6, animal.getImgAnimal());
            stmt.setInt(6, SessaoUsuario.getIdUsuario());
            stmt.setInt(7, id);
            stmt.executeUpdate();
        }
    }


    public void delete(int idAnimal) throws SQLException {
        String sql = "DELETE FROM Animal WHERE idAnimal = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAnimal);
            stmt.executeUpdate();
        }
    }
    
    private Animal mapRow(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setIdAnimal(rs.getInt("idAnimal"));
        animal.setNome(rs.getString("nome"));
        animal.setSexo(rs.getString("sexo").charAt(0)); 
        animal.setIdade(rs.getInt("idade"));
        animal.setEspecie(rs.getString("especie"));
        animal.setRaca(rs.getString("raca"));
        //animal.setImgAnimal(rs.getBytes("foto"));
        return animal;
    }
    
    public List<Animal> read() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM Animal";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                animals.add(mapRow(rs)); 
            }
        }
        return animals;
    }

    
    
}
