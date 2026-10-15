#!/usr/bin/env bash
set -Eeuo pipefail
cd "$(dirname "$0")"
set -a
source .env
set +a
classpath=".:mysql-connector-j-9.4.0.jar:opengauss-jdbc-6.0.3-og.jar"
javac -cp "$classpath" JdbcLab.java
java -cp "$classpath" JdbcLab | tee results.txt
