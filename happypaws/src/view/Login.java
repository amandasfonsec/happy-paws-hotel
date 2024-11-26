package view;

import javax.swing.*;


import dao.DonoDAO;
import model.Dono;
import util.ConectaMySQL;
import util.SessaoUsuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField tfEmail;
    private JPasswordField tfSenha;
    private JButton btnLogin;
    private JButton btnCadastro;
    private ConectaMySQL conn;
    
    public Login() {
        super("Login - Happy Paws");
   
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(163, 196, 104));
        this.setIconImage(new ImageIcon(getClass().getResource("/img/Logo.png")).getImage());
        
        JPanel panelLogo = new JPanel();
        panelLogo.setBackground(new Color(163, 196, 104));
        
        JLabel lblImagem = new JLabel();
        lblImagem.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/LogoNome.png")).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH))); 
        panelLogo.add(lblImagem);
        
        JPanel panelLogin = new JPanel(new GridLayout(4, 2, 20, 20));
        panelLogin.setBackground(new Color(163, 196, 104));
        
        JLabel lblEmail = new JLabel("E-MAIL:");
        lblEmail.setForeground(Color.WHITE);
        lblEmail.setBackground(new Color(123, 146, 64)); 
        lblEmail.setOpaque(true); 
        lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
        lblEmail.setFont(new Font("Arial", Font.BOLD, 14));
        tfEmail = new JTextField();
        
        JLabel lblSenha = new JLabel("SENHA:");
        lblSenha.setForeground(Color.WHITE); 
        lblSenha.setBackground(new Color(123, 146, 64)); 
        lblSenha.setOpaque(true); 
        lblSenha.setHorizontalAlignment(SwingConstants.CENTER);
        lblSenha.setFont(new Font("Arial", Font.BOLD, 14));
        tfSenha = new JPasswordField();
        
        btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(new Color(90, 108, 55));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnCadastro = new JButton("CADASTRAR");
        btnCadastro.setBackground(new Color(90, 108, 55));
        btnCadastro.setForeground(Color.WHITE);
        btnCadastro.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelLogin.add(lblEmail);
        panelLogin.add(tfEmail);
        panelLogin.add(lblSenha);
        panelLogin.add(tfSenha);
        panelLogin.add(new JLabel());  
        panelLogin.add(btnLogin);
        panelLogin.add(new JLabel()); 
        panelLogin.add(btnCadastro);

        add(panelLogo, BorderLayout.NORTH);
        add(panelLogin, BorderLayout.CENTER);
        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = tfEmail.getText();
                    String senha = new String(tfSenha.getPassword());
                    
                    Dono dono = new Dono(email,senha);

                    DonoDAO donoDAO = new DonoDAO(conn.conectaBD());
                    Integer idUsuario = donoDAO.validarLogin(dono);
                    
                    if (idUsuario != null) { 
                        SessaoUsuario.setIdUsuario(idUsuario); 
                        Menu menu = new Menu();
                        menu.setVisible(true);
                        dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha inválida");
                    }
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, "Login: " + erro);
                }
            }
        });

        
        btnCadastro.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                CadastroDono cdsDono = new CadastroDono();
                cdsDono.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login tela = new Login();
            tela.setVisible(true);
        });
    }
}
