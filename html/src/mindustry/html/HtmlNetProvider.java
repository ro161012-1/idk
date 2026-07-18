package mindustry.html;

import arc.net.*;
import arc.struct.*;
import arc.util.*;
import mindustry.net.*;
import mindustry.gen.*;

public class HtmlNetProvider extends NetProvider {
    private WebSocketClient webSocket;
    private NetConnection connection;
    private boolean connected = false;
    
    @Override
    public void connect(String host, int port, NetConnection listener) {
        this.connection = listener;
        
        try {
            String wsUrl = "ws://" + host + ":" + port;
            if (host.startsWith("wss://") || host.startsWith("ws://")) {
                wsUrl = host + (port > 0 ? ":" + port : "");
            }
            
            Log.info("Connecting to WebSocket: @", wsUrl);
            webSocket = new WebSocketClient(wsUrl, this);
        } catch (Exception e) {
            Log.err("Failed to create WebSocket connection", e);
            if (connection != null) {
                connection.failed(e.getMessage());
            }
        }
    }
    
    @Override
    public void disconnect() {
        if (webSocket != null) {
            webSocket.close();
            webSocket = null;
        }
        connected = false;
    }
    
    @Override
    public void send(Packet packet) {
        if (webSocket != null && connected) {
            try {
                byte[] data = packet.toBytes();
                webSocket.sendBinary(data);
            } catch (Exception e) {
                Log.err("Failed to send packet", e);
            }
        }
    }
    
    @Override
    public void sendRaw(byte[] data) {
        if (webSocket != null && connected) {
            webSocket.sendBinary(data);
        }
    }
    
    @Override
    public boolean isConnected() {
        return connected && webSocket != null && webSocket.isOpen();
    }
    
    @Override
    public String getAddress() {
        return webSocket != null ? webSocket.getUrl() : "disconnected";
    }
    
    // Called from JavaScript when WebSocket opens
    public void onOpen() {
        connected = true;
        Log.info("WebSocket connected");
        if (connection != null) {
            connection.connected();
        }
    }
    
    // Called from JavaScript when WebSocket receives data
    public void onMessage(byte[] data) {
        if (connection != null) {
            connection.receive(data);
        }
    }
    
    // Called from JavaScript when WebSocket closes
    public void onClose(int code, String reason) {
        connected = false;
        Log.info("WebSocket closed: @ (@)", code, reason);
        if (connection != null) {
            connection.disconnected();
        }
    }
    
    // Called from JavaScript when WebSocket errors
    public void onError(String error) {
        Log.err("WebSocket error: @", error);
        if (connection != null) {
            connection.failed(error);
        }
    }
    
    /** Native WebSocket client implementation */
    private static class WebSocketClient {
        private final String url;
        private final HtmlNetProvider provider;
        private Object jsWebSocket; // JavaScript WebSocket object
        
        public WebSocketClient(String url, HtmlNetProvider provider) {
            this.url = url;
            this.provider = provider;
            createWebSocket();
        }
        
        private native void createWebSocket() /*-{
            var self = this;
            var ws = new $wnd.WebSocket(self.@mindustry.html.HtmlNetProvider.WebSocketClient::url);
            self.@mindustry.html.HtmlNetProvider.WebSocketClient::jsWebSocket = ws;
            
            ws.binaryType = 'arraybuffer';
            
            ws.onopen = function(event) {
                self.@mindustry.html.HtmlNetProvider.WebSocketClient::onOpen()();
            };
            
            ws.onmessage = function(event) {
                var data = event.data;
                if (data instanceof ArrayBuffer) {
                    var bytes = new Uint8Array(data);
                    self.@mindustry.html.HtmlNetProvider.WebSocketClient::onMessage([B)(bytes);
                } else if (data instanceof Blob) {
                    var reader = new FileReader();
                    reader.onload = function() {
                        var bytes = new Uint8Array(reader.result);
                        self.@mindustry.html.HtmlNetProvider.WebSocketClient::onMessage([B)(bytes);
                    };
                    reader.readAsArrayBuffer(data);
                }
            };
            
            ws.onclose = function(event) {
                self.@mindustry.html.HtmlNetProvider.WebSocketClient::onClose(II)(event.code, event.reason);
            };
            
            ws.onerror = function(event) {
                self.@mindustry.html.HtmlNetProvider.WebSocketClient::onError(Ljava/lang/String;)('WebSocket error');
            };
        }-*/;
        
        private void onOpen() {
            provider.onOpen();
        }
        
        private void onMessage(byte[] data) {
            provider.onMessage(data);
        }
        
        private void onClose(int code, String reason) {
            provider.onClose(code, reason);
        }
        
        private void onError(String error) {
            provider.onError(error);
        }
        
        public native void sendBinary(byte[] data) /*-{
            var ws = this.@mindustry.html.HtmlNetProvider.WebSocketClient::jsWebSocket;
            if (ws && ws.readyState === 1) { // OPEN
                ws.send(data);
            }
        }-*/;
        
        public native void close() /*-{
            var ws = this.@mindustry.html.HtmlNetProvider.WebSocketClient::jsWebSocket;
            if (ws) {
                ws.close();
            }
        }-*/;
        
        public native boolean isOpen() /*-{
            var ws = this.@mindustry.html.HtmlNetProvider.WebSocketClient::jsWebSocket;
            return ws && ws.readyState === 1;
        }-*/;
        
        public native String getUrl() /*-{
            var ws = this.@mindustry.html.HtmlNetProvider.WebSocketClient::jsWebSocket;
            return ws ? ws.url : '';
        }-*/;
    }
}