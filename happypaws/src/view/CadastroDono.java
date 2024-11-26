package view;

import javax.swing.*;

import dao.DonoDAO;
import model.Dono;
import util.ConectaMySQL;
import util.ValidarCpf;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroDono extends JFrame {
	private JTextField tfNomeDono, tfEmailDono, tfCPF, tfTelefone, tfContatoEmergencia;
	private JPasswordField tfSenha;
	private JButton btnCadastrar, btnCancelar;
	private ConectaMySQL conn;
	private DonoDAO dao;

	public CadastroDono() {
		super("Cadastro - Happy Paws");
		this.dao = new DonoDAO(conn.conectaBD());

		setSize(400, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(7, 2, 10, 10));
		getContentPane().setBackground(new Color(163, 196, 104));
		this.setIconImage(new ImageIcon(getClass().getResource("/img/Logo.png")).getImage());

		JLabel lblNome = new JLabel("Nome:");
		tfNomeDono = new JTextField();
		JLabel lblEmail = new JLabel("Email:");
		tfEmailDono = new JTextField();
		JLabel lblCPF = new JLabel("CPF:");
		tfCPF = new JTextField();
		JLabel lblSenha = new JLabel("Senha:");
		tfSenha = new JPasswordField();
		JLabel lblTelefone = new JLabel("Telefone:");
		tfTelefone = new JTextField();
		JLabel lblContatoEmergencia = new JLabel("Contato de Emergência:");
		tfContatoEmergencia = new JTextField();
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBackground(new Color(90, 108, 55));
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(new Color(90, 108, 55));
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));

		add(lblNome);
		add(tfNomeDono);
		add(lblEmail);
		add(tfEmailDono);
		add(lblCPF);
		add(tfCPF);
		add(lblSenha);
		add(tfSenha);
		add(lblTelefone);
		add(tfTelefone);
		add(lblContatoEmergencia);
		add(tfContatoEmergencia);
		add(btnCancelar);
		add(btnCadastrar);

		btnCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String nomeDono = tfNomeDono.getText();
				String email = tfEmailDono.getText();
				String cpf = tfCPF.getText();
				String senha = new String(tfSenha.getPassword());
				String telefone = tfTelefone.getText();
				String contatoEmergencia = tfContatoEmergencia.getText();

				if (nomeDono.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty() || telefone.isEmpty()
						|| contatoEmergencia.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (!ValidarCpf.validarCPF(cpf)) {
					JOptionPane.showMessageDialog(null, "CPF inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					cpf = ValidarCpf.limpar(cpf);
				}

				if (dao.verificarEmail(email)) {
					JOptionPane.showMessageDialog(null, "Email já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (dao.verificarCpfExistente(cpf)) {
					JOptionPane.showMessageDialog(null, "CPF já cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
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
					dao.create(dono);
					JOptionPane.showMessageDialog(null, "Cadastro concluído com sucesso");
					CadastroDono.this.setVisible(false);
					dispose();
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroDono.this.setVisible(false);
				dispose();

			}
		});
	}

}
