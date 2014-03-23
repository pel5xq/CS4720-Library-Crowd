<?php

require 'flight/Flight.php';

//
Flight::route('/', function() {
    echo "<a href=\"Web_service_1_API.txt\">Library Crowd Data API</a>";
});

//Insert
Flight::route('/insert/library/@lib/section/@sec/crowd/@crowd/noise/@noise', function($lib, $sec, $crowd, $noise) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("INSERT INTO `LibraryCrowd`(`Library`, `Section`, `Crowd`, `Noise`) VALUES (?,?,?,?)")) {
          $stmt->bind_param("ssdd", $lib, $sec, $crowd, $noise);
          $stmt->execute();
          http_response_code(200);
      }
      else {
          http_response_code(500);
      }
    }
});

//Library, Section, and Weekly Pattern (today)
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

//Library, Section, and Weekly Pattern (specified)
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

//Libraryand Weekly Pattern (today)
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

//Library and Weekly Pattern (specified)
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

//Library, Section, Timespan, and Weekly Pattern (today)
Flight::route('/library/@lib/section/@sec/timespan/@minutes/day', function($lib, $sec, $minutes) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = DAYOFWEEK(NOW()) 
        and Library = ? and Section = ? and (60*ABS(HOUR(Now()) - HOUR(Time)) + ABS(MINUTE(Now()) - MINUTE(Time))) <= ? 
        GROUP BY Library, Section")) {
          $stmt->bind_param("ssd", $lib, $sec, $minutes);
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

//Library, Section, Timespan, and Weekly Pattern (specified)
Flight::route('/library/@lib/section/@sec/timespan/@minutes/day/@day', function($lib, $sec, $minutes, $day) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = ? 
        and Library = ? and Section = ? and (60*ABS(HOUR(Now()) - HOUR(Time)) + ABS(MINUTE(Now()) - MINUTE(Time))) <= ?
        GROUP BY Library, Section")) {
          $stmt->bind_param("dssd", $day, $lib, $sec, $minutes);
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

//Library Timespan, and Weekly Pattern (today)
Flight::route('/library/@lib/timespan/@minutes/day', function($lib, $minutes) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = DAYOFWEEK(NOW()) 
        and Library = ? and (60*ABS(HOUR(Now()) - HOUR(Time)) + ABS(MINUTE(Now()) - MINUTE(Time))) <= ? GROUP BY Library")) {
          $stmt->bind_param("sd", $lib, $minutes);
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

//Library Timespan, and Weekly Pattern (specified)
Flight::route('/library/@lib/timespan/@minutes/day/@day', function($lib, $minutes, $day) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DAYOFWEEK(TIMESTAMP(Time)) = ? 
        and Library = ? and (60*ABS(HOUR(Now()) - HOUR(Time)) + ABS(MINUTE(Now()) - MINUTE(Time))) <= ?
        GROUP BY Library, Section")) {
          $stmt->bind_param("dsd", $day, $lib, $minutes);
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

//Library, Section, and Timespan
Flight::route('/library/@lib/section/@sec/timespan/@minutes', function($lib, $sec, $minutes) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DATE(Time) = CURDATE() and
        Library = ? and Section = ? and (60*ABS(HOUR(Now()) - HOUR(Time)) + ABS(MINUTE(Now()) - MINUTE(Time))) <= ?
        GROUP BY Library, Section")) {
          $stmt->bind_param("ssd", $lib, $sec, $minutes);
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

//Library and Timespan
Flight::route('/library/@lib/timespan/@minutes', function($lib, $minutes) {
    $db_connection = new mysqli('stardock.cs.virginia.edu', 'cs4720pel5xq', '2014spring', 'cs4720pel5xq');
    if (mysqli_connect_errno()) {
        http_response_code(500);
    }
    else {
      $stmt = $db_connection->stmt_init();
      if($stmt->prepare("SELECT AVG(Crowd), AVG(Noise) FROM `LibraryCrowd` WHERE DATE(Time) = CURDATE() and
        Library = ? and (60*ABS(HOUR(Now()) - HOUR(Time)) + ABS(MINUTE(Now()) - MINUTE(Time))) <= ?
        GROUP BY Library, Section")) {
          $stmt->bind_param("sd", $lib, $minutes);
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
