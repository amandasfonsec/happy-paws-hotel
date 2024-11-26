package view;

import javax.swing.*;

import dao.DonoDAO;
import model.Dono;
import util.ConectaMySQL;
import util.SessaoUsuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class EditarPerfilDono extends JFrame {
    private JTextField tfNome, tfEmail, tfCPF, tfTelefone, tfContatoEmergencia;
    private JPasswordField tfSenha;
    private JButton btnSalvar, btnCancelar;
    private ConectaMySQL conn;
    private DonoDAO dao;

    public EditarPerfilDono() {
        super("Editar Perfil - Happy Paws");
        dao = new DonoDAO(conn.conectaBD());

        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));
        getContentPane().setBackground(new Color(163, 196, 104));
        this.setIconImage(new ImageIcon(getClass().getResource("/img/Logo.png")).getImage());

        tfNome = new JTextField();
        tfEmail = new JTextField();
        tfSenha = new JPasswordField();
        tfCPF = new JTextField();
        tfTelefone = new JTextField();
        tfContatoEmergencia = new JTextField();
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(90, 108, 55));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(90, 108, 55));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        
        tfCPF.setOpaque(true);
		tfCPF.setBackground(Color.LIGHT_GRAY);
        Dono dono;
		try {
			dono = dao.read().stream().filter(d -> d.getIdDono() == SessaoUsuario.getIdUsuario()).findFirst().orElse(null);
			if (dono != null) {
	            tfNome.setText(dono.getNomeDono());
	            tfEmail.setText(dono.getEmail());
	            tfCPF.setText(dono.getCpf());
	            tfCPF.setEditable(false); 
	            tfTelefone.setText(dono.getTelefone());
	            tfContatoEmergencia.setText(dono.getContatoEmergencia());
	        }
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
        

       
        add(new JLabel("Nome:"));
        add(tfNome);
        add(new JLabel("Email:"));
        add(tfEmail);
        add(new JLabel("Senha:"));
        add(tfSenha);
        add(new JLabel("CPF:"));
        add(tfCPF);
        add(new JLabel("Telefone:"));
        add(tfTelefone);
        add(new JLabel("Contato de Emergência:"));
        add(tfContatoEmergencia);
        add(btnCancelar);
        add(btnSalvar);
      
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String nomeDono = tfNome.getText();
                String email = tfEmail.getText();
                String cpf = tfCPF.getText();
                String senha = new String(tfSenha.getPassword());
                String telefone = tfTelefone.getText();
                String contatoEmergencia = tfContatoEmergencia.getText();

                
                if (nomeDono.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() ||
                        telefone.isEmpty() || contatoEmergencia.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                	Dono dono = new Dono();
                	dono.setNomeDono(nomeDono);
                	dono.setEmail(email);
                	dono.setCpf(cpf);
                	dono.setSenha(senha);
                	dono.setTelefone(telefone);
                	dono.setContatoEmergencia(contatoEmergencia);
                	
                	try {
						dao.update(dono);;
						JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
						EditarPerfilDono.this.setVisible(false);
						dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
               
            }
        });

        
        btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditarPerfilDono ed = new EditarPerfilDono();
				ed.setVisible(false);
				dispose();
				
			}
		});
        setVisible(true);
    }

   

    
}