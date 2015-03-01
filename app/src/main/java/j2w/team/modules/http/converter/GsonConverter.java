package j2w.team.modules.http.converter;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by sky on 15/2/23.
 */
public class GsonConverter implements J2WConverter {

	private final Gson		gson;

	private final Charset	charset;

	private final MediaType	mediaType;

	public GsonConverter() {
		this(new Gson());
	}

	public GsonConverter(Gson gson) {
		this(gson, Charset.forName("UTF-8"));
	}

	public GsonConverter(Gson gson, Charset charset) {
		if (gson == null) throw new NullPointerException("gson == null");
		if (charset == null) throw new NullPointerException("charset == null");
		this.gson = gson;
		this.charset = charset;
		this.mediaType = MediaType.parse("application/json; charset=" + charset.name());
	}

	@Override public Object fromBody(ResponseBody body, Type type) throws IOException {
		Charset charset = this.charset;
		if (body.contentType() != null) {
			charset = body.contentType().charset(charset);
		}

		InputStream is = body.byteStream();
		try {
			return gson.fromJson(new InputStreamReader(is, charset), type);
		} finally {
			try {
				is.close();
			} catch (IOException ignored) {
			}
		}
	}

	@Override public RequestBody toBody(Object object, Type type) {
		String json = gson.toJson(object, type);
		return RequestBody.create(mediaType, json);
	}
}
