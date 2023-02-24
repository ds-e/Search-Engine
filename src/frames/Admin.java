package frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Fichier;
import model.IndexDoc;
import model.ListMotsFreq;
import model.Main;
import model.MotFreq;

public class Admin extends JFrame implements ActionListener{

	private JTable table_List_DocIndex;
	private JTable List_MotsFreq;
	private JPanel contentPane;
    protected IndexDoc indexDoc;
    protected ListMotsFreq listMotsFreq;
    protected JButton bt_AjoutDoc;
    private JScrollPane scrollPane;
    
    
	public Admin() {
		indexDoc = Main.listDoc;
		
		setBackground(new Color(238, 238, 238));
		setTitle("Administrateur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 802, 626);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 105, 401, 481);
		contentPane.add(scrollPane);
		
		table_List_DocIndex = new JTable();
		scrollPane.setViewportView(table_List_DocIndex);
		feedIndexTable(table_List_DocIndex, indexDoc);
		table_List_DocIndex.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				feedTable_MotsFreq();
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(477, 105, 270, 481);
		contentPane.add(scrollPane_1);
		
		List_MotsFreq = new JTable();
		List_MotsFreq.setEnabled(false);
		List_MotsFreq.setBounds(477, 105, 270, 481);
		scrollPane_1.setViewportView(List_MotsFreq);
		//contentPane.add(List_MotsFreq);
		
		JLabel mot_Associe_frec = new JLabel("Mot associé/Féquence");
		mot_Associe_frec.setBounds(477, 78, 173, 16);
		contentPane.add(mot_Associe_frec);
		
		JLabel lb_index = new JLabel("Index de document");
		lb_index.setBounds(16, 77, 173, 16);
		contentPane.add(lb_index);
		
		bt_AjoutDoc = new JButton("Mettre a jour la liste");
		bt_AjoutDoc.setBounds(16, 36, 233, 29);
		contentPane.add(bt_AjoutDoc);
		bt_AjoutDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Main.reload();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				indexDoc = Main.listDoc;
				feedIndexTable(table_List_DocIndex, indexDoc);
			}
		});
		
	}
	public IndexDoc getIndexDoc() {
		return indexDoc;
	}

	public void setIndexDoc(IndexDoc indexDoc) {
		this.indexDoc = indexDoc;
	}
	
	public static void feedIndexTable(JTable table, IndexDoc data) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setColumnCount(0);
		model.setRowCount(0);
		model.addColumn("Nom Du Doc");
     
		Object rowdata[] = new Object[2];
		for (int i=0; i< data.getIndexSize(); i++) {
			System.out.println("i =" + i);
			rowdata[0]=data.index.get(i).getName();
			model.addRow(rowdata);
		}
		table.setModel(model);
	}

	private void feedTable_MotsFreq() {
		Fichier temFichier = new Fichier();
		int row = table_List_DocIndex.getSelectedRow();
		temFichier = this.indexDoc.getByindex(row); 
		DefaultTableModel model = (DefaultTableModel) List_MotsFreq.getModel();
		model.setColumnCount(0);
		model.setRowCount(0);
		model.addColumn("Mot");
		model.addColumn("Frequence");
		
		Object rowdata[] = new Object[2];
		for(MotFreq mf:temFichier.token.getMotFreqList()) {
			rowdata[0]= mf.mot;
			rowdata[1]= mf.frequence;
			model.addRow(rowdata);
		}
		
		List_MotsFreq.setModel(model);
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
