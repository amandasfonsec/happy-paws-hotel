package model;

import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class Estadia {
    private int idEstadia;
    private int idAnimal;
    private Date dataEntrada;
    private Date dataSaida;
    private String observacoes;
    private static final double VALOR_DIARIO = 100.0;
    
    

    public Estadia() {
		
	}

	public Estadia(Date dataEntrada, Date dataSaida, String observacoes) {
		super();
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.observacoes = observacoes;
	}

	public int getIdEstadia() {
        return idEstadia;
    }

    public void setIdEstadia(int idEstadia) {
        this.idEstadia = idEstadia;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
    public double calcularValorTotal() {
        long dias = ChronoUnit.DAYS.between(dataEntrada.toLocalDate(), dataSaida.toLocalDate());
        return dias * VALOR_DIARIO;
    }
    
    
}
