# quotes-clj

A trivial web app written in Clojure with noir and clj-record.

## Demo

http://quotes-clj.herokuapp.com

## Dependencies

- Clojure
- Leiningen
- Postgresql

## Usage

```bash
lein deps
export DATABASE_USER=<postgres user>
export DATABASE_PASSWORD=<postgres password>
export DATABASE_SUBNAME=<//<hostname>:<port>/<database name>
lein run
```
