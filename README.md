# neowrites: test harness for high-volume unlabeled and unlabeled node writes to neo4j graph db

## There are 3 "Loaders" available:
- UnlabeledNodeLoader - writes unlabeled nodes using bolt driver
- LabeledNodeLoader - writes labeled nodes using bolt driver
- HttpUnlabeledNodeLoader - writes unlabeled nodes via the neo4j HTTP endpoint (code in master branch uses Unirest client)

## Params:
- ```-l``` : which loader to use, default for async-http branch is ```HttpUnlabeledNodeLoader```
- ```-u``` : which endpoint to hit (as in URL), asyn-http branch default is ```http://localhost:7474/db/data/transaction/commit```
- ```-t``` : how many threads to spawn, default for async-http branch is 100
- ```-n``` : how many nodes to write per thread, default for async-http branch is 10

## To build/launch:
- clone the repo locally
- ```mvn clean install```
- ```java -jar ./target/neowrites-1.0-SNAPSHOT.jar -t 5 -n 20``` - this will launch the harness with the default URL and loader values with 5 threads x 20 nodes per thread 

### Async
- ```async-http``` branch has an async implementation of the ```HttpUnlabeledNodeLoader``` with OkHttp
- TODO is figuring out if additional asynchronicity can be applied to the bolt-based loaders
