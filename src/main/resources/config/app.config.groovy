package config;

import java.util.concurrent.TimeUnit

import fr.litarvan.paladin.http.AcceptCrossOriginRequestsMiddleware
import fr.slixe.mining.http.controller.MainController

[
    sessionDuration: TimeUnit.DAYS.toMillis(2), // 2 days

    /**
     * The app controllers, call them whatever you want to
     */
    controllers: [
		main: MainController
    ],

	routeMiddlewares: 
	[
		cors: AcceptCrossOriginRequestsMiddleware
	],
	
    /**
     * Global middlewares (applied on all routes)
     */
    globalMiddlewares: [
        AcceptCrossOriginRequestsMiddleware
    ]
]