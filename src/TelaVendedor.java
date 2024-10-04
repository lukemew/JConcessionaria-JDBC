import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Veiculo;
import model.dao.impl.VeiculoDaoJDBC;
import db.DB;

public class TelaVendedor extends JFrame {

    private JComboBox<String> comboVeiculos;
    private JTextField txtModelo, txtAno, txtCor;
    private JButton btnAdicionar, btnEditar, btnDeletar, btnSair;
    private VeiculoDaoJDBC veiculoDao;
    private List<Veiculo> listaVeiculos;

    public TelaVendedor() {
        veiculoDao = new VeiculoDaoJDBC(DB.getConnection());
        listaVeiculos = veiculoDao.buscarTodosVeiculos(); 

        setTitle("Área do Vendedor - Concessionária TOPcar");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10)); 
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelForm.setBackground(new Color(30, 30, 30));

       
        JLabel lblSelecionar = new JLabel("Selecionar Veículo:");
        lblSelecionar.setForeground(Color.WHITE);
        comboVeiculos = new JComboBox<>();
        carregarVeiculosNoComboBox(); 
        comboVeiculos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDadosVeiculoSelecionado();
            }
        });

       
        JLabel lblModelo = new JLabel("Modelo:");
        lblModelo.setForeground(Color.WHITE);
        txtModelo = new JTextField();

        JLabel lblAno = new JLabel("Ano:");
        lblAno.setForeground(Color.WHITE);
        txtAno = new JTextField();

        JLabel lblCor = new JLabel("Cor:");
        lblCor.setForeground(Color.WHITE);
        txtCor = new JTextField();

        panelForm.add(lblSelecionar);
        panelForm.add(comboVeiculos);
        panelForm.add(lblModelo);
        panelForm.add(txtModelo);
        panelForm.add(lblAno);
        panelForm.add(txtAno);
        panelForm.add(lblCor);
        panelForm.add(txtCor);

       
        JPanel panelButtons = new JPanel(new GridLayout(1, 4, 10, 10));
        panelButtons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelButtons.setBackground(new Color(30, 30, 30));

      
        btnAdicionar = new JButton("Adicionar Veículo");
        btnAdicionar.setBackground(Color.BLACK);
        btnAdicionar.setForeground(Color.YELLOW);
        btnAdicionar.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarVeiculo();
            }
        });

        
        btnEditar = new JButton("Editar Veículo");
        btnEditar.setBackground(Color.BLACK);
        btnEditar.setForeground(Color.ORANGE);
        btnEditar.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarVeiculo();
            }
        });

        
        btnDeletar = new JButton("Deletar Veículo");
        btnDeletar.setBackground(Color.BLACK);
        btnDeletar.setForeground(Color.RED);
        btnDeletar.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        btnDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarVeiculo();
            }
        });

        
        btnSair = new JButton("Sair");
        btnSair.setBackground(Color.BLACK);
        btnSair.setForeground(Color.WHITE);
        btnSair.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaLogin().setVisible(true);
                dispose();
            }
        });

        panelButtons.add(btnAdicionar);
        panelButtons.add(btnEditar);
        panelButtons.add(btnDeletar);
        panelButtons.add(btnSair);

        add(panelForm, BorderLayout.CENTER);
        add(panelButtons, BorderLayout.SOUTH);
    }

    
    private void carregarVeiculosNoComboBox() {
        comboVeiculos.removeAllItems(); 
        for (Veiculo veiculo : listaVeiculos) {
            comboVeiculos.addItem(veiculo.getModelo()); 
        }
    }

    
    private void carregarDadosVeiculoSelecionado() {
        int index = comboVeiculos.getSelectedIndex();
        if (index >= 0) {
            Veiculo veiculoSelecionado = listaVeiculos.get(index);
            txtModelo.setText(veiculoSelecionado.getModelo());
            txtAno.setText(String.valueOf(veiculoSelecionado.getAno()));
            txtCor.setText(veiculoSelecionado.getCor());
        }
    }

   
    private void adicionarVeiculo() {
        try {
            int ano = Integer.parseInt(txtAno.getText());
            String modelo = txtModelo.getText();
            String cor = txtCor.getText();

            Veiculo veiculo = new Veiculo(ano, modelo, cor);
            veiculoDao.adicionarVeiculo(veiculo);

            JOptionPane.showMessageDialog(null, "Veículo adicionado com sucesso!");
            listaVeiculos = veiculoDao.buscarTodosVeiculos(); 
            carregarVeiculosNoComboBox(); 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um ano válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar veículo: " + e.getMessage());
        }
    }

    
    private void editarVeiculo() {
        int index = comboVeiculos.getSelectedIndex();
        if (index >= 0) {
            try {
                int ano = Integer.parseInt(txtAno.getText());
                String modelo = txtModelo.getText();
                String cor = txtCor.getText();

                Veiculo veiculo = listaVeiculos.get(index);
                veiculo.setAno(ano);
                veiculo.setModelo(modelo);
                veiculo.setCor(cor);

                veiculoDao.atualizarVeiculo(veiculo); 

                JOptionPane.showMessageDialog(null, "Veículo atualizado com sucesso!");
                listaVeiculos = veiculoDao.buscarTodosVeiculos(); 
                carregarVeiculosNoComboBox(); 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, insira um ano válido.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao editar veículo: " + e.getMessage());
            }
        }
    }

    
    private void deletarVeiculo() {
        int index = comboVeiculos.getSelectedIndex();
        if (index >= 0) {
            String modelo = listaVeiculos.get(index).getModelo();

            veiculoDao.deletarVeiculoPelaCor(modelo);
            JOptionPane.showMessageDialog(null, "Veículo deletado com sucesso!");
            listaVeiculos = veiculoDao.buscarTodosVeiculos(); 
            carregarVeiculosNoComboBox(); 
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecione o modelo do veículo a ser deletado.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaVendedor().setVisible(true));
    }
}
