# websocket-chat

People want to have a place to talk to their friends. With this application people are able to create an account, a private room and get chatting.

This application is using a PostgreSQL database hosted on Heroku and will be implementing the [Javamail](https://www.oracle.com/technetwork/java/javamail/index.html) API.


## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/heroku/java-getting-started.git
$ cd java-getting-started
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file that reads something like this:

```
JDBC_DATABASE_URL=jdbc:postgresql://localhost:5432/java_database_name
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku master
$ heroku open
```

