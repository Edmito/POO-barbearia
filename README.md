# POO-barbearia

Projeto em Java para a matéria de POO. Um sistema para barbearia.

## Pré-requisitos

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) ou superior
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/release/2023-09/r/eclipse-ide-java-developers)
- [Visual Studio Code](https://code.visualstudio.com/)
- [Maven](https://maven.apache.org/download.cgi) (integrado ao Eclipse)

## Configuração do Ambiente

1. **Clone o repositório:**

   ```sh
   git clone https://github.com/usuario/POO-barbearia.git
   cd POO-barbearia
   cd demo
   ```

2. **Importe o projeto no Eclipse:**

   - Abra o Eclipse
   - Clique em `File` > `Import` > `Maven` > `Existing Maven Projects` > `Next`
   - Clique em `Browse` e selecione a pasta do projeto
   - Clique em `Finish`

3. **Importe o projeto no Visual Studio Code:**

   - Abra o Visual Studio Code
   - Clique em `File` > `Open Folder...` e selecione a pasta do projeto
   - Clique em `Open`
   - Certifique-se de que a extensão `Java Extension Pack` está instalada
   - O Visual Studio Code deve reconhecer o projeto como um projeto Maven

4. **Execute o projeto:**

   - Navegue até a classe principal do projeto `(Main.java)` em `src/main/java/com/barbershop/Main`.java.
   - Clique com o botão direito na classe e selecione `Run As` > `Java Application`
   - No VS Code, clique com o botão direito na classe e selecione `Run` > `Run Java`

## Dependências

As dependências do projeto estão no arquivo `pom.xml` e são gerenciadas pelo Maven. Certifique-se de que o Maven esteja configurado corretamente no Eclipse para baixar e gerenciar essas dependências automaticamente.

## Estrutura do Projeto

- `src/main/java`: Código-fonte principal do aplicativo.
- `src/test/java`: Código-fonte dos testes.
- `src/main/resources`: Recursos do aplicativo (ex.: arquivos de configuração).
- `src/test/resources`: Recursos dos testes.
- `target`: Diretório onde os arquivos compilados e pacotes são gerados.
