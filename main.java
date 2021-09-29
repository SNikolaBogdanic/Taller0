package taller0;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class main {

	public static void main(String[] args) throws IOException{
		
		String[] nombresApellidos = new String[1023];
		String[] Ruts = new String[1023];
		String[] Claves = new String[1023];
		int[] Saldos = new int[1023];
		boolean[] pasesMovilidad = new boolean[1023];
		String[][] infoEntradasClientes = new String[1023][15];
		
		boolean[][] Sala1M = new boolean [29][9];
		boolean[][] Sala1T = new boolean [29][9];
		boolean[][] Sala2M = new boolean [29][9];
		boolean[][] Sala2T = new boolean [29][9];
		boolean[][] Sala3M = new boolean [29][9];
		boolean[][] Sala3T = new boolean [29][9];
		
		String[] cartelera = new String[255];
		boolean[] estreno = new boolean[255];
		int[] recaudacionDia = new int[255];
		int[] recaudacionTotal = new int[255];
		int[] recaudacionDiurna = new int[255];
		int[] recaudacionTarde = new int[255];
		int[][] salasPeliculas = new int [255][5];
		char[] horarioPelicula = new char[255];
		
		int cantidadPeliculas = lecturaCantPeliculas(cartelera, estreno, recaudacionTotal, salasPeliculas);
		int cantidadClientes = lecturaCantPersonas(nombresApellidos, Ruts, Claves, Saldos);
		calculoTotalHabilitados(pasesMovilidad, Ruts, cantidadClientes);
		
		
	}
	
	public static int lecturaCantPersonas(String[] nombresApellidos, String[] Ruts,
			String[] Claves, int[] Saldos)throws IOException {
		Scanner archivo = new Scanner(new File("Clientes.txt"));
		int cant = 0;
		while (archivo.hasNextLine()) {
			String linea = archivo.nextLine();
			String[] datos = linea.split(",");
			String nombre = datos[0];
			String apellido = datos[1];
			String rut = datos[2];
			String Clave = datos[3];
			int saldo = Integer.parseInt(datos[4]);
			
			nombresApellidos[cant] = nombre + " " + apellido;
			Ruts[cant] = rut;
			Claves[cant] = Clave;
			Saldos[cant] = saldo;
			
			cant++;
		}
		archivo.close();
		
		return cant;
	}
	
	public static int index(String[] array, String key, int cant) {
		for (int i = 0; i < cant; i++) {
			if (key.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static void calculoTotalHabilitados(boolean[] pasesMovilidad, String[] Ruts, int cantidadClientes) throws IOException {
		Scanner archivo2 = new Scanner(new File("Status.txt"));
		boolean pase;
		while (archivo2.hasNextLine()) {
			String linea2 = archivo2.nextLine();
			String[] datos2 = linea2.split(",");
			String rut = datos2[0];
			String rawEstado = datos2[1];
			if (rawEstado == "HABILITADO") {
				pase = true;
			} else {
				pase = false;
			}
			
			int indiceCliente = index(Ruts, rut, cantidadClientes);
			pasesMovilidad[indiceCliente] = pase;
		}
		
	}
	
	public static int lecturaCantPeliculas(String[] cartelera, boolean[] estreno, int[] RecaudacionTotal,
			int[][] salasPeliculas) throws IOException{
		Scanner archivo = new Scanner(new File("Clientes.txt"));
		int cant = 0;
		boolean es_estreno;
		while (archivo.hasNextLine()) {
			es_estreno = false;
			String linea = archivo.nextLine();
			String[] datos = linea.split(",");
			String pelicula = datos[0];
			String tipo = datos[1];
			if (tipo == "estreno") {
				es_estreno = true;
			}
			int recaudacion = Integer.parseInt(datos[2]);
			for (int i = 3; i < 14;i = i+2) {
				if (datos[i] == null){
					break;
				}
				
				int numeroSala = Integer.parseInt(datos[i]);
				String horario = datos[i+1];
				
				if(horario == "T") {
					numeroSala = numeroSala+3;
				}
				salasPeliculas[cant][(i-3)/2] = numeroSala;
			}
			
			cartelera[cant] = pelicula;
			estreno[cant] = es_estreno;
			RecaudacionTotal[cant] = recaudacion;
			
			cant++;
		}
		archivo.close();
		
		return cant;
	}

}
