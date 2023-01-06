package chasingclub.server.core.Utils;

public class SlotSQL {
    private final String uuid;
    private final String first;
    private final String second;
    private final String third;
    private final String fourth;
    private final String fifth;
    private final String sixth;
    private final String seventh;
    private final String eighth;
    private final String ninth;
    private final String offhand;
    public SlotSQL(String uuid, String first, String second, String third, String fourth, String fifth, String sixth, String seventh, String eighth, String ninth, String offhand) {
        this.uuid = uuid;
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.fifth = fifth;
        this.sixth = sixth;
        this.seventh = seventh;
        this.eighth = eighth;
        this.ninth = ninth;
        this.offhand = offhand;
    }
    // UUID
    public String GetUUID() {
        return uuid;
    }
    // THINGSSSSssss
    public String Get1() {
        return first;
    }
    public String Get2() {
        return second;
    }
    public String Get3() {
        return third;
    }
    public String Get4() {
        return fourth;
    }
    public String Get5() {
        return fifth;
    }
    public String Get6() {
        return sixth;
    }
    public String Get7() {
        return seventh;
    }
    public String Get8() {
        return eighth;
    }
    public String Get9() {
        return ninth;
    }
    public String GetOffhand() {
        return offhand;
    }
}
