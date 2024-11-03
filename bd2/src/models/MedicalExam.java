package models;

import java.util.Date;

public class MedicalExam {
    private int nroExame;
    private int idMedico;
    private int nroPaciente;
    private Date dtExame;
    private int tipoExame;
    private int idResultadoGeral;

    // Novos atributos para detalhes completos
    private String nomeExame;
    private String resultadoExame;
    private String obsExame;

    // Getters e Setters
    public int getNroExame() {
        return nroExame;
    }

    public void setNroExame(int nroExame) {
        this.nroExame = nroExame;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getNroPaciente() {
        return nroPaciente;
    }

    public void setNroPaciente(int nroPaciente) {
        this.nroPaciente = nroPaciente;
    }

    public Date getDtExame() {
        return dtExame;
    }

    public void setDtExame(Date dtExame) {
        this.dtExame = dtExame;
    }

    public int getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(int tipoExame) {
        this.tipoExame = tipoExame;
    }

    public int getIdResultadoGeral() {
        return idResultadoGeral;
    }

    public void setIdResultadoGeral(int idResultadoGeral) {
        this.idResultadoGeral = idResultadoGeral;
    }

    // Novos getters e setters
    public String getNomeExame() {
        return nomeExame;
    }

    public void setNomeExame(String nomeExame) {
        this.nomeExame = nomeExame;
    }

    public String getResultadoExame() {
        return resultadoExame;
    }

    public void setResultadoExame(String resultadoExame) {
        this.resultadoExame = resultadoExame;
    }

    public String getObsExame() {
        return obsExame;
    }

    public void setObsExame(String obsExame) {
        this.obsExame = obsExame;
    }
}
