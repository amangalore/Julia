//===============================================//
//  Author:      Aman Mangalore, Daniel Chen     //
//  Date:        2/28/14           				 //
//  Program:     Prog 4 assignment               //
//  Description: Use multiple classes to 		 //
//				 create an image that represents //
// 				 the z^2 + c components of 		 //
//				 the Julia Set					 //
//===============================================//
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JFrame;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import java.lang.Math;
public class Julia {
	
	 public static double cReal;
	 public static  double cImage;
	 public static double xMin;
	 public static double xMax;
	 public static double yMin;
	 public static double yMax;
	 public static double zReal;
	 public static double zImaginary;
	 public static int width    = 512;
	 public static int height   = 512;
	 public static int imgsize = 512;
	 public  static char fname;
	 public static double square;
	 public static  double root;
	 public static Complex variable2;
	 public static Complex variable3;
	 public static Complex variable4;
	 public static int iterations;	
	 public static int num;
	 public static JFrame f;
	 public static String name1 = "";

	 // saves image
	public static void saveImage( BufferedImage img, File file ) throws IOException {

	        ImageWriter      writer = null;
	        java.util.Iterator iter = ImageIO.getImageWritersByFormatName("jpg");

	        if (iter.hasNext())
	            writer = (ImageWriter)iter.next();

	        ImageOutputStream ios = ImageIO.createImageOutputStream( file );
	        writer.setOutput(ios);

	        ImageWriteParam param = new JPEGImageWriteParam( java.util.Locale.getDefault() );
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
	        param.setCompressionQuality(0.98f);

	        writer.write(null, new IIOImage( img, null, null ), param);

	    }
	
	//place method for converting iterations to color
	public static int getHSBColor(int idx)
    {
        return Color.getHSBColor((float)(idx/255.0), 1.0f, 1.0f).getRGB();
    }
	//method for outputting an image
	public static BufferedImage julia()
	{
		final BufferedImage  outImage = new BufferedImage( width, height, BufferedImage.TYPE_3BYTE_BGR );
	    
	    Scanner scan = new Scanner(System.in);
	    System.out.print( "Input: ");
		cReal = scan.nextDouble ();
		cImage = scan.nextDouble ();
		xMin = scan.nextDouble();
		xMax = scan.nextDouble();
		yMin = scan.nextDouble();
		yMax = scan.nextDouble();
		name1 = scan.nextLine();
		//for loop for every pixel in 512x512 image
		Complex C = new Complex(cReal, cImage);
		for(int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++ )
			{	//changes pixel to lie between -1 and 1
				zReal = xMin + i*(xMax - xMin)/512;
				zImaginary = yMin + j*(yMax - yMin)/512;
				Complex Z = new Complex(zReal, zImaginary);
				iterations = 1;
				Complex var1 = Z;
				//check if the magnitude of z(n+1) crosses 2 between 0 and 256
				
				for (int k = 0; k < 256; k++){
					
					var1 = var1.multiply(var1);
					Complex var2 = C;
					Complex var3 = new Complex(0.0,0.0);
					var3 = var3.add(var1);
					var3 = var3.add(var2);
					double mag1 = var3.magnitude();
					//record the amount of iterations it took for magnitude to hit 2
					if(mag1 > 2 || k == 255)
					{
						iterations  = k;
						break;
					}
					else
					{
						var1 = var3;
					}
					
				}
				// map the iterations count to color
				outImage.setRGB(i, j, getHSBColor(iterations));	
			}
		}
		return outImage;
	}
	
	//print out the image
	public static void main(String[] args) throws IOException {
		
		saveImage( julia(),  new File( name1));
	  }
	    
}		
//create class for complex numbers
class Complex {
	double real;
	double imaginary;
	
	//constructor for complex object
	Complex(double newReal, double newImaginary)
	{
		real = newReal;
		imaginary = newImaginary;
	}
	//method to find the magnitude of object
	public double magnitude()
	{
		double mag = 0;
		// do math to find magnitude using real and imaginary components.
		double square = (this.real*this.real) + (this.imaginary*this.imaginary);
	    double root = Math.sqrt(square);
		mag = root;
		return mag;
	}
	//method to multiply the complex object
	public Complex multiply(Complex toMultiply) {
		double xreal = (this.real * toMultiply.real) - (this.imaginary * toMultiply.imaginary);
		double ximaginary = (this.imaginary * toMultiply.real) + (this.real * toMultiply.imaginary);
		Complex total = new Complex(xreal, ximaginary);
		return total;
		
		//finish this code
		
	}
	//method to add the complex object
	public Complex add(Complex toAdd)
	{
		double realAddComponent;
		double imaginaryAddComponent;
		realAddComponent = this.real + toAdd.real;
		imaginaryAddComponent = this.imaginary + toAdd.imaginary;
		
		Complex added = new Complex(realAddComponent, imaginaryAddComponent);
		return added;
	}	
}





