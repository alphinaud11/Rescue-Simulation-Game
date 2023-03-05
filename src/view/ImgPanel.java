package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImgPanel extends JPanel {

	  private Image img;
	  


	public Image getImg() {
		return img;
	}


	public ImgPanel(String img) {
	    this(new ImageIcon(img).getImage());
	  }

	  public ImgPanel(Image img) {
	    this.img = img;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
	    
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    setSize(size);
	    setLayout(null);
	  }

	  public void paintComponent(Graphics g) {
	    g.drawImage(img, 0, 0, null);
	  }
	  
	  public void setImage(ImageIcon img) {
		    this.img = img.getImage();
		    repaint();
		}

}
