package okeanos.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SickNoteDao {

    public static Logger logger = LoggerFactory.getLogger(SickNoteDao.class);

    public static void save(int userId, byte[] bytes) {
        logger.info("SAVE FILE INTO DATA BASE HERE");
    }
}
