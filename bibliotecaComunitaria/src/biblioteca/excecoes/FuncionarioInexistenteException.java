package biblioteca.excecoes;

/* @author victoria */

public class FuncionarioInexistenteException extends Exception{
    
    public FuncionarioInexistenteException (){
        
        super("Funcionário não existe");
        
    }
    
}
