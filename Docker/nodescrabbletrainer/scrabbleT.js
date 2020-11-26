var express = require('express');
require('url');
var app = express();
var dbtype = 2; /** 1 mssql(partially implemented) 2 mysql ***/

app.use(express.static('public'));
app.get('/index.htm', function (req, res) {
   res.sendFile( __dirname + "/" + "index.htm" );
})
app.get('/', function (req, res) {
  
   if (!req.query.qr) {
   res.sendFile( __dirname + "/" + "index.htm" );
   }
else {

 if(dbtype == 1){
      
    var sql = require("mssql");

    // config for your database
    var config = {
        user: 'coldfusion',
        password: 'coldfusion',
        server: 'localhost', 
        database: 'belfran' 
    };

    // connect to your database
    sql.connect(config, function (err) {
    
        if (err) console.log(err);

        // create Request object
        var request = new sql.Request();
           
        // query to the database and get the records
        var querystring ="select TOP 10 * from words";
        if(req.query.qr =="inorder"){
          /** mssql  ***/
          if(dbtype == 1){
         querystring = "select word from words where CHARINDEX('" + req.query.data + "',word ) > 0 and Len(word)" + req.query.op + req.query.size 
           			}
           /*** mysql ***/
           else {
          querystring = "Select word from words WHERE LOCATE('" + req.query.data + "', word) >0 AND CHARACTER_LENGTH(word) " + req.query.op + req.query.size +") or die (mysql_error())";
           }
           }
        else if(req.query.qr =="startswith"){
         querystring = "select TOP 10 * from words";
        }
        else if(req.query.qr =="atleastcount"){
        querystring = "select TOP 10 * from words";
        }
          else if(req.query.qr =="exactcount"){
        querystring = "select TOP 10 * from words";
        }
        else if(req.query.qr =="countorless"){
        querystring = "select TOP 10 * from words";
        }
         else if(req.query.qr =="wildcard"){
        querystring = "select TOP 10 * from words";
        }
        
        
        
        
        request.query(querystring, function (err, recordset) {
            
            if (err) console.log(err)

            // send records as a response
            res.send(recordset);
           
            
        }); 
    });
    
    }
    /*** mysql ***/
    else {
        var mysql  = require('mysql');
        console.log('gothere')

    // config for your database
   var connection = mysql.createConnection({
        user: 'michel',
        password: 'qkSxvH96iwV79neo',
        host: 'db',
        database: 'scrabble'
	});

    // connect to your database
    connection.connect(function (err) {
    
        if (err) console.log(err);

        // create Request object
         var querystring = "select * from words limit 10";
           
        // query to the database and get the records
        if(req.query.qr =="inorder"){
          /** mssql  ***/
          if(dbtype == 1){
         querystring = "select word from words where CHARINDEX('" + req.query.data + "',word ) > 0 and Len(word)" + req.query.op + " " + req.query.size 
           			}
           /*** mysql ***/
           else {

		querystring = "Select word from words WHERE LOCATE('" + req.query.data + "', word) >0 AND CHARACTER_LENGTH(word) " + req.query.op + " " + req.query.size ;
           console.log(querystring);
	       }         
  }

        else if(req.query.qr =="startswith"){
        querystring = "Select word from words WHERE LOCATE('" + req.query.data + "', word) = 1 AND CHARACTER_LENGTH(word) " + req.query.op + " " + req.query.size ;
        }
        else if(req.query.qr =="atleastcount"){
	 querystring = "select word from words where LENGTH(word)" + req.query.op + " " + req.query.size;
	
for (i = 0; i < req.query.data.length; i++) {
counted = (req.query.data.split(req.query.data.charAt(i)).length - 1);
querystring = querystring + " AND LENGTH(word) - LENGTH(REPLACE(word,'" + req.query.data.charAt(i) + "', '')) >=" + counted;
}        
	}    
          else if(req.query.qr =="exactcount"){
        querystring = "select word from words where LENGTH(word)" + req.query.op + " " + req.query.size;
	
for (i = 0; i < req.query.data.length; i++) {
counted = (req.query.data.split(req.query.data.charAt(i)).length - 1);
querystring = querystring + " AND LENGTH(word) - LENGTH(REPLACE(word,'" + req.query.data.charAt(i) + "', '')) =" + counted;
}    
        }
        else if(req.query.qr =="countorless"){
        querystring = "select word from words where LENGTH(word)<= "  + req.query.size;
for (i = 0; i < req.query.data.length; i++) {
counted = (req.query.data.split(req.query.data.charAt(i)).length - 1);
querystring = querystring + " AND LENGTH(word) - LENGTH(REPLACE(word,'" + req.query.data.charAt(i) + "', '')) <=" + counted;
}
querystring = querystring + " AND word REGEXP '^[" + req.query.data + "]{2," + req.query.data.length + "}$'";
        }
         else if(req.query.qr =="wildcard"){
       querystring = "SELECT word FROM words WHERE word REGEXP '^" + req.query.data + "$'";
        }
        
        connection.query(querystring, function (err, recordset) {
            
            if (err) console.log(err)

            // send records as a response
            res.send(recordset);
            
        });
    });
    
    }
}
   
})

app.get('/words.xml', function (req, res) {
 res.sendFile( __dirname + "/" + "words.xml"  );
 })


app.get('/css/build/bootstrap.min.css', function (req, res) {
 res.sendFile( __dirname + "/css/bootstrap.min.css"  );
})
app.get('/js/jquery.min.js', function (req, res) {
 res.sendFile( __dirname + "/js/jquery.min.js"  );
})
app.get('/js/bootstrap.min.js', function (req, res) {
 res.sendFile( __dirname + "/js/bootstrap.min.js"  );
})
app.get('/css/w3.css', function (req, res) {
 res.sendFile( __dirname + "/css/w3.css"  );
})

app.get('/js/angular1.4.8.min.js', function (req, res) {
 res.sendFile( __dirname + "/js/angular1.4.8.min.js"  );
})
app.get('/js/xml2json.js', function (req, res) {
 res.sendFile( __dirname + "/js/xml2json.js"  );
}) 


// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);