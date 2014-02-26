<?php

require 'flight/Flight.php';

Flight::route('/', function() {
	echo 'Hello World!';
});

Flight::route('/users/@id', function($id) {
	echo 'Look up user ' . $id . '!';
});

Flight::route('/@name/', function($name) {
	echo "Your name is: " . $name;
});


Flight::start();

?>
