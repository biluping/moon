name=moon-server
imageId=$(docker images -a | grep $name | awk '{print $3}')
cid=$(docker ps -a | grep $name | awk '{print $1}')
docker rm -f "$cid"
docker rmi -f "$imageId"
docker build -t $name:1.0 .
docker run -d -p 10305:10305 --name $name $name:1.0
docker logs -f $name
