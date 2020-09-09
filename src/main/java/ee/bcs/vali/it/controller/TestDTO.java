package ee.bcs.vali.it.controller;

public class TestDTO {
    private String nickName;
    private boolean canFly;
    private int lifePack;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public int getLifePack() {
        return lifePack;
    }

    public void setLifePack(int lifePack) {
        this.lifePack = lifePack;
    }
}
