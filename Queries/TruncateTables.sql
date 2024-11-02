-- Desativa as restrições de chaves estrangeiras temporariamente
SET FOREIGN_KEY_CHECKS = 0;

-- Limpa os dados de todas as tabelas
TRUNCATE TABLE FonePaciente;
TRUNCATE TABLE EmailPaciente;
TRUNCATE TABLE ConsultaMedica;
TRUNCATE TABLE ExameMedico;
TRUNCATE TABLE ResultadoExame;
TRUNCATE TABLE TipoExame;
TRUNCATE TABLE CodigoDiagnostico;
TRUNCATE TABLE Medico;
TRUNCATE TABLE FoneMedico;
TRUNCATE TABLE EmailMedico;
TRUNCATE TABLE Paciente;
TRUNCATE TABLE Endereco;
TRUNCATE TABLE Logradouro;
TRUNCATE TABLE TipoLogradouro;
TRUNCATE TABLE Bairro;
TRUNCATE TABLE Cidade;
TRUNCATE TABLE UnidadeFederativa;
TRUNCATE TABLE ddi;
TRUNCATE TABLE ddd;

-- Reativa as restrições de chaves estrangeiras
SET FOREIGN_KEY_CHECKS = 1;
