var http = require('http');
var url = require("url");
var mysql = require("mysql");
var connection = mysql.createConnection({
	host: "stardock.cs.virginia.edu",
	user: "cs4720pel5xq",
	password: "2014spring",
	database: "cs4720pel5xq"
});

http.createServer(function (request, response) {
	var pathname = url.parse(request.url).pathname;
	var url_parts = url.parse(request.url, true);
	var query = url_parts.query;
	var url_split = pathname.split("/");

	if (url_split[1] == "insert" &&
		url_split[2] == "library" &&
		url_split[4] == "section" &&
		url_split[6] == "department" &&
		url_split[8] == "courseNum" &&
		url_split[10] == "name" &&
		url_split[12] == "description" &&
		url_split[14] == "start" &&
		url_split[16] == "end") {
		console.log(url_split[3]+", "+url_split[5]+", "+url_split[7]+", "+url_split[9]+
			", "+url_split[11]+", "+url_split[13]+", "+url_split[15]+", "+url_split[17]);
		connection.query('INSERT INTO `LibraryStudyGroups`(`Library`, `Section`, `Dept`, `CourseNum`, `Name`, `Descrip`, `StartTime`, `EndTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)', 
			[url_split[3].toString(), url_split[5].toString(), url_split[7].toString(), url_split[9].toString(), url_split[11].toString(), url_split[13].toString(), 
			url_split[15].toString(), url_split[17].toString()],
			function(error, rows, fields) {
			response.writeHead(200, {'Content-Type': 'text/plain'});
			response.end();
		});
	}
	
	else if(url_split[1] == "department" && url_split[3] == "courseNum"){
	
		if(url_split[5]=="library"){
			if(url_split[7] == "section"){
				console.log("Querying" + url_split[8]);
				connection.query('SELECT Library, Section, Dept, CourseNum, Name FROM LibraryStudyGroups WHERE CourseNum = ? AND Dept = ? AND Library = ? AND Section = ? AND CURDATE() = DATE(EndTime) AND (HOUR(NOW())*60+MINUTE(NOW()))<(60*HOUR(EndTime) + MINUTE(EndTime))', 
					[url_split[4].toString(), url_split[2].toString(), url_split[6].toString(), url_split[8].toString()],
				function(error, rows, fields){
					response.writeHead(200, {'Content-Type': 'text/plain'});
					response.write(JSON.stringify(rows));
					response.end();
		
				});
			}
			else{
				console.log("Querying" + url_split[6]);
				connection.query('SELECT Library, Section, Dept, CourseNum, Name FROM LibraryStudyGroups WHERE CourseNum = ? AND Dept = ? AND Library = ? AND CURDATE() = DATE(EndTime) AND (HOUR(NOW())*60+MINUTE(NOW()))<(60*HOUR(EndTime) + MINUTE(EndTime))', 
					[url_split[4].toString(), url_split[2].toString(), url_split[6].toString()],
				function(error, rows, fields){
					response.writeHead(200, {'Content-Type': 'text/plain'});
					response.write(JSON.stringify(rows));
					response.end();
		
				});
			}
		}
		else{
		console.log("Querying" + url_split[2] + " " + url_split[4]);
		connection.query('SELECT Library, Section, Dept, CourseNum, Name FROM LibraryStudyGroups WHERE CourseNum = ? AND Dept = ? AND CURDATE() = DATE(EndTime) AND (HOUR(NOW())*60+MINUTE(NOW()))<(60*HOUR(EndTime) + MINUTE(EndTime))', [url_split[4].toString(), url_split[2].toString()],
			function(error, rows, fields){
				response.writeHead(200, {'Content-Type': 'text/plain'});
				response.write(JSON.stringify(rows));
				response.end();
		
			});
		}
	}
	else {
		response.writeHead(404, {'Content-Type': 'text/plain'});
		response.end();
	}
	
}).listen(process.env.PORT || 8080)