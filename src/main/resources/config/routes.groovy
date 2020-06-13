package config;

group '/api', {
	get '/job'
	get '/info'
	get '/miners'
	get '/blocks'
}, [
	action: "main"
]

post '/json_rpc', 'main:jsonRpc'