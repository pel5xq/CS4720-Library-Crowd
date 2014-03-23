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

	if (pathname == "/") {
		console.log("QueryAll");
		connection.query('SELECT * FROM LibraryStudyGroups;', 
			function(error, rows, fields) {
			response.writeHead(200, {'Content-Type': 'text/plain'});
			response.write(JSON.stringify(rows));
			response.end();
		});
	}
	else if (pathname != "/favicon.ico"){
		console.log("QueryLibrary "+pathname.substring(1));
		connection.query('SELECT * FROM LibraryStudyGroups WHERE Library = '+
			connection.escape(pathname.substring(1))+';', 
			function(error, rows, fields) {
			response.writeHead(200, {'Content-Type': 'text/plain'});
			response.write(JSON.stringify(rows));
			response.end();
		});
	}
}).listen(process.env.PORT || 8080);