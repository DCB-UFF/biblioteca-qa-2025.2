package biblioteca.excecoes;

/* @author victoria */

public class LivroNaoExistenteException extends Exception{
    public LivroNaoExistenteException(){
        super("O livro não existe no acervo\n");
    }
}
