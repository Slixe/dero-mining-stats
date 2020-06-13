package fr.slixe.mining.service;

import fr.litarvan.paladin.ISessionManager;
import fr.litarvan.paladin.Session;
import fr.litarvan.paladin.http.Request;
import fr.litarvan.paladin.http.Response;

public class SessionManager implements ISessionManager {

	@Override
	public Session get(Request request, Response response) {
		return null;
	}

	@Override
	public long getExpirationDelay() {
		return 0;
	}

	@Override
	public void setExpirationDelay(long expirationDelay) {}}
