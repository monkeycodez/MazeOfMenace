package render.io;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class MMWindow{

	private boolean inited = false;

	private JFrame win = null;

	private JPanel panel = null;

	private BufferedImage disp, buf;

	private Graphics2D gbuf;

	public MMWindow() {
	}

	public void init(){
		if(inited){
			return;
		}
		inited = true;
		try{
			SwingUtilities.invokeAndWait(new Runnable(){

				@Override
				public void run(){
					_init();
				}
			});
		}catch(InterruptedException | InvocationTargetException e){
			e.printStackTrace();
			return;
		}
		Dimension d = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getMaximumWindowBounds().getSize();

		disp = new BufferedImage(
			d.width,
			d.height,
			BufferedImage.TYPE_INT_ARGB);
		buf = new BufferedImage(
			d.width,
			d.height,
			BufferedImage.TYPE_INT_ARGB);
	}

	private void _init(){

		//Startup window
		win = new JFrame("The Maze of Menace");
		win.setExtendedState(Frame.MAXIMIZED_BOTH);
		win.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		win.setLayout(new BorderLayout());
		panel = new MWPanel();
		win.add(panel, BorderLayout.CENTER);
		panel.setMinimumSize(new Dimension(640, 480));
		win.pack();
		win.setVisible(true);

	}

	private class MWPanel extends JPanel{

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent( Graphics g ){
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

}