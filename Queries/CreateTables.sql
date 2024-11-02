-- Tabelas de suporte
CREATE TABLE `ddd` (
  `ddd_id` int PRIMARY KEY,
  `ddd` varchar(3)
);

CREATE TABLE `ddi` (
  `ddi_id` int PRIMARY KEY,
  `ddi` varchar(3)
);

CREATE TABLE `TipoLogradouro` (
  `idTipoLogradouro` int PRIMARY KEY,
  `nomeTipoLogradouro` varchar(10)
);

CREATE TABLE `UnidadeFederativa` (
  `idUnidadeFederativa` int PRIMARY KEY,
  `siglaUF` varchar(3),
  `nomeUF` varchar(100)
);

-- Tabelas geográficas
CREATE TABLE `Cidade` (
  `idCidade` int PRIMARY KEY,
  `idUnidadeFederativa` int,
  `nomeCidade` varchar(40),
  FOREIGN KEY (`idUnidadeFederativa`) REFERENCES `UnidadeFederativa` (`idUnidadeFederativa`)
);

CREATE TABLE `Bairro` (
  `idBairro` int PRIMARY KEY,
  `nomeBairro` varchar(50)
);

CREATE TABLE `Logradouro` (
  `idLogradouro` int PRIMARY KEY,
  `idTipoLogradouro` int,
  `nomeLogradouro` varchar(40),
  FOREIGN KEY (`idTipoLogradouro`) REFERENCES `TipoLogradouro` (`idTipoLogradouro`)
);

CREATE TABLE `Endereco` (
  `idEndereco` int PRIMARY KEY,
  `idLogradouro` int,
  `idCidade` int,
  `idBairro` int,
  FOREIGN KEY (`idLogradouro`) REFERENCES `Logradouro` (`idLogradouro`),
  FOREIGN KEY (`idCidade`) REFERENCES `Cidade` (`idCidade`),
  FOREIGN KEY (`idBairro`) REFERENCES `Bairro` (`idBairro`)
);

-- Tabelas médicas e de pacientes
CREATE TABLE `Medico` (
  `idMedico` int PRIMARY KEY,
  `nome` varchar(40),
  `dtNascimento` date,
  `estCivil` ENUM('SOLTEIRO', 'CASADO', 'DIVORCIADO', 'VIÚVO'),
  `idEndereco` int,
  `sexo` ENUM('M', 'F'),
  `docIdentidade` varchar(9),
  `crm` varchar(10),
  FOREIGN KEY (`idEndereco`) REFERENCES `Endereco` (`idEndereco`)
);

CREATE TABLE `Paciente` (
  `nroPaciente` int PRIMARY KEY,
  `nome` varchar(40),
  `dtNascimento` date,
  `estCivil` ENUM('SOLTEIRO', 'CASADO', 'DIVORCIADO', 'VIÚVO'),
  `idEndereco` int,
  `sexo` ENUM('M', 'F'),
  `docIdentidade` varchar(9),
  FOREIGN KEY (`idEndereco`) REFERENCES `Endereco` (`idEndereco`)
);

-- Contato
CREATE TABLE `EmailMedico` (
  `idMedico` int,
  `enderecoEmail` varchar(30),
  PRIMARY KEY (`idMedico`, `enderecoEmail`),
  FOREIGN KEY (`idMedico`) REFERENCES `Medico` (`idMedico`)
);

CREATE TABLE `EmailPaciente` (
  `nroPaciente` int, 
  `enderecoEmail` varchar(30),
  PRIMARY KEY (`nroPaciente`, `enderecoEmail`),
  FOREIGN KEY (`nroPaciente`) REFERENCES `Paciente` (`nroPaciente`)
);

CREATE TABLE `FoneMedico` (
  `idMedico` int,
  `ddi_id` int,
  `ddd_id` int,
  `numTelefone` varchar(20),
  PRIMARY KEY (`idMedico`, `numTelefone`),
  FOREIGN KEY (`idMedico`) REFERENCES `Medico` (`idMedico`),
  FOREIGN KEY (`ddi_id`) REFERENCES `ddi` (`ddi_id`),
  FOREIGN KEY (`ddd_id`) REFERENCES `ddd` (`ddd_id`)
);

CREATE TABLE `FonePaciente` (
  `nroPaciente` int,
  `ddi_id` int,
  `ddd_id` int,
  `numTelefone` varchar(20),
  PRIMARY KEY (`nroPaciente`, `numTelefone`),
  FOREIGN KEY (`nroPaciente`) REFERENCES `Paciente` (`nroPaciente`),
  FOREIGN KEY (`ddi_id`) REFERENCES `ddi` (`ddi_id`),
  FOREIGN KEY (`ddd_id`) REFERENCES `ddd` (`ddd_id`)
);

-- Tabelas de consulta e exames
CREATE TABLE `TipoExame` (
  `tipoExame` int PRIMARY KEY,
  `nomeExame` varchar(40)
);

CREATE TABLE `ResultadoExame` (
  `idResultadoGeral` int PRIMARY KEY,
  `resultadoExame` varchar(40),
  `obsExame` TEXT
);

CREATE TABLE `CodigoDiagnostico` (
  `idCodDiagnostico` int PRIMARY KEY,
  `codigoCID` varchar(30),
  `descricaoDiagnostico` TEXT
);

CREATE TABLE `ConsultaMedica` (
  `nroConsulta` int PRIMARY KEY AUTO_INCREMENT,
  `nroPaciente` int,
  `idMedico` int,
  `idCodDiagnostico` int,
  `dataConsulta` date,
  FOREIGN KEY (`nroPaciente`) REFERENCES `Paciente` (`nroPaciente`),
  FOREIGN KEY (`idMedico`) REFERENCES `Medico` (`idMedico`),
  FOREIGN KEY (`idCodDiagnostico`) REFERENCES `CodigoDiagnostico` (`idCodDiagnostico`)
);

CREATE TABLE `ExameMedico` (
  `nroExame` int PRIMARY KEY AUTO_INCREMENT,
  `idMedico` int,
  `nroPaciente` int,
  `dtExame` date,
  `tipoExame` int,
  `idResultadoGeral` int,
  FOREIGN KEY (`idMedico`) REFERENCES `Medico` (`idMedico`),
  FOREIGN KEY (`nroPaciente`) REFERENCES `Paciente` (`nroPaciente`),
  FOREIGN KEY (`tipoExame`) REFERENCES `TipoExame` (`tipoExame`),
  FOREIGN KEY (`idResultadoGeral`) REFERENCES `ResultadoExame` (`idResultadoGeral`)
);
