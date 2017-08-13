import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

/**
 * Sticky Corners for Windows 7
 * @author Jayden Weaver
 *
 */
public class StickyCorners {
	//Create a System Tray
	static SystemTray tray;
	//Create frame
	static JFrame frame = new JFrame("Sticky Corners");

	public static void main(String args[]){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			//do nothing...there's no point...
		}


		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				frame.setVisible(false);
				hideToSystemTray();
			}
		});

		//Panel 1 Content
		JPanel panel1 = new JPanel();
		JLabel settingsComingSoon = new JLabel("Settings coming soon...");
		panel1.add(settingsComingSoon);

		//Panel 2 Content
		JPanel panel2 = new JPanel();

		//Panel 2 Layout
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		//Author
		JLabel author = new JLabel("Jayden Weaver");

		//Year
		JLabel year = new JLabel("2017");

		/*
		 * Start Twitter Label
		 */
		JLabel twitter = new JLabel("@WeaverFever69");
		twitter.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				try{
					Desktop.getDesktop().browse(new URI("http://www.twitter.com/weaverfever69")); //twitter account
				}
				catch(Exception ex){
					System.err.println(ex);
				}
			}});
		/*
		 * End Twitter Label
		 */

		/*
		 * Start Github Label
		 */
		JLabel github = new JLabel("github.com/jayden2013");
		github.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				try{
					Desktop.getDesktop().browse(new URI("http://www.github.com/jayden2013")); //github account
				}
				catch(Exception ex){
					System.err.println(ex);
				}
			}			
		});
		/*
		 * End Github Label
		 */		

		//Add all labels
		panel2.add(author);
		panel2.add(twitter);
		panel2.add(github);
		panel2.add(year);

		//Create Tabbed Pane
		JTabbedPane tabs = new JTabbedPane();

		//Add Everything to Tabbed Pane
		tabs.add("Settings", panel1);
		tabs.add("About", panel2);
		frame.add(tabs);

		try {
			frame.setVisible(true);
			Robot robot = new Robot(); //beep boop
			Point p;
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = (int) screenSize.getWidth();			
			while(true){
				p = MouseInfo.getPointerInfo().getLocation();
				if (p.getX() >= width && p.getY() <= 20){
					robot.mouseMove(width - 10, 5);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	/**
	 * Hides program in System Tray.
	 */
	private static void hideToSystemTray(){
		tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("icon.jpg");
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem = new MenuItem("Close");
		defaultItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
				System.exit(0);
			}
		});
		popup.add(defaultItem);

		defaultItem = new MenuItem("Open");
		defaultItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setVisible(true);
				frame.setExtendedState(JFrame.NORMAL);
			}
		});
		popup.add(defaultItem);

		TrayIcon trayIcon = new TrayIcon(image, "Sticky Corners", popup);
		trayIcon.setImageAutoSize(true);
		try {
			tray.add(trayIcon);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}
}
