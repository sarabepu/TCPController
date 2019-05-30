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
 * Clase para probar localmente la descarga de un torrent en la carpeta nuevosArchivos2
 * @author Sara María Bejarano
 *
 */
public class Cliente2 {

	static SimpleClient client = new SimpleClient();
	public static void main(String[] args) throws Exception {

		Thread.sleep(1000);
		BasicConfigurator.configure();



		InetAddress localhost = InetAddress.getLocalHost();
		Inet4Address iPv4Address = (Inet4Address) localhost;
		

		try
		{
			File torrentFile = new File(args[0]);
			File outputFile = new File(args[1]);
			String ipController= args[2];
			descargarTorrent(torrentFile, outputFile, localhost, iPv4Address, ipController);

		}
		catch(ArrayIndexOutOfBoundsException e)
		{

			File torrentFile = new File("./archivos/torrents/local.torrent");
			File outputFile = new File("./archivos/nuevosarchivos");

			descargarTorrent(torrentFile, outputFile, localhost, iPv4Address, "localhost");
		}
	}

	private static void descargarTorrent(File torrentFile, File outputFile, InetAddress localhost, Inet4Address iPv4Address, String ipController) throws Exception 
	{
		// TODO Auto-generated method stub
		Socket clientSocket = new Socket(ipController, 44444);
		PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String numTest = inFromServer.readLine();
		System.out.println(numTest);
		Date date= new Date();

		long timeI = date.getTime();
		client.downloadTorrent(
				torrentFile.getAbsolutePath(),
				outputFile.getAbsolutePath(),
				iPv4Address);
		Date dateF= new Date();

		long timeF = dateF.getTime();

		String str = "Test #: "+numTest+" Fecha: "+date+" Cliente: "+ localhost.getHostAddress()+" demoro "+(timeF-timeI);

		System.err.println("El tiempo fue:"+ (timeF-timeI));

		outToServer.println(str);
		
		String received= inFromServer.readLine();
		if(received.equals("end"))
		{
			System.out.println("borrando");
			client.stop();
			vaciarCarpeta(outputFile);
		}
		clientSocket.close();
	}

	private static void vaciarCarpeta(File outputFile) {	
		// TODO Auto-generated method stub
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
