package models;

import java.util.Date;

public class MedicalConsultation {
    private int nroConsulta;
    private int nroPaciente;
    private int idMedico;
    private int idCodDiagnostico;
    private Date dataConsulta;

    // Getters e Setters
    public int getNroConsulta() {
        return nroConsulta;
    }

    public void setNroConsulta(int nroConsulta) {
        this.nroConsulta = nroConsulta;
    }

    public int getNroPaciente() {
        return nroPaciente;
    }

    public void setNroPaciente(int nroPaciente) {
        this.nroPaciente = nroPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdCodDiagnostico() {
        return idCodDiagnostico;
    }

    public void setIdCodDiagnostico(int idCodDiagnostico) {
        this.idCodDiagnostico = idCodDiagnostico;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }
}
