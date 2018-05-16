#/bin/bash
mkdir -p out
mkdir -p fatjar
javac -cp ./src/main/java ./src/main/java/fr/rivieradev/_2018/_338/*.java -d out && jar cvfm fatjar/BananaServer.jar ./src/main/resources/META-INF/MANIFEST.MF -C ./out/ . && java -jar ./fatjar/BananaServer.jar
