

buildAll: deployLocal
	# build android-demo for test plugin
	cd android-demo && ./gradlew clean assembleDebug --build-cache --no-daemon --console=plain

deployLocal:
	# deploy cache-ext to local maven repository
	./gradlew clean --console=plain && ./gradlew :cache-ext:publishToMavenLocal --no-daemon --console=plain