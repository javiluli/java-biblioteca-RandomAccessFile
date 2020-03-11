import java.io.File;
import java.io.IOException;

public class Archivos {

	public static void CrearFichero() throws IOException {
		File f = new File(Const.FLIBROS);
		if (!f.exists())
			f.createNewFile();
	}
}
