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
import java.io.ByteArrayOutputStream;
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

public class EditarPet extends JFrame {
    private JTextField tfNomeAnimal, tfIdadeAnimal,tfEspecieAnimal, tfRacaAnimal ;
    private JComboBox<String> cbSexo;
    private JLabel jlFoto;
    private JButton btnAlterar, btnCancelar;
    private JFileChooser chooser;
	private FileNameExtensionFilter imageFilter;
	private File selecionado;
	private AnimalDAO dao;
	private ConectaMySQL conn;

    public EditarPet(int idAnimal) {
        super("Editar Pet - Happy Paws");
        this.dao = new AnimalDAO(conn.conectaBD());
        
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));
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
        //JLabel lblFoto = new JLabel("Foto");
        //jlFoto = new JLabel();
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(90, 108, 55));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnAlterar = new JButton("Alterar");
        btnAlterar.setBackground(new Color(90, 108, 55));
        btnAlterar.setForeground(Color.WHITE);
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 14));
        
        
        /*
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
				if (chooser.showOpenDialog(EditarPet.this) == JFileChooser.APPROVE_OPTION){
					selecionado = chooser.getSelectedFile();
					try {
						BufferedImage bufImg = ImageIO.read(selecionado);
						Image imagem = bufImg.getScaledInstance(jlFoto.getWidth(), jlFoto.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon imgLabel = new ImageIcon(imagem);
						jlFoto.setIcon(imgLabel);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			}
		}); */
        
		Animal animal;
		try {
		    animal = dao.read().stream().filter(a -> a.getIdAnimal() == idAnimal).findFirst().orElse(null);  
		    if (animal != null) {
		        tfNomeAnimal.setText(animal.getNome());
		        cbSexo.setSelectedItem(String.valueOf(animal.getSexo())); 
		        tfIdadeAnimal.setText(String.valueOf(animal.getIdade()));
		        tfEspecieAnimal.setText(animal.getEspecie());
		        tfRacaAnimal.setText(animal.getRaca());
		        /*
		        if (animal.getImgAnimal() != null) {
		            ImageIcon img = new ImageIcon(animal.getImgAnimal());
		            jlFoto.setIcon(img);
		        } else {
		        	
		        }
		        */
		    }

		} catch (SQLException e) {
		    JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		
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
        //add(lblFoto);
        //add(jlFoto);
        add(btnCancelar); 
        add(btnAlterar);

        btnAlterar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String nomeAnimal = tfNomeAnimal.getText();
                String sexo = (String) cbSexo.getSelectedItem();
                String idade = tfIdadeAnimal.getText();
                String especie = tfEspecieAnimal.getText();
                String raca = tfRacaAnimal.getText();

                if (nomeAnimal.isEmpty() || idade.isEmpty() || especie.isEmpty() || raca.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                int idadeInt;
                try {
                    idadeInt = Integer.parseInt(idade);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Idade deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Animal animal = new Animal();
                animal.setNome(nomeAnimal);
                animal.setSexo(sexo.charAt(0));
                animal.setIdade(idadeInt);
                animal.setEspecie(especie);
                animal.setRaca(raca);
                /*if (selecionado != null) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(selecionado);
                        ByteArrayOutputStream imgByte = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "jpg", imgByte);
                        animal.setImgAnimal(imgByte.toByteArray());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar a foto do animal.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
*/
                try {
                    dao.update(animal, idAnimal); 
                    JOptionPane.showMessageDialog(null, "Dados alterados com sucesso");
                    EditarPet.this.setVisible(false);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar os dados do animal: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
     
        }});
        
        btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EditarPet.this.setVisible(false);
				dispose();
				
			}
		});
        
        
    }
    
}


