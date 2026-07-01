# Personal Tasks Firebase

Aplicativo Android completo de tarefas pessoais feito com Kotlin 2, Jetpack Compose, Material Design 3, MVVM com `StateFlow`, Hilt, Compose Navigation, Firebase Authentication e Cloud Firestore em tempo real com `addSnapshotListener`.

## Requisitos

- Android Studio mais recente com JDK 17.
- Projeto Firebase no plano Spark gratuito.
- Android SDK 35 instalado.

## Stack

- Kotlin `2.0.21`
- Android Gradle Plugin `8.7.3`
- min SDK `26`
- compile/target SDK `35`
- Jetpack Compose com Material 3 e `dynamicColor = true`
- Firebase Authentication: e-mail/senha e recuperação de senha
- Firestore Database: CRUD em `users/{userId}/tasks/{taskId}`
- Hilt para injeção de dependência
- Compose Navigation para login, cadastro e recuperação de senha

## Configurar Firebase

1. Acesse o [Firebase Console](https://console.firebase.google.com/).
2. Crie um projeto no plano gratuito Spark.
3. Adicione um app Android com o package name:

   ```text
   com.example.personaltasks
   ```

4. Baixe o arquivo `google-services.json`.
5. Substitua o arquivo de exemplo em:

   ```text
   app/google-services.json
   ```

6. Em Authentication, habilite o provedor "E-mail/senha".
7. Em Firestore Database, crie o banco em modo de produção.
8. Publique as regras do arquivo `firestore.rules` pelo console ou via Firebase CLI:

   ```bash
   firebase deploy --only firestore:rules
   ```

## Regras do Firestore

As tarefas ficam organizadas por usuário:

```text
users/{userId}/tasks/{taskId}
```

As regras garantem que apenas o usuário autenticado cujo `request.auth.uid` é igual ao `{userId}` consiga ler e escrever os próprios dados.

## Rodar no Android Studio

1. Abra a pasta `PersonalTasksFirebase` no Android Studio.
2. Aguarde o Gradle Sync terminar.
3. Confirme que o Android SDK 35 está instalado.
4. Substitua o `app/google-services.json` de exemplo pelo arquivo real do Firebase.
5. Execute o app em um emulador ou dispositivo com Android 8.0 ou superior.

## Funcionalidades

- Cadastro com e-mail e senha.
- Login com e-mail e senha.
- Recuperação de senha por e-mail.
- Logout.
- Lista de tarefas em tempo real via `addSnapshotListener`.
- Criar, editar, concluir e excluir tarefas no Firestore.
- Tema claro, escuro e cores dinâmicas em Android 12+.

## Estrutura principal

```text
app/src/main/java/com/example/personaltasks
├── data
│   ├── auth
│   └── task
├── di
├── ui
│   ├── auth
│   ├── navigation
│   ├── tasks
│   └── theme
├── MainActivity.kt
└── TaskApplication.kt
```

## Observação

O `google-services.json` incluído é apenas um placeholder para manter a estrutura completa do projeto. Para autenticação e Firestore funcionarem em runtime, use o arquivo real gerado no Firebase Console.
