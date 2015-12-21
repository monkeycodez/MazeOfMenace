package window.widget;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Label{

	public static final int CENTERED = 0x01;

	private String text;

	private Font font;

	private Rectangle box;

	private Color bg, fg;

	private int txty = -1, txtx = -1;

	public Label(String text, Font font, Rectangle box, Color fg, Color bg) {
		this.text = text;
		this.font = font;
		this.box = box;
		this.bg = bg;
		this.fg = fg;

	}

	public void draw( Graphics2D gd ){
		gd.setFont(font);
		if(txtx == -1 || txty == -1){
			FontMetrics fm = gd.getFontMetrics(font);
			int slen = fm.stringWidth(text);
			int ycen = box.y + box.height / 2;
			int xcen = box.x + box.width / 2;

			txtx = xcen - slen / 2;
			txty = ycen + fm.getAscent() / 2;
		}
		gd.setColor(bg);
		gd.fillRect(box.x, box.y, box.width, box.height);
		gd.setColor(fg);
		gd.drawString(text, txtx, txty);
	}

	public void set_bg( Color bg ){
		this.bg = bg;
	}

	public void set_box( Rectangle rect ){
		box = rect;
		txtx = -1;
		txty = -1;
	}

}
