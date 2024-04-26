# docker run -it --name ttt -v "$(pwd)":/app team02 bash
docker run -it --name ttt -v "$(pwd)":/app -p 8080:8080 team02 bash