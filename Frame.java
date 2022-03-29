import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Frame extends JFrame {

	int[][] rCords;
	int[][] gCords;
	int[][] bCords;

	int xSize;
	int ySize;

	String fileName;

	public Frame(int[][] rCords, int[][] gCords, int[][] bCords, int xSize, int ySize, String fileName) {
		super("Frame"); // window name
		this.rCords = rCords;
		this.gCords = gCords;
		this.bCords = bCords;

		this.xSize = xSize;
		this.ySize = ySize;

		this.fileName = fileName;

		// int[][] cords
		// first array is rows
		// second array is columns
		// ie an array of rows of columns
		setSize(rCords[0].length, rCords.length);// (x, y) or (columns, rows)

		// FrameSettings
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g0) {

		BufferedImage img = new BufferedImage(rCords[0].length, rCords.length, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(new Color(255, 0, 0));
		g.drawRect(100, 100, 0, 0);
		// System.out.println(rCords.length);
		// System.out.println(rCords[0].length);

		for (int i = 0; i <= rCords.length - 1; i++) {
			for (int j = 0; j <= rCords[0].length - 1; j++) {
				g.setColor(new Color(rCords[ySize - 1 - i][j], gCords[ySize - 1 - i][j], bCords[ySize - 1 - i][j]));
				g.drawRect(j, i, 0, 0);
			}
		}
//		g.setColor(new Color(0, 0, 255));
//		g.drawRect(100 + 1, 100 + 1, 0, 0);
//		for (int i = 0; i <= 200; i++) {
//			g.drawRect(50 + i, i, 0, 0);
//		}
		// g0.drawImage(img, rCords[0].length, rCords.length, this);
		g0.drawImage(img, 0, 35, this);

		File outputfile = new File(fileName + ".png");
		try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}