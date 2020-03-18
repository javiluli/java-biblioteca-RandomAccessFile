
/**
 *
 * @author Javier Delgado Rodriguez
 */

import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class Usuario {

	/** The nombre. */
	private String nombre;

	/** The id. */
	private int id;

	/** The libros prestados. */
	Libro[] librosPrestados = new Libro[Const.MAXLIBROSPRES];

	/**
	 * Instantiates a new usuario.
	 */
	public Usuario() {
	}

	/**
	 * Instantiates a new usuario.
	 *
	 * @param nombre the nombre
	 * @param id     the id
	 */
	public Usuario(String nombre, int id) {
		this.nombre = nombre;
		this.id = id;
	}

	/**
	 * Instantiates a new usuario.
	 *
	 * @param nombre          the nombre
	 * @param id              the id
	 * @param librosPrestados the libros prestados
	 */
	public Usuario(String nombre, int id, Libro[] librosPrestados) {
		this.nombre = nombre;
		this.id = id;
		this.librosPrestados = librosPrestados;
	}

	/**
	 * Gets the tamano registro. Longitud del registro por usuario
	 * 
	 * Const.TAMANONOMBRE = Tamaño del nombre.
	 * 
	 * 1 int = 4 bytes
	 * 
	 * TOTAL = 24 bytes
	 *
	 * @return the tamano registro
	 */
	public static int getlongitugRegistroUsuario() {
		return (Const.LONGITUDNOMBRE + 4);
	}

	/**
	 * Gets the tamano registro. Longitud del registro por usuario con sus libros
	 * prestados
	 * 
	 * Const.TAMANONOMBRE = Tamaño del nombre = 20 bytes.
	 * 
	 *
	 * 
	 * TOTAL DE LA LONGITUD DEL REGISTRO = 274 bytes
	 * 
	 * @return the tamano registro
	 */
	int getlongitugRegistroUsuarioPrestamos() {
		return (getlongitugRegistroUsuario() + (Libro.getLongitudRegistroLibro() * Const.MAXLIBROSPRES));
	}

	/**
	 * Pasar stringa array bytes.
	 *
	 * @param nombre the nombre
	 * @return the byte[]
	 */
	byte[] pasarStringaArrayBytes(String nombre) {
		byte nombreB[] = new byte[Const.LONGITUDNOMBRE];
		for (int i = 0; i < nombre.length() && i < Const.LONGITUDNOMBRE; i++)
			nombreB[i] = (byte) nombre.charAt(i);
		for (int i = nombre.length(); i < Const.LONGITUDNOMBRE; i++)
			nombreB[i] = (byte) 0;
		return nombreB;
	}

	/**
	 * Elimina del string los caracteres vacíos (0)
	 * 
	 * @param s
	 * @return el string sin vacíos.
	 */
	String eliminarVacios(String s) {
		StringBuffer sb = new StringBuffer(s);
		int i = 0;
		while (sb.charAt(i) != (char) 0)
			i++;
		sb.setLength(i);
		return (sb.toString());
	}

	void escribir(RandomAccessFile f) throws IOException {
		byte nombreBusuario[];
		nombreBusuario = pasarStringaArrayBytes(nombre);
		f.write(nombreBusuario);
		f.writeInt(id);
	}

	boolean leer(RandomAccessFile f) throws IOException {
		// devuelve true si lee algo y false si no devuelve nada
		try {
			byte nombreB[] = new byte[Const.LONGITUDNOMBRE];
			f.read(nombreB, 0, Const.LONGITUDNOMBRE);
			// nombre=new String(nombreB, "UTF-8"); //convierte el array de bytes en un
			// string con la notación UTF-8
			nombre = new String(nombreB, "ISO-8859-1"); // Mejor 8859 para ñ y acentos.
			nombre = eliminarVacios(nombre);

			id = f.readInt();
			return true;
		} catch (EOFException e) {
			return false;
		}
	}

	void mostrarUsuarios() {
		System.out.println("ID: " + id + " | Nombre: " + nombre);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Libro[] getLibrosPrestados() {
		return librosPrestados;
	}

	public void setLibrosPrestados(Libro[] librosPrestados) {
		this.librosPrestados = librosPrestados;
	}

	@Override
	public String toString() {
		return "ID: " + id + " | Nombre: " + nombre;
	}

}
