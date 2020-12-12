package biblioteca.biblioteca;
import biblioteca.arquivo.*;
import biblioteca.livros.Acervo;
import biblioteca.pessoas.*;
import java.util.ArrayList;

/* @author victoria */

public class Sistema{
    protected int contadorUnidades;
    public ArrayList <Unidade> unidades = new ArrayList<>();
    
    public void carregarUnidades(){
        for (Unidade un: this.unidades){
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
    }
        
    public void criarUnidade(String nome, String rua, String bairro, String cep, String cidade, String estado){
        this.contadorUnidades++; // Adiciona uma nova no contador
        String path = String.valueOf(this.contadorUnidades); // Passa o contador atual para string
        Criador.criarPastaUnidade(path); // Cria a pasta da nova unidade no diretorio
        Unidade nova = new Unidade(path,nome,rua, bairro,cep,cidade,estado); // Cria a nova unidade de fato
        Escritor.escreverUnidade(this, nova);
    }
 
    public void imprimirUnidades(Sistema sistema){
        for(Unidade u: sistema.unidades){
            System.out.println(u.getNome());
        }
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


