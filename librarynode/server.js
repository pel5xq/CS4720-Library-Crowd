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
		url_split[8] == "coursenum" &&
		url_split[10] == "name" &&
		url_split[12] == "description" &&
		url_split[14] == "start" &&
		url_split[16] == "end") {
		console.log(url_split[3]+", "+url_split[5]+", "+url_split[7]+", "+url_split[9]+
			", "+url_split[11]+", "+url_split[13]+", "+url_split[15]+", "+url_split[17]);
		connection.query('INSERT INTO `LibraryStudyGroups`(`Library`, `Section`, `Dept`, `CourseNum`, `Name`, `Descrip`, `StartTime`, `EndTime`) VALUES ('
				+connection.escape(url_split[3])+','+connection.escape(url_split[5])+','+connection.escape(url_split[7])+','+connection.escape(url_split[9])+','
				+connection.escape(decodeURI(url_split[11]))+','+connection.escape(decodeURI(url_split[13]))+',TimeStamp('+connection.escape(url_split[15])
				+'),TimeStamp('+connection.escape(url_split[17])+'))', 
			function(error, rows, fields) {
			response.writeHead(200, {'Content-Type': 'text/plain'});
			response.end();
		});
	}
}).listen(process.env.PORT || 8080)