INSERT INTO UnidadeFederativa (idUnidadeFederativa, siglaUF, nomeUF) VALUES 
    (1, 'SP', 'São Paulo'),
    (2, 'RJ', 'Rio de Janeiro'),
    (3, 'MG', 'Minas Gerais');
INSERT INTO Cidade (idCidade, idUnidadeFederativa, nomeCidade) VALUES 
    (1, 1, 'São Paulo'),
    (2, 2, 'Rio de Janeiro'),
    (3, 3, 'Belo Horizonte');

INSERT INTO Bairro (idBairro, nomeBairro) VALUES 
    (1, 'Centro'),
    (2, 'Botafogo'), 
    (3, 'Savassi');

INSERT INTO TipoLogradouro (idTipoLogradouro, nomeTipoLogradouro) VALUES 
    (1, 'Rua'),
    (2, 'Avenida');

INSERT INTO Logradouro (idLogradouro, idTipoLogradouro, nomeLogradouro) VALUES
    (1, 1, 'Rua das Flores'),
    (2, 2, 'Avenida Brasil'),
    (3, 2, 'Avenida Afonso Pena');

INSERT INTO Endereco (idEndereco, idLogradouro, idCidade, idBairro) VALUES 
    (1, 1, 1, 1),
    (2, 2, 2, 2),
    (3, 3, 3, 3);

INSERT INTO Medico (idMedico, nome, dtNascimento, estCivil, idEndereco, sexo, docIdentidade, crm)
VALUES 
  (1, 'Dr. João Silva', '1975-05-20', 'CASADO', 1, 'M', '123456789', 'CRM123'),
  (2, 'Dra. Maria Oliveira', '1980-08-12', 'SOLTEIRO', 1, 'F', '987654321', 'CRM456'),
  (3, 'Dr. Carlos Souza', '1985-11-30', 'DIVORCIADO', 1, 'M', '234567890', 'CRM789'),
  (4, 'Dra. Paula Fernandes', '1970-12-05', 'CASADO', 2, 'F', '666666666', 'CRM101'),
  (5, 'Dr. Eduardo Lima', '1982-06-18', 'DIVORCIADO', 3, 'M', '777777777', 'CRM202');

INSERT INTO Paciente (nroPaciente, nome, dtNascimento, estCivil, idEndereco, sexo, docIdentidade)
VALUES 
  (1, 'Ana Pereira', '1990-01-15', 'SOLTEIRO', 1, 'F', '111111111'),
  (2, 'Bruno Santos', '1988-04-22', 'CASADO', 1, 'M', '222222222'),
  (3, 'Clara Melo', '1995-07-10', 'SOLTEIRO', 1, 'F', '333333333'),
  (4, 'Diego Rocha', '1993-09-05', 'DIVORCIADO', 1, 'M', '444444444'),
  (5, 'Eva Martins', '1987-03-25', 'VIÚVO', 1, 'F', '555555555'),
  (6, 'Gabriel Almeida', '1992-11-13', 'CASADO', 2, 'M', '888888888'),
  (7, 'Larissa Costa', '1997-05-30', 'SOLTEIRO', 3, 'F', '999999999');

INSERT INTO CodigoDiagnostico (idCodDiagnostico, codigoCID, descricaoDiagnostico)
VALUES 
  (1, 'A01', 'Infecção Gastrointestinal'),
  (2, 'B02', 'Infecção Viral'),
  (3, 'C03', 'Doença Respiratória Crônica'),
  (4, 'D04', 'Diabetes Tipo 2'),
  (5, 'E05', 'Hipertensão Arterial');
  

INSERT INTO ConsultaMedica (nroConsulta, nroPaciente, idMedico, idCodDiagnostico, dataConsulta)
VALUES 
    (1, 1, 1, 1, '2024-10-01'),
    (2, 2, 2, 2, '2024-10-15'),
    (3, 3, 3, 3, '2024-10-20'),
    (4, 4, 4, 4, '2024-11-01'),
    (5, 5, 5, 5, '2024-11-02'),
    (6, 6, 1, 2, '2024-11-03'),
    (7, 7, 2, 4, '2024-11-04');

INSERT INTO TipoExame (tipoExame, nomeExame)
VALUES 
    (1, 'Hemograma'),
    (2, 'Raio-X'),
    (3, 'Ultrassonografia'),
    (4, 'Ressonância Magnética'),
    (5, 'Eletrocardiograma'),
    (6, 'Tomografia Computadorizada'),
    (7, 'Endoscopia');

INSERT INTO ResultadoExame (idResultadoGeral, resultadoExame, obsExame)
VALUES 
    (1, 'Normal', 'Exame dentro dos parâmetros normais.'),
    (2, 'Alterado', 'Valores elevados detectados.'),
    (3, 'Inconclusivo', 'Necessária nova coleta.'),
    (4, 'Normal', 'Sem anormalidades.'),
    (5, 'Alterado', 'Sinais de inflamação.'),
    (6, 'Normal', 'Não foram encontradas anormalidades significativas.'),
    (7, 'Alterado', 'Presença de lesão moderada detectada.');

INSERT INTO ExameMedico (nroExame, idMedico, nroPaciente, dtExame, tipoExame, idResultadoGeral)
VALUES 
    (1, 1, 1, '2024-10-02', 1, 1),
    (2, 1, 2, '2024-10-03', 2, 2),
    (3, 2, 3, '2024-10-16', 3, 3),
    (4, 3, 4, '2024-10-21', 4, 4),
    (5, 3, 5, '2024-10-22', 5, 5),
    (6, 4, 4, '2024-11-02', 6, 6),
    (7, 5, 5, '2024-11-03', 7, 7),
    (8, 1, 6, '2024-11-04', 1, 2),
    (9, 2, 7, '2024-11-05', 3, 3);

INSERT INTO EmailMedico(idMedico, enderecoEmail) 
VALUES
    (1, 'joaosilva12@gmail.com'),
    (2, 'maroliveira@gmail.com'),
    (3, 'souzacarlos33@gmail.com'),
    (4, 'paulafernandes@gmail.com'),
    (5, 'eduardolima@gmail.com');

INSERT INTO EmailPaciente (nroPaciente, enderecoEmail)
VALUES 
    (1, 'aapereira@gmail.com'),
    (2, 'bsantosb@gmail.com'),
    (3, 'meloclaraa@unioeste.br'),
    (4, 'digorocha@unioeste.br'),
    (5, 'evamar@outlook.com'),
    (6, 'gabriel.almeida@gmail.com'),
    (7, 'larissa.costa@gmail.com');
