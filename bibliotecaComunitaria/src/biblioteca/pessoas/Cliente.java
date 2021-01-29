package biblioteca.pessoas;

import biblioteca.biblioteca.Endereco;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.io.*;


/* @author victoria */

public class Cliente extends Pessoa{
    private String CPF;
    private Endereco end;

    public Cliente(String nome, String CPF, String nascimento, String telefone, String rua, String bairro, String cep, String cidade, String estado){
        this.CPF=CPF;
        super.cadastro(nome, nascimento, telefone);
        this.end = new Endereco(rua, bairro, cep, cidade, estado);
    
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    
    @Override
    public String toString() {
        return ("Nome: " + this.getNome() + " - " + "CPF: " + this.getCPF() + " - " + "Nascimento: " + this.getNascimento() + " - " + "Telefone: " + this.getTelefone() + " - " + "Endereço -> " + this.getEnd());
    }
    

    public Endereco getEnd() {
        return end;
    }
    
    /* @author victoria */
    public static void escreverCliente(Cliente cliente, String path) {
        BufferedWriter bw = null;
        String linha = cliente.getNome() + "," + cliente.getCPF() + "," + cliente.getNascimento() 
                + "," + cliente.getTelefone() + "," + cliente.getEnd().getRua() 
                + "," + cliente.getEnd().getBairro() + "," + cliente.getEnd().getCep() 
                + "," + cliente.getEnd().getCidade() + "," + cliente.getEnd().getEstado();
        try { 
            bw = new BufferedWriter(new FileWriter(path+"clientes.csv", true));
            PrintWriter pw = new PrintWriter(bw);
            pw.println(linha);
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }   
        }
    }
}
