# 🧠 QuizApp

Um aplicativo de Quiz moderno para Android que combina o poder do **Firebase** para sincronização em tempo real e autenticação, com a robustez do **Room Database** para persistência local e funcionamento offline. O projeto foi desenvolvido seguindo a arquitetura **MVVM** e as melhores práticas de UI declarativa.

---

## 🚀 Tecnologias e Arquitetura

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **Interface:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (UI Declarativa)
* **Arquitetura:** **MVVM** (Model-View-ViewModel) para uma separação clara de responsabilidades.
* **Persistência Híbrida de Dados:**
    * **[Room Persistence Library](https://developer.android.com/training/data-storage/room):** Utilizado para cache local, armazenamento de perfil e histórico de partidas offline.
    * **[Firebase (Auth & Firestore)](https://firebase.google.com/):** Gerenciamento de usuários e sincronização de dados/rankings na nuvem.
* **Navegação:** Compose Navigation com rotas tipadas.

---

## 📁 Estrutura do Projeto

A organização dos pacotes reflete uma estrutura escalável e organizada:

* **`data/`**: Implementação do banco de dados (Room), DAOs, Entidades e Repositórios que gerenciam a fonte de verdade entre o local e o remoto (Firebase).
* **`models/`**: Definições de objetos de domínio e categorias (ex: `QuizCategory`).
* **`ui/`**: Camada visual dividida logicamente:
    * `screens/`: Telas principais do aplicativo.
    * `navigation/`: Lógica de rotas e fluxo de telas.
    * `state/`: Gerenciamento de estados de UI (UiState).
    * `viewmodels/`: Ponte de lógica de negócio entre dados e interface.

---

## ✨ Funcionalidades

-  **Autenticação Segura:** Cadastro e login integrados via Firebase Auth.
-  **Dados Sincronizados:** Funcionamento offline via Room com sincronização automática com o Firebase.
-  **Histórico de Quiz:** Registro detalhado das pontuações e partidas anteriores.
-  **Ranking Global:** Visualização de desempenho comparado a outros usuários.
-  **Perfil de Usuário:** Gerenciamento de informações do perfil salvas localmente e na nuvem.

---

## 📲 Como Testar o App (Download do APK)

Para testar o aplicativo diretamente no seu dispositivo Android sem precisar configurar o ambiente de desenvolvimento:

1.  Acesse a pasta ou o link do **APK** disponível neste repositório.
2.  Faça o download do arquivo `release/app-release.apk`.
3.  Transfira o arquivo para o seu smartphone.
4.  Habilite a instalação de fontes desconhecidas nas configurações do Android e instale o app.
5.  Se o quiz não carregar de primeira, encerre o app e abra de novo.

---

## 🛠️ Para Desenvolvedores (Build Local)

Se você deseja explorar o código ou realizar modificações:

1.  Clone este repositório:
    ```bash
    git clone [https://github.com/duartelucas03/quizapp.git](https://github.com/duartelucas03/quizapp.git)
    ```
2.  Certifique-se de adicionar o seu próprio arquivo `google-services.json` (gerado no Console do Firebase) na pasta `app/`.
3.  Abra o projeto no **Android Studio**.
4.  Sincronize o Gradle e execute o projeto em um emulador ou dispositivo físico.

---

**Desenvolvido por:** 🚀
- Artur Batalini Coelho Alvarim
- Lucas Duarte Soares
- Luiz Alexandre Anchieta Freitas
- Vitor Hugo Rocha Curcino