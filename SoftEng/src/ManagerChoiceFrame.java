import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class ManagerChoiceFrame extends JFrame{
		private JButton s_chain =new JButton("Supply Chain");
		private JButton shift =new JButton("Shift Work");

		private JPanel D_Panel;
		
		private db conn;

					 
		public ManagerChoiceFrame(db connection) {
			conn=connection;
			D_Panel = new JPanel();
			D_Panel.add(s_chain);
			D_Panel.add(shift);
			
			ButtonListener listener = new ButtonListener();
			s_chain.addActionListener(listener);
			shift.addActionListener(listener);
			

			ImageIcon icon = new ImageIcon("hospital1.png");
			JLabel lb = new JLabel(icon);
			D_Panel.add(lb);
			
			lb.addMouseListener(new MouseAdapter() 
			{
				public void mouseClicked(MouseEvent e) 
			    {	 
			    	setVisible(false);
			        new GlobalHomeFrame(conn);           
			    }
			});
			
			this.setContentPane(D_Panel);
			this.setVisible(true);
			this.setSize(400, 300);
			this.setTitle("Manager");
						 
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		
		
		class ButtonListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				
				//Button: Supply Chain
				if(e.getSource() == s_chain) {
					setVisible(false);

					//  GUI για όταν πατιέται το κουμπί εφοδιαστικής αλυσίδας
				}
				//Button: Shift Work
				else if(e.getSource() == shift){
					setVisible(false);

					new ManagerHomePageFrame(conn);
				}
				
			}
		}
		
		
	}

