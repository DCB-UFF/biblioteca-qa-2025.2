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