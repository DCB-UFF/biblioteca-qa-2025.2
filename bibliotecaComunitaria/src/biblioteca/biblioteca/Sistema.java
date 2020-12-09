package biblioteca.biblioteca;
import biblioteca.arquivo.Leitor;
import biblioteca.livros.Acervo;
import biblioteca.pessoas.Autor;
import biblioteca.pessoas.Cliente;
import biblioteca.pessoas.Funcionario;
import java.util.ArrayList;

/* @author victoria */

public class Sistema{
    protected int contadorUnidades;
    public ArrayList <Unidade> unidades = new ArrayList<>();

    public void carregarUnidade(Unidade un){
         //Lê o acervo
        Acervo acervoUn = Leitor.leitorEstantes(un.getPath()); // Lê as estantes pro acervo
        acervoUn.setAutores(Leitor.leitorAutores(un.getPath()));//Lê os autores
        Leitor.leitorLivros(acervoUn,un.getPath()); // Lê os livros
        acervoUn.setEmprestimos(Leitor.leitorEmprestimos(un.getPath())); // Lê os empréstimos
        un.setAcervo(acervoUn);
        
         //Lê os clientes
        ArrayList<Cliente> clientesNiteroi = Leitor.leitorClientes(un.getPath());
        un.setClientes(clientesNiteroi);
        
        //Lê os funcionarios
        ArrayList<Funcionario> funcionariosNiteroi = Leitor.leitorFuncionarios(un.getPath());
        un.setFuncionarios(funcionariosNiteroi);
    }
 
    
    public int getContadorUnidades() {
        return contadorUnidades;
    }

    public void setContadorUnidades(int contadorUnidades) {
        this.contadorUnidades = contadorUnidades;
    }

    public int addContadorUnidades() {
        contadorUnidades++;
        return contadorUnidades;
    }
    
    public ArrayList<Unidade> getUnidades() {
        return unidades;
    }

    public void setUnidades(ArrayList<Unidade> unidades) {
        this.unidades = unidades;
    }
    
    public void add(Unidade u){
        unidades.add(u);
    }

}


