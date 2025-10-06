# Sistema de Gerenciamento de Biblioteca (SGB)

## Estrutura de Arquivos

- Código-fonte: `src/biblioteca/`
- Dados das unidades: `src/unidades/unidades.csv`

### Principais Arquivos

- `src/biblioteca/biblioteca/Unidade.java`: Classe responsável pela gestão das unidades da biblioteca.
- `src/biblioteca/livros/Livro.java`: Classe que representa os livros do acervo.
- `src/biblioteca/menu/Menu.java`: Interface de interação com o usuário.
- `src/unidades/unidades.csv`: Arquivo de dados das unidades cadastradas.

---

## Sobre a Aplicação

O software é um Sistema de Gerenciamento de Biblioteca (SGB) console-based, desenvolvido em Java.  
Principais funcionalidades:

- Gerenciar operações de diferentes unidades (filiais) de uma biblioteca.
- Gestão de acervo, controle de empréstimos e devoluções de livros, administração das unidades.
- Interação via menu de texto no terminal.
- Persistência dos dados das unidades em arquivo CSV.

---

## Casos de Teste

### Teste QT-001

**Título:** Visualizar o acervo completo de uma unidade.

**Pré-condições:**
1. O sistema está em execução.
2. A unidade `Niteroi` existe e possui um acervo com livros de diversas estantes.
3. Alguns livros da unidade `Niteroi` estão marcados como emprestados (ex: Cód: 2970387).

**Passos:**
1. Na tela inicial, selecionar a opção para escolher uma unidade (ex: digitar `1`).
2. Digitar o nome da unidade: `Niteroi` e pressionar Enter.
3. No menu da unidade, selecionar a opção `Acervo` (ex: digitar `1`).
4. No menu do acervo, selecionar a opção `Imprimir acervo` (ex: digitar `3`) e pressionar Enter.

**Resultado Esperado:**
- O sistema deve exibir o cabeçalho `Acervo da Unidade - Niteroi`.
- A lista de livros deve ser exibida, agrupada por **Estante**.
- Cada grupo de estante deve mostrar o nome da estante (ex: `Estante 1 - Aventura`) e a contagem de livros nela.
- Cada livro deve exibir seu Código, Título, Autor e Editora.
- Livros emprestados devem ter a marcação `[Emprestado]` ao lado do código.
- A formatação geral (espaçamento, quebra de linha) deve ser clara e legível.

---

### Teste CT-002

**Título:** Tentar selecionar unidade com entrada numérica inválida.  
**Tipo:** Teste Funcional de Exceção

**Pré-condições:**
1. O sistema está na tela inicial, exibindo a lista de unidades disponíveis (`Niteroi`, `Sao Goncalo`, `Rio de Janeiro`).

**Passos:**
1. Na tela inicial, selecionar a opção para `Escolher unidade` (digitar `1`).
2. Quando o sistema solicitar `Digite o nome da unidade:`, digitar `1` e pressionar Enter.

**Resultado Esperado:**
- O sistema deve exibir a mensagem de erro: `Unidade não existe`.
- O sistema deve permanecer na tela de seleção de unidade ou voltar para o menu principal, sem quebrar ou entrar em um estado de erro inesperado.

---

### Teste CT-003

**Título:** Empréstimo de Livro por um Cliente

**Objetivo:** Verificar se um cliente cadastrado consegue pegar um livro emprestado com sucesso em uma unidade da biblioteca.

**Pré-condições:**
*   O sistema da biblioteca deve estar em execução.
*   Deve existir pelo menos uma unidade cadastrada (ex: "Rio de Janeiro").
*   A unidade "Rio de Janeiro" deve ter pelo menos um cliente cadastrado. Para este teste, usaremos o cliente "Maria" com CPF `150999497-88`.
*   A unidade "Rio de Janeiro" deve ter pelo menos um livro disponível no acervo. Para este teste, usaremos o livro "Me chame pelo seu nome" com ISBN `3867953`.

**Passos para execução:**

1.  Inicie a aplicação. O menu principal será exibido.
2.  Escolha a opção `1` para "Escolher unidade" e pressione Enter.
3.  Digite o nome da unidade, que neste caso é `Rio de Janeiro`, and pressione Enter.
4.  No menu da unidade, escolha a opção `2` para "Emprestimo" e pressione Enter.
5.  No menu de empréstimos, escolha a opção `1` para "Emprestar um livro" e pressione Enter.
6.  Quando solicitado, digite o CPF do cliente: `150999497-88` e pressione Enter.
7.  Em seguida, digite o ISBN do livro: `3867953` e pressione Enter.
8.  Digite a data do empréstimo (ex: `06/10/2025`) e pressione Enter.
9.  Digite a data de devolução (ex: `20/10/2025`) e pressione Enter.

**Resultados Esperados:**

*   O sistema deve registrar o empréstimo e exibir uma mensagem de confirmação.
*   Ao reiniciar a aplicação e consultar os dados da unidade "Rio de Janeiro", o livro "Me chame pelo seu nome" deve constar como emprestado.
*   O empréstimo deve estar registrado no histórico de empréstimos do cliente "Maria".
