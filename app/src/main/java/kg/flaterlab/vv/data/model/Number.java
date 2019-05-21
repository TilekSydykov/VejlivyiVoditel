package kg.flaterlab.vv.data.model;

public class Number {
    int version;
    String value;

    String region;

    String num;

    String code;

    final static int VERSION_OLD = 0;
    // FORMAT 0 ( STANDART ) = Example - B 2345 NB

    final static int VERSION_NEW = 1;
    // FORMAT 1 ( NEW ) = Example - 01 345 ABC

    public Number(String value, int version) {
        this.version = version;

        String[] arr = value.split(" ");

        region = arr[0];

        num = arr[1];

        code = arr[2];

        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public String getValue() {
        return value;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getRegion() {
        return region;
    }

    public String getNum() {
        return num;
    }

    public String getCode() {
        return code;
    }
}
