
/**
 *
 * @author Javier Delgado Rodriguez
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

// TODO: Auto-generated Javadoc
/**
 * The Class Backup.
 */
public class Backup {

	/**
	 * Reemplazo fich.
	 *
	 * @param FDATOS    the fdatos
	 * @param FDATOSAUX the fdatosaux
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void reemplazoFich(String FDATOS, String FDATOSAUX) throws IOException {
		File datos = new File(FDATOS);
		datos.delete();
		File datosAux = new File(FDATOSAUX);
		datosAux.renameTo(datos);
	}

	/**
	 * Backup libro.
	 *
	 * @param FDATOS    the fdatos
	 * @param FDATOSAUX the fdatosaux
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void backupLibro(String FDATOS, String FDATOSAUX) throws IOException {
		RandomAccessFile faux = new RandomAccessFile(Const.FLIBROSBACKUP, "rw");

		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(l.getLongitudRegistroLibro()); // f.length();

			while (hayDatos) {
				l.escribir(faux);
				hayDatos = l.leer(f);
			}

			f.close();
			faux.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: backupLibro");
	}

	/**
	 * Backup usuario.
	 *
	 * @param FDATOS    the fdatos
	 * @param FDATOSAUX the fdatosaux
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void backupUsuario(String FDATOS, String FDATOSAUX) throws IOException {

		RandomAccessFile faux = new RandomAccessFile(Const.FUSUARIOSBACKUP, "rw");

		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);
			f.seek(u.getlongitugRegistroUsuario()); // f.length();

			while (hayDatos) {
				u.escribir(faux);
				hayDatos = u.leer(f);
			}

			f.close();
			faux.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: backupLibro");
	}
}
