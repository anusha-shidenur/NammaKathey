#!/bin/sh
APP_HOME=`pwd -P`
APP_NAME="Gradle"
APP_BASE_NAME=`basename "$0"`
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'
JAVA_HOME_DIRS="/usr/lib/jvm/java-17-openjdk-amd64 /usr/lib/jvm/java-11-openjdk-amd64 /usr/local/lib/jvm/java-11"
for jhome in $JAVA_HOME_DIRS; do [ -d "$jhome" ] && { JAVA_HOME="$jhome"; break; }; done
JAVA_EXE="${JAVA_HOME}/bin/java"
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar
exec "$JAVA_EXE" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
