Código-fonte: src/biblioteca/
Dados das unidades: src/unidades/unidades.csv

src/biblioteca/biblioteca/Unidade.java: Classe responsável pela gestão das unidades da biblioteca.
src/biblioteca/livros/Livro.java: Classe que representa os livros do acervo.
src/biblioteca/menu/Menu.java: Interface de interação com o usuário.
src/unidades/unidades.csv: Arquivo de dados das unidades cadastradas.


Sugestão de escopo de testes:
1 Testes de Unidade

    Testar métodos das classes principais (Unidade, Livro, Cliente, Funcionario, etc.).
    Validar leitura e escrita de arquivos CSV.
    Testar exceções personalizadas.

2 Testes de Integração

    Simular operações completas (ex: adicionar/remover cliente, empréstimo/devolução de livro).
    Verificar persistência dos dados após operações.

3 Testes de Interface
    Testar menus e fluxos de navegação.
    Validar entradas do usuário e respostas do sistema.

4 Testes de Regressão
    Garantir que novas funcionalidades não quebrem as existentes.