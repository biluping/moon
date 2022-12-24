package org.moon.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.moon.entity.vo.MoonConfigVo;
import org.moon.factory.MoonConfigFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * io多路复用模式socket服务端
 */
@Slf4j
public class SelectSocketServer {

    private Selector getSelect() {
        try {
            Selector selector = Selector.open();

            // 创建可选择通道，必须设置成非阻塞模式
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            // 获取 ServerSocket，绑定端口
            ServerSocket socket = serverSocketChannel.socket();
            socket.bind(new InetSocketAddress(18080));

            // 向 Select 注册感兴趣的事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            return selector;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void listen() throws IOException {
        Selector select = getSelect();
        while(true){
            // 无事件发生则阻塞，有事件发生则继续往下运行
            select.select();
            Set<SelectionKey> selectionKeys = select.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    processAccept(selectionKey);
                }else if (selectionKey.isReadable()){
                    processReadable(selectionKey);
                }else {
                    // 可写事件不处理，否则当socket可写时，如果没东西好写就一直触发，想写时直接写就行，不用注册这个事件
                    System.out.println("其他事件发生");
                }
            }
        }
    }


    /**
     * 处理客户端连接事件
     */
    private void processAccept(SelectionKey key) throws IOException {
        // ServerSocketChannel 用于监听 socket 连接，连接后生成新的 socketChannel，在这个 socketChannel 中进行通信
        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = channel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    /**
     * 处理socket可读事件
     * 客户端向服务端发送信息后，这时可读事件发送，服务端需尽快处理，并响应给客户端
     */
    private void processReadable(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len = channel.read(byteBuffer);
        if (len > 0){
            byteBuffer.flip();
            String msg = StandardCharsets.UTF_8.decode(byteBuffer).toString();
            List<MoonConfigVo> list = JSON.parseObject(msg, new TypeReference<List<MoonConfigVo>>() {});

            list.forEach(vo -> {
                MoonConfigFactory.setConfig(vo.getKey(), vo.getValue());
            });
            channel.write(StandardCharsets.UTF_8.encode("ok"));
            log.info("配置文件更新成功");
            channel.close();
        }else {
            channel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new SelectSocketServer().listen();
    }

}
