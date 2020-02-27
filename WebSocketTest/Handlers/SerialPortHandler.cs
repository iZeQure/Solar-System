using System;
using System.Collections.Generic;
using System.IO.Ports;
using System.Linq;
using System.Threading.Tasks;

namespace WebSocketTest.Handlers
{
    public class SerialPortHandler
    {
        SerialPort serialPort;

        public SerialPortHandler(string comPortName, int baudRate)
        {
            serialPort = new SerialPort(comPortName, baudRate);
            serialPort.Open();
        }

        public void WriteMessage(string message)
        {
            serialPort.WriteLine(message);
        }

        public bool IsOpen()
        {
            return serialPort.IsOpen;
        }
    }
}
