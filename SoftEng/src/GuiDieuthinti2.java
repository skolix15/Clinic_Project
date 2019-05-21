import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class GuiDieuthinti2 extends JFrame{
		JButton b1 =new JButton("Εφοδιασιτκή Αλυσίδα");
		JButton b2 =new JButton("Εφημερίες - Βάρδιες");

		JPanel D_Panel;
					 
		public GuiDieuthinti2() {
			D_Panel = new JPanel();
			D_Panel.add(b1);
			D_Panel.add(b2);
			
			ButtonListener listener = new ButtonListener();
			b1.addActionListener(listener);
			b2.addActionListener(listener);
			

			ImageIcon icon = new ImageIcon("hospital1.png");
			JLabel lb = new JLabel(icon);
			D_Panel.add(lb);
			
			lb.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
			    {	 
			    	setVisible(false);
			        new BasicGUI();           
			    }
			});
			
			this.setContentPane(D_Panel);
			this.setVisible(true);
			this.setSize(400, 300);
			this.setTitle("Διευθυντής");
						 
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		
		
		class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
				//Κουμπί: Εφοδιαστική Αλυσίδα
				if(e.getSource() == b1) {
					setVisible(false);

					//  GUI για όταν πατιέται το κουμπί εφοδιαστικής αλυσίδας
				}
				//Κουμπί: Εφημερίες-Βάρδιες
				else if(e.getSource() == b2){
					setVisible(false);

					new GuiEfimeries();
				}
				
			}
		}
		
		
	}

