package biblioteca.livros;

public class Livro implements Emprestavel {
   int idLivro;
   String titulo;
   String autor;
   int numPaginas;
   long ISBN;
   String genero;
   String idioma;
   int anoPublicacao;
   String editora;
   int edicao;
   boolean estaEmprestado;
   
   
    public Livro(String titulo, String autor) {
        this.idLivro = Acervo.idsLivros++;
        this.titulo = titulo;
        this.autor = autor;
    }
    public Livro(String titulo, String autor, int numPaginas, long ISBN, String genero, 
            String idioma, int anoPublicacao, String editora, int edicao) {
        this.idLivro = Acervo.idsLivros++;
        this.titulo = titulo;
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.ISBN = ISBN;
        this.genero = genero;
        this.idioma = idioma;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.edicao = edicao;
    }

    
    public void imprimirLivro(){
        System.out.printf("%s (%d)\n%s - %s %dªed. \n", this.titulo, this.anoPublicacao, this.autor, this.editora, this.edicao);
    }

    @Override
    public void emprestarLivro(Acervo acervo/*, Cliente c*/) {
        Emprestimo novo = new Emprestimo(this, "12/10/20", "17/12/20" );
        acervo.registarEmprestimo(novo);
        // Adicionar livro emprestado ao array do Cliente
        this.estaEmprestado = true;
        
    }

    @Override
    public void devolverLivro(Acervo acervo, int idEmprestimo) {
        // Checar atraso
        Emprestimo emp = acervo.buscarEmprestimo(idEmprestimo);
        // Buscar o livro no acervo e mudar para não-emprestado
        //Buscar Cliente no Admnistração e retirar livro do array dele 
        acervo.registarDevolucao(emp);
        
    }
   
}
