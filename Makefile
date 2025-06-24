

buildAll: deployLocal
	# build android-demo for test plugin
	cd android-demo && ./gradlew clean assemble --build-cache --no-daemon

deployLocal:
	# deploy cache-ext to local maven repository
	./gradlew clean && ./gradlew :cache-ext:publishToMavenLocal --no-daemon