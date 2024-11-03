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
        while (option != 9) {
            System.out.println("Menu:");
            System.out.println("1 - Pesquisar cliente");
            System.out.println("2 - Listar todos os pacientes");
            System.out.println("3 - Listar consultas médicas realizadas");
            System.out.println("4 - Listar exames realizados");
            System.out.println("5 - Imprimir prontuário completo do paciente");
            System.out.println("6 - Produto Cartesiano (Medico e TipoExame)");
            System.out.println("7 - União de Médicos e Pacientes Casados");
            System.out.println("8 - Diferença (Pacientes com consulta mas sem exame)");
            System.out.println("0 - Sair");
            System.out.print("Selecione uma opção: ");

            try {
                option = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                option = -1; // Opção inválida
            }

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
                    printPatientRecord(scanner, patientService);
                    break;
                case 6:
                    performCartesianProduct(patientService);
                    break;
                case 7:
                    unionCasado(patientService);
                    break;
                case 8:
                    listPatientsWithConsultationNoExam(patientService);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void performCartesianProduct(PatientService patientService) {
        List<String> results = patientService.getCartesianProduct();
        System.out.println("Produto Cartesiano (Medico e TipoExame):");
        for (String result : results) {
            System.out.println(result);
        }
    }

    private static void unionCasado(PatientService patientService) {
        List<String> results = patientService.getUnionCasado();
        System.out.println("União de Médicos e Pacientes Casados:");
        for (String result : results) {
            System.out.println(result);
        }
    }

    private static void listPatientsWithConsultationNoExam(PatientService patientService) {
        List<Patient> patients = patientService.getPatientsWithConsultationNoExam();
        System.out.println("Pacientes que fizeram consulta mas não fizeram exame:");
        for (Patient patient : patients) {
            System.out.println("Nome: " + patient.getNome() + " | Documento: " + patient.getDocIdentidade());
        }
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
            if (patient.getEndereco() != null) {
                System.out.println("Endereço: " + patient.getEndereco().toString());
            } else {
                System.out.println("Endereço: Não disponível");
            }

            // Exibe o segundo menu
            int subOption = 0;
            while (subOption != 3) {
                System.out.println("Menu:");
                System.out.println("1 - Listar consultas médicas do paciente");
                System.out.println("2 - Listar exames médicos do paciente");
                System.out.println("3 - Voltar ao menu principal");
                System.out.print("Selecione uma opção: ");
                
                try {
                    subOption = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    subOption = -1; // Opção inválida
                }

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

    private static void printPatientRecord(Scanner scanner, PatientService patientService) {
        System.out.print("Digite o documento do paciente: ");
        String docIdentidade = scanner.nextLine();

        Patient patient = patientService.getPatientByDocument(docIdentidade);

        if (patient != null) {
            System.out.println("Prontuário Completo do Paciente:");
            System.out.println("==================================");
            System.out.println("Número do paciente: " + patient.getNroPaciente());
            System.out.println("Nome: " + patient.getNome());
            System.out.println("Data de nascimento: " + patient.getDtNascimento());
            System.out.println("Estado civil: " + patient.getEstCivil());
            System.out.println("Sexo: " + patient.getSexo());
            System.out.println("Documento de identidade: " + patient.getDocIdentidade());
            if (patient.getEndereco() != null) {
                System.out.println("Endereço: " + patient.getEndereco().toString());
            } else {
                System.out.println("Endereço: Não disponível");
            }
            System.out.println("----------------------------------");

            // Listar todas as consultas
            List<MedicalConsultation> consultations = patientService.getConsultationsByPatient(patient.getNroPaciente());
            System.out.println("Consultas Médicas:");
            if (consultations.isEmpty()) {
                System.out.println("Nenhuma consulta médica encontrada para este paciente.");
            } else {
                for (MedicalConsultation consultation : consultations) {
                    System.out.println("Número da Consulta: " + consultation.getNroConsulta());
                    System.out.println("Data da Consulta: " + consultation.getDataConsulta());
                    System.out.println("Nome do Médico: " + consultation.getNomeMedico());
                    System.out.println("CRM do Médico: " + consultation.getCrmMedico());
                    System.out.println("Email do Médico: " + consultation.getEmailMedico());
                    System.out.println("Código CID: " + consultation.getCodigoCID());
                    System.out.println("Descrição do Diagnóstico: " + consultation.getDescricaoDiagnostico());
                    System.out.println("----------------------------------");
                }
            }

            // Listar todos os exames
            List<MedicalExam> exams = patientService.getExamsByPatient(patient.getNroPaciente());
            System.out.println("Exames Médicos:");
            if (exams.isEmpty()) {
                System.out.println("Nenhum exame médico encontrado para este paciente.");
            } else {
                for (MedicalExam exam : exams) {
                    System.out.println("Número do Exame: " + exam.getNroExame());
                    System.out.println("Data do Exame: " + exam.getDtExame());
                    System.out.println("Código do Tipo de Exame: " + exam.getTipoExame());
                    System.out.println("Nome do Exame: " + exam.getNomeExame());
                    System.out.println("Resultado do Exame: " + exam.getResultadoExame());
                    System.out.println("Observações do Exame: " + exam.getObsExame());
                    System.out.println("----------------------------------");
                }
            }

            System.out.println("==================================");
        } else {
            System.out.println("Paciente não encontrado com o documento fornecido.");
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
                System.out.println("Nome do Médico: " + consultation.getNomeMedico());
                System.out.println("CRM do Médico: " + consultation.getCrmMedico());
                System.out.println("Email do Médico: " + consultation.getEmailMedico());
                System.out.println("Código CID: " + consultation.getCodigoCID());
                System.out.println("Descrição do Diagnóstico: " + consultation.getDescricaoDiagnostico());
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
                System.out.println("Código do Tipo de Exame: " + exam.getTipoExame());
                System.out.println("Nome do Exame: " + exam.getNomeExame());
                System.out.println("Resultado do Exame: " + exam.getResultadoExame());
                System.out.println("Observações do Exame: " + exam.getObsExame());
                System.out.println("-----------------------------------");
            }
        }
    }
}
