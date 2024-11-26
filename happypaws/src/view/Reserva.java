package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import dao.EstadiaDAO;
import model.Estadia;
import util.ConectaMySQL;

public class Reserva extends JFrame {
    private JTextField txtDataInicio, txtDataTermino;
    private JTextArea txtObservacoes;
    private JButton btnConfirmar, btnCancelar;
    private SimpleDateFormat formatoData = new SimpleDateFormat("dd-MM-yyyy");
    private EstadiaDAO dao;
    private ConectaMySQL conn;

    public Reserva(int idPet) {
        super("Reserva - Happy Paws");
        this.dao = new EstadiaDAO(conn.conectaBD());

        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10,10));
        getContentPane().setBackground(new Color(163, 196, 104));
        this.setIconImage(new ImageIcon(getClass().getResource("/img/Logo.png")).getImage());

        JLabel lblDataInicio = new JLabel("Data de entrada(DD-MM-AAAA):");
        txtDataInicio = new JTextField();
        JLabel lblDataTermino = new JLabel("Data de saída(DD-MM-AAAA):");
        txtDataTermino = new JTextField();
        JLabel lblObservacoes = new JLabel("Observações:");

        txtObservacoes = new JTextArea("");
        JScrollPane scrollObservacoes = new JScrollPane(txtObservacoes);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);

        btnConfirmar = new JButton("Confirmar");
        btnConfirmar.setBackground(new Color(90, 108, 55));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 try {
                    Date dataInicio = new Date(formatoData.parse(txtDataInicio.getText()).getTime());
                    Date dataTermino = new Date(formatoData.parse(txtDataTermino.getText()).getTime());
                    String observacoes = txtObservacoes.getText();

                    if (dataInicio.toString().isEmpty() || dataTermino.toString().isEmpty()) {
                    	JOptionPane.showMessageDialog(null, "Preencha todos os campos de data!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    Estadia estadia = new Estadia(dataInicio, dataTermino, observacoes);
                    
                    try {
						dao.create(estadia, idPet);
						JOptionPane.showMessageDialog(null, "Reserva realizada com sucesso!");
						Reserva.this.setVisible(false);
						dispose();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "Erro no cadstro da reserva");
					}
                   
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(Reserva.this, "Erro: Insira as datas no formato DD-MM-YYYY.", "Erro de Formato de Data", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(90, 108, 55));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.addActionListener(e -> dispose());

        add(lblDataInicio);
        add(txtDataInicio);
        add(lblDataTermino);
        add(txtDataTermino);
        add(lblObservacoes);
        add(scrollObservacoes);
        add(btnCancelar);
        add(btnConfirmar);
        
    }

}
