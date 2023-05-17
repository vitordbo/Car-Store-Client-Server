package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import implementacoes.LojaReplicadaImpl;
import interfaces.Loja;
import replicacao.ServiceGateway;

public class SuperServidor {

    private static final String[] REPLICA_ADDRESSES = {"endereco_replica1", "endereco_replica2", "endereco_replica3"};
    public static void main(String[] args) {
        try {
            // Obtém o registro do RMI
            Registry registry = LocateRegistry.createRegistry(1099);
    
            System.out.println("Servidor pronto para receber conexões dos clientes");
    
            // Verifica se há réplicas disponíveis
            if (REPLICA_ADDRESSES.length == 0) {
                System.out.println("Não há réplicas disponíveis");
                return;
            }
    
            // Cria uma lista para armazenar as réplicas disponíveis
            List<Loja> replicaList = new ArrayList<>();
    
            // Cria instâncias das réplicas e adiciona à lista
            for (String replicaAddress : REPLICA_ADDRESSES) {
                Loja replica = new LojaReplicadaImpl("D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/arquivos/carros.txt");
                replicaList.add(replica);
            }
    
            // Converte a lista em um array
            Loja[] replicas = replicaList.toArray(new Loja[0]);
    
            // Cria uma instância do objeto do servidor redirecionador
            ServiceGateway redirector = new ServiceGateway(replicas, true);
    
            // Exporta o objeto remoto
            Loja stub = (Loja) UnicastRemoteObject.exportObject(redirector, 0);
    
            // Associa o stub do objeto remoto ao registro
            registry.rebind("redirecionador", stub);
    
            System.out.println("Servidor redirecionador registrado no registro RMI");
    
        } catch (RemoteException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }
    
}
