package fr.slixe.mining.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import fr.litarvan.paladin.Paladin;
import fr.litarvan.paladin.http.Cookie;
import fr.litarvan.paladin.http.Header;
import fr.litarvan.paladin.http.HeaderPair;
import fr.litarvan.paladin.http.HttpMethod;
import fr.litarvan.paladin.http.Request;
import fr.litarvan.paladin.http.Response;
import fr.litarvan.paladin.http.server.PaladinHttpServer;
import spark.Spark;

public class SparkHttpServer implements PaladinHttpServer
{
	private Paladin paladin;
	private int port;

	private boolean isRunning;

	public SparkHttpServer(Paladin paladin, int port)
	{
		this.paladin = paladin;
		this.port = port;
	}

	@Override
	public void start() throws IOException
	{
		Spark.port(port);
		Spark.threadPool(15);
		Spark.staticFileLocation("public");
		
		Spark.before((request, originalResponse) -> {
			Response response = new Response();
			paladin.execute(createRequest(request, response), response);

			applyResponse(originalResponse, response);

			Spark.halt();
		});

		Spark.awaitInitialization();
		this.isRunning = true;
	}

	protected Request createRequest(spark.Request request, Response response)
	{
		String[] headerNames = request.headers().toArray(new String[0]);
		Header[] headers = new Header[headerNames.length];

		for (int i = 0; i < headerNames.length; i++) {
			headers[i] = new Header(headerNames[i], request.headers(headerNames[i]));
		}

		String[] queryParams = request.queryParams().toArray(new String[0]);

		Map<String, String> params = new HashMap<>(request.params());
		for (String queryParam : queryParams) {
			params.put(queryParam, request.queryParams(queryParam));
		}

		String[] cookieNames = request.cookies().keySet().toArray(new String[0]);
		Cookie[] cookies = new Cookie[cookieNames.length];

		for (int i = 0; i < cookieNames.length; i++) {
			cookies[i] = new Cookie(cookieNames[i], request.cookie(cookieNames[i]));
		}

		Header contentType = null;
		for (Header header : headers)
		{
			if (header.getName().equalsIgnoreCase(Header.CONTENT_TYPE))
			{
				contentType = header;
				break;
			}
		}

		if (contentType != null && Header.CONTENT_TYPE_FORM_URL_ENCODED.equals(contentType.getValue()))
		{
			extractParams(params, new String(request.bodyAsBytes(), Charset.defaultCharset()));
		}

		return new Request(paladin, request.ip(), HttpMethod.valueOf(request.requestMethod()), request.uri(), headers, request.bodyAsBytes(), params, cookies, response);
	}

	protected void extractParams(Map<String, String> params, String query)
	{
		for (String param : query.split("&"))
		{
			String[] split = param.split("=");

			try
			{
				params.put(URLDecoder.decode(split[0], Charset.defaultCharset().name()), split.length > 1 ? URLDecoder.decode(split[1], Charset.defaultCharset().name()) : "");
			}
			catch (UnsupportedEncodingException e)
			{
				// Can't happen
			}
		}
	}

	protected void applyResponse(spark.Response sparkResponse, Response response)
	{
		for (Header header : response.getHeaders()) {
			String value = "";

			if (header.getValue() != null && !header.getValue().isEmpty())
			{
				value = header.getValue();
			}
			else if (header.getPairs() != null)
			{
				for (int i1 = 0; i1 < header.getPairs().size(); i1++)
				{
					HeaderPair pair = header.getPairs().get(i1);
					try
					{
						value += pair.getName() + "=" + URLEncoder.encode(pair.getValue(), Charset.defaultCharset().name()) + (i1 + 1 < header.getPairs().size() ? "; " : "");
					}
					catch (UnsupportedEncodingException ignored)
					{
						// Can't happen
					}
				}
			}

			sparkResponse.header(header.getName(), value);
		}

		for (Cookie cookie : response.getCookies()) {
			// TODO: Cookie params ? (& Dans Paladin)
			sparkResponse.cookie(cookie.getName(), cookie.getValue());
		}

		sparkResponse.status(response.getCode());

		try {
			sparkResponse.raw().getOutputStream().write(response.getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadSSLCert(File file, char[] secret) throws GeneralSecurityException, IOException
	{
		Spark.secure(file.getPath(), new String(secret), null, null);
	}

	@Override
	public synchronized void waitFor() throws InterruptedException
	{
		while (this.isRunning) {
			this.wait();
		}
	}

	@Override
	public void shutdown()
	{
		this.isRunning = false;
		Spark.stop();
	}

	@Override
	public String getAddress()
	{
		try {
			return InetAddress.getLocalHost().getHostAddress() + ":" + port;
		} catch (UnknownHostException e) {
			return ":" + port;
		}
	}
}
