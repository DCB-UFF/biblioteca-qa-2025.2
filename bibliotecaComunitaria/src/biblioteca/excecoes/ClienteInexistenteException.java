package biblioteca.excecoes;

/* @author victoria */

public class ClienteInexistenteException extends Exception{
    
    public ClienteInexistenteException(){
        
        super("Cliente não existe");
        
    }
    
}
