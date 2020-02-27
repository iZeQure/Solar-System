using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using System.Net.WebSockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using WebSocketTest.SocketManager;

namespace WebSocketTest.Handlers
{
    public abstract class SocketHandeler
    {
        public ConnectionManager Connection { get; set; }

        public SocketHandeler(ConnectionManager connection)
        {
            Connection = connection;
        }

        public virtual async Task OnConnected(WebSocket socket)
        {
            await Task.Run(() => { Connection.AddSocket(socket); });
        }

        public virtual async Task OnDisconnected(WebSocket socket)
        {
            await Connection.RemoveSocketAsyns(Connection.GetId(socket));
        }

        public async Task SendMessage(WebSocket socket,string message)
        {
            if (socket.State != WebSocketState.Open)
                return;
            await socket.SendAsync(new ArraySegment<byte>(Encoding.ASCII.GetBytes(message), 0, message.Length), WebSocketMessageType.Text, true, CancellationToken.None);
        }

        public async Task SendMessage(string id, string message)
        {
            await SendMessage(Connection.GetSocketById(id), message);
        }

        public async Task SendMessageToAll(string message)
        {
            foreach (var connection in Connection.GetAllConnections())
            {
                Console.WriteLine("check type in SocketHandeler line 48 "+connection.GetType());
                await SendMessage(connection.Value, message);
            }
        }

        public abstract Task Receive(WebSocket socket, WebSocketReceiveResult result, byte[] buffer);
    }
}
