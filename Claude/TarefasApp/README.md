# Minhas Tarefas — App Android de Gestão de Tarefas Pessoais

Aplicativo Android completo de gestão de tarefas pessoais construído **exclusivamente sobre a camada gratuita do Firebase**:

- **Firebase Authentication** — login/cadastro com e-mail e senha + recuperação de senha.
- **Cloud Firestore** — CRUD de tarefas com **leitura em tempo real** via `addSnapshotListener`.

## ✨ Recursos

- Cadastro, login e recuperação de senha por e-mail.
- Criação, edição, conclusão e exclusão de tarefas.
- Sincronização em tempo real (mudanças aparecem instantaneamente).
- Cada usuário só acessa as **próprias** tarefas (isolamento garantido pelas regras do Firestore).
- Prioridades (Baixa / Média / Alta).
- Material Design 3 com **Dark/Light mode** automático e **dynamicColor** (Material You) no Android 12+.

## 🧱 Stack técnica

| Camada | Tecnologia |
|---|---|
| Linguagem | Kotlin 2.0 |
| UI | Jetpack Compose + Material 3 |
| Arquitetura | MVVM com `StateFlow` |
| Injeção de dependência | Hilt |
| Navegação | Navigation Compose |
| Backend | Firebase Authentication + Cloud Firestore |
| Build | Gradle Kotlin DSL + Version Catalog |
| SDK | minSdk 26 · target/compile SDK 35 |

## 📁 Estrutura do projeto

```
TarefasApp/
├── build.gradle.kts                  # Build do projeto (plugins)
├── settings.gradle.kts
├── gradle/libs.versions.toml         # Version catalog (todas as dependências)
├── firestore.rules                   # Regras de segurança do Firestore
├── app/
│   ├── build.gradle.kts              # Build do módulo app + dependências
│   ├── google-services.json.example  # Modelo do arquivo de config do Firebase
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── res/                      # Tema, ícones, strings
│       └── java/com/example/tarefas/
│           ├── TarefasApplication.kt # @HiltAndroidApp
│           ├── MainActivity.kt       # @AndroidEntryPoint + setContent
│           ├── di/
│           │   └── FirebaseModule.kt # Provê FirebaseAuth e Firestore
│           ├── data/
│           │   ├── model/Task.kt
│           │   └── repository/
│           │       ├── AuthRepository.kt
│           │       └── TaskRepository.kt   # addSnapshotListener → Flow
│           └── ui/
│               ├── theme/            # Color, Type, Theme (dark/light + dynamic)
│               ├── navigation/       # Screen (rotas) + AppNavigation (NavHost)
│               ├── auth/             # AuthViewModel + Login/Register/ForgotPassword
│               └── tasks/            # TaskViewModel + List/Item/Dialog
```

## 🔥 Configuração do Firebase (passo a passo)

1. Acesse o [Firebase Console](https://console.firebase.google.com/) e crie um projeto (plano **Spark / gratuito**).
2. Em **Build → Authentication → Sign-in method**, ative **E-mail/senha**.
3. Em **Build → Firestore Database**, crie o banco em **modo de produção**.
4. Na aba **Rules** do Firestore, cole o conteúdo de [`firestore.rules`](firestore.rules) e publique.
5. Em **Project settings → General → Your apps**, adicione um app **Android**:
   - **Package name:** `com.example.tarefas` (precisa ser idêntico ao `applicationId`).
6. Baixe o arquivo **`google-services.json`** gerado e coloque-o em:
   ```
   app/google-services.json
   ```
   > Use o [`app/google-services.json.example`](app/google-services.json.example) apenas como referência da estrutura — ele **não** funciona; o arquivo real precisa ser baixado do Console.

## ▶️ Como compilar e rodar

### Pré-requisitos
- **Android Studio** (versão mais recente — Ladybug ou superior).
- **JDK 17** (já incluso no Android Studio).
- Um dispositivo/emulador com **Android 8.0 (API 26)** ou superior **e acesso à internet**.

### Passos
1. Abra o Android Studio → **Open** → selecione a pasta `TarefasApp`.
2. Aguarde o **Gradle Sync** (o Android Studio gera o `gradle-wrapper.jar` automaticamente caso não exista).
3. Garanta que o arquivo `app/google-services.json` (real) está no lugar.
4. Selecione um emulador ou dispositivo físico.
5. Clique em **Run ▶** (ou `Shift + F10`).

Pela linha de comando (com o wrapper já gerado):

```bash
# Windows
gradlew.bat assembleDebug

# Linux/macOS
./gradlew assembleDebug
```

## 🔒 Modelo de dados e segurança

As tarefas ficam em uma subcoleção por usuário:

```
users/{userId}/tasks/{taskId}
```

As regras em [`firestore.rules`](firestore.rules) garantem que somente o usuário autenticado dono do `userId` consegue ler ou gravar seus documentos:

```
match /users/{userId}/tasks/{taskId} {
  allow read, write: if request.auth != null && request.auth.uid == userId;
}
```

Documento `Task`:

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | String | ID do documento (`@DocumentId`) |
| `title` | String | Título da tarefa |
| `description` | String | Descrição (opcional) |
| `completed` | Boolean | Concluída ou não |
| `priority` | String | `LOW` / `MEDIUM` / `HIGH` |
| `createdAt` | Timestamp | Data de criação (ordenação) |

## 🧩 Notas

- A leitura em tempo real usa `addSnapshotListener` encapsulado em um `callbackFlow`, exposto pelo `TaskRepository` como `Flow<List<Task>>` e coletado no `TaskViewModel` para um `StateFlow`.
- O estado de autenticação também é observado em tempo real (`AuthStateListener`), redirecionando a navegação automaticamente em login/logout.
- `dynamicColor` é aplicado apenas no Android 12+ (API 31); abaixo disso usa-se a paleta estática definida em `ui/theme/Color.kt`.
