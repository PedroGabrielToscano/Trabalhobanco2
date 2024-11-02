--Objetivo
--Listar Consultas Médicas Realizadas
--NroConsulta // DataConsulta // Medico(Nome, CRM, email) // Diag (Codigo, desc, CID)
SELECT
    c.nroConsulta AS "Número da Consulta",
    c.dataConsulta AS "Data da Consulta",
    m.nome AS "Nome do Médico",
    m.crm AS "CRM",
    em.enderecoEmail AS "Email do Médico",
    d.codigoCID AS "Codigo CID",
    d.descricaoDiagnostico AS "Descrição do Diagnostico"
FROM 
    ConsultaMedica c
JOIN 
    Medico m ON c.idMedico = m.idMedico
LEFT JOIN 
    EmailMedico em ON m.idMedico = em.idMedico
JOIN 
    CodigoDiagnostico d ON c.idCodDiagnostico = d.idCodDiagnostico;

--Listar Exames Realizados
--NroExame // Data Exame // Tipo (cod, nome, tipo) // Resultado // Observações
SELECT 
    e.nroExame AS "Número do Exame",
    e.dtExame AS "Data do Exame",
    t.tipoExame AS "Código do Tipo de Exame",
    t.nomeExame AS "Nome do Exame",
    r.resultadoExame AS "Resultado do Exame",
    r.obsExame AS "Observações do Exame"
FROM 
    ExameMedico e
JOIN 
    TipoExame t ON e.tipoExame = t.tipoExame
JOIN 
    ResultadoExame r ON e.idResultadoGeral = r.idResultadoGeral;

--EXTRAS
--Listar todas as consultas médicas realizadas, com os nomes dos pacientes, médicos e descrições dos diagnósticos  
SELECT 
    cm.nroConsulta,
    p.nome AS paciente,
    m.nome AS medico,
    cd.codigoCID,
    cd.descricaoDiagnostico,
    cm.dataConsulta
FROM 
    ConsultaMedica cm
JOIN 
    Paciente p ON cm.nroPaciente = p.nroPaciente
JOIN 
    Medico m ON cm.idMedico = m.idMedico
JOIN 
    CodigoDiagnostico cd ON cm.idCodDiagnostico = cd.idCodDiagnostico;

--Listar exames realizados por um paciente específico, com informações sobre o médico que solicitou, tipo do exame e resultado
SELECT 
    em.nroExame,
    p.nome AS paciente,
    m.nome AS medico,
    te.nomeExame,
    re.resultadoExame,
    re.obsExame,
    em.dtExame
FROM 
    ExameMedico em
JOIN 
    Paciente p ON em.nroPaciente = p.nroPaciente
JOIN 
    Medico m ON em.idMedico = m.idMedico
JOIN 
    TipoExame te ON em.tipoExame = te.tipoExame
JOIN 
    ResultadoExame re ON em.idResultadoGeral = re.idResultadoGeral
WHERE 
    p.nome = 'Ana Pereira';

--Contar o número de consultas realizadas por cada médico
SELECT 
    m.nome AS medico,
    COUNT(cm.nroConsulta) AS total_consultas
FROM 
    Medico m
LEFT JOIN 
    ConsultaMedica cm ON m.idMedico = cm.idMedico
GROUP BY 
    m.nome;
