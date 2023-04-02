public class Carro {
    private String renavan;
    private String nome;
    private String categoria;
    private int anoFabricacao;
    private int quantidadeDisponivel;
    private double preco;
    
    // Construtor da classe Carro
    public Carro(String renavan, String nome, String categoria, int anoFabricacao, int quantidadeDisponivel, double preco) {
        this.renavan = renavan;
        this.nome = nome;
        this.categoria = categoria;
        this.anoFabricacao = anoFabricacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.preco = preco;
    }
    
    // Getters e Setters
    public String getRenavan() {
        return renavan;
    }
    
    public void setRenavan(String renavan) {
        this.renavan = renavan;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getAnoFabricacao() {
        return anoFabricacao;
    }
    
    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }
    
    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
    
    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
