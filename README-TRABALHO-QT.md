# Sistema de Gerenciamento de Biblioteca (SGB)

## Sobre a Aplicação

O software é um **Sistema de Gerenciamento de Biblioteca (SGB)** console-based, desenvolvido em Java. Trata-se de uma aplicação robusta para gerenciar operações de diferentes unidades (filiais) de uma biblioteca comunitária.

### Principais Funcionalidades

- **Gerenciamento de Unidades**: Criar e administrar múltiplas filiais da biblioteca
- **Gestão de Acervo**: Cadastro, consulta e organização de livros em estantes
- **Controle de Empréstimos e Devoluções**: Registro e acompanhamento de empréstimos de livros
- **Administração de Clientes e Funcionários**: Cadastro e gerenciamento de usuários do sistema
- **Interface de Usuário**: Interação via menu de texto no terminal
- **Persistência de Dados**: Armazenamento de dados em arquivos CSV

---

## Estrutura de Arquivos

- **Código-fonte**: `src/biblioteca/`
- **Testes**: `src/test/java/biblioteca/`
- **Dados das unidades**: `src/unidades/unidades.csv`
- **Repositório**: `https://github.com/DCB-UFF/biblioteca-qa-2025.2`
- **TestLink**: `Plano de Teste - GRUPO DCB / Plano de teste - Biblioteca`

### Principais Classes

| Classe | Localização | Descrição |
|--------|-------------|-----------|
| `Unidade.java` | `src/biblioteca/biblioteca/` | Responsável pela gestão das unidades da biblioteca e operações gerais |
| `Livro.java` | `src/biblioteca/livros/` | Representa os livros do acervo com propriedades e métodos de gerenciamento |
| `Emprestimo.java` | `src/biblioteca/livros/` | Gerencia operações de empréstimo e devolução de livros |
| `Cliente.java` | `src/biblioteca/pessoas/` | Representa clientes cadastrados no sistema |
| `Funcionario.java` | `src/biblioteca/pessoas/` | Representa funcionários da biblioteca |
| `Acervo.java` | `src/biblioteca/livros/` | Gerencia a coleção de livros de uma unidade |
| `Estante.java` | `src/biblioteca/livros/` | Organiza livros em estantes dentro do acervo |
| `Menu.java` | `src/biblioteca/menu/` | Interface de interação com o usuário |

---

## Testes Implementados

### Testes Unitários

Foram implementados testes unitários abrangentes para as principais classes do sistema, cobrindo funcionalidades críticas de:
- Validação de dados de entrada
- Tratamento de exceções
- Cálculos e lógica de negócio

---

### Testes Manuais

Além dos testes unitários, foram executados os seguintes testes manuais para validação de cenários reais:

#### 1. Busca por Livro Emprestado (QTD-3)
**Objetivo**: Validar entrada de título que corresponde a um livro atualmente emprestado versus livros disponíveis ou inexistentes.

- **Classe Válida (Livro Emprestado)**: "Dom Casmurro"
- **Classe Inválida (Disponível ou Inexistente)**: "O Cortiço"

**Resultado**: Sistema consegue diferenciar entre livros emprestados, disponíveis e inexistentes, apresentando mensagens apropriadas para cada situação.

#### 2. Busca por Autor com Múltiplos Resultados (QTD-4)
**Objetivo**: Validar entrada de autor que possui 2 ou mais obras no acervo versus autores com apenas 1 ou nenhuma obra.

- **Classe Válida (Múltiplos Livros)**: "Machado de Assis"
- **Classe Inválida (1 ou 0 Livros)**: "George Orwell"

**Resultado**: Busca por autor retorna corretamente múltiplos resultados quando o autor possui vários livros, e lista vazia ou mensagem apropriada para autores sem obras.

#### 3. Criação de Unidade - Validação de ID (QTD-7)
**Objetivo**: Validar a inserção de um código de unidade inédito no sistema versus um código já existente (duplicado).

- **Classe Válida (ID Novo)**: "UN-05"
- **Classe Inválida (ID Existente)**: "UN-01"

**Resultado**: Sistema valida corretamente IDs únicos e rejeita tentativas de duplicação com mensagem de erro apropriada.

#### 4. Realização de Empréstimo (QTD-9)
**Objetivo**: Validar tentativa de empréstimo onde cliente e livro estão aptos versus situações onde um deles possui restrição.

- **Classe Válida (Cliente Apto + Livro Disponível)**: Cliente "Ana" com Livro "Código Limpo"
- **Classe Inválida (Cliente Bloqueado ou Livro Indisponível)**: Cliente "Pedro" com Livro "Dom Casmurro"

**Resultado**: Empréstimo é permitido apenas quando cliente está habilitado e livro está disponível. Sistema impede empréstimos quando cliente está bloqueado ou livro já está emprestado.

---

## Requisitos para Executar

Para conseguir rodar a aplicação e os testes desenvolvidos, são necessários os seguintes requisitos:

- **JUnit 5**: Framework de testes unitários
- **IntelliJ IDEA**: IDE para desenvolvimento Java
- **JDK 21**: Java Development Kit versão 21 ou superior
- **Processador dual-core**: Mínimo recomendado
- **4GB RAM**: Memória mínima recomendada

