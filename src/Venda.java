// Classe modelo para Venda
class Venda {
    private int id;
    private Bicicleta bicicleta;
    private Usuario vendedor;
    private Comprador comprador;
    private String data;
    private String formaPagamento;
    private double desconto;

    public Venda(int id, Bicicleta bicicleta, Usuario vendedor, Comprador comprador, String data, String formaPagamento, double desconto) {
        this.id = id;
        this.bicicleta = bicicleta;
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.data = data;
        this.formaPagamento = formaPagamento;
        this.desconto = desconto;
    }
}
