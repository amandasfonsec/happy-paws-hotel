package view;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import dao.AnimalDAO;
import model.Animal;
import util.ConectaMySQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CadastroPet extends JFrame {
    private JTextField tfIdAnimal, tfNomeAnimal, tfIdadeAnimal, tfEspecieAnimal,tfRacaAnimal ;
    private JComboBox<String> cbSexo;
    private JLabel jlFoto;
    private JButton btnCadastrar, btnCancelar;
    private JFileChooser chooser;
	private FileNameExtensionFilter imageFilter;
	private File selecionado;
	private AnimalDAO dao;
	private ConectaMySQL conn;

    public CadastroPet() {
        super("Cadastro Pet - Happy Paws");
        this.dao = new AnimalDAO(conn.conectaBD());
        
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10 ,10));
        getContentPane().setBackground(new Color(163, 196, 104));
        this.setIconImage(new ImageIcon(getClass().getResource("/img/Logo.png")).getImage());

        JLabel lbNome = new JLabel("Nome:");
        tfNomeAnimal = new JTextField();
        JLabel lbSexo = new JLabel("Sexo:");
        cbSexo = new JComboBox<>(new String[]{"M", "F"});
        JLabel lbIdade = new JLabel("Idade:");
        tfIdadeAnimal = new JTextField();
        JLabel lbEspecie = new JLabel("Espécie:");
        tfEspecieAnimal = new JTextField();
        JLabel lbRaca = new JLabel("Raça:");
        tfRacaAnimal = new JTextField();
        JLabel lblFoto = new JLabel("Foto:");
        jlFoto = new JLabel();
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(90, 108, 55));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(new Color(90, 108, 55));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        
        //Config da foto
        jlFoto.setBounds(331, 11, 75, 77);
        jlFoto.setOpaque(true);
		jlFoto.setBackground(Color.LIGHT_GRAY);
		chooser = new JFileChooser();
		imageFilter = new FileNameExtensionFilter("Imagens", ImageIO.getReaderFileSuffixes());
		
		jlFoto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				if(e.getClickCount() == 2) {
				chooser.setFileFilter(imageFilter);
				if (chooser.showOpenDialog(CadastroPet.this) == JFileChooser.APPROVE_OPTION){
					selecionado = chooser.getSelectedFile();
					try {
						BufferedImage bufImg = ImageIO.read(selecionado);
						Image imagem = bufImg.getScaledInstance(jlFoto.getWidth(), jlFoto.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon imgLabel = new ImageIcon(imagem);
						jlFoto.setIcon(imgLabel);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			}
		});
        
        add(lbNome);
        add(tfNomeAnimal);
        add(lbSexo);
        add(cbSexo);
        add(lbIdade);
        add(tfIdadeAnimal);
        add(lbEspecie);
        add(tfEspecieAnimal);
        add(lbRaca);
        add(tfRacaAnimal);
        add(lblFoto);
        add(jlFoto);
        add(btnCancelar); 
        add(btnCadastrar);

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    
                  
                    if (tfNomeAnimal.getText().trim().isEmpty() ||tfEspecieAnimal.getText().trim().isEmpty() || tfRacaAnimal.getText().trim().isEmpty()) {
                        
                    	JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    	
                    } else {
                        Animal animal = new Animal();
                        
                        String nome = tfNomeAnimal.getText();
                        char sexo = cbSexo.getSelectedItem().toString().charAt(0);
                        int idade = Integer.parseInt(tfIdadeAnimal.getText());
                        String especie = tfEspecieAnimal.getText();
                        String raca = tfRacaAnimal.getText();
                        
                        animal.setNome(nome);
                        animal.setSexo(sexo);
                        animal.setIdade(idade);
                        animal.setEspecie(especie);
                        animal.setRaca(raca);
                        
                        try {
    						if (selecionado != null) {
    							byte[] imagemBytes;
    							imagemBytes = Files.readAllBytes(selecionado.toPath());
    							animal.setImgAnimal(imagemBytes);
    							dao.create(animal);
    							JOptionPane.showMessageDialog(null, "Cadastro de animal realizado com sucesso!");
    							
    	                        CadastroPet.this.setVisible(false);
    	                        dispose();
    						} else {
    							JOptionPane.showMessageDialog(CadastroPet.this, "Insira uma foto!");
    						}
    					} catch (Exception e2) {
    						JOptionPane.showMessageDialog(null, e2);
    					}
                        
                        
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Idade deve ser numérico.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroPet.this.setVisible(false);
				dispose();
				
			}
		});
        
        
    }  
    
}

