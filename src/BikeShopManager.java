import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class BikeShopManager {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}

class LoginScreen extends JFrame {
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginScreen() {
        setTitle("Login - Bike Software");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xDDDDDD));

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(0xDDDDDD));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Sistema Operacional SportBike", SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(0xC464C2));
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        panel.add(new JLabel("Usuário:"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Senha:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Entrar");
        loginButton.setBackground(new Color(0x61CCAD));
        loginButton.setForeground(Color.BLACK);
        panel.add(loginButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        new MainScreen().setVisible(true);
        dispose();
    }
}

class MainScreen extends JFrame {
    private JTable bikeTable;
    private DefaultTableModel tableModel;

    public MainScreen() {
        setTitle("Tela Principal - Bike Shop");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xF0F0F0));

        JLabel title = new JLabel("Bicicletas Disponíveis", SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(0xC464C2));
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Modelo", "Marca", "Cor", "Tamanho", "Preço"}, 0);
        bikeTable = new JTable(tableModel);
        add(new JScrollPane(bikeTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton vendasButton = new JButton("Vendas");
        vendasButton.addActionListener(e -> new SalesScreen().setVisible(true));
        buttonPanel.add(vendasButton);

        JButton cadastroButton = new JButton("Cadastro de Bicicletas");
        cadastroButton.addActionListener(e -> new CadastroBicicletaScreen().setVisible(true));
        buttonPanel.add(cadastroButton);

        add(buttonPanel, BorderLayout.SOUTH);

        carregarBicicletasDisponiveis();
    }

    private void carregarBicicletasDisponiveis() {
        tableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_shop", "root", "211006")) {
            String sql = "SELECT id, modelo, marca, cor, tamanho, preco FROM bicicletas WHERE disponibilidade = true";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("modelo"),
                    rs.getString("marca"),
                    rs.getString("cor"),
                    rs.getString("tamanho"),
                    rs.getDouble("preco")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class CadastroBicicletaScreen extends JFrame {
    private JTextField modeloField, marcaField, corField, tamanhoField, precoField;
    private JButton salvarButton;

    public CadastroBicicletaScreen() {
        setTitle("Cadastro de Bicicleta");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));
        getContentPane().setBackground(new Color(0xFFFFFF));
        setResizable(false);

        add(new JLabel("Modelo:"));
        modeloField = new JTextField();
        add(modeloField);

        add(new JLabel("Marca:"));
        marcaField = new JTextField();
        add(marcaField);

        add(new JLabel("Cor:"));
        corField = new JTextField();
        add(corField);

        add(new JLabel("Tamanho:"));
        tamanhoField = new JTextField();
        add(tamanhoField);

        add(new JLabel("Preço:"));
        precoField = new JTextField();
        add(precoField);

        salvarButton = new JButton("Salvar");
        salvarButton.setBackground(new Color(0x61CCAD));
        salvarButton.addActionListener(e -> salvarBicicleta());
        add(salvarButton);
    }

    private void salvarBicicleta() {
        String modelo = modeloField.getText();
        String marca = marcaField.getText();
        String cor = corField.getText();
        String tamanho = tamanhoField.getText();
        double preco = Double.parseDouble(precoField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_shop", "root", "211006")) {
            String sql = "INSERT INTO bicicletas (modelo, marca, cor, tamanho, preco) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, modelo);
            stmt.setString(2, marca);
            stmt.setString(3, cor);
            stmt.setString(4, tamanho);
            stmt.setDouble(5, preco);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Bicicleta cadastrada com sucesso!");
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar bicicleta.", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

class SalesScreen extends JFrame {
    private JTable salesTable;
    private DefaultTableModel tableModel;

    public SalesScreen() {
        setTitle("Registro de Vendas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0xDDDDDD));

        JLabel title = new JLabel("Registro de Vendas", SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(new Color(0xC464C2));
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Modelo", "Marca", "Preço", "Cliente", "Pagamento", "Desconto"}, 0);
        salesTable = new JTable(tableModel);
        add(new JScrollPane(salesTable), BorderLayout.CENTER);

        JButton registerSaleButton = new JButton("Registrar Venda");
        JPanel panel = new JPanel();
        panel.add(registerSaleButton);
        add(panel, BorderLayout.SOUTH);

        registerSaleButton.addActionListener(e -> registrarVenda());
        carregarVendas();
    }

    private void registrarVenda() {
        String cliente = JOptionPane.showInputDialog(this, "Nome do Cliente:");
        String idStr = JOptionPane.showInputDialog(this, "ID da Bicicleta:");
        String pagamento = JOptionPane.showInputDialog(this, "Forma de Pagamento:");
        String descontoStr = JOptionPane.showInputDialog(this, "Desconto (em reais):");

        if (idStr == null || idStr.isEmpty() || cliente == null || cliente.isEmpty()) {
            return;
        }

        try {
            int idBicicleta = Integer.parseInt(idStr);
            double desconto = descontoStr != null && !descontoStr.isEmpty() ? Double.parseDouble(descontoStr) : 0.0;

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_shop", "root", "211006")) {
                String insertClienteSQL = "INSERT INTO compradores (nome) VALUES (?)";
                PreparedStatement stmtCliente = conn.prepareStatement(insertClienteSQL, Statement.RETURN_GENERATED_KEYS);
                stmtCliente.setString(1, cliente);
                stmtCliente.executeUpdate();

                ResultSet rs = stmtCliente.getGeneratedKeys();
                int idComprador = -1;
                if (rs.next()) {
                    idComprador = rs.getInt(1);
                }

                String insertVendaSQL = "INSERT INTO vendas (bicicleta_id, comprador_id, forma_pagamento, desconto, vendedor_id) VALUES (?, ?, ?, ?, NULL)";
                PreparedStatement stmtVenda = conn.prepareStatement(insertVendaSQL);
                stmtVenda.setInt(1, idBicicleta);
                stmtVenda.setInt(2, idComprador);
                stmtVenda.setString(3, pagamento);
                stmtVenda.setDouble(4, desconto);
                stmtVenda.executeUpdate();

                // Atualizar disponibilidade da bicicleta
                String updateDisponibilidadeSQL = "UPDATE bicicletas SET disponibilidade = false WHERE id = ?";
                PreparedStatement stmtUpdate = conn.prepareStatement(updateDisponibilidadeSQL);
                stmtUpdate.setInt(1, idBicicleta);
                stmtUpdate.executeUpdate();

                carregarVendas();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID ou desconto inválido. Por favor, insira valores corretos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarVendas() {
        tableModel.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bike_shop", "root", "211006")) {
            String sql = """
                SELECT v.id, b.modelo, b.marca, b.preco, c.nome AS cliente, v.forma_pagamento, v.desconto
                FROM vendas v
                JOIN bicicletas b ON v.bicicleta_id = b.id
                JOIN compradores c ON v.comprador_id = c.id
            """;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("modelo"),
                    rs.getString("marca"),
                    rs.getDouble("preco"),
                    rs.getString("cliente"),
                    rs.getString("forma_pagamento"),
                    rs.getDouble("desconto")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
