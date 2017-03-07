/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juanko
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fid.Fiv;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 5;
	private BufferedImage m_image;

	public void showImage(Fid image) {
		Fiv view = image.getViews()[0];
		m_image = new BufferedImage(view.getWidth(), view.getHeight(),
				BufferedImage.TYPE_BYTE_GRAY);
		m_image.getRaster().setDataElements(0, 0, view.getWidth(),
				view.getHeight(), view.getImageData());
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(m_image, 0, 0, null);
	}

}