package fr.slixe.mining;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.litarvan.paladin.Paladin;
import fr.litarvan.paladin.PaladinBuilder;
import fr.litarvan.paladin.PaladinConfig;
import fr.slixe.mining.http.SparkHttpServer;
import fr.slixe.mining.service.SessionManager;

public class Main
{
	private static final Logger log = LoggerFactory.getLogger("Main");

	public static void main(String[] args)
	{
		Paladin paladin = PaladinBuilder.create(App.class)
							 			.addModule(new MyModule())
										.loadCommandLineArguments(args)
										.setSessionManager(new SessionManager())
										.build();

		PaladinConfig config = paladin.getConfig();
		SparkHttpServer httpServer = new SparkHttpServer(paladin, config.get("port", int.class));

		if (config.get("enableSSL", boolean.class))
		{
			log.info("Enabling SSL...");

			String filePath = config.get("keystoreFile");
			char[] secret = config.get("keystorePassword").toCharArray();
			File file = new File(filePath);

			if (!file.exists()) {
				log.error(String.format("Keystore file at '%s' not found. Skipping SSL...", file.getAbsolutePath()));
			}
			else {
				try {
					httpServer.loadSSLCert(file, secret);
					log.info("SSL is now enabled!");
				} catch (GeneralSecurityException | IOException e) {
					log.error(e.getLocalizedMessage());
				}
			}
		}
		log.info(config.get("walletAddress"));

		if ("dERokevAZEZVJ2N7o39VH81BXBqX9ojtncnPTDMyiVbmYiTXQY93AUCLcor9xsWCKWhYy25ja89ikZWXWab9kXRB7LYfUmbQyS".contentEquals(config.get("walletAddress")))
		{
			log.warn("You're running using the default DERO wallet address!");
			log.warn("If you want to change it: edit the 'config.json' file.");
		}

		paladin.start(httpServer);
	}
}