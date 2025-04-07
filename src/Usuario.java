// Classe modelo para Usuario
class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipo;

    public Usuario(int id, String nome, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }
}
