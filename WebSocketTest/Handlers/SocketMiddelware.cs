using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.WebSockets;
using System.Threading;
using System.Threading.Tasks;

namespace WebSocketTest.Handlers
{
    public class SocketMiddelware
    {
        private readonly RequestDelegate next;
        private SocketHandeler handler { get; set; }

        public SocketMiddelware(RequestDelegate next, SocketHandeler handeler)
        {
            this.next = next;
            this.handler = handeler;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            if (!context.WebSockets.IsWebSocketRequest)
                return;

            var socket = await context.WebSockets.AcceptWebSocketAsync();
            await handler.OnConnected(socket);
            await Receive(socket, async(result, buffer) => 
            {
                if (result.MessageType == WebSocketMessageType.Text)
                {
                    await handler.Receive(socket, result, buffer);
                }
                else if (result.MessageType == WebSocketMessageType.Close)
                {
                    await handler.OnDisconnected(socket);
                }
            });

        }

        private async Task Receive(WebSocket socket, Action<WebSocketReceiveResult, byte[]> messageHandler)
        {
            var buffer = new byte[1024 * 4];
            while (socket.State == WebSocketState.Open)
            {
                var result = await socket.ReceiveAsync(new ArraySegment<byte>(buffer), CancellationToken.None);
                messageHandler(result, buffer);
            }
        }
    }
}
