package okeanos.restApp.resources;

import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okeanos.util.AppProperties;
import spark.Request;
import spark.Response;

public class SickNoteResource extends AbstractResource {

	private static Logger logger = LoggerFactory.getLogger(SickNoteResource.class);

	protected String ressourcePath = AppProperties.API_CONTEXT + "/file";

	protected static Document currentDoc;

	public SickNoteResource() {
		super.setupEndpoints();

		post(ressourcePath + "/multipart", (request, response) -> {
			receveDocument(request, response);
			return "File uploaded";
		});

		get(ressourcePath + "/multipart", (request, response) -> {
			sendDocument(response);
			return response.raw();
		});
	}

	private void receveDocument(Request request, Response response) {
		request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

		try {
			if (request.raw().getParts().size() > 0) {
				Part part = request.raw().getParts().iterator().next();
				InputStream is = part.getInputStream();

				logger.info("getHeaderNames : {}", part.getHeaderNames());
				logger.info("content-disposition : {}", part.getHeader("content-disposition"));
				logger.info("content-type : {}", part.getHeader("content-type"));

				currentDoc = new Document();
				currentDoc.type = part.getHeader("content-type");
				currentDoc.contentDisposition = part.getHeader("content-disposition");
				currentDoc.data = IOUtils.toByteArray(is);
			} else {
				halt(405, "server error : request part is empty !");
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		response.redirect("https://localhost:8080/index.html#/sickNote");
	}

	private void sendDocument(Response response) throws IOException {
		if (currentDoc == null)
			return;

		logger.info("response.type {}", currentDoc.type);
		logger.info("response.header {}", currentDoc.contentDisposition);
		// logger.info("response.raw {}", currentDoc.data);

		response.type(currentDoc.type);
		response.header("Content-Disposition", currentDoc.contentDisposition);
		OutputStream oStream = response.raw().getOutputStream();
		try {
			oStream.write(currentDoc.data);
			oStream.flush();
			oStream.close();
		} catch (Exception e) {
			halt(405, "server error");
		}
	}

	public class Document {
		public String type;
		public String contentDisposition;
		public byte[] data;
	}
}
