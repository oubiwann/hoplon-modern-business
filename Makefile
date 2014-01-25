watch:
	@JVM_OPTS="-Xmx1024m -server" boot watch hoplon

dev:
	@lein run

clean:
	rm -rf target/classes target/*.jar target/stale
