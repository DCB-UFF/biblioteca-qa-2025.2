package biblioteca.livros;

public class Livro implements Emprestavel {
   int idLivro;
   boolean estaEmprestado;
   
   String titulo;
   String autor;
   int numPaginas;
   long ISBN;
   String genero;
   String idioma;
   int anoPublicacao;
   String editora;
   int edicao;
   
   
   
    public Livro(String titulo, String autor, int anoPublicacao, String editora, int edicao) {
        this.idLivro = Acervo.idsLivros++;
        this.estaEmprestado = false;
        
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.edicao= edicao;
        
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
        this.estaEmprestado = false;
    }

    
    public void imprimirLivro(){
        if (this.estaEmprestado){
            System.out.printf("Cód: %d [Emprestado]| %s (%d)\n%s - %s %dªed.  \n",
                    this.idLivro, this.titulo, this.anoPublicacao, this.autor, this.editora, this.edicao);    
        }
        else{
            System.out.printf("Cód: %d | %s (%d)\n%s - %s %dªed. \n",
            this.idLivro, this.titulo, this.anoPublicacao, this.autor, this.editora, this.edicao);
        }       
    }

    @Override
    public void emprestarLivro(Acervo acervo/*, Cliente c*/) {
        Emprestimo novo = new Emprestimo(this, "12/10/20", "17/12/20" );
        acervo.registarEmprestimo(novo);
        // Adicionar livro emprestado ao array do Cliente
        this.estaEmprestado = true;
        
    }
    
   @Override
    public void devolverLivro() {
        this.estaEmprestado = false;
    }

    public boolean isEmpty() {
       return this.idLivro==0;
    }

   
}
