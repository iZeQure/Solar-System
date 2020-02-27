using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO.Ports;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using WebSocketTest.Objects;
using WebSocketTest.SocketManager;

namespace WebSocketTest.Handlers
{
    public class WebSocketMessageHandler : SocketHandeler
    {
        private static SerialPortHandler serialPortHandler;

        public WebSocketMessageHandler(ConnectionManager connection) : base(connection)
        {
            SerialPortHandler = new SerialPortHandler("COM20", 4800);
        }

        private static SerialPortHandler SerialPortHandler { get => serialPortHandler; set => serialPortHandler = value; }

        public override async Task OnConnected(WebSocket socket)
        {
            await base.OnConnected(socket);
            var socketId = Connection.GetId(socket);

        }

        public override async Task Receive(WebSocket socket, WebSocketReceiveResult result, byte[] buffer)
        {
            var socketId = Connection.GetId(socket);
            var message = Encoding.UTF8.GetString(buffer, 0, result.Count);

            char commad = message.ToCharArray().ElementAt(0);
            message = message.Remove(0, 1);

            switch (commad)
            {
                // command 1 new user registration
                case '1':

                    User userObj = new User(socketId, message);
                    await SendMessageToAll($"1,{JsonConvert.SerializeObject(userObj)}");
                    break;

                // command 2 turn on planet
                case '2':
                    // to arduino
                    // 2 command contains planet number to change state
                    if (SerialPortHandler.IsOpen())
                    {
                        SerialPortHandler.WriteMessage(message);
                    }

                    Planet temp = null;
                    foreach (Planet planet in ConnectionManager.Planets)
                    {
                        if (planet.PlanetNumber == int.Parse(message))
                        {
                            planet.State = !planet.State;
                            temp = planet;
                        }
                    }

                    await SendMessageToAll($"2," + JsonConvert.SerializeObject(temp));
                    break;

                // get all planet states
                case '3':

                    await SendMessage(socket, "3," + JsonConvert.SerializeObject(ConnectionManager.Planets, Formatting.Indented));
                    foreach (Planet planet in ConnectionManager.Planets)
                    {
                        Debug.WriteLine($"Planet Number <{planet.PlanetNumber}> State<{planet.State}>");
                    }
                    break;

                default:
                    break;
            }


        }
    }
}
