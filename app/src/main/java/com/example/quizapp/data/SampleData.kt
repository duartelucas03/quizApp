package com.example.quizapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.Color
import com.example.quizapp.models.QuizCategory
import com.example.quizapp.data.Question
object SampleData {
    val quizCategories = listOf(
        QuizCategory(
            id = 1,
            title = "SQL Básico",
            icon = Icons.Default.DataArray,
            questionCount = 10,
            difficulty = "Iniciante",
            color = Color(0xFF4CAF50),
            questions = listOf(
                Question("sql_1", "Qual comando é usado para buscar dados de uma tabela?", "INSERT", "UPDATE", "SELECT", "DELETE", 2),
                Question("sql_2", "Qual cláusula filtra os resultados de uma consulta?", "WHERE", "GROUP BY", "ORDER BY", "HAVING", 0),
                Question("sql_3", "Qual comando remove todos os registros de uma tabela sem excluir a tabela?", "DROP", "REMOVE", "DELETE", "TRUNCATE", 3),
                Question("sql_4", "Para ordenar os resultados em ordem decrescente, usa-se:", "ASC", "DESC", "ORDER", "SORT", 1),
                Question("sql_5", "Qual função conta o número de linhas em um conjunto de dados?", "COUNT()", "SUM()", "TOTAL()", "ADD()", 0),
                Question("sql_6", "Qual operador é usado para procurar um padrão específico em uma coluna?", "IS", "LIKE", "MATCH", "SEARCH", 1),
                Question("sql_7", "Qual comando adiciona uma nova linha em uma tabela?", "ADD", "UPDATE", "CREATE", "INSERT INTO", 3),
                Question("sql_8", "O que o comando DISTINCT faz?", "Soma valores", "Remove duplicatas", "Filtra nulos", "Agrupa dados", 1),
                Question("sql_9", "Qual cláusula é usada para filtrar grupos após um GROUP BY?", "WHERE", "FILTER", "HAVING", "ORDER", 2),
                Question("sql_10", "Qual comando remove uma tabela inteira do banco de dados?", "DELETE", "DROP TABLE", "REMOVE", "TRUNCATE", 1)
            )
        ),
        QuizCategory(
            id = 2,
            title = "Python para Dados",
            icon = Icons.Default.Code,
            questionCount = 10,
            difficulty = "Intermediário",
            color = Color(0xFF2196F3),
            questions = listOf(
                Question("py_1", "Qual biblioteca é a mais usada para manipulação de DataFrames?", "NumPy", "Pandas", "Matplotlib", "Scikit-learn", 1),
                Question("py_2", "Como se verifica os tipos de dados de uma coluna no Pandas?", ".type()", ".dtypes", ".info", ".format()", 1),
                Question("py_3", "Qual método do Pandas é usado para ler arquivos CSV?", "read_csv()", "load_csv()", "open_csv()", "get_csv()", 0),
                Question("py_4", "Qual estrutura de dados do NumPy é a base para cálculos numéricos?", "List", "Dictionary", "ndarray", "Series", 2),
                Question("py_5", "Qual função do Pandas remove valores nulos de um DataFrame?", "remove_null()", "dropna()", "clean()", "delete_nan()", 1),
                Question("py_6", "Como selecionar as 5 primeiras linhas de um DataFrame?", ".first(5)", ".top(5)", ".head()", ".begin()", 2),
                Question("py_7", "Qual biblioteca é focada em visualização de dados estática?", "Flask", "Scipy", "Matplotlib", "Requests", 2),
                Question("py_8", "Qual método combina dois DataFrames com base em uma chave comum?", "join()", "merge()", "concat()", "append()", 1),
                Question("py_9", "No Pandas, o que o método .describe() faz?", "Resume estatísticas", "Cria um gráfico", "Deleta a tabela", "Renomeia colunas", 0),
                Question("py_10", "Como acessamos uma linha específica pelo seu índice nominal?", ".iloc", ".loc", ".get", ".row", 1)
            )
        ),
        QuizCategory(
            id = 3,
            title = "Engenharia de Dados",
            icon = Icons.Default.Storage,
            questionCount = 10,
            difficulty = "Avançado",
            color = Color(0xFFFF9800),
            questions = listOf(
                Question("eng_1", "O que significa a sigla ETL?", "Extract, Transform, Load", "Execute, Train, List", "Error, Test, Log", "Entry, Transfer, Level", 0),
                Question("eng_2", "Qual ferramenta é amplamente usada para orquestração de pipelines?", "Docker", "Airflow", "Pandas", "Tableau", 1),
                Question("eng_3", "O que caracteriza um 'Data Lake'?", "Dados estruturados apenas", "Armazenamento de arquivos binários", "Repositório de dados brutos", "Banco de dados relacional", 2),
                Question("eng_4", "Qual framework é famoso pelo processamento distribuído em larga escala?", "Apache Spark", "Django", "React", "Keras", 0),
                Question("eng_5", "O que é um arquivo Parquet?", "Um script Python", "Formato de armazenamento colunar", "Um tipo de vírus", "Protocolo de rede", 1),
                Question("eng_6", "Qual a principal diferença entre banco SQL e NoSQL?", "Preço", "Esquema flexível vs rígido", "Velocidade de internet", "Linguagem de máquina", 1),
                Question("eng_7", "O que é Idempotência em um pipeline?", "Rodar várias vezes com o mesmo resultado", "Aumentar a velocidade", "Criptografar dados", "Excluir logs antigos", 0),
                Question("eng_8", "Qual serviço da AWS é voltado para Data Warehousing?", "S3", "EC2", "Redshift", "Lambda", 2),
                Question("eng_9", "O que é o processo de CDC (Change Data Capture)?", "Deletar dados antigos", "Capturar mudanças em tempo real", "Comprimir arquivos", "Criptografar colunas", 1),
                Question("eng_10", "Qual o papel do HDFS no ecossistema Hadoop?", "Processar texto", "Gerenciar memória RAM", "Sistema de arquivos distribuído", "Interface gráfica", 2)
            )
        ),
        QuizCategory(
            id = 4,
            title = "Análise Qualitativa",
            icon = Icons.Default.Analytics,
            questionCount = 10,
            difficulty = "Iniciante",
            color = Color(0xFF9C27B0),
            questions = listOf(
                Question("qual_1", "Qual o foco da pesquisa qualitativa?", "Contar ocorrências", "Entender significados e nuances", "Testar hipóteses estatísticas", "Medir volume de vendas", 1),
                Question("qual_2", "Qual técnica consiste em observar o usuário em seu ambiente natural?", "Etnografia", "A/B Testing", "Survey", "Data Mining", 0),
                Question("qual_3", "O que é um Grupo Focal (Focus Group)?", "Um software", "Entrevista em grupo mediada", "Análise de código", "Filtro de Excel", 1),
                Question("qual_4", "Qual destas é uma fonte comum de dados qualitativos?", "Sensores de calor", "Logs de servidor", "Entrevistas abertas", "Planilhas financeiras", 2),
                Question("qual_5", "O que significa 'Análise de Conteúdo'?", "Contar palavras", "Codificar e interpretar textos", "Medir tempo de leitura", "Publicar posts", 1),
                Question("qual_6", "Qual o objetivo de uma pergunta 'aberta'?", "Sim ou Não", "Permitir resposta livre", "Limitar o tempo", "Confundir o entrevistado", 1),
                Question("qual_7", "O que é Bias (Viés) de confirmação?", "Erro de digitação", "Procurar apenas dados que apoiam sua crença", "Velocidade de processamento", "Cálculo de média", 1),
                Question("qual_8", "Na análise qualitativa, o que é a codificação?", "Escrever em Java", "Categorizar trechos de texto", "Criptografar o arquivo", "Somar os resultados", 1),
                Question("qual_9", "O que é a saturação de dados?", "Quando o HD fica cheio", "Quando novas entrevistas não trazem fatos novos", "Erro no servidor", "Excesso de números", 1),
                Question("qual_10", "Qual ferramenta é usada para análise de dados qualitativos (CAQDAS)?", "NVivo", "TensorFlow", "AutoCAD", "Pandas", 0)
            )
        )
    )
}