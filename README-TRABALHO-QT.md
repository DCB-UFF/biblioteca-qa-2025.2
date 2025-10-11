# Sistema de Gerenciamento de Biblioteca (SGB)

## Estrutura de Arquivos

- Código-fonte: `src/biblioteca/`
- Dados das unidades: `src/unidades/unidades.csv`
- Link: `https://github.com/DCB-UFF/biblioteca-qa-2025.2`

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

## Requisitos para rodar

Para conseguir rodar a aplicação e os testes desenvolvidos são necessários os seguintes requisitos:

- JUnit 5
- IntelliJ IDEA
- JDK 21
- Processador dual-core
- 4gb RAM

