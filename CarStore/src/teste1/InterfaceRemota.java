import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceRemota extends Remote {
public void adicionarCarro(Carro carro) throws RemoteException;
public void apagarCarro(Carro carro) throws RemoteException;
public void alterarCarro(Carro carro, String novoModelo, String novaMarca, int novoAno) throws RemoteException;
public ArrayList<Carro> listarCarros() throws RemoteException;
public ArrayList<Carro> pesquisarCarro(String modelo, String marca, int ano) throws RemoteException;
public int exibirQuantidadeDeCarros() throws RemoteException;
public void comprarCarro(Carro carro, Usuario usuario) throws RemoteException;

public void adicionarUsuario(Usuario usuario) throws RemoteException;
public void apagarUsuario(Usuario usuario) throws RemoteException;
public void alterarUsuario(Usuario usuario, String novoNome, String novoEmail, String novoTelefone) throws RemoteException;
public ArrayList<Usuario> listarUsuarios() throws RemoteException;
public ArrayList<Usuario> pesquisarUsuario(String nome, String email, String telefone) throws RemoteException;
public int exibirQuantidadeDeUsuarios() throws RemoteException;

public void adicionarFuncionario(Funcionario funcionario) throws RemoteException;
public void apagarFuncionario(Funcionario funcionario) throws RemoteException;
public void alterarFuncionario(Funcionario funcionario, String novoNome, String novoEmail, String novoTelefone, double novoSalario) throws RemoteException;
public ArrayList<Funcionario> listarFuncionarios() throws RemoteException;
public ArrayList<Funcionario> pesquisarFuncionario(String nome, String email, String telefone, double salario) throws RemoteException;
public int exibirQuantidadeDeFuncionarios() throws RemoteException;
}