
/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFLibros {
	/** The f. */
	static RandomAccessFile f;

	/**
	 * Agrega un libro al final del fichero
	 *
	 * @param s Titulo del libro
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void insertarAlFinalFichero(String s) throws IOException {
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		int n = contarResgistrosLibros();
		Libro l = new Libro((n), s.toUpperCase(), false);
		f.seek(Libro.getLongitudRegistroLibro() * n);
		l.escribir(f);
		f.close();
	}

	/**
	 * Permite agregar un libro nuevo al fichero
	 *
	 * @param s Titulo del libro
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void altaLibro(String s) throws IOException {
		try {
			Archivos.exceptionLongitudMaxima(s, Const.LONGITUDTITULO);
			RAFLibros.insertarAlFinalFichero(s);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Cuenta la cantidad de libros almacenados en el fichero
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int contarResgistrosLibros() throws IOException {
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
	 * Permite ver por consola todos los libros que no se encuentren en prestamo
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void verLibrosNoPrestados() throws IOException {

		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(Libro.getLongitudRegistroLibro()); // f.length();

			while (hayDatos) {
				if (!l.prestado)
					l.mostrar();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: verLibrosNoPrestados");
	}

	/**
	 * Permite ver todo el fichero
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void mostrarFicheroLibros() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FLIBROS, "r");
			Libro l = new Libro();
			boolean hayDatos = l.leer(f);
			f.seek(Libro.getLongitudRegistroLibro()); // f.length();

			while (hayDatos) {
				l.mostrar();
				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}
}
