
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

//	/**
//	 * Agrega un usuario al final del fichero
//	 *
//	 * @param s Nombre del usuario
//	 * @throws IOException Signals that an I/O exception has occurred.
//	 */
//	public static void insertarAlFinalFichero(String s) throws IOException {
//		f = new RandomAccessFile(Const.FUSUARIOS, "rw");
//		int n = contarResgistros();
//		Usuario u = new Usuario(s, n);
//		f.seek(Usuario.getlongitugRegistroUsuario() * n);
//		u.escribir(f);
//		f.close();
//	}

	/**
	 * Usuarios nulos.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void usuariosNulos() throws IOException {

		f = new RandomAccessFile(Const.FUSUARIOS, "rw");
		int n = contarResgistros();
		Usuario u = new Usuario("", -1);
		while (n < Const.MAXUSUARIOS) {
			n = contarResgistros();
			f.seek(Usuario.getlongitugRegistroUsuario() * n);
			u.escribir(f);
		}
		f.close();

	}

	/**
	 * Permite agregar un usuario nuevo al fichero
	 * 
	 * NOTA: Al leer se salta al siguiente registro, es necesario retroceder dicho
	 * registro para manipular este mismo. Tambien se debe buscar el siguiente
	 * registro con una coincidencia de ID -1
	 *
	 * @param s Nombre del usuario
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void altaUsuario(String s) throws IOException {
		try {
			exceptionLongitudNombre(s);
			int n = CrearArrayDe.buscarUsuarioNulo();
//			System.out.println(n);

			f = new RandomAccessFile(Const.FUSUARIOS, "rw");
			// Para el resto de registro se debera hacer un seek para no reemplazxar
			// resgitros anteriores.
			// Cambiar a partir de aqui ->
			Usuario u = new Usuario();

			u.leer(f);
//			u.mostrarUsuarios();
			f.seek(0);
//			System.out.println("Puntero 1: " + f.getFilePointer());
//			System.out.println("N vale: " + n);
			f.seek(Usuario.getlongitugRegistroUsuario() * n);
			
//			System.out.println("Puntero 2: " + f.getFilePointer());
			u.leer(f);
			f.seek(Usuario.getlongitugRegistroUsuario() * n);
//			System.out.println(u.toString());

			if (u.getId() == -1) {
				u = new Usuario(s, n);
				u.escribir(f);
			}
//			u.mostrarUsuarios();
			f.close();

//			RAFUsuarios.insertarAlFinalFichero(s);
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
		irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
		Usuario u = new Usuario();
		u.leer(f);

		if (u.getId() != -1) {
			System.out.println("El usuario " + u.getNombre() + " con el ID " + u.getId() + " ha sido eliminado");
			u = new Usuario("", -1);
			irARegistro(f, id, Usuario.getlongitugRegistroUsuario());
			u.escribir(f);
		} else
			System.out.println("El usuario no existe");
		f.close();
	}

}
