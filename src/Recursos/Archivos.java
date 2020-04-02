/**
 *
 * @author Javier Delgado Rodriguez
 */
package Recursos;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;

public class Archivos {

	/** The f. */
	static RandomAccessFile f;

	/**
	 * Exception longitud maxima. Genera una excepcion cuando la longitud del ttulo
	 * del libros o el nombre del usuario sea mayo de la establecida en la Class
	 * Const
	 *
	 * @param s the s
	 * @param n the n
	 * @throws Exception the exception
	 */
	public static void exceptionLongitudMaxima(String s, int n) throws Exception {
		if (s.length() >= n)
			throw new Exception("ERROR: Ha excedido la longitud maxima permitida");
	}

	/**
	 * Permite ir de manera directa al libro deseado
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
	 * Retroceder registro usurio. Permmite leer un usuario y, ya que RAF mueve el
	 * punte al siguiente registro, este metodo permite retroceder la cantidad de
	 * Bytes necesarios para volver a dejar el puntero en el registro ya leido
	 *
	 * @param f       the f. Acceso al fcihero con RandomAccessFile
	 * @param pos     the pos. Posicion del registro deseado
	 * @param longReg the long reg. Longitud en byte por cada uno de los registros
	 * @param u       the u. Objeto de tipo Usuario
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void retrocederRegistroUsurio(RandomAccessFile f, int pos, int longReg, Usuario u)
			throws IOException {
		// busco el primer usuario no valido de la lista, con un ID -1
		f.seek(longReg * pos);

		// leo la posicion del usuario y retrocedo para permanecer en el mmismo lugar
		u.leer(f);
		f.seek(longReg * pos);
	}

	/**
	 * Retroceder registro libro. Permmite leer un libro y, ya que RAF mueve el
	 * punte al siguiente registro, este metodo permite retroceder la cantidad de
	 * Bytes necesarios para volver a dejar el puntero en el registro ya leido
	 *
	 * @param f       the f
	 * @param pos     the pos
	 * @param longReg the long reg
	 * @param l       the l
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void retrocederRegistroLibro(RandomAccessFile f, int pos, int longReg, Libro l) throws IOException {
		// avanzo hasta la posicion indicada
		f.seek(longReg * pos);

		// leo el objeto Libro y retrocedo al inicio del mismo objeto Libro
		l.leer(f);
		f.seek(longReg * pos);
	}

	/**
	 * Crear fichero. Creara un fichero llamado FLIBROS y FUSUARIOS en el caso de
	 * que alguno de estos no exista
	 *
	 * @throws Exception the exception
	 */
	public static void crearFichero() throws Exception {
		File f = new File(Const.FLIBROS);
		if (!f.exists())
			f.createNewFile();
		f = new File(Const.FUSUARIOS);
		if (!f.exists()) {
			f.createNewFile();
			usuariosNulos();
		}
		f = new File(Const.FPRESTAMOS);
		if (!f.exists()) {
			f.createNewFile();
			escribirPrestamosNulos();
		}
		f = new File(Const.FLIBROASIGNADOA);
		if (!f.exists()) {
			f.createNewFile();
			LibrosAsignadosNulos();

		}
	}

	/**
	 * Usuarios nulos. Crea un fichero con todos los usuario que se pueden almacenar
	 * pero estos usuarios son nulos
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void usuariosNulos() throws IOException {
		RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "rw");
		int n = 0;
		Usuario u = new Usuario("", -1);
		while (n < Const.MAXUSUARIOS) {
			f.seek(Usuario.getlongitugRegistroUsuario() * n);
			u.escribir(f);
			n++;
		}
		f.close();
	}

	/**
	 * Escribir prestamos nulos. Genera un fichero con todos los usuarios y sus
	 * libros que pueden toma rprestados pero con valores nulos
	 *
	 * @throws Exception the exception
	 */
	public static void escribirPrestamosNulos() throws Exception {
		RandomAccessFile f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
		int n = 0;
		Usuario u = new Usuario("", -1);
		while (n < Const.MAXUSUARIOS) {
			f.seek(Usuario.getlongitugRegistroUsuarioPrestamos() * n);
			u.escribir(f);
			Libro l = new Libro(-1, "", false);
			for (int i = 0; i < Const.MAXLIBROSPRES; i++) {
				l.escribir(f);
			}
			n++;
		}
		f.close();
	}

	/**
	 * Contar usurios nulos. Metodo que permite contar la cantidad de usuarios nulos
	 * (ID = -1) que aun permanecen en el archivo
	 *
	 * @return the int
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static int contarUsuriosNulos() throws IOException {
		int n = 0;

		// Comprobar antes si existe el fichero.
		if (new File(Const.FUSUARIOS).isFile()) {
			RandomAccessFile f = new RandomAccessFile(Const.FUSUARIOS, "r");
			Usuario u = new Usuario();
			boolean hayDatos = u.leer(f);
			f.seek(Usuario.getlongitugRegistroUsuario()); // f.length();

			while (hayDatos) {
				if (u.getId() != -1)
					n++;

				hayDatos = u.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: contarUsuriosNulos");

		return n;
	}

	/**
	 * Lisbros asignados nulos. Crea un fichero de asignaciones Libro a Usuario por
	 * la misma cantidad de libros almacenados en la biblioteca
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void LibrosAsignadosNulos() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");

			Usuario u = new Usuario("", -1);
			Libro l = new Libro(-1, "", false);
			for (int i = 0; i < Const.MAXLIBROS; i++) {
				f.seek(i * Const.N);
				l.escribir(f);
				u.escribir(f);
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");
	}
}
