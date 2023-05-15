package replicacao;

import java.io.*;
import java.util.*;
import implementacoes.CarroImpl;
import interfaces.Carro;

public class ReplicadorDeArquivos {
    private File primaryFile;
    private File replicaFile1;
    private File replicaFile2;
    private File replicaFile3;
    private File leaderFile;
    private boolean isLeader;
    private String fileOriginal = "D:/Users/vitor/git/Car-Store-Client-Server/CarStore/src/carros.txt";
    private List<CarroImpl> carros = new ArrayList<CarroImpl>();


    public ReplicadorDeArquivos(String primaryFileName, String replicaFileName1, String replicaFileName2, String replicaFileName3) {
        this.primaryFile = new File(primaryFileName);
        this.replicaFile1 = new File(replicaFileName1);
        this.replicaFile2 = new File(replicaFileName2);
        this.replicaFile3 = new File(replicaFileName3);
        
        // primeiro como líder
        this.leaderFile = this.replicaFile1;
        this.isLeader = true;

        // Verifica se os arquivos existem e cria caso não existam
        if (!this.primaryFile.exists()) {
            try {
                    File primaryFile = new File(fileOriginal);
                    Scanner leitor = new Scanner(primaryFile);
                    while (leitor.hasNextLine()){
                        String linha = leitor.nextLine();
                        String[] campos = linha.split(",");
                        String nome = campos[0];
                        String renavan = campos[1];
                        String categoria = campos[2];
                        int ano = Integer.parseInt(campos[3]);
                        double preco = Double.parseDouble(campos[4]);
                        int quantidade = Integer.parseInt(campos[5]);
                        CarroImpl carro = new CarroImpl(nome, renavan, categoria, ano, preco, quantidade);                
                        carros.add(carro);
                    }
                    leitor.close();
                    this.primaryFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!this.replicaFile1.exists()) {
            try {
                this.replicaFile1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!this.replicaFile2.exists()) {
            try {
                this.replicaFile2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!this.replicaFile3.exists()) {
            try {
                this.replicaFile3.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // atualizar carro pelo renavan 
    public synchronized void updateFile(Carro car) {
        try {
            // Abre o arquivo para leitura e escrita
            RandomAccessFile file = new RandomAccessFile(replicaFile1, "rw");
            
            // Procura a entrada existente para o carro pelo renavan
            String line;
            long pointer = 0;
            while ((line = file.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[1].equals(car.getRenavan())) {
                    // Atualiza os campos relevantes para o carro
                    file.seek(pointer);
                    file.writeBytes(car.getNome() + "," + car.getRenavan() + "," + car.getCategoria() + "," + car.getAnoFabricacao() + "," + car.getPreco() + "," + car.getQuantidadeDisponivel() + "\n");
                    break;
                }
                pointer = file.getFilePointer();
            }
    
            // Fecha o arquivo
            file.close();
    
            // Propaga as mudanças para as réplicas
            propagateChanges();
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo de carros: " + e.getMessage());
        }
    }

    public synchronized void adicionaCarroNoArquivo(Carro car) {
        try {
            // Abre o arquivo para leitura e escrita
            RandomAccessFile file = new RandomAccessFile(replicaFile1, "rw");
            
            // Procura a entrada existente para o carro pelo renavan
            String line;
            long pointer = 0;
            boolean encontrado = false;
            while ((line = file.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields[1].equals(car.getRenavan())) {
                    // Atualiza os campos relevantes para o carro
                    file.seek(pointer);
                    file.writeBytes(car.getNome() + "," + car.getRenavan() + "," + car.getCategoria() + "," + car.getAnoFabricacao() + "," + car.getPreco() + "," + car.getQuantidadeDisponivel() + "\n");
                    encontrado = true;
                    break;
                }
                pointer = file.getFilePointer();
            }
    
            // Se não encontrar a entrada pelo renavan, adiciona uma nova entrada para o carro no final do arquivo
            if (!encontrado) {
                file.seek(file.length());
                file.writeBytes(car.getNome() + "," + car.getRenavan() + "," + car.getCategoria() + "," + car.getAnoFabricacao() + "," + car.getPreco() + "," + car.getQuantidadeDisponivel() + "\n");
            }
    
            // Fecha o arquivo
            file.close();
    
            // Propaga as mudanças para as réplicas
            propagateChanges();
        } catch (IOException e) {
            System.err.println("Erro ao atualizar o arquivo de carros: " + e.getMessage());
        }
    }
    

    public synchronized void deleteFile(String id) {
        try {
            // Cria um arquivo temporário para escrever as entradas não deletadas
            File tempFile = new File(primaryFile.getAbsolutePath() + ".tmp");
            BufferedReader primaryReader = new BufferedReader(new FileReader(primaryFile));
            BufferedWriter tempWriter = new BufferedWriter(new FileWriter(tempFile));
            String line;

            while ((line = primaryReader.readLine()) != null) {
                String[] fields = line.split(",");
                if (!fields[1].equals(id)) {
                    tempWriter.write(line + "\n");
                }
            }

            primaryReader.close();
            tempWriter.close();

            // Substitui o arquivo primário pelo arquivo temporário
            primaryFile.delete();
            tempFile.renameTo(primaryFile);

            // Propaga as mudanças para as réplicas
            propagateChanges();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Carro> getAllCars() {
        List<Carro> cars = new ArrayList<>();
        try {
            BufferedReader primaryReader = new BufferedReader(new FileReader(primaryFile));
            String line;

            // Lê todas as entradas do arquivo primário e adiciona na lista de carros
            while ((line = primaryReader.readLine()) != null) {
                String[] fields = line.split(",");
                Carro car = new CarroImpl(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4]), Integer.parseInt(fields[5]));
                cars.add(car);
            }

            primaryReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cars;
    }

    private synchronized void propagateChanges() {
        // Escreve as mudanças no arquivo da réplica do líder
        try {
            FileWriter leaderWriter = new FileWriter(leaderFile);
            BufferedWriter leaderBuffer = new BufferedWriter(leaderWriter);
            BufferedReader primaryReader = new BufferedReader(new FileReader(primaryFile));
            String line;

            while ((line = primaryReader.readLine()) != null) {
                leaderBuffer.write(line + "\n");
            }

            primaryReader.close();
            leaderBuffer.close();

            // Alterna o líder e copia o arquivo do novo líder para as outras réplicas
            if (isLeader) {
                leaderFile = replicaFile2;
            } else if (leaderFile.equals(replicaFile1)) {
                leaderFile = replicaFile3;
            } else {
                leaderFile = replicaFile1;
            }

            copyFile(leaderFile, replicaFile2);
            copyFile(leaderFile, replicaFile3);

            isLeader = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copyFile(File source, File destination) throws IOException {
        FileInputStream inputStream = new FileInputStream(source);
        FileOutputStream outputStream = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();
    }
}