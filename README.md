# slick-dev-box
Playing with slick

# docker

start && rebuild local image:
    
    docker-compose up --build -d
      
    docker exec -i slick-dev-box_mysql-db_1 mysql -u root -e 'CREATE DATABASE slick'

stop:
    docker-compose down
        
    docker-compose down -v // to drop relevan volus

