package fr.slixe.mining;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import fr.litarvan.paladin.OnStart;
import fr.litarvan.paladin.OnStop;
import fr.litarvan.paladin.PaladinApp;
import fr.litarvan.paladin.PaladinConfig;
import fr.slixe.mining.service.DaemonService;
import spark.Spark;

@PaladinApp(name = "DERO Mining", version = App.VERSION, author = "Slixe")
public class App
{
	public static final String VERSION = "1.0.0";

	private static final Logger log = LoggerFactory.getLogger("DERO Mining");

	private final Timer timer = new Timer();

	@Inject
	private DaemonService daemon;

	@Inject
	private PaladinConfig config;


	@OnStart
	public void start()
	{
		TimerTask task = new TimerTask() {
			
			@Override
			public void run()
			{
				daemon.updateInfo();
				daemon.updateJob();
			}
		};

		this.timer.schedule(task, 0, config.get("job-interval", long.class));	
	}

	@OnStop
	public void stop()
	{
		this.timer.cancel();
		log.info("Shutting down http service...");
		Spark.stop();
	}
}
