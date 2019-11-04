package AIO;

import DCS.WareHouse;

public class TestClient {
    public static void main(String[] args){
        AsyncTimeClient.asyncOperate("127.0.0.1",8010,new WareHouse("word","Hello"));
    }
}
