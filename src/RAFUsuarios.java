
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
	 * Permite agregar un usuario nuevo al fichero.
	 *
	 * @param s Nombre del usuario
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see Archivos#retrocederRegistroUsurio
	 */
	public static void altaUsuario(String s) throws IOException {
		try {
			Archivos.exceptionLongitudMaxima(s, Const.LONGITUDNOMBRE);
			int n = CrearArrayDe.buscarUsuarioNulo();

			f = new RandomAccessFile(Const.FUSUARIOS, "rw");
			Usuario u = new Usuario();

			Archivos.retrocederRegistroUsurio(f, n, Usuario.getlongitugRegistroUsuario(), u);

			// si en diha posicion el usuario tine eun ID -1 se permitira la escritura de
			// uno nuevo
			if (u.getId() == -1) {
				u = new Usuario(s, n);
				u.escribir(f);
			}
			f.close();

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
	public static int contarResgistrosUsuarios() throws IOException {
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
	public static void mostrarFicheroUsuarios() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);
			f.seek(Usuario.getlongitugRegistroUsuario()); // f.length();

			while (hayDatos) {
				u.mostrarUsuarios();
				hayDatos = u.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}

	/**
	 * Baja usuario.
	 *
	 * @param id the id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void bajaUsuario(int id) throws IOException {
		f = new RandomAccessFile(Const.FUSUARIOS, "rw");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
		Usuario u = new Usuario();
		u.leer(f);

		if (u.getId() != -1) {
			System.out.println("--------------------------------------------------");
			System.out.println("EL USUARIO " + u.getNombre() + " CON EL ID " + u.getId() + " HA SIDO ELIMINADO");
			System.out.println("--------------------------------------------------");

			u = new Usuario("", -1);
			Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
			u.escribir(f);
		} else
			System.out.println("El usuario no existe");
		f.close();
	}

}
