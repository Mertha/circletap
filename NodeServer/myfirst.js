var app = require('express')();
var http = require('http').createServer(app);
var io = require('socket.io')(http);

app.get('/', function(req, res){
  res.sendFile(__dirname + '/index.html');
});

io.on('connection', function(socket){

  console.log('a user connected');

  socket.on('disconnect', function(){
    console.log('user disconnected');
  });

  socket.on('new message', function(msg){ 
    console.log('message: ' + msg);
    io.emit('new message', msg); //ez mindenkinek meg maganak is elkuldi
    //socket.broadcast.emit('new message',msg); //ez mindenki masnak kuldi maganak nem
  });

});

http.listen(8888, function(){
  console.log('listening on *:8888');
});

//kell majd json