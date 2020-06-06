# Unity

Unity is a web app, where people can help each other.


## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Technology
Backend: Java, Spring Framework(Spring Boot, Spring MVC), REST, Java Utility Libraries, Amazon S3, H2 Database.

## How to launch?

Install Docker
- Ubuntu: https://docs.docker.com/engine/install/ubuntu/
- Windows: https://docs.docker.com/docker-for-windows/
- MacOS: https://docs.docker.com/docker-for-mac/install/

Install Maven
- Ubuntu: https://linuxize.com/post/how-to-install-apache-maven-on-ubuntu-18-04/
- Windows: https://howtodoinjava.com/maven/how-to-install-maven-on-windows/
- MacOS: https://www.baeldung.com/install-maven-on-windows-linux-mac

Install git: https://git-scm.com/book/en/v2/Getting-Started-Installing-Git and then
- Run "git config core.fileMode false"
- Set correct user name and user email for git config
- git config --local user.name "name"
- git config --local user.email "email"

Clone repository from branch __"frontend"__: `git clone https://github.com/IvankivRLCoder/Unity.git -b backend`

Change dir to your project: `cd your_project_dir_name`

Build *.jar you can use the Maven command line: `mvn package`

Build the image you can use the Docker command line: `docker build -t springio/gs-spring-boot-docker .`

Run container you can use the Docker command line: `docker run springio/gs-spring-boot-docker`

Swagger API support added. Link to switch to Swagger: localhost:8080/swagger-ui.html.

Application uses in-memory database H2. It is accessible with the next link: localhost:8080/h2db.

