import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.dao.impl.VeiculoDaoJDBC;
import model.Veiculo;
import db.DB;

public class TelaCliente extends JFrame {

    private JPanel panel;
    private JButton[] btnTestDrive;
    private JLabel[] lblCarro;
    private JLabel[] lblImagem;
    private JButton btnSair;
    private VeiculoDaoJDBC veiculoDao;
    private List<Veiculo> listaVeiculos;

    public TelaCliente() {
        
        veiculoDao = new VeiculoDaoJDBC(DB.getConnection()); 
        listaVeiculos = veiculoDao.buscarTodosVeiculos(); 

        setTitle("Área do Cliente - Concessionária TOPcar");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(6, 3, 10, 10)); 
        panel.setBackground(new Color(30, 30, 30)); 

        
        String[] imagens = {
                "src/imagens/bmw_serie3.png",     
                "src/imagens/chevrolet_camaro.png",   
                "src/imagens/ford_mustang.png", 
                "src/imagens/toyotacorolla.png",
                "src/imagens/mercedes-benz_classe_c.png",
                "src/imagens/honda_civic2.png"
        };

        btnTestDrive = new JButton[listaVeiculos.size()];
        lblCarro = new JLabel[listaVeiculos.size()];
        lblImagem = new JLabel[6];  

        Font fonteTexto = new Font("SansSerif", Font.PLAIN, 16);

       
        for (int i = 0; i < listaVeiculos.size(); i++) {
            Veiculo veiculo = listaVeiculos.get(i); 

            
            lblImagem[i] = new JLabel();
            lblImagem[i].setIcon(redimensionarImagem(imagens[i], 150, 100)); 
            lblImagem[i].setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(lblImagem[i]);

            
            lblCarro[i] = new JLabel("<html>Modelo: " + veiculo.getModelo() + "<br>Cor: " + veiculo.getCor() + "</html>");
            lblCarro[i].setForeground(Color.WHITE);
            lblCarro[i].setFont(fonteTexto);
            lblCarro[i].setHorizontalAlignment(SwingConstants.CENTER); 
            panel.add(lblCarro[i]);

           
            btnTestDrive[i] = new JButton("Agendar Test Drive");
            btnTestDrive[i].setBackground(Color.BLACK);
            btnTestDrive[i].setForeground(Color.white);
            btnTestDrive[i].setFont(fonteTexto);
            btnTestDrive[i].setFocusPainted(false);
            btnTestDrive[i].setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            btnTestDrive[i].addActionListener(new AgendarTestDriveListener(i)); 
            panel.add(btnTestDrive[i]);
        }

        
        btnSair = new JButton("Sair");
        btnSair.setBackground(Color.BLACK);
        btnSair.setForeground(Color.YELLOW);
        btnSair.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSair.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 1));
        btnSair.setFocusPainted(false);
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                new TelaLogin().setVisible(true);
                dispose();
            }
        });

        
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(30, 30, 30));

        
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelSuperior.setBackground(new Color(30, 30, 30));
        painelSuperior.add(btnSair);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(panel), BorderLayout.CENTER);

        add(painelPrincipal);
    }

    
    private ImageIcon redimensionarImagem(String caminhoImagem, int largura, int altura) {
        ImageIcon icone = new ImageIcon(caminhoImagem);
        Image imagem = icone.getImage();
        Image imagemRedimensionada = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH); 
        return new ImageIcon(imagemRedimensionada);
    }

    
    private class AgendarTestDriveListener implements ActionListener {
        private int index;

        public AgendarTestDriveListener(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realizar um test drive com o " + listaVeiculos.get(index).getModelo() + "?", "Confirmar Test Drive", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Test Drive realizado com sucesso!");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCliente().setVisible(true));
    }
}
