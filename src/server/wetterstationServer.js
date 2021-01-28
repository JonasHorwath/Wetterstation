var dataRaw;
var weatherstationData;

const SerialPort = require('serialport');
const portNameWindows = 'COM5';
const portNamePi = '/dev/ttyUSB0';
const Readline = require('@serialport/parser-readline');
const port = new SerialPort(portNameWindows, { baudRate: 9600 });
const parser = port.pipe(new Readline({ delimiter: '\n' }));// Read the port data

port.on("open", () => {
  console.log('serial port open');
});

parser.on('data', data =>{
    dataRaw = data.slice(0, data.length - 2).split(',');
    weatherstationData = '{'
       +'"brightness":"' + dataRaw[0]
       +'", "temperatureout":"' + dataRaw[1]
       +'", "temperaturein":"'  + dataRaw[2]
       +'", "pressure":"' + dataRaw[3]
       +'", "altitude":"' + dataRaw[4]
       +'", "humidity":"' + dataRaw[5]
       +'"}';
});

const express = require("express"); 
const app = express(); 
const webPort = 8000;   
    
app.listen(webPort, () => console.log("Server Start at the Port")); 
app.get('/weatherstation', alldata); 
   
function alldata(request, response) { 

    response.send(weatherstationData); 

}