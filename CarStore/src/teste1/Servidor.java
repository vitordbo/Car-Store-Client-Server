import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Servidor extends UnicastRemoteObject implements InterfaceRemota {
private ArrayList<Carro> carros;
private ArrayList<Usuario> usuarios;
private ArrayList<Funcionario> funcionarios;

public Servidor() throws RemoteException {
    super();
    this.carros = new ArrayList<Carro>();
    this.usuarios = new ArrayList<Usuario>();
    this.funcionarios = new ArrayList<Funcionario>();
}

@Override
public void adicionarCarro(Carro carro) throws RemoteException {
    this.carros.add(carro);
}

@Override
public void apagarCarro(Carro carro) throws RemoteException {
    this.carros.remove(carro);
}

@Override
public void alterarCarro(Carro carro, String novoModelo, String novaMarca, int novoAno) throws RemoteException {
    carro.setModelo(novoModelo);
    carro.setMarca(novaMarca);
    carro.setAno(novoAno);
}

@Override
public ArrayList<Carro> listarCarros() throws RemoteException {
    return this.carros;
}

@Override
public ArrayList<Carro> pesquisarCarro(Pesquisa pesquisa) throws RemoteException {
    ArrayList<Carro> resultados = new ArrayList<Carro>();
    for (Carro carro : this.carros) {
        if (pesquisa.getModelo().equals(carro.getModelo()) &&
            pesquisa.getMarca().equals(carro.getMarca()) &&
            pesquisa.getAno() == carro.getAno()) {
            resultados.add(carro);
        }
    }
    return resultados;
}

@Override
public int exibirQuantidadeDeCarros() throws RemoteException {
    return this.carros.size();
}

@Override
public void comprarCarro(Usuario usuario, int indiceCarro) throws RemoteException {
    Carro carro = this.carros.get(indiceCarro);
    usuario.comprarCarro(carro);
}


}