import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MetodosRAF {
	/** The f. */
	static RandomAccessFile f;

	/**
	 * Permite ir de manera directa al Alumno deseado
	 *
	 * @param f      the f
	 * @param pos    the pos
	 * @param tamReg the tam reg
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void irARegistro(RandomAccessFile f, int pos, int tamReg) throws IOException {
		f.seek(pos * tamReg);
	}

	/**
	 * Insertar al final fichero.
	 *
	 * @param nombre the nombre
	 * @param notas  the notas
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void insertarAlFinalFichero(String titulo) throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
//		boolean b = leerPrimerRegistro();
		int n = contarResgistros();
		Libro l = new Libro((n), titulo.toUpperCase(), false);
		f.seek(l.getLongitudRegistroLibro() * n); // f.length();
		l.escribir(f);
		f.close();
	}

	/**
	 * Contar resgistros. Cuenta la cantidad de alumnos almacenados en el fichero
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static int contarResgistros() throws IOException {
		int n = 0;
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();

			boolean hayDatos = l.leer(f);
			while (hayDatos) {
				hayDatos = l.leer(f);
				n++;
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
		return n;
	}

	/**
	 * Mostrar fichero.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	static void mostrarFichero() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(l.getLongitudRegistroLibro()); // f.length();

			while (hayDatos) {
				l.mostrar();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}
}
