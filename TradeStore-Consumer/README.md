# TradeStore-Consumer application

cd C:\kafka\kafka_2.12-2.5.0>
.\bin\windows\zookeeper-server-start.bat config\zookeeper.properties

check zookeeper is up and running - netstat -ano | findstr 2181

after making sure that zookeper is up, open another CMD in the downloaded folder and paste the command below:
.\bin\windows\kafka-server-start.bat config\server.properties

http://localhost:8083/h2-console/

for NO SQL Database - Used Mongo DB Atlas
Cluster0
mevikas4_db_user
JYSb3ydEKOCJAKk9
mongodb+srv://mevikas4_db_user:JYSb3ydEKOCJAKk9@cluster0.ghgk1bh.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0

http://localhost:8084/order
{
"tradeId": "1",
"version": 1,
"counterPartyId": "Vikas ji",
"bookId": "B2",
"maturityDate": "2025-09-09",
"createdDate": "2025-09-06",
"expired": false
}

In case use as rest controler
http://localhost:8080/trade/1
http://localhost:8080/trade
{
    "tradeId": "1",
    "version": 1,
    "counterPartyId": "Vikas Nidhi ji",
    "bookId": "B2",
    "maturityDate": "2025-09-07",
    "createdDate": "2025-09-06",
    "expired": false
}
