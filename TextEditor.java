import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
/*
 * bug belom kelar pas udh ada kalimat di dlm dan pas mau buka qfile baru, file baru ikut font dan font size
 * 
 * FIX font, color, font size ikut keganti saat open file
 * 
 * UDH ADD fitur word COUNT
 * */
public class TextEditor extends JFrame implements ActionListener { //actionlistener buat klo mau ad action dan mau kluar value 
	
	JTextArea textArea; //buat tempat nulisny
	JScrollPane scrollPane; //tempat scroll pin
	JSpinner fontSizeSpinner; //bkin scroll pinnya
	JLabel fontLabel, wordCount; //buat label ex:tulis text
	JButton fontColorButton, wordCountButton; //buat bkin button
	JComboBox fontBox; //fontboxnya
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	
 	TextEditor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Simple Text Editor");
		this.setSize(600,600);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null); //biar pas ngeload muncul ditengah
		
		textArea = new JTextArea();

		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN,20));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fontLabel = new JLabel("Font: ");
		
		
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int)fontSizeSpinner.getValue()));
			}
			
		});
		
		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//ambil font di java	
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");//select initial value
		
		wordCountButton = new JButton("Word");
		wordCountButton.addActionListener(this);
		
		wordCount = new JLabel("Word count: ");
		
		
		//------------ menubar ------------
		
			menuBar = new JMenuBar();
			fileMenu = new JMenu("File");
			openItem = new JMenuItem("Open");
			saveItem = new JMenuItem("Save");
			exitItem = new JMenuItem("Exit");
			
			openItem.addActionListener(this);
			saveItem.addActionListener(this);
			exitItem.addActionListener(this);
		
			fileMenu.add(openItem);
			fileMenu.add(saveItem);
			fileMenu.add(exitItem);
			menuBar.add(fileMenu);

		
		
		//------------ menubar ------------
		
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.add(wordCountButton);
		this.add(wordCount);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == fontColorButton) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
			
			textArea.setForeground(color);
			
		}
		
		if(e.getSource() == fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize()));
		}
		
		if(e.getSource() == exitItem) {
			JFrame frame = new JFrame("Exit");
			
			if(JOptionPane.showConfirmDialog(frame, "Confirm you want to exit?", "Simple text editor",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
			{
				System.exit(0);
			}
		}
		String text = textArea.getText();
		if(e.getSource() == wordCountButton) {
			
			String words[]=text.split("\\s+");
			Integer wordLength = words.length;
			String wordLengthStr = wordLength.toString();
			wordCount.setText("Word count: "+ wordLengthStr);
			
			
		}
		
		if(e.getSource() == saveItem) {
			JFileChooser jfc = new JFileChooser();
			jfc.setCurrentDirectory(new File("C:\\Users\\tjitr\\OneDrive\\Desktop"));//buat tempat default save file
			
			int response = jfc.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				PrintWriter fileOut = null;
				File file;
				
				file = new File(jfc.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter (file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
					
				}
				
			}
			
		}
		
		if(e.getSource() == openItem) {
			textArea.setText(null);

			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("C:\\Users\\tjitr\\OneDrive\\Desktop"));
			
			//optional (to search certain file extension)
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
			fc.setFileFilter(filter);
			//optional
			
			int response = fc.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fc.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+ "\n";
							textArea.append(line);
						}
						
						
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
				
			}
		}
		
		
		
		
	}
	
}
