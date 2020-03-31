package PrestamoLibro;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;
import Recursos.Archivos;
import Recursos.Const;
import Recursos.CrearArrayDe;

public class LibroAsignadoA {
	static RandomAccessFile f;
	public final static int N = Usuario.getlongitugRegistroUsuario() + Libro.getLongitudRegistroLibro();

	public static void LisbrosAsignadosNulos() throws IOException {
		// Comprobar antes si existe el fichero.
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");

			Usuario u = new Usuario("", -1);
			Libro l = new Libro(-1, "", false);
			for (int i = 0; i < Const.MAXLIBROS; i++) {
				f.seek(i * N);
				l.escribir(f);
				u.escribir(f);
			}
			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");

	}

	public static void guardarAsignacion(int id, int codigo) throws IOException {
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");
			Archivos.irARegistro(f, codigo, N);

			Libro[] vLibros = CrearArrayDe.crearArrayLibros();
			Libro l = new Libro(codigo, vLibros[codigo].titulo, true);
			l.escribir(f);
			l.mostrarLibro();

			Usuario[] vUsuarios = CrearArrayDe.crearArrayUsuarios();
			Usuario u = new Usuario(vUsuarios[id].getNombre(), id);
			u.escribir(f);
			u.mostrarUsuario();

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");

	}

	public static void mostrarAsignaciones() throws IOException {
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");

			Libro l = new Libro();
			Usuario u = new Usuario();
			boolean hayDatos = l.leer(f);

			while (hayDatos) {
				l.mostrarLibro();

				u.leer(f);
				u.mostrarUsuario();

				hayDatos = l.leer(f);
			}

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");

	}

}
