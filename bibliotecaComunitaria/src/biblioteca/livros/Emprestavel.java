package biblioteca.livros;

import biblioteca.pessoas.*;

/* @author Luam  */

public interface Emprestavel {
    public void emprestarLivro(Acervo a, Cliente c);
    public void devolverLivro();
    
}
