<?php
require 'flight/Flight.php';

Flight::route('/', function(){
    echo 'hello world!';
});

Flight::route('/users/@id', function($id) {
  echo "You have logged in as $id";
});

Flight::route('/@name/@id:[0-9]{3}', function($name, $id){
    echo "Welcome $name!  Your id is $id";
});

Flight::route('/@name/', function($name){
    echo "Welcome $name!";
});

function login($id) {
  echo "You have logged in as $id";
}


Flight::start();
?>