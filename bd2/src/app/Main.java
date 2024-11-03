package app;

import java.util.List;
import java.util.Scanner;

import models.MedicalConsultation;
import models.MedicalExam;
import models.Patient;
import services.PatientService;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PatientService patientService = new PatientService();

        int option = 0;
        while (option != 5) {
            System.out.println("Menu:");
            System.out.println("1 - Pesquisar cliente");
            System.out.println("2 - Listar todos os pacientes");
            System.out.println("3 - Listar consultas médicas realizadas");
            System.out.println("4 - Listar exames realizados");
            System.out.println("5 - Sair");
            System.out.print("Selecione uma opção: ");

            option = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (option) {
                case 1:
                    searchClient(scanner, patientService);
                    break;
                case 2:
                    listAllPatients(patientService);
                    break;
                case 3:
                    listAllConsultations(patientService);
                    break;
                case 4:
                    listAllExams(patientService);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void searchClient(Scanner scanner, PatientService patientService) {
        System.out.print("Digite o documento do paciente: ");
        String docIdentidade = scanner.nextLine();

        Patient patient = patientService.getPatientByDocument(docIdentidade);

        if (patient != null) {
            // Exibe as informações do paciente
            System.out.println("Paciente encontrado:");
            System.out.println("Número do paciente: " + patient.getNroPaciente());
            System.out.println("Nome: " + patient.getNome());
            System.out.println("Data de nascimento: " + patient.getDtNascimento());
            System.out.println("Estado civil: " + patient.getEstCivil());
            System.out.println("Sexo: " + patient.getSexo());
            System.out.println("Documento de identidade: " + patient.getDocIdentidade());

            // Exibe o segundo menu
            int subOption = 0;
            while (subOption != 3) {
                System.out.println("Menu:");
                System.out.println("1 - Listar consultas médicas do paciente");
                System.out.println("2 - Listar exames médicos do paciente");
                System.out.println("3 - Voltar ao menu principal");
                System.out.print("Selecione uma opção: ");
                subOption = scanner.nextInt();
                scanner.nextLine(); // Consome a nova linha

                switch (subOption) {
                    case 1:
                        // Lista as consultas do paciente
                        listPatientConsultations(patient.getNroPaciente(), patientService);
                        break;
                    case 2:
                        // Lista os exames do paciente
                        listPatientExams(patient.getNroPaciente(), patientService);
                        break;
                    case 3:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } else {
            System.out.println("Paciente não encontrado com o documento fornecido.");
        }
    }

    private static void listAllPatients(PatientService patientService) {
        List<Patient> patients = patientService.getAllPatients();

        if (patients.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            System.out.println("Lista de pacientes:");
            for (Patient patient : patients) {
                System.out.println("Nome: " + patient.getNome());
                System.out.println("Documento de identidade: " + patient.getDocIdentidade());
                System.out.println("-----------------------------------");
            }
        }
    }

    private static void listAllConsultations(PatientService patientService) {
        List<MedicalConsultation> consultations = patientService.getAllConsultations();

        if (consultations.isEmpty()) {
            System.out.println("Nenhuma consulta médica encontrada.");
        } else {
            System.out.println("Consultas médicas realizadas:");
            for (MedicalConsultation consultation : consultations) {
                System.out.println("Número da Consulta: " + consultation.getNroConsulta());
                System.out.println("Data da Consulta: " + consultation.getDataConsulta());
                System.out.println("Nome do Médico: " + consultation.getNomeMedico());
                System.out.println("CRM: " + consultation.getCrmMedico());
                System.out.println("Email do Médico: " + consultation.getEmailMedico());
                System.out.println("Código CID: " + consultation.getCodigoCID());
                System.out.println("Descrição do Diagnóstico: " + consultation.getDescricaoDiagnostico());
                System.out.println("-----------------------------------");
            }
        }
    }

    private static void listAllExams(PatientService patientService) {
        List<MedicalExam> exams = patientService.getAllExams();

        if (exams.isEmpty()) {
            System.out.println("Nenhum exame médico encontrado.");
        } else {
            System.out.println("Exames realizados:");
            for (MedicalExam exam : exams) {
                System.out.println("Número do Exame: " + exam.getNroExame());
                System.out.println("Data do Exame: " + exam.getDtExame());
                System.out.println("Código do Tipo de Exame: " + exam.getTipoExame());
                System.out.println("Nome do Exame: " + exam.getNomeExame());
                System.out.println("Resultado do Exame: " + exam.getResultadoExame());
                System.out.println("Observações do Exame: " + exam.getObsExame());
                System.out.println("-----------------------------------");
            }
        }
    }

    private static void listPatientConsultations(int nroPaciente, PatientService patientService) {
        List<MedicalConsultation> consultations = patientService.getConsultationsByPatient(nroPaciente);

        if (consultations.isEmpty()) {
            System.out.println("Nenhuma consulta médica encontrada para este paciente.");
        } else {
            System.out.println("Consultas médicas do paciente:");
            for (MedicalConsultation consultation : consultations) {
                System.out.println("Número da consulta: " + consultation.getNroConsulta());
                System.out.println("Data da consulta: " + consultation.getDataConsulta());
                System.out.println("ID do médico: " + consultation.getIdMedico());
                System.out.println("ID do diagnóstico: " + consultation.getIdCodDiagnostico());
                System.out.println("-----------------------------------");
            }
        }
    }

    private static void listPatientExams(int nroPaciente, PatientService patientService) {
        List<MedicalExam> exams = patientService.getExamsByPatient(nroPaciente);

        if (exams.isEmpty()) {
            System.out.println("Nenhum exame médico encontrado para este paciente.");
        } else {
            System.out.println("Exames médicos do paciente:");
            for (MedicalExam exam : exams) {
                System.out.println("Número do exame: " + exam.getNroExame());
                System.out.println("Data do exame: " + exam.getDtExame());
                System.out.println("ID do médico: " + exam.getIdMedico());
                System.out.println("Tipo de exame: " + exam.getTipoExame());
                System.out.println("ID do resultado: " + exam.getIdResultadoGeral());
                System.out.println("-----------------------------------");
            }
        }
    }
}
