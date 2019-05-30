package Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;

import com.turn.ttorrent.client.SimpleClient;
/**
 * Clasecliente que descarga un torrent
 * @author Sara María Bejarano
 *
 */
public class Cliente {

	static SimpleClient client = new SimpleClient();
	public static void main(String[] args) throws Exception {

		Thread.sleep(1000);
		//Para que muestre en consola el LOG
		BasicConfigurator.configure();



		InetAddress localhost = InetAddress.getLocalHost();
		Inet4Address iPv4Address = (Inet4Address) localhost;
		

		try
		{
			//Toma los parametros que entran como argumentos
			File torrentFile = new File(args[0]);
			File outputFile = new File(args[1]);
			String ipController= args[2];
			//Descarga el torrent
			descargarTorrent(torrentFile, outputFile, localhost, iPv4Address, ipController);

		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			//Entra acaá si lo estoy corriendo localmente (sin argumentos)
			File torrentFile = new File("./archivos/torrents/local.torrent");
			File outputFile = new File("./archivos/nuevosarchivos2");
			//Descarga el torrent
			descargarTorrent(torrentFile, outputFile, localhost, iPv4Address, "localhost");
		}
	}
	/**
	 * Metodo que permite que el cliente se conecte al controlador y descargue/elimine el torrent
	 * @param torrentFile Archivo torrent a descargar
	 * @param outputFile Carpeta donde se guardará el archivo
	 * @param localhost Direccion IP del cliente
	 * @param iPv4Address Direccion IP del cliente en formato IPV4
	 * @param ipController Direccion IP del controlador
	 * @throws Exception
	 */
	private static void descargarTorrent(File torrentFile, File outputFile, InetAddress localhost,
			Inet4Address iPv4Address, String ipController) throws Exception 
	{
		// TODO Auto-generated method stub
		//Se conecta al controlador
		Socket clientSocket = new Socket(ipController, 44444);
		PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		//Espera a que el controlador de la orden de empezar la descarga
		String numTest = inFromServer.readLine();
		System.out.println(numTest);
		Date date= new Date();
		//Guarda elt iempo justo antes de empezar la descarga
		long timeI = date.getTime();
		try
		{
		client.downloadTorrent(
				torrentFile.getAbsolutePath(),
				outputFile.getAbsolutePath(),
				iPv4Address);
		
		Date dateF= new Date();
		//guarda el tiempo despues de terminar la descarga
		long timeF = dateF.getTime();
		
		String str = "Test #: "+numTest+" Fecha: "+dateF+" Cliente: "+ localhost.getHostAddress()+" demoro "+(timeF-timeI);

		System.err.println("El tiempo fue:"+ (timeF-timeI));
		//Manda al controlador el tiempo demorado en descargar el torrent y empieza a ser Seeder
		outToServer.println(str);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			client.stop();
			vaciarCarpeta(outputFile);
		}
		//Recibe orden del controlador que el experimento terminó
		String received= inFromServer.readLine();
		if(received.equals("end"))
		{
			System.out.println("borrando");
			//Detiene el cliente
			client.stop();
			//Elimina el archivo
			vaciarCarpeta(outputFile);
		}
		
		clientSocket.close();
	}

	private static void vaciarCarpeta(File outputFile) {	
		
		File[] archivos=outputFile.listFiles();
		if(archivos!=null)
		{
			for (File file : archivos) {
				if(!file.delete())
				{
					vaciarCarpeta(file);
				}
			}
		}
		else
		{
			outputFile.delete();
		}
	}
}
