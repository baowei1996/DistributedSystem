package AIO;

import DCS.Handler;
import DCS.WareHouse;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;
    private Handler handler;

    public ReadCompletionHandler(AsynchronousSocketChannel channel, Handler handler){
        if(this.channel == null)
            this.channel = channel;
        this.handler = handler;
    }

    /**
     * 业务处理，从ByteBuffer读取业务数据，做业务操作
     * @param result
     * @param attachment
     */
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte [] body = new byte[attachment.remaining()];
        attachment.get(body);
        try(ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(body))){
            WareHouse ob = (WareHouse) objectInputStream.readObject();
//            System.out.println(ob);
            //采用放射机制
            //交给一个统一的处理器处理
            WareHouse outHouse = handler.process(ob);
            dowrite(outHouse);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public  void dowrite(WareHouse ob){
        if(ob != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(ob);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //分配一个写缓存
            ByteBuffer write = ByteBuffer.allocate(bytes.length);
            //将返回数据写入缓存
            write.put(bytes);
            write.flip();
            //将缓存写进channel
            channel.write(write, write, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    //如果发现还有数据没写完，继续写
                    if(buffer.hasRemaining()) {
                        channel.write(buffer, buffer, this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    try {
                        //写失败，关闭channel，并释放与channel相关联的一切资源
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }
    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            //读，关闭channel，并释放与channel相关联的一切资源
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
