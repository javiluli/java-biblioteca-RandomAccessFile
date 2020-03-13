
/**
 *
 * @author Javier Delgado Rodriguez
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFUsuarios {
	static RandomAccessFile f;

	/**
	 * Exception longitud nombre. Genera una excepcion cuando la longitud del nombre
	 * del usuario sea mayo de la establecida en Const.LONGITUDNOMBRE
	 * 
	 * @param s the s
	 * @throws Exception the exception
	 */
	public static void exceptionLongitudNombre(String s) throws Exception {
		if (s.length() >= Const.LONGITUDNOMBRE)
			throw new Exception("El nombre del usuario excede la longitud permitida (20 caracteres)");
	}

	/**
	 * Permite ir de manera directa al usuario deseado
	 *
	 * @param f      the f
	 * @param pos    the pos
	 * @param tamReg the tam reg
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void irARegistro(RandomAccessFile f, int pos, int tamReg) throws IOException {
		f.seek(pos * tamReg);
	}

	/**
	 * Agrega un usuario al final del fichero
	 *
	 * @param s Nombre del usuario
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void insertarAlFinalFichero(String s) throws IOException {
		f = new RandomAccessFile(Const.FUSUARIOS, "rw");
		int n = contarResgistros();
		Usuario u = new Usuario(s, n);
		f.seek(u.getlongitugRegistroUsuario() * n);
		u.escribir(f);
		f.close();
	}

	/**
	 * Permite agregar un usuario nuevo al fichero
	 *
	 * @param s Nombre del usuario
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void altaUsuario(String s) throws IOException {
		try {
			exceptionLongitudNombre(s);
			RAFUsuarios.insertarAlFinalFichero(s);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Cuenta la cantidad de usuaros almacenados en el fichero
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int contarResgistros() throws IOException {
		int n = 0;
		// Comprobar antes si existe el fichero.
		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);
			while (hayDatos) {
				hayDatos = u.leer(f);
				n++;
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarResgistros");
		return n;
	}

	/**
	 * Mostrar informacion almacenada en el fichero
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void mostrarFichero() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);
			f.seek(u.getlongitugRegistroUsuario()); // f.length();

			while (hayDatos) {
				u.mostrarUsuarios();
				hayDatos = u.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}
}
