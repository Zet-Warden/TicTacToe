import javax.swing.ImageIcon;
import java.net.URL;

public class MyImage extends ImageIcon
{	
	public MyImage (URL path) throws NullPointerException
	{
		super (path);

		if (path == null) {
			System.err.println("ERROR - Couldn't find file: " + path + "\nGame might not be displayed properly");
			throw new NullPointerException ("Image File not Found! " + path + " is missing");
		}
	}

}