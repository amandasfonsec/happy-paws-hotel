package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.AnimalDAO;
import dao.EstadiaDAO;
import model.Animal;
import model.Estadia;
import util.ConectaMySQL;
import util.SessaoUsuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;

public class Menu extends JFrame {
	private JButton btnEditarPerfil, btnSair, btnDeletar, btnCadastarPet, btnAtualizar;
	private JButton btnAlterar, btnExcluir, btnReserva, btnEditarPet;
	private JTable tabelaAnimais, tabelaEstadia;
	private JPanel panelAnimais, panelEstadia, panelCentral;
	private ConectaMySQL conn;
	private AnimalDAO animalDao;
	private EstadiaDAO estadiaDao;
	private int idAnimal, idEstadia;
	private int rowE = -1;
	private int rowA = -1;
	private String nomeAnimal;

	public Menu() {
		super("Happy Paws");
		animalDao = new AnimalDAO(conn.conectaBD());
		estadiaDao = new EstadiaDAO(conn.conectaBD());

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		this.setIconImage(new ImageIcon(getClass().getResource("/img/Logo.png")).getImage());

		btnEditarPerfil = new JButton("EDITAR PERFIL");
		btnEditarPerfil.setBackground(new Color(90, 108, 55));
		btnEditarPerfil.setForeground(Color.WHITE);
		btnEditarPerfil.setFont(new Font("Arial", Font.BOLD, 14));
		btnEditarPerfil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				EditarPerfilDono ed = new EditarPerfilDono();
				ed.setVisible(true);
			}
		});

		btnEditarPet = new JButton("EDITAR PET");
		btnEditarPet.setBackground(new Color(90, 108, 55));
		btnEditarPet.setForeground(Color.WHITE);
		btnEditarPet.setFont(new Font("Arial", Font.BOLD, 14));
		btnEditarPet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rowA == -1) {
					JOptionPane.showMessageDialog(Menu.this, "Selecione um pet para editar");
				} else {
				EditarPet edP = new EditarPet(idAnimal);
				edP.setVisible(true);
				}
			}
		});
		btnCadastarPet = new JButton("CADASTRAR PET");
		btnCadastarPet.setBackground(new Color(90, 108, 55));
		btnCadastarPet.setForeground(Color.WHITE);
		btnCadastarPet.setFont(new Font("Arial", Font.BOLD, 14));
		btnCadastarPet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CadastroPet cadastroPet = new CadastroPet();
				cadastroPet.setVisible(true);
			}
		});

		btnDeletar = new JButton("EXCLUIR PET");
		btnDeletar.setBackground(new Color(90, 108, 55));
		btnDeletar.setForeground(Color.WHITE);
		btnDeletar.setFont(new Font("Arial", Font.BOLD, 14));
		btnDeletar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rowA == -1) {
					JOptionPane.showMessageDialog(Menu.this, "Selecione um pet para excluir");
				} else {
					if (nomeAnimal != null) {
						int op = JOptionPane.showConfirmDialog(null, "Deseja excluir o pet " + nomeAnimal + "?");
						if (op == 0) {
							try {
								animalDao.delete(idAnimal);
								JOptionPane.showMessageDialog(null, "Pet excluído");
								carregarAnimais();
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						}
					} else {
						JOptionPane.showMessageDialog(Menu.this, "Erro ao acessar o nome do pet.");
					}
				}

			}
		});

		btnReserva = new JButton("FAZER RESERVA");
		btnReserva.setBackground(new Color(90, 108, 55));
		btnReserva.setForeground(Color.WHITE);
		btnReserva.setFont(new Font("Arial", Font.BOLD, 14));
		btnReserva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rowA == -1) {
					JOptionPane.showMessageDialog(Menu.this, "Selecione um pet para realizar a reserva!");
				} else {
					Reserva r = new Reserva(idAnimal);
					r.setVisible(true);
				}
				
			}
		});

		btnAtualizar = new JButton("ATUALIZAR");
		btnAtualizar.setBackground(new Color(90, 108, 55));
		btnAtualizar.setForeground(Color.WHITE);
		btnAtualizar.setFont(new Font("Arial", Font.BOLD, 14));
		btnAtualizar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				carregarAnimais();
				carregarEstadias();
			}
		});

		btnSair = new JButton("SAIR");
		btnSair.setBackground(new Color(90, 108, 55));
		btnSair.setForeground(Color.WHITE);
		btnSair.setFont(new Font("Arial", Font.BOLD, 14));
		btnSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SessaoUsuario.setIdUsuario(0);
				JOptionPane.showMessageDialog(Menu.this, "Você saiu da sua conta.");
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});

		btnAlterar = new JButton("EDITAR RESERVA");
		btnAlterar.setBackground(new Color(90, 108, 55));
		btnAlterar.setForeground(Color.WHITE);
		btnAlterar.setFont(new Font("Arial", Font.BOLD, 14));
		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rowE == -1) {
					JOptionPane.showMessageDialog(Menu.this, "Selecione uma reserva para ser editada!");
				} else {
					EditarReserva edR = new EditarReserva(idAnimal, idEstadia);
					edR.setVisible(true);
				}
				
			}
		});

		btnExcluir = new JButton("EXCLUIR RESERVA");
		btnExcluir.setBackground(new Color(90, 108, 55));
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
		btnExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rowE == -1) {
					JOptionPane.showMessageDialog(Menu.this, "Selecione uma reserva para ser cancelada!");
				} else {
					int op = JOptionPane.showConfirmDialog(null, "Deseja cancelar a reserva " + idEstadia + "?");
					if (op == 0) {
						try {
							estadiaDao.delete(idEstadia);
							JOptionPane.showMessageDialog(null, "Reserva cancelada");
							carregarAnimais();
							carregarEstadias();
						} catch (SQLException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}
				
			}
		});

		JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelAcoes.add(btnEditarPerfil);
		panelAcoes.add(btnEditarPet);
		panelAcoes.add(btnCadastarPet);
		panelAcoes.add(btnDeletar);
		panelAcoes.add(btnReserva);
		panelAcoes.add(btnAtualizar);
		panelAcoes.setBackground(new Color(163, 196, 104));

		JPanel panelSair = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelSair.add(btnSair);
		panelSair.setBackground(new Color(163, 196, 104));

		panelAnimais = new JPanel(new BorderLayout());
		panelAnimais.setBorder(BorderFactory.createTitledBorder("PETS"));
		panelAnimais.setBackground(new Color(163, 196, 104));

		tabelaAnimais = new JTable();
		tabelaAnimais.setPreferredScrollableViewportSize(new Dimension(700, 200));
		JScrollPane sp1 = new JScrollPane(tabelaAnimais);
		tabelaAnimais.getTableHeader().setBackground(new Color(32, 23, 11));
		tabelaAnimais.getTableHeader().setForeground(Color.WHITE);
		tabelaAnimais.setForeground(Color.BLACK);
		tabelaAnimais.setBackground(new Color(248, 248, 238));
		
		tabelaAnimais.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaAnimais.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				rowA = tabelaAnimais.getSelectedRow();
				if (rowA != -1) {
					idAnimal = (int) tabelaAnimais.getValueAt(rowA, 6);
					nomeAnimal = tabelaAnimais.getValueAt(rowA, 1).toString();
				}

			}
		});
		;

		JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBotoes.add(btnReserva);
		panelBotoes.add(btnAlterar);
		panelBotoes.add(btnExcluir);
		panelBotoes.setBackground(new Color(163, 196, 104));

		panelAnimais.add(sp1, BorderLayout.CENTER);
		panelAnimais.setPreferredSize(new Dimension(panelAnimais.getWidth(), 400));

		panelEstadia = new JPanel(new BorderLayout());
		panelEstadia.setBorder(BorderFactory.createTitledBorder("RESERVAS"));
		panelEstadia.setBackground(new Color(163, 196, 104));

		tabelaEstadia = new JTable();
		JScrollPane sp2 = new JScrollPane(tabelaEstadia);
		tabelaEstadia.getTableHeader().setBackground(new Color(32, 23, 11));
		tabelaEstadia.getTableHeader().setForeground(Color.WHITE);
		tabelaEstadia.setForeground(Color.BLACK);
		tabelaEstadia.setBackground(new Color(248, 248, 238));

		tabelaEstadia.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaEstadia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				rowE = tabelaEstadia.getSelectedRow();
				if (rowE != -1) {
					idEstadia = (int) tabelaEstadia.getValueAt(rowE, 0);
				}

			}
		});
		;
		panelEstadia.add(sp2, BorderLayout.CENTER);

		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.add(panelAnimais);
		panelCentral.add(panelBotoes);
		panelCentral.add(panelEstadia);

		setLayout(new BorderLayout());
		add(panelAcoes, BorderLayout.NORTH);
		add(panelCentral, BorderLayout.CENTER);
		add(panelSair, BorderLayout.PAGE_END);

		carregarAnimais();
		carregarEstadias();
	}

	public void carregarAnimais() {
		int idDono = SessaoUsuario.getIdUsuario();
		try {
			List<Animal> animais = animalDao.mostrarAnimais(idDono);
			DefaultTableModel model = new DefaultTableModel(
					new Object[] { "Foto", "Nome", "Sexo", "Idade", "Espécie", "Raça", "ID" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			for (Animal animal : animais) {
				ImageIcon img = null;
				if (animal.getImgAnimal() != null) {
					try {
						ByteArrayInputStream byteImg = new ByteArrayInputStream(animal.getImgAnimal());
						BufferedImage bufferedImage = ImageIO.read(byteImg);
						Image imagemRedimensionada = bufferedImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
						img = new ImageIcon(imagemRedimensionada);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				model.addRow(new Object[] { img, animal.getNome(), animal.getSexo(), animal.getIdade(),
						animal.getEspecie(), animal.getRaca(), animal.getIdAnimal() });
			}

			tabelaAnimais.setRowHeight(60);
			tabelaAnimais.setModel(model);

			tabelaAnimais.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					return new JLabel((ImageIcon) value);
				}
			});

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar animais: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void carregarEstadias() {
		int idDono = SessaoUsuario.getIdUsuario();
		try {
			List<Estadia> estadias = estadiaDao.getEstadiasPorDono(idDono);
			DefaultTableModel model = new DefaultTableModel(
					new Object[] { "ID Reserva", "ID Pet", "Data Entrada", "Data Saída", "OBS", "Valor Total" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};

			for (Estadia estadia : estadias) {
				model.addRow(new Object[] { estadia.getIdEstadia(), estadia.getIdAnimal(), estadia.getDataEntrada(),
						estadia.getDataSaida(), estadia.getObservacoes(), estadia.calcularValorTotal() });
			}

			tabelaEstadia.setModel(model);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar reservas: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
