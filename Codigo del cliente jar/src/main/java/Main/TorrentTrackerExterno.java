package Main;
import com.turn.ttorrent.client.SimpleClient;
import com.turn.ttorrent.common.TorrentCreator;
import com.turn.ttorrent.common.TorrentMetadata;
import com.turn.ttorrent.common.TorrentSerializer;
import com.turn.ttorrent.common.creation.MetadataBuilder;

import static com.turn.ttorrent.common.TorrentMetadataKeys.ANNOUNCE;
import static com.turn.ttorrent.common.TorrentMetadataKeys.COMMENT;
import static com.turn.ttorrent.common.TorrentMetadataKeys.CREATED_BY;
import static com.turn.ttorrent.common.TorrentMetadataKeys.CREATION_DATE_SEC;
import static com.turn.ttorrent.common.TorrentMetadataKeys.FILE_LENGTH;
import static com.turn.ttorrent.common.TorrentMetadataKeys.INFO_TABLE;
import static com.turn.ttorrent.common.TorrentMetadataKeys.NAME;
import static com.turn.ttorrent.common.TorrentMetadataKeys.PIECES;
import static com.turn.ttorrent.common.TorrentMetadataKeys.PIECE_LENGTH;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Savepoint;
import java.util.Map;

import com.turn.ttorrent.Constants;
import com.turn.ttorrent.bcodec.BEValue;
import com.turn.ttorrent.client.SharedTorrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

public class TorrentTrackerExterno {


	public static void main(String[] args) throws Exception {

		//		TorrentMetadata t = TorrentCreator.create(new File("C:/Users/Sara pc/Pictures/fiesta 20/FOTO.jpg"), announceURI, createdBy);
		//		File torrentFile = new File("C:/Users/Sara pc/Pictures/fiesta 20/FOTO.torrent");
		//		saveTorrent(t,torrentFile);

		try
		{
			TorrentMetadata metadata = new MetadataBuilder()
					.addFile(new File(args[0]))
					.setTracker(args[1])
					.build();
			saveTorrent(metadata,new File(args[2]));

		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			TorrentMetadata metadata = new MetadataBuilder()
					.addFile(new File("./archivos/archivos/prueba.jpg"))
					.setTracker("http://tracker.corpscorp.online/announce")
					.build();
			saveTorrent(metadata,new File("./archivos/torrents/ultimoexterno.torrent"));
		}
	}

	public static void saveTorrent(TorrentMetadata torrent, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(new TorrentSerializer().serialize(torrent));
		fos.close();
	}
}
