import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Javier Delgado Rodriguez
 */

/**
 * The Class Libro.
 */
public class Libro {

	/** The codigo. */
	public int codigo;

	/** The titulo. */
	public String titulo;

	/** The prestado. */
	boolean prestado;

	public Libro() {
	}

	public Libro(int codigo, String titulo, boolean prestado) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.prestado = prestado;
	}

	/**
	 * Obtener el tamaño o longitud de cada uno de los libros almacenados. Esta
	 * longitud es la suma del titulo (45 bytes), el codigo ID del libro (4 bytes) y
	 * un bollean que indica el estado del prestamo (1 byte).
	 * 
	 * TOTAL DE LA LONGITUD DEL REGISTRO = 50 bytes
	 *
	 * @return the tamano registro
	 */
	static int getLongitudRegistroLibro() {
		return (Const.LONGITUDTITULO + 4 + 1);
	}

	/**
	 * Pasar stringa array bytes.
	 *
	 * @param s the nombre
	 * @return the byte[]
	 */
	byte[] pasarStringaArrayBytes(String s) {
		byte nombreB[] = new byte[Const.LONGITUDTITULO];
		for (int i = 0; i < s.length() && i < Const.LONGITUDTITULO; i++)
			nombreB[i] = (byte) s.charAt(i);
		for (int i = s.length(); i < Const.LONGITUDTITULO; i++)
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
		f.writeInt(codigo);
		byte tituloAux[];
		tituloAux = pasarStringaArrayBytes(titulo);
		f.write(tituloAux);
		f.writeBoolean(prestado);
	}

	boolean leer(RandomAccessFile f) throws IOException {
		// devuelve true si lee algo y false si no devuelve nada
		try {
			codigo = f.readInt();

			byte tituloAux[] = new byte[Const.LONGITUDTITULO];
			f.read(tituloAux, 0, Const.LONGITUDTITULO);
			// titulo=new String(tituloAux, "UTF-8"); //convierte el array de bytes en un
			// string con la notación UTF-8
			titulo = new String(tituloAux, "ISO-8859-1"); // Mejor 8859 para ñ y acentos.
			titulo = eliminarVacios(titulo);

			prestado = f.readBoolean();

			return true;
		} catch (EOFException e) {
			return false;
		}
	}

	void mostrar() {
		System.out.println("codigo: " + codigo + " | prestado: " + (prestado ? "SI" : "NO") + " | titulo: " + titulo);
	}

	@Override
	public String toString() {
		return "Libro [codigo=" + codigo + ", titulo=" + titulo + ", prestado=" + prestado + "]";
	}

}
