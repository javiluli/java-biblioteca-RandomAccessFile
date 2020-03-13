
/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.File;
import java.io.IOException;

public class Archivos {

	/**
	 * Crear fichero. Creara un fichero llamado FLIBROS y FUSUARIOS en el caso de
	 * que alguno de estos no exista
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void CrearFichero() throws IOException {
		File f = new File(Const.FLIBROS);
		if (!f.exists())
			f.createNewFile();
		f = new File(Const.FUSUARIOS);
		if (!f.exists())
			f.createNewFile();
	}
}
