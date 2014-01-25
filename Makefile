watch:
	@JVM_OPTS="-Xmx1024m -server" boot watch hoplon

dev:
	@#@boot devserver start :port 9999
	@lein run

clean:
	rm -rf target/classes target/*.jar target/stale
