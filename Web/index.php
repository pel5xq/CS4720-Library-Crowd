<?php

require 'flight/Flight.php';

Flight::route('/library/@lib/section/@sec/day', function($lib, $sec) {
  	$db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
  	if (mysqli_connect_errno()) {
      	http_response_code(500);
  	}
  	else {
    	$stmt = $db_connection->stmt_init();
    	if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = DAYOFWEEK(NOW()) 
    		and Library = ? and Section = ? GROUP BY Library, Section")) {
        	$stmt->bind_param("ss", $lib, $sec);
        	$stmt->execute();
        	$stmt->bind_result($Crowd, $Noise);
        	$answer = array('crowd' => '0', 'noise' => '0');
      		while($stmt->fetch()) {
      			$answer = array('crowd' => $Crowd, 'noise' => $Noise);
      		}
      		echo json_encode($answer);
    	}
    	else {
      		http_response_code(500);
    	}
  	}
});

Flight::route('/library/@lib/section/@sec/day/@day', function($lib, $sec, $day) {
  	$db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
  	if (mysqli_connect_errno()) {
      	http_response_code(500);
  	}
  	else {
    	$stmt = $db_connection->stmt_init();
    	if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = ? 
    		and Library = ? and Section = ? GROUP BY Library, Section")) {
        	$stmt->bind_param("dss", $day, $lib, $sec);
        	$stmt->execute();
        	$stmt->bind_result($Crowd, $Noise);
        	$answer = array('crowd' => '0', 'noise' => '0');
      		while($stmt->fetch()) {
      			$answer = array('crowd' => $Crowd, 'noise' => $Noise);
      		}
      		echo json_encode($answer);
    	}
    	else {
      		http_response_code(500);
    	}
  	}
});

Flight::route('/library/@lib/day', function($lib) {
  	$db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
  	if (mysqli_connect_errno()) {
      	http_response_code(500);
  	}
  	else {
    	$stmt = $db_connection->stmt_init();
    	if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = DAYOFWEEK(NOW()) 
    		and Library = ? GROUP BY Library")) {
        	$stmt->bind_param("s", $lib);
        	$stmt->execute();
        	$stmt->bind_result($Crowd, $Noise);
        	$answer = array('crowd' => '0', 'noise' => '0');
      		while($stmt->fetch()) {
      			$answer = array('crowd' => $Crowd, 'noise' => $Noise);
      		}
      		echo json_encode($answer);
    	}
    	else {
      		http_response_code(500);
    	}
  	}
});

Flight::route('/library/@lib/day/@day', function($lib, $day) {
  	$db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
  	if (mysqli_connect_errno()) {
      	http_response_code(500);
  	}
  	else {
    	$stmt = $db_connection->stmt_init();
    	if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = ? 
    		and Library = ? GROUP BY Library, Section")) {
        	$stmt->bind_param("ds", $day, $lib);
        	$stmt->execute();
        	$stmt->bind_result($Crowd, $Noise);
        	$answer = array('crowd' => '0', 'noise' => '0');
      		while($stmt->fetch()) {
      			$answer = array('crowd' => $Crowd, 'noise' => $Noise);
      		}
      		echo json_encode($answer);
    	}
    	else {
      		http_response_code(500);
    	}
  	}
});

Flight::start();

?>
