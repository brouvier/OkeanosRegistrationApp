package okeanos.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SickNoteDao {

	public static Logger logger = LoggerFactory.getLogger(SickNoteDao.class);

	protected static byte[] file;

	public static void save(int userId, byte[] bytes) {
		logger.info("SAVE FILE INTO DATA BASE HERE");
		file = bytes;
	}

	public static byte[] get() {
		return file;
	}
}
