# slick-dev-box
Playing with slick

# docker


Option 1

start && rebuild local image:
    
    docker-compose up --build -d
      
    docker exec -i slick-dev-box_mysql-db_1 mysql -u root -p  -e 'CREATE DATABASE slick'

stop:
    docker-compose down
        
    docker-compose down -v // to drop relevan volus

Option 2.
    run up.sh / down.sh
