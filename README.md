# slowloris
Basic GUI Based SlowLoris DoS Tool

## About
This is a basic GUI(Java) based DoS Tool (SlowLoris) developed for educational and research purposes only. There's no intention to cause any harm to third-party services.

## About SlowLoris
>Slowloris is a type of denial of service attack tool invented by Robert "RSnake" Hansen which allows a single machine to take down another machine's web server with minimal bandwidth and side effects on unrelated services and ports.

>Slowloris tries to keep many connections to the target web server open and hold them open as long as possible. It accomplishes this by opening connections to the target web server and sending a partial request. Periodically, it will send subsequent HTTP headers, adding to—but never completing—the request. Affected servers will keep these connections open, filling their maximum concurrent connection pool, eventually denying additional connection attempts from clients.
https://en.wikipedia.org/wiki/Slowloris_(computer_security)

## Build (Java 8)
```shell
mvn clean package
```

## Run
```shell
java -jar slowloris.jar
```

## Parameters
* Host: The target host (must be a valid domain, ex: http://localhost, http://192.168.0.1);
* Path: The request path (ex: /index.php, /blog);
* Port: The HTTP port;
* Threads: The number of threads to open.

![GUI](http://oi68.tinypic.com/21f0jew.jpg)

## License
This code is open-source software licensed under the [MIT license](https://opensource.org/licenses/MIT) .
