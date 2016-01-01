package window.widget;

import java.awt.*;

public class Label extends WComp{

	public static final int CENTERED = 0x01;

	private String text;

	private Font font;

	private Color bg, fg;

	private int txty = -1, txtx = -1;

	private int txtw = -1, txth = 0, tasc = -1;

	public Label(String text, Font font, Rectangle box, Color fg, Color bg) {
		super(box);
		this.text = text;
		this.font = font;
		this.bg = bg;
		this.fg = fg;

	}

	public Label(String text, Font font, FontMetrics mets) {
		this.text = text;
		this.font = font;
		txtw = mets.stringWidth(text);
		txth = mets.getHeight();
		tasc = mets.getAscent();
	}

	@Override
	public void drawComp(Graphics2D gd){
		gd.setFont(font);
		gd.setColor(bg);
		gd.fill(gd.getClip());
		if(txtx == -1 || txty == -1){
			if(txtw == -1){
				FontMetrics mets = gd.getFontMetrics(font);
				txtw = mets.stringWidth(text);
				txth = mets.getHeight();
				tasc = mets.getAscent();
			}
			Rectangle bdns = super.get_bounds();
			txtx = bdns.width / 2 - txtw / 2;
			txty = bdns.height / 2 + tasc / 2;
		}
		gd.setColor(fg);
		gd.drawString(text, txtx, txty);
	}

	public void set_bg(Color bg){
		this.bg = bg;
	}

	@Override
	public void set_bounds(Rectangle r){
		super.set_bounds(r);
		txtx = -1;
		txty = -1;
	}

	@Override
	public Dimension get_pref_min_size(){
		return new Dimension(txtw, txth);
	}
}
