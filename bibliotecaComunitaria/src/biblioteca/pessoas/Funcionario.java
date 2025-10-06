package biblioteca.pessoas;

import biblioteca.biblioteca.Endereco;
import biblioteca.biblioteca.*;
import biblioteca.livros.*;
import biblioteca.pessoas.*;
import java.util.ArrayList;
import java.io.*;


/* @author victoria */

public class Funcionario extends Pessoa{
    
    private String CPF;
    private float salario;
    private String cargo;
    private Endereco end;

    public Funcionario(String nome, String CPF,String nascimento, String telefone, float salario, String cargo, String rua, String bairro, String cep, String cidade, String estado){
        
        super.cadastro(nome, nascimento, telefone);
        this.CPF = CPF;
        this.salario = salario;
        this.cargo = cargo;
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
        return ("Nome: " + this.getNome() + " - " + "CPF: " + this.CPF + " - " + "Nascimento: " + this.getNascimento() + " - " + "Telefone: " + this.getTelefone() + " - " + "Cargo: " + this.getCargo() + " - " + "Endereço -> " + this.getEnd());
    }

    public float getSalario() {
        return salario;
    }

    public String getCargo() {
        return cargo;
    }

    public Endereco getEnd() {
        return end;
    }
    
    public static void escreverFuncionario(Funcionario funcionario, String path) {
        BufferedWriter bw = null;
        String linha = funcionario.getNome() + "," + funcionario.getCPF()+ "," +funcionario.getNascimento() + "," + funcionario.getTelefone() + ","+ funcionario.getSalario() + "," + funcionario.getCargo() + "," + funcionario.getEnd().getRua() + "," + funcionario.getEnd().getBairro() + "," + funcionario.getEnd().getCep() + "," + funcionario.getEnd().getCidade() + "," + funcionario.getEnd().getEstado();
        try {
            bw = new BufferedWriter(new FileWriter(path+"funcionarios.csv", true));
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
    
    public static ArrayList<Funcionario> leitorFuncionarios(String path) {
        BufferedReader br = null;
        String linha = "";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(path+"funcionarios.csv"));
            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] funcionario = linha.split(",");
                Funcionario novo = new Funcionario(funcionario[0], funcionario[1], funcionario[2],
                        funcionario[3], Float.parseFloat(funcionario[4]), funcionario[5],
                        funcionario[6], funcionario[7], funcionario[8], funcionario[9], funcionario[10]);
                funcionarios.add(novo);
            }
            return funcionarios;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public static void removerFuncionario(Funcionario funcionarioDeletado, String path) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        File antigo = new File(path+"funcionarios.csv");
        File novo = new File (path+"temp.csv");
        
        try {
            br = new BufferedReader(new FileReader(antigo));
            bw = new BufferedWriter(new FileWriter(novo, true));
            PrintWriter pw= new PrintWriter(bw);
            String linha = "";
            
            while ((linha = br.readLine()) != null) {
    
                String[] funcionario = linha.split(",");
                if (!funcionario[1].equals(funcionarioDeletado.getCPF())){
                    pw.println(linha);
                }
            }
            pw.flush();  
            pw.close();
            br.close();
            antigo.delete();
            
            File aux = new File (path+"funcionarios.csv");
            novo.renameTo(aux);
            
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
