package biblioteca.livros;

public class Livro {
   String titulo;
   String autor;
   int numPaginas;
   long ISBN;
   String genero;
   String idioma;
   int anoPublicacao;
   String editora;
   int edicao;

    public Livro(String titulo, String autor, int numPaginas, long ISBN, String genero, 
            String idioma, int anoPublicacao, String editora, int edicao) {
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
   
}
