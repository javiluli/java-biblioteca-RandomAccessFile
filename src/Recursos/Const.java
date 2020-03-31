/**
 *
 * @author Javier Delgado Rodriguez
 */
package Recursos;

/**
 *
 * @author Javier Delgado Rodriguez
 */

import MetLibro.Libro;
import MetUsuario.Usuario;

/**
 * The Class Constantes.
 */
public class Const {
	// ######################################
	// Constantes sobre Usuarios
	// ######################################

	/** Longitud maxima que puede tener el nombre de un usuario */
	public final static int LONGITUDNOMBRE = 20;

	/** Cantidad maxima de usuarios que almacena la biblioteca */
	public final static int MAXUSUARIOS = 25;

	/** Conexion con el archico donde se almacenan los usuarios de la biblioteca */
	public final static String FUSUARIOS = "datos\\datosUsuarios.dat";

	/**
	 * Conexion con un archivo auxiliar de los usuarios almacenados. Su uso esta mas
	 * orientado a cuando se realizan cambios en los datos de los usuarios, como en
	 * los libros que tiene en prestamos.
	 */
	public final static String FUSUARIOSAUX = "datos\\datosUsuarios_aux.dat";

	/**
	 * Conexion con el archivo de backup de usuarios almacenados en la biblioteca
	 */
	public final static String FUSUARIOSBACKUP = "datos\\datosUsuarios_backup.dat";

	// ######################################
	// Constantes sobre Libros
	// ######################################

	/** Longitud maxima que puede tener el titulo de un libro */
	public final static int LONGITUDTITULO = 45;

	/** Cantidad maxima de libros almacenados en la biblioteca */
	public final static int MAXLIBROS = 100;

	/** Cantidad maxima de libros que puede tener un usuario en prestamo */
	public final static int MAXLIBROSPRES = 5;

	/** Conexion con el archivo donde se almacenan los libros de la biblioteca. */
	public final static String FLIBROS = "datos\\datosLibros.dat";

	/**
	 * Conexion con un archivo auxiliar de los libros almacenados. Su uso esta mas
	 * orientado a cuando se realizan cambios en los datos de los libros, como en
	 * saber si se encuentra en prestamo
	 */
	public final static String FLIBROSAUX = "datos\\datosLibros_aux.dat";

	/** Conexion con el archivo de backup de libros almacenados en la biblioteca */
	public final static String FLIBROSBACKUP = "datos\\datosLibros_backup.dat";

	/**
	 * Conexion con el archivo que almacena tanto usuarios como sus libris prestados
	 */
	public final static String FPRESTAMOS = "datos\\datosPrestamos.dat";

	/**
	 * Fichero de almacenamiendo de libro por usuario, acceso directo al prestamo
	 * del libro y el usuario que lo tiene.
	 */
	public final static String FLIBROASIGNADOA = "datos\\librosAsignados.dat";

	/** Longitud en Bytes de un Usuario y un Libro = 74 Bytes */
	public final static int N = Usuario.getlongitugRegistroUsuario() + Libro.getLongitudRegistroLibro();

}
