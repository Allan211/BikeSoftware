// Classe modelo para Bicicleta
class Bicicleta {
    private int id;
    private String modelo;
    private String marca;
    private String cor;
    private String tamanho;
    private double preco;
    private boolean disponibilidade;

    public Bicicleta(int id, String modelo, String marca, String cor, String tamanho, double preco, boolean disponibilidade) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.tamanho = tamanho;
        this.preco = preco;
        this.disponibilidade = disponibilidade;
    }
}