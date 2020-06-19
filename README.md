
# DERO Mining Stats

DERO Mining Stats is a simple website that track your solo mining stats in one convenient location.

The backend part uses the [Paladin Framework](https://github.com/paladin-framework/paladin) and is made in Java.

The frontend part uses [VueJS Framework](https://vuejs.org/) and [Vuetify](https://vuetifyjs.com) and can be found in [dero-stats](https://github.com/Slixe/dero-mining-stats/tree/master/dero-stats) folder.

![Schema](https://i.imgur.com/uyBNb12.png)

Each miner is (for the moment) recognized by IP.
Backend updates the job every 200ms by default (configurable: `job-interval`) by querying the daemon.

This way the miners get the job back from the backend and allows us to make statistics to see which miner is connected, the number of blocks found, the last query made...

# How to start
To start, you will need to build the backend and frontend separately.

## Backend
First, you must have a java version higher or equal to Java 8.
To compile the backend, do the following command:
```
gradlew fatJar
```
The executable jar file will be found in `build/libs/` folder.
To execute it, all you'll have to do is:
```
java -jar dero-mining-stats-1.0.1.jar
```

 `config.json` file will be created once the application is launched. 

### Configuration
config.json
```json
{
  "port": 8080,
  "enableSSL": false,
  "keystoreFile": "",
  "keystorePassword": "",
  "daemonURL": "https://wallet.dero.io:443",
  "walletAddress": "dERokevAZEZVJ2N7o39VH81BXBqX9ojtncnPTDMyiVbmYiTXQY93AUCLcor9xsWCKWhYy25ja89ikZWXWab9kXRB7LYfUmbQyS",
  "reserveSize": 10,
  "job-interval": 200
}
```

`port` is the port on which the application is going to listen to.
`daemonURL` is the URL of the DERO node you want to mine.
`reserveSize` is used by the DERO daemon.
`walletAddress` is the DERO address where you want to mine on.
`job-interval` is the interval in milliseconds between each request to update the job.

#### Enable SSL
There are two ways to enable SSL on DERO Mining Stats:
- You can make a reverse proxy using NGINX (or another one) for example.
- You can directly activate it in the config.json file

For the second solution, you will need to generate a Java keystore file (.jks).
To create a java keystore file from an already existing certificate, please refer [here](https://stackoverflow.com/questions/906402/how-to-import-an-existing-x-509-certificate-and-private-key-in-java-keystore-to)

For more examples: [here](https://www.javacodegeeks.com/2014/07/java-keystore-tutorial.html)

once this step is done, just configure the "config.json" file.

`enableSSL` must be set to `true` if you want to activate it.

`keystoreFile` is the path to your file. If it is in the same folder as the application, you can simply specify the file name as above.

`keystorePassword` is the password needed for the keystore file.

## Frontend
Yarn must be installed, if not, click [here](https://classic.yarnpkg.com/en/docs/install/) to find out how to install it.

First you will need to change the value of `Vue.prototype.$api` with your API address (so the IP address/domain name where your backend is running) in the `src/main.js` file.

Install dependencies:
```
yarn install
```

Then, to build the frontend:
```
yarn run build
```
The result will be available in the `dist` folder and all you have to do is move its content to a web server.

# Screenshots
Some screenshots of the current design.

### Dark Theme
![Dark Theme](https://forum.dero.io/uploads/default/original/1X/e4e7d23eae90b08fe26954ad9efed67972851717.png)

### Light Theme
![Light Theme](https://forum.dero.io/uploads/default/original/1X/ed3247f6cb45c8a651bfd8014671b6d874d50c17.png)

## Donations
Donations are greatly appreciated and I thank you for them.
### DERO:
```
dERokevAZEZVJ2N7o39VH81BXBqX9ojtncnPTDMyiVbmYiTXQY93AUCLcor9xsWCKWhYy25ja89ikZWXWab9kXRB7LYfUmbQyS
```

