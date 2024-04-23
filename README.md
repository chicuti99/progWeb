# Roteiro laboratório 1


1. Defina o `groupId` e o `artifactId` para seu projeto. Para o primeiro, comumente se usa o endereço da sua organização na Web invertido (ex.: os projetos do [LabES/UFES](https://labes.inf.ufes.br/) utilizam o `groupId` `br.ufes.inf.labes`). Para o segundo, sugere-se usar o nome do projeto com todas as letras minúsculas e substituindo espaços por `-`;

2. Siga as instruções da seção [Create a new project with JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial#create-a-new-project-with-jbutler) do [tutorial de CRUD do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial), ajustando alguns parâmetros específicos do seu projeto, como, por exemplo:

      - O `groupId` e o `artifactId` do projeto Maven (`pom.xml`);
      - A versão do Java a ser utilizada (`pom.xml`);
      - Caso o leiaute Admin Template do AdminFaces não seja utilizado (vide próximos passos), exclusão da dependência (`pom.xml`);
      - O nome da aplicação (`web.xml`);
      - Os dados para conexão ao banco de dados (`web.xml`);
      - Os nomes dos pacotes onde serão colocados os _resource bundles_ (`faces-config.xml`);
      - As mensagens globais para a sua aplicação Web (`messages.properties`);

3. Decida se prefere usar um leiaute personalizado ou se ficará com o Admin Template do AdminFaces, leiaute usado no [JButler Base Project](https://gitlab.labes.inf.ufes.br/labes/jbutler-base-project) e no exemplo do [Oldenburg](https://gitlab.labes.inf.ufes.br/labes/oldenburg):

      - Se for usar um leiaute personalizado, o processo é análogo ao seguido no [tutorial do JavaHostel @ Jakarta EE 9](https://github.com/dwws-ufes/javahostel/tree/main/jakartaee9). Em particular, observe as instruções da seção [Implement the JavaHostel proper](https://github.com/dwws-ufes/javahostel/tree/main/jakartaee9#implement-the-javahostel-proper) até (inclusive) o passo 5, adaptando ao leiaute escolhido ou criado pelo time. Caso queira usar algum leiaute pronto, uma [busca por _free responsive website templates_ no Google](https://www.google.com/search?q=free+responsive+website+templates) retorna inúmeros resultados;

      - Se for usar o Admin Template, siga as instruções da seção [Set up AdminFaces, a ready-to-use front-end](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial#set-up-adminfaces-a-ready-to-use-front-end) do [tutorial de CRUD do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial), novamente ajustando alguns parâmetros específicos do seu projeto.


## Criação de um usuário administrador

4. Obtenha o projeto do Oldenburg no Visual Paradigm, disponibilizado juntamente com este roteiro, abra-o e procure o Modelo de Entidades de FrameWeb em _Model Explorer_ > _Design Model_;

5. Implemente o tipo enumerado `Role` e a classe `User`, ajustando se necessário à realidade do seu projeto. Para a implementação da entidade `User`, vide seção [Implement the domain and persistence classes](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial#implement-the-domain-and-persistence-classes) do [tutorial de CRUD do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial);

6. Implemente o DAO (interface e classe) para a classe `User` com base no Modelo de Persistência, também disponível no projeto do Oldenburg, ajustando se necessário à realidade do seu projeto. A seção mencionada no passo anterior também possui instruções para implementação do DAO básico. O método de recuperar um usuário dado seu e-mail pode ser implementado da seguinte forma:

      ```java
      @Override
      public User retrieveByEmail(String email)
            throws PersistentObjectNotFoundException, MultiplePersistentObjectsFoundException {
            // Constructs the query over the User class.
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            // Filters the query with the email.
            cq.where(cb.equal(root.get(User_.email), email));
            User result = executeSingleResultQuery(cq, email);
            return result;
      }
      ```

      > A classe `User_` é gerada automaticamente pela biblioteca `hibernate-jpamodelgen` declarada como uma das dependências do projeto no `pom.xml`. Para que ela seja gerada, no entanto, execute `mvn package` após a implementação da classe `User` como uma `@Entity` e verifique se a classe foi gerada em `target/generated-sources`.

7. Implemente um serviço (interface e classe) de configuração do sistema com base no Modelo de Aplicação, também disponível no projeto do Oldenburg, ajustando se necessário à realidade do seu projeto. A seção [Implement the CRUD (application, controller and view)](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial#implement-the-crud-application-controller-and-view) do [tutorial de CRUD do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial) mostra um exemplo de um serviço de CRUD, a diferença aqui é não estender a interface `CrudService` ou a classe `CrudServiceImpl`, pois não se trata de um serviço de CRUD. Este serviço deve implementar um método de instalação do sistema, que irá criar um usuário administrador, por exemplo:

      ```java
      @Stateless
      public class ConfigureSystemServiceBean implements ConfigureSystemService {
            /** Logger for this class. */
            private static final Logger logger =
                  Logger.getLogger(ConfigureSystemServiceBean.class.getCanonicalName());

            /** The Jakarta Security password hash generator. */
            @Inject
            private Pbkdf2PasswordHash passwordHash;

            /** The User DAO is used to create the admin user during installation. */
            @EJB
            private UserDAO userDAO;

            @Override
            public void installSystem() throws SystemAlreadyInstalledException {
                  // Checks if the system has already been installed. Cannot be installed twice.
                  if (userDAO.retrieveCount() > 0) {
                        logger.log(Level.WARNING, "The system has already been installed. New installation aborted.");
                        throw new SystemAlreadyInstalledException();
                  }

                  // Creates the administrator account with basic login information.
                  logger.log(Level.FINE,
                        "Creating basic administrator account with login 'admin' and password 'admin'.");
                  String password = passwordHash.generate("admin".toCharArray());
                  User admin = new User("Oldenburg Admin", "admin", password, Role.ADMIN);
                  userDAO.save(admin);
            }
      }
      ```

      > A classe instancia um objeto `Logger` para enviar mensagens de log que vão parar no console do WildFly, pode ser útil para monitorar a aplicação. Ela também pede que seja injetada uma instância de `Pbkdf2PasswordHash`, que é um objeto provido pelo Jakarta EE para fazer criptografia de senhas. É usado para salvar uma senha no banco e depois será usado no login também para comparar a senha informada pelo usuário com a que foi salva no banco.

8. A classe `Pbkdf2PasswordHash` faz parte do mecanismo de segurança do _Jakarta Security_. Para que ele possa ser injetado, é preciso criar uma classe com algumas configurações, como a que segue abaixo:

      ```java
      package br.ufes.inf.labes.oldenburg.core.application;

      import jakarta.enterprise.context.ApplicationScoped;
      import jakarta.faces.annotation.FacesConfig;
      import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
      import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
      import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

      @CustomFormAuthenticationMechanismDefinition(
      loginToContinue = @LoginToContinue(loginPage = "/login.xhtml", useForwardToLogin = false,
            errorPage = ""))
      @DatabaseIdentityStoreDefinition(dataSourceLookup = "java:app/datasources/oldenburg",
      callerQuery = "select password from User where email = ?",
      groupsQuery = "select role from User where email = ?")
      @FacesConfig
      @ApplicationScoped
      public class AppConfig {
      }
      ```

      > A classe não precisa ter conteúdo, só as anotações mesmo. A primeira anotação indica que a autenticação será feita a partir de um formulário criado por nós em `/login.xhtml`. A segunda anotação indica a consulta em SQL que o Jakarta Security deve fazer para obter a senha dado um nome de usuário (usaremos o e-mail como nome de usuário) e para obter os papéis dado um nome de usuário. Em nosso projeto de exemplo cada usuário só pode ter um papel. No projeto [Marvin](https://gitlab.labes.inf.ufes.br/marvin/marvin/) há um [exemplo de múltiplos papeis por usuário](https://gitlab.labes.inf.ufes.br/marvin/marvin/-/blob/main/src/main/java/br/ufes/inf/labes/marvin/core/application/AppConfig.java).

9. Implemente um _Servlet_ (vide novamente Modelo de Aplicação) que chame o método de serviço implementado no passo 7 quando a URL `/install` for acessada e depois redirecione o usuário para uma página de login. Nas primeiras demonstrações sobre desenvolvimento Web em Java (material disponível no Classroom) foi desenvolvido o `HelloServlet`, que pode servir de exemplo. Uma instância que implementa `ConfigureSystemService` pode ser injetada no servlet com `@EJB`, da mesma forma que o `UserDAO` foi injetado no código do passo 7. Por fim, para redirecionar o usuário para uma outra página a partir de um Servlet, utilize:

      ```java
      response.sendRedirect(request.getContextPath() + "/login.xhtml");
      ```

10. Teste sua funcionalidade de instalação. Ao executá-la, ela tentará redirecionar para uma página que não existe ainda (`/login.xhtml`) e aparentemente nada acontece, porém verifique no banco de dados se o usuário administrador foi cadastrado. Nos próximos passos criaremos a tela de login.


## Autenticação (login/logout)

11. Crie a página `login.xhtml` na raiz da aplicação Web (`src/main/webapp`). Caso esteja usando o template do AdminFaces, a [página de login do Marvin](https://gitlab.labes.inf.ufes.br/marvin/marvin/-/blob/main/src/main/webapp/login.xhtml) pode ser usada como base. Caso contrário, deve montar sua tela de login de acordo com o leiaute que esteja usando.

      > Se usar a tela de login do Marvin, verifique as adaptações que devem ser feitas, como por exemplo os títulos e a imagem de fundo do _template_ de login. Atente-se aos textos que devem ser incluídos em _resource bundles_ e também a eventuais links no final da página de login para funcionalidades que não existem (pode exclui-los ou fazê-los apontar para a própria página de login por enquanto). Note, por fim, que o formulário de login faz referência a um controlador `sessionController`, que deve ser implementado a seguir.

12. Com base no controlador `SessionController`, no serviço `LoginService` e demais classes associadas do [pacote `core` do Marvin](https://gitlab.labes.inf.ufes.br/marvin/marvin/-/tree/main/src/main/java/br/ufes/inf/labes/marvin/core), implemente o restante da funcionalidade de login. Alguns ajustes que podem ser necessários:

      - No controlador, não é necessário verificar se o sistema já foi instalado;
      - No controlador, se quiser, pode apagar os controles de menu, mas deixá-los também não atrapalha;
      - No serviço, não é necessário salvar a data e hora do último login;
      - No serviço, não é necessário disparar um evento CDI indicando que houve um login.

13. Antes de testar, leia a seção [Configuração do WildFly](https://gitlab.labes.inf.ufes.br/labes/catalogo/-/wikis/plataformas/java,-jakarta-ee-(wildfly),-postgresql-e-maven#configura%C3%A7%C3%A3o-do-wildfly) da página do Catálogo do LabES sobre desenvolvimento na plataforma Jakarta EE e siga os passos para contornar um bug do WildFly em relação ao Jakarta Security. Se os passos indicados ali não forem feitos, a autenticação não irá funcionar;

14. Teste se o login funciona, adicionando `<p><h:outputText value="#{sessionController.currentUser}" /></p>` ao final da página `index.xhtml`. Antes do login, deve imprimir `null`. Após o login, deve imprimir uma referência a um objeto `User`;

15. Crie um link para fazer _logout_ em seu leiaute. Caso esteja usando o AdminFaces, pode copiar a seção `<ui:define name="top-menu">` do [template do Marvin](https://gitlab.labes.inf.ufes.br/marvin/marvin/-/blob/main/src/main/webapp/WEB-INF/templates/template.xhtml). Neste caso, remova referências a `coreInfo`, `creationDateLegacy` e `/core/editProfile/index` e lembre-se dos textos nos _resource bundles_;

16. Implemente um _Servlet_ de _logout_ [como no Marvin](https://gitlab.labes.inf.ufes.br/marvin/marvin/-/blob/main/src/main/java/br/ufes/inf/labes/marvin/core/controller/LogoutServlet.java);

17. Teste se o _login_ e o _logout_ estão funcionando corretamente.



# Roteiro laboratório 2


1. Pense nas funcionalidades CRUD que você pretende incluir no seu sistema. Por exemplo, no [Oldenburg](https://gitlab.labes.inf.ufes.br/labes/oldenburg) já foi feito um cadastro de Workshops (como resultado do [tutorial de CRUD do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial)) e agora, após o **Lab Web 01 - Base da Aplicação**, precisaremos também de um cadastro de usuários;

      > A classe `Usuario` e o seu DAO já foram criados no roteiro de laboratório anterior. Pode-se pensar, no entanto, em cadastros de objetos de classes que ainda não existam. No Oldenburg, por exemplo, poderíamos querer um cadastro de áreas de pesquisa para que cada estudante possa indicar os assuntos que prefere revisar e também indicar a área de pesquisa dos artigos submetidos. A criação de novas classes de domínio e seus DAOs segue as instruções do roteiro anterior.
      > 
      > Também é preciso refletir neste momento quais funcionalidades são CRUD e quais não são. Novamente usando Oldenburg como exemplo, os cadastros de usuário e de assuntos seriam algo que um administrador precisaria fazer e, portanto, se encaixam bem como CRUDs. Já o envio de artigos por parte dos estudantes não se encaixa bem como CRUD e deve ser pensada e desenvolvida uma interface com usuário mais adequada (mostrar ao estudante apenas Workshops ativos no momento, permitir escolher um Workshop e enviar as informações para este), bem como uma classe de serviço apenas para os cenários específicos deste caso de uso (ex.: submeter um artigo, ver a submissão, retirar uma submissão). Neste caso, o JButler pode continuar ajudando com a classe de domínio e o DAO apenas.

2. Implemente os CRUDs planejados conforme as instruções do [tutorial de CRUD do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Tutorial), seção **Implement the CRUD (application, controller and view)**;

      > Dicas: tenha o `toString()` implementado na classe de domínio objeto do CRUD para as mensagens ficarem mais apropriadas. Para o campo papel (cujo tipo é enumerado), sugere-se ter no tipo enumerado um campo que armazene uma chave, exemplo:
      >
      > ```java
      > public enum Role {
      >   ADMIN("admin"), PROFESSOR("professor"), STUDENT("student");
      > 
      >   private final String key;
      > 
      >   Role(String key) {
      >     this.key = key;
      >   }
      > 
      >   public String getKey() {
      >     return key;
      >   }
      > }
      > ```
      > 
      > Você pode então configurar textos no _resource bundle_ colocando as chaves usadas no tipo enumerado como parte da chave da mensagem:
      > 
      > ```
      > manageUsers.field.role.admin = Administrator
      > manageUsers.field.role.professor = Professor
      > manageUsers.field.role.student = Student
      > ```
      > 
      > E então na hora de mostrar este dado na página XHTML, concatenar o prefixo `manageUsers.field.role.` com a chave obtida no objeto do tipo enumerado:
      > 
      > ```xhtml
      > <p:column headerText="#{msgsCore['manageUsers.field.role']}" sortBy="#{entity.role}" filterBy="#{entity.role}" filterStyle="display: none">
      >   <h:outputText value="#{msgsCore['manageUsers.field.role.'.concat(entity.role.key)]}" />
      > </p:column>
      >
      > <p:selectOneRadio id="roleField" value="#{manageUsersController.selectedEntity.role}" unselectable="true" required="true">
      >   <f:selectItems value="#{manageUsersController.roles}" var="obj" itemLabel="#{msgsCore['manageUsers.field.role.'.concat(obj.key)]}" itemValue="#{obj}" />
      > </p:selectOneRadio>
      > ```
      > 
      > O primeiro trecho acima (`<p:column />`) mostra o papel do usuário na tabela de listagem de usuários. O segundo (`<p:selectOneRadio />`) é um campo do tipo botão de rádio a ser usado no formulário de cadastro. Para que o botão de rádio liste as opções (os diferentes papeis disponíveis), adicione ao controlador:
      > 
      > ```java
      >   private final List<Role> roles = List.of(Role.values());
      >   public List<Role> getRoles() {
      >     return roles;
      >   }
      > ```
      > 


## Inclusão de regras de validação

3. Os CRUDs básicos já incluem validações simples como, por exemplo, se um campo é obrigatório, se pode ter até N caracteres, se é um número ou data válida, etc. Pense nas validações mais complexas que você precisará implementar. Por exemplo, no Oldenburg:

      - Não podem haver 2 workshops com a mesma sigla;
      - O prazo de revisão de um workshop deve ser posterior ao prazo de submissão;
      - Não se pode excluir um workshop de um ano anterior;
      - Não podem haver 2 usuários com o mesmo email;
      - Não se pode excluir um usuário administrador.

4. Seguindo o [tutorial de validação de CRUDs do JButler](https://gitlab.labes.inf.ufes.br/labes/jbutler/-/wikis/tutorials/JButler-CRUD-Validation), implemente as validações desejadas.

      > Algumas validações (ex.: não ter 2 objetos com o mesmo valor para um determinado atributo) podem envolver criação de consultas ao banco de dados, como no próprio exemplo do tutorial de validação com JButler. Aprenderemos mais sobre como fazer estas consultas nas próximas aulas, portanto talvez seja um desafio muito grande implementar algumas das validações planejadas no passo anterior. Tente implementar alguma parecida com a que é exemplificada no tutorial.

