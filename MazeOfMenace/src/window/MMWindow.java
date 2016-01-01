package window;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

public class MMWindow{

	private boolean inited = false;

	private JFrame win = null;

	private JPanel panel = null;

	private BufferedImage disp, buf;

	private Graphics2D gbuf;

	private KeyEvent laste = null;

	private boolean close;

	public MMWindow() {
	}

	public void init(){
		if(inited){
			return;
		}
		inited = true;
		try{
			SwingUtilities.invokeAndWait(()-> _init());
			Thread.sleep(300);
		}catch(InterruptedException | InvocationTargetException e){
			e.printStackTrace();
			return;
		}
		Dimension d = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getMaximumWindowBounds().getSize();

		disp = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
		buf = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
	}

	private void _init(){

		//Startup window
		win = new JFrame("The Maze of Menace");
		win.setExtendedState(Frame.MAXIMIZED_BOTH);
		win.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		win.setLayout(new BorderLayout());
		panel = new MWPanel();
		win.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent e){
				close = true;
			}
		});
		win.addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e){

			}

			@Override
			public void keyReleased(KeyEvent e){
			}

			@Override
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_END){
					close = true;
				}
				laste = e;
			}
		});
		win.add(panel, BorderLayout.CENTER);
		try{
			UIManager.setLookAndFeel(UIManager
				.getCrossPlatformLookAndFeelClassName());
		}catch(Exception e1){
			e1.printStackTrace();
			System.err.println("Cannot initalize look and feel");
		}
		panel.setMinimumSize(new Dimension(640, 480));
		win.pack();
		win.setVisible(true);

	}

	private class MWPanel extends JPanel{

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g){
			g.drawImage(disp, 0, 0, null);
		}
	}

	public Graphics2D get_graphics(){
		gbuf = buf.createGraphics();
		return gbuf;
	}

	public Dimension get_size(){
		return panel.getSize();
	}

	public void swap(){
		gbuf.dispose();
		BufferedImage tmp = disp;
		disp = buf;
		buf = tmp;
		win.repaint();
	}

	public KeyEvent get_last_event(){
		KeyEvent k = laste;
		laste = null;
		return k;
	}

	public boolean is_close_requested(){
		return close;
	}

	public FontMetrics get_metrics(Font fnt){
		return win.getFontMetrics(fnt);
	}

}
