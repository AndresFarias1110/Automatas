import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon img;

	public PanelFondo(ImageIcon img) {
		// TODO Auto-generated constructor stub
		this.img = img;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(),
				null);
	}

}
