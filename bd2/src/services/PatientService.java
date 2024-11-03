package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import models.MedicalConsultation;
import models.MedicalExam;
import models.Patient;

public class PatientService {

    public Patient getPatientByDocument(String docIdentidade) {
        Patient patient = null;
        String sql = "SELECT * FROM Paciente WHERE docIdentidade = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, docIdentidade);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                patient = new Patient();
                patient.setNroPaciente(rs.getInt("nroPaciente"));
                patient.setNome(rs.getString("nome"));
                patient.setDtNascimento(rs.getDate("dtNascimento"));
                patient.setEstCivil(rs.getString("estCivil"));
                patient.setIdEndereco(rs.getInt("idEndereco"));
                patient.setSexo(rs.getString("sexo"));
                patient.setDocIdentidade(rs.getString("docIdentidade"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return patient;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT nome, docIdentidade FROM Paciente";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setNome(rs.getString("nome"));
                patient.setDocIdentidade(rs.getString("docIdentidade"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar pacientes: " + e.getMessage());
            e.printStackTrace();
        }
        return patients;
    }

    public List<MedicalConsultation> getAllConsultations() {
        List<MedicalConsultation> consultations = new ArrayList<>();
        String sql = "SELECT " +
                     "c.nroConsulta, c.dataConsulta, " +
                     "m.nome AS nomeMedico, m.crm, em.enderecoEmail, " +
                     "d.codigoCID, d.descricaoDiagnostico " +
                     "FROM ConsultaMedica c " +
                     "JOIN Medico m ON c.idMedico = m.idMedico " +
                     "LEFT JOIN EmailMedico em ON m.idMedico = em.idMedico " +
                     "JOIN CodigoDiagnostico d ON c.idCodDiagnostico = d.idCodDiagnostico";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicalConsultation consultation = new MedicalConsultation();
                consultation.setNroConsulta(rs.getInt("nroConsulta"));
                consultation.setDataConsulta(rs.getDate("dataConsulta"));
                consultation.setNomeMedico(rs.getString("nomeMedico"));
                consultation.setCrmMedico(rs.getString("crm"));
                consultation.setEmailMedico(rs.getString("enderecoEmail"));
                consultation.setCodigoCID(rs.getString("codigoCID"));
                consultation.setDescricaoDiagnostico(rs.getString("descricaoDiagnostico"));
                consultations.add(consultation);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar consultas: " + e.getMessage());
            e.printStackTrace();
        }
        return consultations;
    }

    public List<MedicalExam> getAllExams() {
        List<MedicalExam> exams = new ArrayList<>();
        String sql = "SELECT " +
                     "e.nroExame, e.dtExame, " +
                     "t.tipoExame AS codigoTipoExame, t.nomeExame, " +
                     "r.resultadoExame, r.obsExame " +
                     "FROM ExameMedico e " +
                     "JOIN TipoExame t ON e.tipoExame = t.tipoExame " +
                     "JOIN ResultadoExame r ON e.idResultadoGeral = r.idResultadoGeral";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicalExam exam = new MedicalExam();
                exam.setNroExame(rs.getInt("nroExame"));
                exam.setDtExame(rs.getDate("dtExame"));
                exam.setTipoExame(rs.getInt("codigoTipoExame"));
                exam.setNomeExame(rs.getString("nomeExame"));
                exam.setResultadoExame(rs.getString("resultadoExame"));
                exam.setObsExame(rs.getString("obsExame"));
                exams.add(exam);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar exames: " + e.getMessage());
            e.printStackTrace();
        }
        return exams;
    }

    public List<MedicalConsultation> getConsultationsByPatient(int nroPaciente) {
        List<MedicalConsultation> consultations = new ArrayList<>();
        String sql = "SELECT * FROM ConsultaMedica WHERE nroPaciente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nroPaciente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicalConsultation consultation = new MedicalConsultation();
                consultation.setNroConsulta(rs.getInt("nroConsulta"));
                consultation.setNroPaciente(rs.getInt("nroPaciente"));
                consultation.setIdMedico(rs.getInt("idMedico"));
                consultation.setIdCodDiagnostico(rs.getInt("idCodDiagnostico"));
                consultation.setDataConsulta(rs.getDate("dataConsulta"));
                consultations.add(consultation);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar consultas: " + e.getMessage());
            e.printStackTrace();
        }
        return consultations;
    }

    public List<MedicalExam> getExamsByPatient(int nroPaciente) {
        List<MedicalExam> exams = new ArrayList<>();
        String sql = "SELECT * FROM ExameMedico WHERE nroPaciente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nroPaciente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicalExam exam = new MedicalExam();
                exam.setNroExame(rs.getInt("nroExame"));
                exam.setIdMedico(rs.getInt("idMedico"));
                exam.setNroPaciente(rs.getInt("nroPaciente"));
                exam.setDtExame(rs.getDate("dtExame"));
                exam.setTipoExame(rs.getInt("tipoExame"));
                exam.setIdResultadoGeral(rs.getInt("idResultadoGeral"));
                exams.add(exam);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar exames: " + e.getMessage());
            e.printStackTrace();
        }
        return exams;
    }
}
