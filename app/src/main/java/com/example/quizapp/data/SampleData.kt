package com.example.quizapp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.Color
import com.example.quizapp.models.QuizCategory
import com.example.quizapp.models.Question

object SampleData {
    val quizCategories = listOf(
        QuizCategory(
            id = 1,
            title = "SQL Básico",
            icon = Icons.Default.DataArray,
            questionCount = 10, // Atualizado para o número de perguntas reais no teste
            difficulty = "Iniciante",
            color = Color(0xFF4CAF50),
            questions = listOf(
                Question("Qual comando é usado para buscar dados de uma tabela?", listOf("INSERT", "UPDATE", "SELECT", "DELETE"), 2),
                Question("Qual cláusula filtra os resultados de uma consulta?", listOf("WHERE", "GROUP BY", "ORDER BY", "HAVING"), 0),
                Question("Qual comando remove todos os registros de uma tabela sem excluir a tabela?", listOf("DROP", "REMOVE", "DELETE", "TRUNCATE"), 3),
                Question("Para ordenar os resultados em ordem decrescente, usa-se:", listOf("ASC", "DESC", "ORDER", "SORT"), 1),
                Question("Qual função conta o número de linhas em um conjunto de dados?", listOf("COUNT()", "SUM()", "TOTAL()", "ADD()"), 0),
                Question("Qual operador é usado para procurar um padrão específico em uma coluna?", listOf("IS", "LIKE", "MATCH", "SEARCH"), 1),
                Question("Qual comando adiciona uma nova linha em uma tabela?", listOf("ADD", "UPDATE", "CREATE", "INSERT INTO"), 3),
                Question("O que o comando DISTINCT faz?", listOf("Soma valores", "Remove duplicatas", "Filtra nulos", "Agrupa dados"), 1),
                Question("Qual cláusula é usada para filtrar grupos após um GROUP BY?", listOf("WHERE", "FILTER", "HAVING", "ORDER"), 2),
                Question("Qual comando remove uma tabela inteira do banco de dados?", listOf("DELETE", "DROP TABLE", "REMOVE", "TRUNCATE"), 1)
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
                Question("Qual biblioteca é a mais usada para manipulação de DataFrames?", listOf("NumPy", "Pandas", "Matplotlib", "Scikit-learn"), 1),
                Question("Como se verifica os tipos de dados de uma coluna no Pandas?", listOf(".type()", ".dtypes", ".info", ".format()"), 1),
                Question("Qual método do Pandas é usado para ler arquivos CSV?", listOf("read_csv()", "load_csv()", "open_csv()", "get_csv()"), 0),
                Question("Qual estrutura de dados do NumPy é a base para cálculos numéricos?", listOf("List", "Dictionary", "ndarray", "Series"), 2),
                Question("Qual função do Pandas remove valores nulos de um DataFrame?", listOf("remove_null()", "dropna()", "clean()", "delete_nan()"), 1),
                Question("Como selecionar as 5 primeiras linhas de um DataFrame?", listOf(".first(5)", ".top(5)", ".head()", ".begin()"), 2),
                Question("Qual biblioteca é focada em visualização de dados estática?", listOf("Flask", "Scipy", "Matplotlib", "Requests"), 2),
                Question("Qual método combina dois DataFrames com base em uma chave comum?", listOf("join()", "merge()", "concat()", "append()"), 1),
                Question("No Pandas, o que o método .describe() faz?", listOf("Resume estatísticas", "Cria um gráfico", "Deleta a tabela", "Renomeia colunas"), 0),
                Question("Como acessamos uma linha específica pelo seu índice nominal?", listOf(".iloc", ".loc", ".get", ".row"), 1)
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
                Question("O que significa a sigla ETL?", listOf("Extract, Transform, Load", "Execute, Train, List", "Error, Test, Log", "Entry, Transfer, Level"), 0),
                Question("Qual ferramenta é amplamente usada para orquestração de pipelines?", listOf("Docker", "Airflow", "Pandas", "Tableau"), 1),
                Question("O que caracteriza um 'Data Lake'?", listOf("Dados estruturados apenas", "Armazenamento de arquivos binários", "Repositório de dados brutos", "Banco de dados relacional"), 2),
                Question("Qual framework é famoso pelo processamento distribuído em larga escala?", listOf("Apache Spark", "Django", "React", "Keras"), 0),
                Question("O que é um arquivo Parquet?", listOf("Um script Python", "Formato de armazenamento colunar", "Um tipo de vírus", "Protocolo de rede"), 1),
                Question("Qual a principal diferença entre banco SQL e NoSQL?", listOf("Preço", "Esquema flexível vs rígido", "Velocidade de internet", "Linguagem de máquina"), 1),
                Question("O que é Idempotência em um pipeline?", listOf("Rodar várias vezes com o mesmo resultado", "Aumentar a velocidade", "Criptografar dados", "Excluir logs antigos"), 0),
                Question("Qual serviço da AWS é voltado para Data Warehousing?", listOf("S3", "EC2", "Redshift", "Lambda"), 2),
                Question("O que é o processo de CDC (Change Data Capture)?", listOf("Deletar dados antigos", "Capturar mudanças em tempo real", "Comprimir arquivos", "Criptografar colunas"), 1),
                Question("Qual o papel do HDFS no ecossistema Hadoop?", listOf("Processar texto", "Gerenciar memória RAM", "Sistema de arquivos distribuído", "Interface gráfica"), 2)
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
                Question("Qual o foco da pesquisa qualitativa?", listOf("Contar ocorrências", "Entender significados e nuances", "Testar hipóteses estatísticas", "Medir volume de vendas"), 1),
                Question("Qual técnica consiste em observar o usuário em seu ambiente natural?", listOf("Etnografia", "A/B Testing", "Survey", "Data Mining"), 0),
                Question("O que é um Grupo Focal (Focus Group)?", listOf("Um software", "Entrevista em grupo mediada", "Análise de código", "Filtro de Excel"), 1),
                Question("Qual destas é uma fonte comum de dados qualitativos?", listOf("Sensores de calor", "Logs de servidor", "Entrevistas abertas", "Planilhas financeiras"), 2),
                Question("O que significa 'Análise de Conteúdo'?", listOf("Contar palavras", "Codificar e interpretar textos", "Medir tempo de leitura", "Publicar posts"), 1),
                Question("Qual o objetivo de uma pergunta 'aberta'?", listOf("Sim ou Não", "Permitir resposta livre", "Limitar o tempo", "Confundir o entrevistado"), 1),
                Question("O que é Bias (Viés) de confirmação?", listOf("Erro de digitação", "Procurar apenas dados que apoiam sua crença", "Velocidade de processamento", "Cálculo de média"), 1),
                Question("Na análise qualitativa, o que é a codificação?", listOf("Escrever em Java", "Categorizar trechos de texto", "Criptografar o arquivo", "Somar os resultados"), 1),
                Question("O que é a saturação de dados?", listOf("Quando o HD fica cheio", "Quando novas entrevistas não trazem fatos novos", "Erro no servidor", "Excesso de números"), 1),
                Question("Qual ferramenta é usada para análise de dados qualitativos (CAQDAS)?", listOf("NVivo", "TensorFlow", "AutoCAD", "Pandas"), 0)
            )
        )
    )
}