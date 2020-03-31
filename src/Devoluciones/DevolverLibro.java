package Devoluciones;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import MetLibro.Libro;
import MetUsuario.Usuario;
import Recursos.Archivos;
import Recursos.Const;

public class DevolverLibro {
	static RandomAccessFile f;
	public final static int N = Usuario.getlongitugRegistroUsuario() + Libro.getLongitudRegistroLibro();

	public static boolean comprobarPrestamo(int codigo) throws IOException {
		// Comprobar antes si existe el fichero.
		boolean b = false;
		if (new File(Const.FLIBROASIGNADOA).isFile()) {
			f = new RandomAccessFile(Const.FLIBROASIGNADOA, "r");
			Archivos.irARegistro(f, codigo, N);

			Libro l = new Libro();
			l.leer(f);
			if (l.prestado)
				b = true;

			f.close();
		} else
			System.out.println("El Fichero no existe - ERROR EN: mostrarFichero");

		return b;
	}

	public static boolean comprobarUsuarioPrestado(int id, int codigo) throws Exception {

		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();
		boolean b = false;
		for (int i = 0; i < Const.MAXLIBROSPRES && !b; i++) {
			l.leer(f);
			if (l.codigo == codigo)
				b = true;
		}

		f.close();
		return b;
	}

	public static int localizarPosicionPrestamo(int id, int codigo) throws Exception {
		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");
		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);
		Libro l = new Libro();
		boolean b = false;
		int n = 0;
		for (int i = 0; i < Const.MAXLIBROSPRES && !b; i++) {
			l.leer(f);
			if (l.codigo == codigo)
				b = true;
			n = i;

		}

		f.close();
		return n;
	}

	public static void devolucion(int id, int codigo) throws Exception {
		int n = localizarPosicionPrestamo(id, codigo);
		System.out.println(n);
		// ###########################################################################
		// Cambios en el Fichero de FPRESTAMOS (Ver Clase Const)
		f = new RandomAccessFile(Const.FPRESTAMOS, "rw");

		Archivos.irARegistro(f, id, Usuario.getlongitugRegistroUsuarioPrestamos());
		Usuario u = new Usuario();
		u.leer(f);

		Libro l = new Libro();

		// almaceno en FP la posicion actial del puntero para seguir en el usuario pero
		// solo para accder de forma directa a los libros prestados
		int fp = (int) f.getFilePointer();

		// fp |> posicion del puntero en el fichero justo despues de leer el usuario
		// seleccionado
		// n |> posicion del primer espacio nulo en sus libros prestados
		// Libro.getLongitudRegistroLibro() |> longitus en bytes de un Object Libro
		f.seek(fp + (n * Libro.getLongitudRegistroLibro()));
		l = new Libro(-1, "", false);
		l.escribir(f);

		// ###########################################################################
		// Cambios en el fichero de FLIBROS (Ver Clase Const)
		f = new RandomAccessFile(Const.FLIBROS, "rw");
		l = new Libro();
		Archivos.retrocederRegistroLibro(f, codigo, Libro.getLongitudRegistroLibro(), l);
		l.prestado = false;
		l.escribir(f);

		// ###########################################################################
		// Cambios en el fichero de FLIBROASIGNADOA (Ver Clase Const)
		f = new RandomAccessFile(Const.FLIBROASIGNADOA, "rw");
		Archivos.irARegistro(f, codigo, N);

		l = new Libro(-1, "", false);
		l.escribir(f);

		u = new Usuario("", -1);
		u.escribir(f);

		f.close();

	}
}
