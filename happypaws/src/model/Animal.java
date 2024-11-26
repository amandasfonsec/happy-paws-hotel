package model;

public class Animal {
    private int idAnimal;
    private String nome;
    private char sexo;
    private int idade;
    private String especie;
    private String raca;
    private byte [] imgAnimal;
	private int idDono;

    // Getters e Setters
    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdDono() {
        return idDono;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }
    
    public byte[] getImgAnimal() {
		return imgAnimal;
	}

	public void setImgAnimal(byte[] imgAnimal) {
		this.imgAnimal = imgAnimal;
	}
}

