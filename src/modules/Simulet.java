package modules;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class Simulet extends JPanel {

	
	protected ArrayList<ImageIcon> images;
	protected ImageIcon currentImage;
	private String nameOfSimulet;

	public Simulet(String nameOfSimulet)
	{
		this.nameOfSimulet=nameOfSimulet;
		loadPictures();
		setCurrentImage();
	}

	public String getNameOfSimulet()
	{
		return nameOfSimulet;
	}

	private void setCurrentImage() 
	{
		currentImage = images.get(0);
		Dimension dimension = new Dimension(currentImage.getIconWidth(), currentImage.getIconHeight());
		setPreferredSize(dimension);
	}

	private void loadPictures()
	{
		images =  new ArrayList<>();
		File directory = new File("pictures/"+nameOfSimulet);
		if(directory.isDirectory())
		{
			File[] files = directory.listFiles();
			for(File file: files)
			{
					ImageIcon icon = new ImageIcon(file.getAbsolutePath());
					images.add(icon);
			}
		}
	}
	public ArrayList<ImageIcon> getImages()
	{
		return images;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(currentImage.getImage(), 0, 0, this);
	}
}
