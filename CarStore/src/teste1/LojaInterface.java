import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LojaInterface extends Remote {

    // Adicionar um novo carro na loja
    public void adicionarCarro(Carro carro) throws RemoteException;

    // Apagar um carro da loja
    public void apagarCarro(String nome) throws RemoteException;

    // Listar todos os carros da loja
    public List<Carro> listarCarros() throws RemoteException;

    // Pesquisar um carro na loja pelo nome ou renavan
    public Carro pesquisarCarro(String nome, String renavan) throws RemoteException;

    // Alterar atributos de um carro na loja
    public void alterarCarro(String nome, Carro novoCarro) throws RemoteException;

    // Consultar a quantidade de carros na loja
    public int consultarQuantidade() throws RemoteException;

    // Comprar um carro da loja
    public void comprarCarro(String nome) throws RemoteException;

    // Autenticar usuário na loja
    public boolean autenticarUsuario(String login, String senha) throws RemoteException;

    // Encerrar a sessão do usuário na loja
    public void encerrarSessao() throws RemoteException;
}

