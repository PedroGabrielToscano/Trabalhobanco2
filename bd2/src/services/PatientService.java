package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Address;
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

                // Recupera o endereço do paciente
                Address address = getAddressById(rs.getInt("idEndereco"));
                patient.setEndereco(address);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar paciente: " + e.getMessage());
            e.printStackTrace();
        }
        return patient;
    }
    
    
    public List<String> getCartesianProduct() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT Medico.idMedico, Medico.nome AS nomeMedico, TipoExame.tipoExame, TipoExame.nomeExame " +
                     "FROM Medico " +
                     "CROSS JOIN TipoExame";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add("ID Medico: " + rs.getInt("idMedico") + 
                            ", Nome Medico: " + rs.getString("nomeMedico") + 
                            ", Tipo Exame: " + rs.getInt("tipoExame") + 
                            ", Nome Exame: " + rs.getString("nomeExame"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao realizar o produto cartesiano: " + e.getMessage());
        }
        return results;
    }

    // União de Médicos e Pacientes Casados
    public List<String> getUnionCasado() {
        List<String> results = new ArrayList<>();
        String sql = "SELECT 'Paciente' AS Tipo, nome, dtNascimento, estCivil " +
                     "FROM Paciente " +
                     "WHERE estCivil = 'CASADO' " +
                     "UNION ALL " +
                     "SELECT 'Medico' AS Tipo, nome, dtNascimento, estCivil " +
                     "FROM Medico " +
                     "WHERE estCivil = 'CASADO'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                results.add("Tipo: " + rs.getString("Tipo") + 
                            ", Nome: " + rs.getString("nome") + 
                            ", Data Nascimento: " + rs.getDate("dtNascimento") + 
                            ", Estado Civil: " + rs.getString("estCivil"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao realizar a união: " + e.getMessage());
        }
        return results;
    }

    // Diferença (Pacientes com consulta mas sem exame)
    public List<Patient> getPatientsWithConsultationNoExam() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT p.nroPaciente, p.nome, p.docIdentidade " +
                     "FROM Paciente p " +
                     "WHERE p.nroPaciente IN (SELECT c.nroPaciente FROM ConsultaMedica c) " +
                     "AND p.nroPaciente NOT IN (SELECT e.nroPaciente FROM ExameMedico e)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setNroPaciente(rs.getInt("nroPaciente"));
                patient.setNome(rs.getString("nome"));
                patient.setDocIdentidade(rs.getString("docIdentidade"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pacientes com consulta mas sem exame: " + e.getMessage());
        }
        return patients;
    }

    private Address getAddressById(int idEndereco) {
        Address address = null;
        String sql = "SELECT e.idEndereco, e.idLogradouro, e.idCidade, e.idBairro, " +
                     "tl.nomeTipoLogradouro, l.nomeLogradouro, " +
                     "c.nomeCidade, uf.siglaUF, uf.nomeUF, b.nomeBairro " +
                     "FROM Endereco e " +
                     "JOIN Logradouro l ON e.idLogradouro = l.idLogradouro " +
                     "JOIN TipoLogradouro tl ON l.idTipoLogradouro = tl.idTipoLogradouro " +
                     "JOIN Cidade c ON e.idCidade = c.idCidade " +
                     "JOIN UnidadeFederativa uf ON c.idUnidadeFederativa = uf.idUnidadeFederativa " +
                     "JOIN Bairro b ON e.idBairro = b.idBairro " +
                     "WHERE e.idEndereco = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEndereco);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                address = new Address();
                address.setIdEndereco(rs.getInt("idEndereco"));
                address.setIdLogradouro(rs.getInt("idLogradouro"));
                address.setIdCidade(rs.getInt("idCidade"));
                address.setIdBairro(rs.getInt("idBairro"));
                address.setTipoLogradouro(rs.getString("nomeTipoLogradouro"));
                address.setNomeLogradouro(rs.getString("nomeLogradouro"));
                address.setNomeCidade(rs.getString("nomeCidade"));
                address.setSiglaUF(rs.getString("siglaUF"));
                address.setNomeUF(rs.getString("nomeUF"));
                address.setNomeBairro(rs.getString("nomeBairro"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar endereço: " + e.getMessage());
            e.printStackTrace();
        }
        return address;
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
        String sql = "SELECT c.nroConsulta, c.dataConsulta, " +
                     "m.nome AS nomeMedico, m.crm, em.enderecoEmail, " +
                     "d.codigoCID, d.descricaoDiagnostico " +
                     "FROM ConsultaMedica c " +
                     "JOIN Medico m ON c.idMedico = m.idMedico " +
                     "LEFT JOIN EmailMedico em ON m.idMedico = em.idMedico " +
                     "JOIN CodigoDiagnostico d ON c.idCodDiagnostico = d.idCodDiagnostico " +
                     "WHERE c.nroPaciente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nroPaciente);
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
            System.err.println("Erro ao buscar consultas: " + e.getMessage());
            e.printStackTrace();
        }
        return consultations;
    }

    public List<MedicalExam> getExamsByPatient(int nroPaciente) {
        List<MedicalExam> exams = new ArrayList<>();
        String sql = "SELECT e.nroExame, e.dtExame, " +
                     "t.tipoExame AS codigoTipoExame, t.nomeExame, " +
                     "r.resultadoExame, r.obsExame " +
                     "FROM ExameMedico e " +
                     "JOIN TipoExame t ON e.tipoExame = t.tipoExame " +
                     "JOIN ResultadoExame r ON e.idResultadoGeral = r.idResultadoGeral " +
                     "WHERE e.nroPaciente = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nroPaciente);
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
            System.err.println("Erro ao buscar exames: " + e.getMessage());
            e.printStackTrace();
        }
        return exams;
    }
}
