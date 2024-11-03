package models;

import java.util.Date;

public class MedicalConsultation {
    private int nroConsulta;
    private int nroPaciente;
    private int idMedico;
    private int idCodDiagnostico;
    private Date dataConsulta;

    // Novos atributos para listagem geral
    private String nomeMedico;
    private String crmMedico;
    private String emailMedico;
    private String codigoCID;
    private String descricaoDiagnostico;

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

    // Novos getters e setters
    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getCrmMedico() {
        return crmMedico;
    }

    public void setCrmMedico(String crmMedico) {
        this.crmMedico = crmMedico;
    }

    public String getEmailMedico() {
        return emailMedico;
    }

    public void setEmailMedico(String emailMedico) {
        this.emailMedico = emailMedico;
    }

    public String getCodigoCID() {
        return codigoCID;
    }

    public void setCodigoCID(String codigoCID) {
        this.codigoCID = codigoCID;
    }

    public String getDescricaoDiagnostico() {
        return descricaoDiagnostico;
    }

    public void setDescricaoDiagnostico(String descricaoDiagnostico) {
        this.descricaoDiagnostico = descricaoDiagnostico;
    }
}
