package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.MedicalConsultation;
import models.MedicalExam;
import models.Patient;
import database.DatabaseConnection;

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
