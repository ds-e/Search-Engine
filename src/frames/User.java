package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import library.Commun;
import library.TextPaneHighlighter;
import model.Fichier;
import model.IndexDoc;
import model.Main;
import model.Repertoir;

public class User extends JFrame implements ActionListener{

	protected IndexDoc indexdoc;
	protected JPanel contentPane;
	protected JTable tb_ResulRech;
	JTextPane txt_pane = new JTextPane();
	protected JTextField tf_recherche_Input;
	TextPaneHighlighter h = new TextPaneHighlighter();
	public Repertoir userRepertoir;
	public static ArrayList<Integer[]> listDoc;
	public static ArrayList<Fichier> listFichier;
	public List<String> stopword;
	
	public User() {
		stopword = Commun.stopwords;
		this.userRepertoir = Main.repertoir;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(00, 100, 802, 626);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 143, 318, 442);
		contentPane.add(scrollPane_1);

		tb_ResulRech = new JTable();
		tb_ResulRech.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String fullText = User.listFichier.get(tb_ResulRech.getSelectedRow()).fullText;
				txt_pane.setText(fullText);
				if(tf_recherche_Input.getText().length()!=0)
					try {
						h.highlightall(txt_pane, Commun.creerTokens(tf_recherche_Input.getText()));
					} catch (IOException e1) {
						e1.printStackTrace();
					}	
			}
		});
		scrollPane_1.setViewportView(tb_ResulRech);
		
		tf_recherche_Input = new JTextField();
		tf_recherche_Input.setBounds(21, 38, 318, 26);
		contentPane.add(tf_recherche_Input);
		tf_recherche_Input.setColumns(10);

		JLabel lb_lancerRech = new JLabel("Resultat de la recherche");
		lb_lancerRech.setBounds(21, 115, 155, 16);
		contentPane.add(lb_lancerRech);

		JButton btn_lancer_recherche = new JButton("Lancer une recherche");
		btn_lancer_recherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] stringAChercher = {""};
				ArrayList<Integer []> listVide = new ArrayList<Integer []>();
				feedIndexTable(tb_ResulRech, listVide);
				
				try {
					stringAChercher = tokenize();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				User.listDoc = userRepertoir.indexMots.rechercheParPhrase(stringAChercher);
				if(tf_recherche_Input.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "Ajoutez une phrase a chercher");
				} else { 
					if(User.listDoc.size() ==0) 
						{
						System.out.println("la liste est vide");
						JOptionPane.showMessageDialog(null, "Aucun doc contient l'ensemble des mots de la recherche");
						tb_ResulRech.setModel(new DefaultTableModel());
						txt_pane.setText(null);

						}
					else 
						{
						if (Commun.isStopword(tf_recherche_Input.getText())){
							JOptionPane.showMessageDialog(null, "Vous avez entré un stopword");
							tb_ResulRech.setModel(new DefaultTableModel());
							txt_pane.setText(null);

						}
						else {
							feedIndexTable(tb_ResulRech, User.listDoc);
							h.highlightall(txt_pane, stringAChercher);
							}
						}
				}
			}
		});
		btn_lancer_recherche.setBounds(21, 76, 318, 26);
		contentPane.add(btn_lancer_recherche);

		JScrollPane scrollPane = new JScrollPane(txt_pane);
		scrollPane.setEnabled(false);
		scrollPane.setBounds(365, 43, 417, 542);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(txt_pane);

	}

	public static void TextFromFile(JTextPane tp, String path) 
	{
		try {
			String textLine = "";
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new FileReader(file));

			while ((textLine = reader.readLine()) != null) {
				tp.read(reader, null);
				textLine = reader.readLine();
			}
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String[] tokenize() throws IOException {
		String recherche = tf_recherche_Input.getText();
		 String[] userText = Commun.creerTokens(recherche);
		return userText;
	}

	public static boolean match(String[] tab1, String[] tab2) {
		java.util.List<String> list2 = Arrays.asList(tab2);
		for (int i = 0; i < tab1.length; i++) {
			if (!(list2.contains(tab1[i]))) {
				System.out.println("Aucun resultat");
				return false;
			}
		}
		return true;
	}
	
	public void feedIndexTable(JTable table, ArrayList<Integer []> lastListe) {
		User.listFichier = userRepertoir.indexMots.trouverFichierParId(lastListe);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnCount(0);
		model.setRowCount(0);
		model.addColumn("NomDuDoc");
		model.addColumn("Freq");
		Object rowdata[] = new Object[2];
		for (int i=0; i< lastListe.size(); i++) {
			System.out.println("i =" + i);
			rowdata[0]=User.listFichier.get(i).getName();
			rowdata[1]=lastListe.get(i)[1];
			model.addRow(rowdata);
		}
		table.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
