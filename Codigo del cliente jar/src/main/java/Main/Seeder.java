package Main;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;

import com.turn.ttorrent.client.SimpleClient;
/**
 * Clase que representa un seeder (Es un cliente que ya ha descargado el torrent solicitado)
 * @author Sara María Bejarano
 *
 */
public class Seeder {

	public static void main(String[] args) throws Exception {
		//Para ver el log en la consola
		BasicConfigurator.configure();
		SimpleClient client = new SimpleClient();
		InetAddress localhost = InetAddress.getLocalHost();
		
		System.err.println(localhost);
		try
		{
			//Para llamarlo desde un bat
		Inet4Address iPv4Address = (Inet4Address) localhost;
		File torrentFile = new File(args[0]);
		File outputFile = new File(args[1]);
		

		client.downloadTorrent(
				torrentFile.getAbsolutePath(),
				outputFile.getAbsolutePath(),
				iPv4Address);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			// El torrent a seedear es local.torrent y el archivo real esta guardado en la carpeta archivos
			Inet4Address iPv4Address = (Inet4Address) localhost;
			File torrentFile = new File("./archivos/torrents/local.torrent");
			File outputFile = new File("./archivos/archivos");


			client.downloadTorrent(
					torrentFile.getAbsolutePath(),
					outputFile.getAbsolutePath(),
					iPv4Address);
		}

	}
}
