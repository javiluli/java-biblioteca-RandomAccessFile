
/**
 *
 * @author Javier Delgado Rodriguez
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * The Class Backup.
 */
public class Backup {

	/**
	 * Reemplazo fich. Elimina el archivo antiguo de usuarios y en su lugar se
	 * renombra el auxiliar con el de usuarios eliminado
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
	 * Backup libro. Genera una copia del fichero como backup. Este metodo es
	 * llamado cuando se agrega un libro o un usuario.
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

//	public static void backupUsuario(String FDATOS, String FDATOSAUX) throws IOException {
//		MiObjectOutputStream oo = new MiObjectOutputStream(new FileOutputStream(FDATOSAUX));
//		MiObjectInputStream oi = null;
//		boolean b = false;
//
//		try {// comprueba que el archivo FLIBROS existe antes de leerlo
//			oi = new MiObjectInputStream(new FileInputStream(FDATOS));
//			b = true;
//		} catch (Exception e) {
//			System.out.println("Archivo no encontrado");
//		}
//
//		if (b) {
//			try {
//				while (b) {// si el archivo existe, muestra en pantalla el listado de libros
//					try {
//						Usuario u = (Usuario) oi.readObject();
//						oo.writeObject(u);
//
//					} catch (EOFException e) {
//						b = false;
//					}
//				} // finaliza el while para la lectura
//				oo.close();
//				oi.close();
//			} catch (Exception e) {// encaso de encontrar el archivo pero no puede leerlo
//				System.out.println("Problemas al leer el archivo de usuarios" + e.toString());
//			}
//		}
//	}
}
