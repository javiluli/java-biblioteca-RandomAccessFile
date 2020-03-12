
/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.File;
import java.io.IOException;

public class Archivos {

	/**
	 * Crear fichero. Creara un fichero llamado FLIBROS en el caso de que este no
	 * exista
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void CrearFichero() throws IOException {
		File f = new File(Const.FLIBROS);
		if (!f.exists())
			f.createNewFile();
	}
}
